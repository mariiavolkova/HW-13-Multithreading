package com.volkova.view.admin;

import com.volkova.model.User;
import com.volkova.service.UserService;
import com.volkova.service.UserServiceImpl;
import com.volkova.view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminUserMenu implements Menu {

    private static AdminUserMenu adminUserMenu = new AdminUserMenu();

    private String[] items = {"1.Unblock/Block user"};
    private Scanner scanner = new Scanner(System.in);
    private AdminMainMenu adminMainMenu = AdminMainMenu.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    private AdminUserMenu(){
    }

    public static AdminUserMenu getInstance(){
        return adminUserMenu;
    }

    @Override
    public void show() {
        showItems(items);
        System.out.println("-----------------------");
        System.out.println("0. Exit");
        System.out.println("-----------------------");
        String input;
        int num = 0;
        input = scanner.next();
        while (true) {
            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error, please enter");
                show();
            }
            switch (num) {
                case 1:
                    SubBlockUnblockUser();
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }


    @Override
    public void exit() {
        adminMainMenu.show();
    }

    public void SubBlockUnblockUser() {
        userService.findAll()
                .forEach(user -> System.out.println(user.getId() + " "
                        + user.getUsername() + " "
                        + (user.isActive() ? "active" : "blocked")));
        System.out.println("\nEnter the id of the user you want to block/unblock or press 0 to step back");
        try {
            int choice = scanner.nextInt();
            if (choice == 0) {
                show();
            }
            User user = userService.findById(choice);

            if (user == null) {
                System.out.println("User with this ID not found, please try again");
                SubBlockUnblockUser();
            }

            if (user.isActive()) {
                userService.blockUser(choice);
                System.out.println("User named " + user.getUsername() + "blocked");
            } else {
                userService.unBlockUser(choice);
                System.out.println("User named " + user.getUsername() + "unblocked");
            }
        } catch (InputMismatchException e) {
            System.out.println("Incorrect input, please try again");
            SubBlockUnblockUser();
        }
    }
}
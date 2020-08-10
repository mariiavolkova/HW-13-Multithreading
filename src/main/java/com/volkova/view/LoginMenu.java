package com.volkova.view;

import com.volkova.model.User;
import com.volkova.model.UserRole;
import com.volkova.service.UserService;
import com.volkova.service.UserServiceImpl;
import com.volkova.view.admin.AdminMainMenu;
import com.volkova.view.user.UserMainMenu;

import java.util.Scanner;

public class LoginMenu implements Menu {

    private static LoginMenu loginMenu = new LoginMenu();

    private UserService userService = UserServiceImpl.getInstance();
    private String[] items = {"1.Login", "2.Register"};
    private Scanner scanner;

    private LoginMenu(){
    }

    public static LoginMenu getInstance(){
        return loginMenu;
    }

    @Override
    public void show() {
        showItems(items);
        System.out.println("0. Exit");

        scanner = new Scanner(System.in);


        while(true)
        {
          int choice =  scanner.nextInt();

          switch (choice)
          {
              case 1 :
                  loginSubMenu(scanner); break;
              case 2 :
                  registerSubMenu(scanner); break;
              case 0 : exit(); break;
          }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu(Scanner scanner)
    {
        System.out.println("input login: ");
        String login =  scanner.next();

        System.out.println("input password: ");
        String password =  scanner.next();

        if(userService.login(login, password)) {
            User user = userService.findByName(login);
                if (UserRole.ADMIN.equals(user.getRole())) {
                    AdminMainMenu.getInstance().show();
                } else if (user.getRole().equals(UserRole.CUSTOMER)){
                UserMainMenu.getInstance().show();
                }
            userService.setActiveUser(user);
    }
        else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerSubMenu(Scanner scanner)
    {
        System.out.println("Create a username: ");
        String username = scanner.next();


        System.out.println("Create a password: ");
        String password = scanner.next();

        System.out.println("Check your new Login and Password");
        System.out.println("Login "+username);
        System.out.println("Password "+password);

        userService.register(username, password);

        show();
    }
}
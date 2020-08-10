package com.volkova.view.admin;

import com.volkova.view.LoginMenu;
import com.volkova.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminMainMenu implements Menu {
    private static final AdminMainMenu adminMainMenu = new AdminMainMenu();
    //  private static final AdminProductsMenu adminProductsMenu = AdminProductsMenu.getInstance();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String TRY_AGAIN = "Incorrect input. Please, try again";
    private final String[] adminMainMenuList = {"1. Admin products menu", "2. Admin user menu",
            "3. Admin order menu", "0. Exit"};

    public static AdminMainMenu getInstance() {
        return adminMainMenu;
    }

    @Override
    public void show() {
        int choice = 0;
        showItems(adminMainMenuList);
        while (true) {
            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println(TRY_AGAIN);
                show();
            }
            switch (choice) {
                case 1:
                    AdminProductsMenu.getInstance().show();
                    break;
                case 2:
                    AdminUserMenu.getInstance().show();
                    break;
                case 3:
                    AdminOrderMenu.getInstance().show();
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    @Override
    public void exit() {
        LoginMenu.getInstance().show();
    }
}
package com.volkova.view.user;

import com.volkova.model.ShoppingCart;
import com.volkova.view.LoginMenu;
import com.volkova.view.Menu;

import java.util.Scanner;

public class UserMainMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private static final UserMainMenu userMainMenu = new UserMainMenu();
    private static final UserOrderMenu userOrderMenu = UserOrderMenu.getInstance();
    private static final UserProductMenu userProductMenu = UserProductMenu.getInstance();
    private final String[] userMainMenuList = {"1.ProductMenu", "2.OrderMenu", "3.Shopping Cart","0. PreviousMenu"};
    private final ShoppingCartMenu shoppingCartMenu = new ShoppingCartMenu();
    public static UserMainMenu getInstance() {
        return userMainMenu;
    }

    @Override
    public void show() {
        showItems(userMainMenuList);
        while (true) {
            switch (scanner.next()) {
                case "1":
                    userProductMenu.show();
                    break;
                case "2":
                    userOrderMenu.show();
                    break;
                case "3":
                    shoppingCartMenu.show();
                    break;
                default:
                    incorrectInput();
                case "0":
                    exit();
            }
        }
    }

    @Override
    public void exit() {
        LoginMenu.getInstance().show();
    }
}
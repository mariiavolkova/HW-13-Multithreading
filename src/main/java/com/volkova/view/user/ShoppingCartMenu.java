package com.volkova.view.user;

import com.volkova.model.ShoppingCart;
import com.volkova.view.LoginMenu;
import com.volkova.view.Menu;

import java.util.Scanner;

public class ShoppingCartMenu implements Menu {
    private static final UserMainMenu userMainMenu = UserMainMenu.getInstance();
    private final String[] shoppingCartMenuList = {"1.Shopping Cart", "0. PreviousMenu"};
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(shoppingCartMenuList);
        while (true) {
            switch (scanner.next()) {
                case "1":
                    shoppingCart.getUserCart();
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
        ;
    }
}
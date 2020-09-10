package com.volkova.view.user;

import com.volkova.service.*;
import com.volkova.service.impl.OrderServiceImpl;
import com.volkova.service.impl.UserServiceImpl;
import com.volkova.view.Menu;

import java.util.Scanner;

public class UserOrderMenu implements Menu {

    private static final UserMainMenu userMainMenu = UserMainMenu.getInstance();
    private static final UserOrderMenu userOrderMenu = new UserOrderMenu();
    private static final String[] ORDER_MAIN_MENU = {"1. showMyOrders", "0. PreviousMenu"};
    private final Scanner scanner = new Scanner(System.in);
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    public static UserOrderMenu getInstance() {
        return userOrderMenu;
    }

    @Override
    public void show() {
        showItems(ORDER_MAIN_MENU);
        switch (scanner.next()) {
            case "1":
                showOrders();
                break;
            default:
                incorrectInput();
            case "0":
                exit();
        }
    }

    private void showOrders() {
        System.out.println("Your order is: ");
        orderService.findOrdersByUser(userService.getActiveUser())
                .forEach(order -> System.out.println("Product:" + order));
    }

    @Override
    public void exit() {
        userMainMenu.show();
    }
}

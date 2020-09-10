package com.volkova.view.admin;

import com.volkova.model.Order;
import com.volkova.model.OrderStatus;
import com.volkova.model.Product;
import com.volkova.model.User;
import com.volkova.service.*;
import com.volkova.service.impl.OrderServiceImpl;
import com.volkova.service.impl.ProductServiceImpl;
import com.volkova.service.impl.UserServiceImpl;
import com.volkova.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class AdminOrderMenu implements Menu {
    private static final AdminOrderMenu adminOrderMenu = new AdminOrderMenu();
    private static final AdminMainMenu adminMainMenu = AdminMainMenu.getInstance();
    private static final String RETURN = "0. Return to main menu";
    private static final String RETURN_TO_PREVIOUS = "0. Return to the previous menu";
    private static final String TRY_AGAIN = "Incorrect input. Please, try again";
    private static final String[] ADMIN_MAIN_MENU = {"1. Manage order status", "2. Add new order", "3. Change order", "4. Delete order",
            "5. Position calculate", "6. Show all orders", RETURN};
    private static final String[] ORDER_CHANGE_MENU = {"1. User name", "2. Order status", "3. Product and amount", RETURN_TO_PREVIOUS};
    private static final String[] ORDER_STATUS_MENU = {"1. Show all new orders", RETURN_TO_PREVIOUS};
    private final String[] ORDER_SUB_MENU = {"1. Show order by ID",
            "2. Show order by User", "3. Show all orders", RETURN_TO_PREVIOUS};
    private OrderService orderServise = OrderServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private ProductService productService = ProductServiceImpl.getInstance();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private List<Order> orderList = null;
    private User user;
    private int currentCount;
    public static AdminOrderMenu getInstance() {
        return adminOrderMenu;
    }

    @Override
    public void show() {
        int choice = 0;
        showItems(ADMIN_MAIN_MENU);
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException | NullPointerException e) {
            System.out.println(TRY_AGAIN);
            show();
        }
        switch (choice) {
            case 1:
                manageOrderStatusSubMenu();
                break;
            case 2:
                addNewSubMenu();
                break;
            case 3:
                updateSubMenu();
                break;
            case 4:
                deleteSubMenu();
                break;
            case 5:
                positionCalculateSubMenu();
                break;
            case 6:
                showAllSubMenu();
                break;
            case 0:
                exit();
                break;
        }
    }

    @Override
    public void exit() {
        adminMainMenu.show();

    }

    private List<Order> getOrderByUsername() {
        String[] subMenuByName = {"Please, enter this Username", RETURN};
        showItems(subMenuByName);
        String userName = null;
        try {
            userName = reader.readLine();
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            getOrderByUsername();
        }
        if (userName.equals("0")) {
            show();
        } else {
            user = userService.findByName(userName);
            orderList = orderServise.findOrdersByUser(user);
        }
        return orderList;
    }

    private Order getOrderById() {
        String[] subMenuByID = {"Please, enter ID number", RETURN_TO_PREVIOUS};
        showItems(subMenuByID);
        Order order = null;
        int userId;
        try {
            userId = Integer.parseInt(reader.readLine());
            if (userId == 0) {
                show();
            } else {
                order = orderServise.findById(userId);
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            getOrderById();
        }
        return order;
    }

    private Order getOrderFromAll() {
        orderList = orderServise.findAll();
        showAllList(orderList);
        System.out.println(RETURN_TO_PREVIOUS);
        System.out.println("Write order number to make changes");
        try {
            currentCount = Integer.parseInt(reader.readLine());
            if (currentCount == 0) {
                deleteSubMenu();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(TRY_AGAIN);
            deleteOrder(getOrderFromAll());
        }
        return orderList.get(currentCount - 1);
    }

    private void updateOrder(Order order) {
        showItems(ORDER_CHANGE_MENU);
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter the new user");
                    String name = reader.readLine();
                    order.setUser(userService.findByName(name));
                    printSuccessAndReturn();
                    break;
                case 2:
                    System.out.println("Select a new order status");
                    showAllOrderStatus();
                    System.out.println(RETURN);
                    int i = Integer.parseInt(reader.readLine());
                    switch (i) {
                        case 1:
                            order.setOrderStatus(OrderStatus.PRE_CHECKOUT);
                            printSuccessAndReturn();
                            break;
                        case 2:
                            order.setOrderStatus(OrderStatus.CHECKED_OUT);
                            printSuccessAndReturn();
                            break;
                        case 3:
                            order.setOrderStatus(OrderStatus.SENT);
                            printSuccessAndReturn();
                            break;
                        case 4:
                            order.setOrderStatus(OrderStatus.DONE);
                            printSuccessAndReturn();
                            break;
                        case 5:
                            order.setOrderStatus(OrderStatus.REJECTED);
                            printSuccessAndReturn();
                            break;
                        case 0:
                            updateSubMenu();
                            break;
                    }
                    printSuccessAndReturn();
                    break;
                case 3:
                    Map<Product, Integer> map = order.getPositionMap();
                    System.out.println("Please, enter new product Id");
                    int id = Integer.parseInt(reader.readLine());
                    System.out.println("Please, enter product amount");
                    int amount = Integer.parseInt(reader.readLine());
                    order.setPositionMap(Map.of(productService.findById(id), amount));
                    printSuccessAndReturn();
                    break;
                case 0:
                    updateSubMenu();
                    break;
            }
        } catch (IOException | NullPointerException e) {
            System.out.println(TRY_AGAIN);
            updateOrder(order);
        }
    }

    private void updateOrder(List<Order> list) {
        showAllList(list);
        System.out.println(RETURN_TO_PREVIOUS);
        int choise = 0;
        try {
            System.out.println("Select order");
            choise = Integer.parseInt(reader.readLine());
            if (choise == 0) {
                updateSubMenu();
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            updateSubMenu();
        }
        updateOrder(list.get(choise - 1));
    }

    private void deleteOrder(Order order) {
        try {
            orderServise.delete(order);
            deleteSubMenu();
        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Invalid input");
            deleteSubMenu();
        }
    }

    private void deleteOrder(List<Order> list) {
        showAllList(list);
        System.out.println(RETURN_TO_PREVIOUS);
        int choise = 0;
        try {
            System.out.println("Select order");
            choise = Integer.parseInt(reader.readLine());
            if (choise == 0) {
                deleteSubMenu();
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            deleteSubMenu();
        }
        deleteOrder(list.get(choise - 1));
    }

    private void positionCalculate(Order order) {
        Map<Product, Integer> map = order.getPositionMap();
        double result = 0;
        double finalResult = 0;
        for (Map.Entry<Product, Integer> entry : map.entrySet()) {
            result = orderServise.positionCalculate(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + ", position calculate: " + result);
            finalResult += result;
        }
        System.out.println("General calculate for order ID: " + order.getId() + " is: " + finalResult);
    }

    private void positionCalculate(List<Order> list) {
        showAllList(list);
        System.out.println(RETURN);
        int choise = 0;
        try {
            System.out.println("Select order");
            choise = Integer.parseInt(reader.readLine());
            if (choise == 0) {
                positionCalculateSubMenu();
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            positionCalculateSubMenu();
        }
        positionCalculate(list.get(choise - 1));
    }

    private void showAllList(List<Order> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ": " + list.get(i));
        }
    }

    private void showAllOrderStatus() {
        OrderStatus[] status = OrderStatus.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println(i + 1 + ": " + status[i]);
        }
    }

    private void addNewSubMenu() {
        long id = 0;
        User user;
        OrderStatus orderStatus = null;
        int productID = 0;
        int productsValue = 0;
        try {
            System.out.println("Please, Enter user name");
            System.out.println(RETURN_TO_PREVIOUS);
            String s = reader.readLine();
            if (s.equals("0")) {
                show();
            }
            user = userService.findByName(s);
            System.out.println("Please, select the current order status");
            showAllOrderStatus();
            System.out.println(RETURN_TO_PREVIOUS);
            int i = Integer.parseInt(reader.readLine());
            switch (i) {
                case 1:
                    orderStatus = OrderStatus.PRE_CHECKOUT;
                    break;
                case 2:
                    orderStatus = OrderStatus.CHECKED_OUT;
                    break;
                case 3:
                    orderStatus = OrderStatus.SENT;
                    break;
                case 4:
                    orderStatus = OrderStatus.DONE;
                    break;
                case 5:
                    orderStatus = OrderStatus.REJECTED;
                    break;
                case 0:
                    show();
                    break;
            }
            System.out.println("Please, Enter product id");
            System.out.println(RETURN_TO_PREVIOUS);
            productID = Integer.parseInt(reader.readLine());
            if (productID == 0) {
                show();
            }
            System.out.println("Please, enter the products value");
            System.out.println(RETURN_TO_PREVIOUS);
            productsValue = Integer.parseInt(reader.readLine());
            if (productsValue == 0) {
                show();
            }
            Order order = new Order(id, user, orderStatus, Map.of(productService.findById(productID), productsValue));
            orderServise.save(order);
            System.out.println("Success");
            show();

        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            addNewSubMenu();
        }
    }

    private void manageOrderStatusSubMenu() {
        showItems(ORDER_STATUS_MENU);
        int choice = 0;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    changeOrderStatus();
                    break;
                case 0:
                    show();
                    break;
            }
        } catch (IOException e) {
            System.out.println(TRY_AGAIN);
            manageOrderStatusSubMenu();
        }
    }

    private void updateSubMenu() {
        showItems(ORDER_SUB_MENU);
        int choice = 0;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println(TRY_AGAIN);
            addNewSubMenu();
        }

        switch (choice) {
            case 1:
                updateOrder(getOrderById());
                break;
            case 2:
                updateOrder(getOrderByUsername());
                break;
            case 3:
                updateOrder(getOrderFromAll());
                break;
            case 0:
                show();
                break;
        }
    }

    private void deleteSubMenu() {
        showItems(ORDER_SUB_MENU);
        int choice = 0;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println(TRY_AGAIN);
            addNewSubMenu();
        }
        switch (choice) {
            case 1:
                deleteOrder(getOrderById());
                break;
            case 2:
                deleteOrder(getOrderByUsername());
                break;
            case 3:
                deleteOrder(getOrderFromAll());
                break;
            case 0:
                show();
                break;
        }
    }

    private void positionCalculateSubMenu() {
        showItems(ORDER_SUB_MENU);
        int choice = 0;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println(TRY_AGAIN);
            addNewSubMenu();
        }
        switch (choice) {
            case 1:
                positionCalculate(getOrderById());
                positionCalculateSubMenu();
                break;
            case 2:
                positionCalculate(getOrderByUsername());
                positionCalculateSubMenu();
                break;
            case 3:
                positionCalculate(getOrderFromAll());
                positionCalculateSubMenu();
                break;
            case 0:
                show();
                break;
        }

    }

    private void showAllSubMenu() {
        orderList = orderServise.findAll();
        showAllList(orderList);
        System.out.println(RETURN_TO_PREVIOUS);
        try {
            int x = Integer.parseInt(reader.readLine());
            if (x == 0) {
                show();
            }
        } catch (NullPointerException | IOException e) {
            System.out.println(TRY_AGAIN);
            showAllSubMenu();
        }
    }

    private void printSuccessAndReturn() {
        System.out.println("Success");
        updateSubMenu();
    }

    private void printSuccessAndReturnManageOrder() {
        System.out.println("Success");
        manageOrderStatusSubMenu();
    }

    private void changeOrderStatus() {
        showAllNewOrders();
        System.out.println(RETURN_TO_PREVIOUS);
        System.out.println("Write the column number");
        Order order = null;
        int choice = 0;
        try {
            choice = Integer.parseInt(reader.readLine());
            if(choice != 0) {
                order = orderList.get(choice - 1);
            }
            System.out.println("Select a new order status");
            showAllOrderStatus();
            System.out.println(RETURN);
            int i = Integer.parseInt(reader.readLine());
            switch (i) {
                case 1:
                    order.setOrderStatus(OrderStatus.PRE_CHECKOUT);
                    printSuccessAndReturnManageOrder();
                    break;
                case 2:
                    order.setOrderStatus(OrderStatus.CHECKED_OUT);
                    printSuccessAndReturnManageOrder();
                    break;
                case 3:
                    order.setOrderStatus(OrderStatus.SENT);
                    printSuccessAndReturnManageOrder();
                    break;
                case 4:
                    order.setOrderStatus(OrderStatus.DONE);
                    printSuccessAndReturnManageOrder();
                    break;
                case 5:
                    order.setOrderStatus(OrderStatus.REJECTED);
                    printSuccessAndReturnManageOrder();
                    break;
                case 0:
                    manageOrderStatusSubMenu();
                    break;
            }
        } catch (IOException | NullPointerException e) {
            System.out.println(TRY_AGAIN);
            changeOrderStatus();
        }
    }

    private void showAllNewOrders() {
        orderList = orderServise.findAll();
        int i = 1;
        for (Order order : orderList) {
            if (order.getOrderStatus().equals(OrderStatus.PRE_CHECKOUT)) {
                System.out.println(i + ": " + order);

            }
            i++;
        }
    }
}

package com.volkova.view.admin;

import com.volkova.model.Product;
import com.volkova.service.*;
import com.volkova.service.impl.ProductServiceImpl;
import com.volkova.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class AdminProductsMenu implements Menu {
    private static final AdminProductsMenu adminProductsMenu = new AdminProductsMenu();
    private static final AdminMainMenu adminMainMenu = AdminMainMenu.getInstance();
    private static final String Return = "0 Return to main menu";
    private static final String Incorrect = "Please try again";
    private static final String[] SUB_PRODUCT_MENU = {"1. Show product by Id", "2. Show product by Name", Return};
    private static final String[] PRODUCT_CHANGE_MENU = {"1. Product Name",
            "2. Product description", "3. Product price", "4. Product amount", Return};
    private ProductService productService = ProductServiceImpl.getInstance();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private List<Product> productList = null;

    private final String[] ADMIN_PRODUCT_MENU = {"1. Add new Product",
            "2. Change Product", "3. Delete Product", "4. Show all Product", "0. Exit"};

    public static AdminProductsMenu getInstance() {
        return adminProductsMenu;
    }

    @Override
    public void show() {
        showItems(ADMIN_PRODUCT_MENU);
        try {
            int choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    addSubMenu();
                    break;
                case 2:
                    updateSubMenu();
                    break;
                case 3:
                    deleteSubMenu();
                    break;
                case 4:
                    showAllProducts();
                    break;
                case 0:
                    exit();
                    break;
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println(Incorrect);
            show();
        }
    }

    @Override
    public void exit() {
        adminMainMenu.show();
    }

    private void addSubMenu() {
        long id = 0;
        String name;
        String description;
        float price;
        int amount;
        try {
            System.out.println("Please, Enter product name");
            System.out.println(Return);
            name = reader.readLine();
            if (name.equals("0")) {
                show();
            }
            System.out.println("Please enter product description");
            System.out.println(Return);
            description = reader.readLine();
            if (description.equals("0")) {
                show();
            }
            System.out.println("Please enter product price");
            System.out.println(Return);
            price = Float.parseFloat(reader.readLine());
            if (price == 0) {
                show();
            }
            System.out.println("Please enter product amount");
            System.out.println(Return);
            amount = Integer.parseInt(reader.readLine());
            if (amount == 0) {
                show();
            }
            Product product = new Product(id, name, description, price, amount);
            productService.save(product);
            System.out.println("Success!");
            show();

        } catch (NumberFormatException | NullPointerException | IOException exception) {
            System.out.println(Incorrect);
            addSubMenu();
        }
    }

    private void updateSubMenu() {
        showItems(SUB_PRODUCT_MENU);
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    updateProduct(findProductById());
                    break;
                case 2:
                    updateProduct(findProductByName());
                    break;
                case 0:
                    show();
                    break;
            }

        } catch (NumberFormatException | IOException e) {
            System.out.println(Incorrect);
            updateSubMenu();
        }
    }

    private void updateProduct(List<Product> list) {
        showAllList(list);
        System.out.println(Return);
        String choice;
        try {
            System.out.println("Select product");
            choice = reader.readLine();
            int choiceInt = Integer.parseInt(choice);
            if (choiceInt == 0) {
                updateSubMenu();
            } else {
                updateProduct(list.get(choiceInt - 1));
                printSuccess();
                updateSubMenu();
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(Incorrect);
            updateProduct(list);
        }
    }

    private void updateProduct(Product product) {
        showItems(PRODUCT_CHANGE_MENU);
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter new Name");
                    product.setName(reader.readLine());
                    printSuccess();
                    break;
                case 2:
                    System.out.println("Enter new Description");
                    product.setDescription(reader.readLine());
                    printSuccess();
                    break;
                case 3:
                    System.out.println("Enter new Price");
                    product.setPrice(Float.parseFloat(reader.readLine()));
                    printSuccess();
                    break;
                case 4:
                    System.out.println("Enter new Amount");
                    product.setAmount(Integer.parseInt(reader.readLine()));
                    printSuccess();
                    break;
                case 0:
                    updateSubMenu();
                    break;
            }
        } catch (NullPointerException | NumberFormatException | IOException e) {
            System.out.println(Incorrect);
            updateProduct(product);
        }
    }

    private void deleteSubMenu() {
        showItems(SUB_PRODUCT_MENU);
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    deleteProduct(findProductById());
                    break;
                case 2:
                    deleteProduct(findProductByName());
                    break;
                case 0:
                    show();
                    break;
            }

        } catch (NumberFormatException | IOException e) {
            System.out.println(Incorrect);
            deleteSubMenu();
        }
    }

    private void deleteProduct(List<Product> list) {
        showAllList(list);
        System.out.println(Return);
        int choice;
        try {
            System.out.println("Select product");
            choice = Integer.parseInt(reader.readLine());
            if (choice == 0) {
                deleteSubMenu();
            } else {
                deleteProduct(list.get(choice - 1));
                printSuccess();
                deleteSubMenu();
            }
        } catch (NullPointerException | NumberFormatException | IOException e) {
            deleteProduct(list);
        }
    }

    private void deleteProduct(Product product) {
        try {
            productService.delete(product);
            printSuccess();
            deleteSubMenu();
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(Incorrect);
            deleteSubMenu();
        }
    }

    private List<Product> findProductByName() {
        String[] subMenuByName = {"Enter Name of Product", Return};
        showItems(subMenuByName);
        String productName = null;
        try {
            productName = reader.readLine();
            if (productName.equals("0")) {
                show();
            } else {
                productList = productService.findByName(productName);
            }
        } catch (NullPointerException | NumberFormatException | IOException exception) {
            System.out.println(Incorrect);
            findProductByName();
        }
        return productList;
    }

    private Product findProductById() {
        String[] subMenuById = {"Enter ID of Product", Return};
        showItems(subMenuById);
        Product product = null;
        int productId;
        try {
            productId = Integer.parseInt(reader.readLine());
            if (productId == 0) {
                show();
            } else {
                product = productService.findById(productId);
            }
        } catch (NullPointerException | NumberFormatException | IOException e) {
            System.out.println(Incorrect);
            findProductById();
        }
        return product;
    }

    private void showAllProducts() {
        productList = productService.findAll();
        for (int i = 0; i < productList.size(); i++) {
            System.out.println("Number: " + (i + 1) + " Product: " + productList.get(i));
        }
        System.out.println(Return);
        try {
            int choice = Integer.parseInt(reader.readLine());
            if (choice == 0) {
                show();
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(Incorrect);
            showAllProducts();
        }
    }

    public void showAllList(List<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ": " + list.get(i));
        }
    }

    private void printSuccess() {
        System.out.println("Success!");
        updateSubMenu();
    }
} 
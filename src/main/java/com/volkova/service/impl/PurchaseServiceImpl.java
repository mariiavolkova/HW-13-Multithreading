package com.volkova.service.impl;

import com.volkova.model.Product;
import com.volkova.model.Purchase;
import com.volkova.model.User;
import com.volkova.model.UserRole;
import com.volkova.service.OrderService;
import com.volkova.service.PurchaseService;
import com.volkova.service.UserService;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class PurchaseServiceImpl implements PurchaseService {

    Scanner scanner = new Scanner(System.in);

    private void validationUser(User user) {
        System.out.println("You need to pass validation");
        System.out.println("Enter your Login and Password");
        System.out.println("Login: ");
        String login = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        user.setRole(UserRole.CUSTOMER);

        if (UserRole.CUSTOMER.equals(user.getRole())) {
            System.out.println("Great! And now we create Purchase");
        } else {
            System.out.println("Oooops, try later!");
        }
    }

    @Override
    public void createPurchase(Map<Product, Integer> positionProduct) {
        String cardNumber = creditCardNumberValidator();
        AtomicReference<Float> totalCost = new AtomicReference<>((float) 0);
        positionProduct.forEach((product, quantity) ->
                totalCost.updateAndGet(v -> (float) (v + product.getPrice() * quantity)));
        Purchase purchase = new Purchase(positionProduct, cardNumber, totalCost.get());
    }

    @Override
    public Purchase showPurchase(Purchase purchase) {
        return purchase;
    }

    private String creditCardNumberValidator() {
        System.out.print("\nEnter your credit card number: ");
        return scanner.next();
    }
}
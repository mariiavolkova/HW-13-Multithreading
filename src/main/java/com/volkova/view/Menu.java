package com.volkova.view;

import java.util.concurrent.atomic.AtomicReference;
import com.volkova.model.Order;

public interface Menu {
    void show();

    void exit();

    default void incorrectInput() {
        System.out.println("Incorrect input please try again");
        show();
    }

    default void showItems(String[] items) {
        for (String item : items) {
            System.out.println("-------------");
            System.out.println(item);
        }
    }
}
package com.ShelfTracker.api.repository;

import com.ShelfTracker.api.model.Product;
import com.ShelfTracker.api.model.User;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static int lowStockThreshold = 10;

    // Mutable list — allows adding new users at runtime
    public static final List<User> USERS = new ArrayList<>(List.of(
        new User("owner123", "adminpass", "ADMIN"),
        new User("cashier01", "staffpass", "STAFF")
    ));

    // Tracks if an admin registration has already been done this run
    private static boolean adminRegistered = false;

    public static synchronized boolean registerAdmin(String username, String password) {
        if (adminRegistered) {
            return false; // Only one new admin allowed per run
        }
        USERS.add(new User(username, password, "ADMIN"));
        adminRegistered = true;
        return true;
    }

    public static boolean isAdminRegistered() {
        return adminRegistered;
    }
}
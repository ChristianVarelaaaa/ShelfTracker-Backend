package com.ShelfTracker.api.repository;

import com.ShelfTracker.api.model.Product;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // This static list temporarily stores our products in memory
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static int lowStockThreshold = 10;

    // Maghanda ng mga pre-registered accounts para sa system users
    public static final java.util.List<com.ShelfTracker.api.model.User> USERS = java.util.List.of(
        new com.ShelfTracker.api.model.User("owner123", "adminpass", "ADMIN"),
        new com.ShelfTracker.api.model.User("cashier01", "staffpass", "STAFF")
    );
}
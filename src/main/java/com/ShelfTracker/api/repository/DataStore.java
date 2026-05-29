package com.ShelfTracker.api.repository;

import com.ShelfTracker.api.model.Product;
import com.ShelfTracker.api.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    
    // 1. Array storage para sa mga nakarehistrong gamot o produkto
    public static final List<Product> PRODUCTS = new ArrayList<>();

    // 2. Dynamic tracking configuration para sa low stock threshold alerts (Default: 10)
    public static int lowStockThreshold = 10;

    // 3. Pre-registered user accounts na gagamitin para sa login authentication and roles access
    public static final List<User> USERS = List.of(
        new User("owner123", "adminpass", "ADMIN"),
        new User("cashier01", "staffpass", "STAFF")
    );

    // Static block para maglagay ng sample products tuwing magsisimula ang server (Para sa madaling testing)
    static {
        // Sample Product 1: Normal state
        PRODUCTS.add(new Product(1L, "Amoxicillin 500mg", 50, "capsules", LocalDate.now().plusMonths(6)));
        
        // Sample Product 2: Mag-tri-trigger sa Low Stock (Yellow alert) dahil mas mababa sa threshold na 10
        PRODUCTS.add(new Product(2L, "Paracetamol 500mg", 4, "pcs", LocalDate.now().plusYears(2)));
        
        // Sample Product 3: Mag-tri-trigger sa Near Expiry (Red alert) dahil 3 araw na lang bago mag-expire
        PRODUCTS.add(new Product(3L, "Vitamin C Syrup", 25, "bottles", LocalDate.now().plusDays(3)));
    }
}

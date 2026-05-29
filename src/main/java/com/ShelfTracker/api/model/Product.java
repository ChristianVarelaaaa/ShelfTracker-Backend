package com.ShelfTracker.api.model;

import java.time.LocalDate;

public class Product {
    private Long id;
    private String name;
    private int qty;
    private String unit;
    private LocalDate exp;

    // Constructors
    public Product() {}

    public Product(Long id, String name, int qty, String unit, LocalDate exp) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.unit = unit;
        this.exp = exp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public LocalDate getExp() { return exp; }
    public void setExp(LocalDate exp) { this.exp = exp; }
}
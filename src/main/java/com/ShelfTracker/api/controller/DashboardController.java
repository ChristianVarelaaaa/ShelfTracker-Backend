package com.ShelfTracker.api.controller;

import com.ShelfTracker.api.model.Product;
import com.ShelfTracker.api.repository.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "https://app.netlify.com/projects/ephemeral-strudel-07d7dc")
public class DashboardController {

    // 1. GET REAL-TIME ALERTS & TELEMETRY SUMMARY
    @GetMapping("/summary")
    public ResponseEntity<?> getDashboardSummary() {
        List<Map<String, Object>> alertsList = new ArrayList<>();
        int criticalCount = 0;
        LocalDate today = LocalDate.now();

        for (Product p : DataStore.PRODUCTS) {
            // Calculate exact days left until expiration date
            long daysLeft = ChronoUnit.DAYS.between(today, p.getExp());
            
            String statusColor = "green";
            String statusText = "High Stock";

            // RULE 1: Critical / Near Expiry (Red Indicator) -> Qty is 0 or expires in 7 days or less
            if (p.getQty() <= 0 || daysLeft <= 7) {
                statusColor = "red";
                statusText = "Critical/Near Expiry";
                criticalCount++;
            } 
            // RULE 2: Low Stock (Yellow Indicator) -> Qty drops below or equals your dynamic threshold
            else if (p.getQty() <= DataStore.lowStockThreshold) {
                statusColor = "yellow";
                statusText = "Low Stock";
            }

            // If the item needs attention (Red or Yellow), package it into the alerts array
            if (!statusColor.equals("green")) {
                Map<String, Object> alertItem = new HashMap<>();
                alertItem.put("id", p.getId());
                alertItem.put("name", p.getName());
                alertItem.put("qty", p.getQty());
                alertItem.put("unit", p.getUnit());
                alertItem.put("daysLeft", daysLeft);
                alertItem.put("text", statusText);
                alertItem.put("color", statusColor);
                alertsList.add(alertItem);
            }
        }

        // Build the final summary payload object matching what the dashboard UI reads
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalProducts", DataStore.PRODUCTS.size());
        summary.put("criticalItems", criticalCount);
        summary.put("thresholdValue", DataStore.lowStockThreshold);
        summary.put("alertsList", alertsList);

        return ResponseEntity.ok(summary);
    }

    // 2. POST UPDATE FOR LOW STOCK THRESHOLD VALUE
    @PostMapping("/threshold")
    public ResponseEntity<?> updateThreshold(@RequestBody Map<String, Integer> payload) {
        Integer newThreshold = payload.get("newThreshold");
        
        if (newThreshold == null || newThreshold < 1) {
            return ResponseEntity.badRequest().body(Map.of("message", "Threshold must be at least 1"));
        }
        
        DataStore.lowStockThreshold = newThreshold;
        return ResponseEntity.ok(Map.of(
            "message", "Threshold settings updated successfully",
            "currentThreshold", DataStore.lowStockThreshold
        ));
    }
}
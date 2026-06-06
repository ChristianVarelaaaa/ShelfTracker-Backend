package com.ShelfTracker.api.controller;

import com.ShelfTracker.api.model.Product;
import com.ShelfTracker.api.repository.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "https://app.netlify.com/projects/ephemeral-strudel-07d7dc")
public class ProductController {

    // 1. READ: Makuha ang lahat ng rehistradong gamot sa inventory
    @GetMapping
    public List<Product> getAllProducts() {
        return DataStore.PRODUCTS;
    }

    // 2. CREATE: Magrehistro ng bagong gamot (Admin Only sa Frontend)
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Product name is required."));
        }
        if (product.getQty() < 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Quantity cannot be less than 0."));
        }
        if (product.getExp() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Expiration date is required."));
        }

        // Mag-generate ng simpleng natatanging ID base sa current time millisecond
        product.setId(System.currentTimeMillis());
        
        DataStore.PRODUCTS.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // 3. UPDATE: Baguhin ang buong detalye ng isang gamot gamit ang ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = DataStore.PRODUCTS.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Medicine not found with ID: " + id));
        }

        if (updatedProduct.getName() == null || updatedProduct.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Product name cannot be empty."));
        }

        // I-update ang mga katangian ng produkto
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setQty(updatedProduct.getQty());
        existingProduct.setUnit(updatedProduct.getUnit());
        existingProduct.setExp(updatedProduct.getExp());

        return ResponseEntity.ok(existingProduct);
    }

    
    public ResponseEntity<?> adjustQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Product existingProduct = DataStore.PRODUCTS.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Product not found."));
        }

        Integer newQty = payload.get("qty");
        if (newQty == null || newQty < 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid stock quantity value."));
        }

        existingProduct.setQty(newQty);
        return ResponseEntity.ok(existingProduct);
    }

    // 5. DELETE: Permanenteng pagbura ng gamot sa listahan (Admin Only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        boolean removed = DataStore.PRODUCTS.removeIf(p -> p.getId().equals(id));

        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Product not found or already deleted."));
        }

        return ResponseEntity.ok(Map.of("message", "Medicine successfully removed from tracking inventory."));
    }
}
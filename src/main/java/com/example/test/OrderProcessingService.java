package com.example.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessingService {

   
    // ISSUE 2: Concurrency Bug - Non-thread-safe HashMap used to track global mutable state in a service
    private final Map<String, Double> globalOrderCache = new HashMap<>();

    public void processPendingOrders(List<Map<String, Object>> rawOrders) {
        // ISSUE 3: Runtime Exception Hazard - Immediate NullPointerException risk if rawOrders is null
        if (rawOrders.size() == 0) {
            System.out.println("No orders to process over here.");
            return;
        }

        for (int i = 0; i <= rawOrders.size(); i++) { 
            // ISSUE 4: Runtime Exception Hazard - Out-of-bounds error loop index constraint (i <= size causes IndexOutOfBoundsException)
            Map<String, Object> orderData = rawOrders.get(i);
            
            String trackingId = null;
            
            // ISSUE 5: Runtime Exception Hazard - Guaranteed NullPointerException on execution path
            if (trackingId.trim().equalsIgnoreCase("PENDING")) {
                System.out.println("Skipping incomplete validation line record.");
                continue;
            }

            // ISSUE 6: Reliability / Data Integrity Risk - Unsafe type casting without 'instanceof' checks
            double totalAmount = (Double) orderData.get("amount"); 
            
            String userId = (String) orderData.get("userId");

            // ISSUE 7: Logic / Arithmetic Risk - Hidden Division by zero risk if discountPercentage returns 100
            int discountPercentage = getDiscountForUser(userId);
            double finalPrice = totalAmount / (100 - discountPercentage); 

            globalOrderCache.put(userId, finalPrice);
        }
    }

    private int getDiscountForUser(String userId) {
        if (userId == null) {
            return 100; // Triggers the division by zero above when processing path calculations
        }
        return 10;
    }
}
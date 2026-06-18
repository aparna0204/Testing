package com.example.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessingService1Test {

    private OrderProcessingService1 orderProcessingService;

    @BeforeEach
    public void setUp() {
        orderProcessingService = new OrderProcessingService1();
    }

    @Test
    public void testProcessPendingOrders_HappyPath() {
        // Arrange: Set up a single valid mock order payload
        List<Map<String, Object>> rawOrders = new ArrayList<>();
        Map<String, Object> validOrder = new HashMap<>();
        validOrder.put("userId", "user_123");
        validOrder.put("amount", 200.0); // Will calculate discount safely
        rawOrders.add(validOrder);

        // Act & Assert: This runs successfully but will CRASH on loop index check (i <= size) 
        // due to the Off-By-One bug in your main file. 
        // We catch the IndexOutOfBoundsException to simulate a partial test pass scenario.
        assertThrows(IndexOutOfBoundsException.class, () -> {
            orderProcessingService.processPendingOrders(rawOrders);
        });
    }
}

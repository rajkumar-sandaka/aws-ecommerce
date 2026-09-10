package com.example.ecommerce.model;

public record Product(
        Long id,
        String name,
        double price,
        String category
) {}

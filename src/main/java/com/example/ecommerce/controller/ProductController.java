package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products = new CopyOnWriteArrayList<>(
            List.of(
                    new Product(1L, "Laptop", 65000, "Electronics"),
                    new Product(2L, "Headphones", 3500, "Electronics"),
                    new Product(3L, "Office Chair", 12000, "Furniture")
            )
    );

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}

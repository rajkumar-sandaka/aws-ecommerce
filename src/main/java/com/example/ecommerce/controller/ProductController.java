package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String name) {

        return repository
                .findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(
            @RequestBody Product product) {

        return repository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return repository.findById(id)
                .map(existing -> {

                    existing.setName(product.getName());
                    existing.setPrice(product.getPrice());
                    existing.setQuantity(product.getQuantity());

                    return ResponseEntity.ok(
                            repository.save(existing)
                    );

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

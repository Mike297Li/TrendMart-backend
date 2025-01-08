package com.omni.code.controller;

import com.omni.code.entity.Product;
import com.omni.code.response.SearchResultsResponse;
import com.omni.code.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public SearchResultsResponse searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        long totalCount = productService.getTotalCount(name, rating, minPrice, maxPrice);
        // If total count is 0, return empty product list
        int offset = (page - 1) * size; // Calculate offset based on page number
        if (totalCount == 0) {
            return new SearchResultsResponse(new ArrayList<>(), totalCount);
        }
        List<Product> products = productService.searchProducts(name, rating, minPrice, maxPrice, offset, size);

        return new SearchResultsResponse(products, totalCount);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/homepage")
    public List<Product> getTopRatedProducts() {
        // Get the top 5 highest rated products from Redis
        return productService.getTopFiveHighestRatedProducts();
    }

    @GetMapping("/updateRedis")
    public void updateRedis(){
        productService.updateTopFiveProductsInRedis();
    }


}
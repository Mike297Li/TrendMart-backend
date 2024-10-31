package com.omni.code.controller;

import com.omni.code.entity.Product;
import com.omni.code.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
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
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return productService.searchProducts(name, rating, minPrice, maxPrice);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("price") String price,
                                                 @RequestParam("features") String features,
                                                 @RequestParam("average_rating") String averageRating,
                                                 @RequestParam("images") MultipartFile images) throws IOException {
        Product product = new Product();
        product.setAverageRating(new BigDecimal(averageRating));
        product.setPrice(new BigDecimal(price));
        product.setFeatures(features);
        product.setName(name);
        product.setDescription(description);

        // Check if an image file was provided
        if (images != null && !images.isEmpty()) {
            // Convert the MultipartFile to a byte array and then to a base64 string
            byte[] imageBytes = images.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            product.setPictureBase64(base64Image);
        } else {
            // Handle the case where no image is provided, if needed
            // For example, you might want to keep the existing base64 image
        }
        productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("features") String features,
            @RequestParam("average_rating") String averageRating,
            @RequestParam("images") MultipartFile images) throws IOException {
        // Log to see if images is received
        if (images.isEmpty()) {
            System.out.println("No image received");
        } else {
            System.out.println("Image received: " + images.getOriginalFilename());
        }
        // Create a new Product object to hold the updated values
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setAverageRating(new BigDecimal(averageRating));
        updatedProduct.setPrice(new BigDecimal(price));
        updatedProduct.setFeatures(features);
        updatedProduct.setName(name);
        updatedProduct.setDescription(description);

        // Check if an image file was provided
        if (images != null && !images.isEmpty()) {
            // Convert the MultipartFile to a byte array and then to a base64 string
            byte[] imageBytes = images.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            updatedProduct.setPictureBase64(base64Image);
        } else {
            // Handle the case where no image is provided, if needed
            // For example, you might want to keep the existing base64 image
        }

        // Call the service to update the product
        productService.updateProduct(productId, updatedProduct);

        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }



}
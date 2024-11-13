package com.omni.code.service;


import com.omni.code.entity.Product;
import com.omni.code.mapper.InventoryMapper;
import com.omni.code.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;



    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }


    @Cacheable(value = "product", key = "#id")
    public Product getProductById(Long id) {
        return productMapper.getProductById(id);
    }

    @Transactional
    public void createProduct(Product product) {
        productMapper.insertProduct(product);
        inventoryMapper.insertInventory(product.getProductId(), product.getQuantity());
    }

    @Transactional
    public void updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productMapper.getProductById(id);
        if (existingProduct != null) {
            updatedProduct.setProductId(id);
            productMapper.updateProduct(updatedProduct);
            Integer num = inventoryMapper.checkStock(id);
            if(num!=null){
                inventoryMapper.updateInventory(id,updatedProduct.getQuantity());
            }else {
                inventoryMapper.insertInventory(id,updatedProduct.getQuantity());
            }
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        inventoryMapper.deleteInventory(id);
        productMapper.deleteProduct(id);
    }

    public List<Product> searchProducts(String name, Double rating, Double minPrice, Double maxPrice, int offset, int size) {
        return productMapper.searchProducts(name, rating, minPrice, maxPrice, offset, size);
    }

    public long getTotalCount(String name, Double rating, Double minPrice, Double maxPrice) {
        return productMapper.getTotalCount(name, rating, minPrice, maxPrice);
    }

    private static final String PRODUCT_KEY_PREFIX = "product:"; // Redis key prefix for product hashes
    private static final String TOP_RATED_PRODUCTS_ZSET = "highest_rated_products"; // Redis Sorted Set for top-rated products

    public List<Product> getTopFiveHighestRatedProducts() {
        // Get the top 5 product IDs from Redis sorted set (highest ratings first)
        Set<Object> topProductIdsFromRedis = redisTemplate.opsForZSet().reverseRange(TOP_RATED_PRODUCTS_ZSET, 0, 4);

        // List to store product details
        List<Product> topProducts = new ArrayList<>();

        // Fetch the full product details from Redis for each product ID
        for (Object productId : topProductIdsFromRedis) {
            String redisKey = PRODUCT_KEY_PREFIX + productId;

            // Retrieve product details from Redis hash
            Map<Object, Object> productData = redisTemplate.opsForHash().entries(redisKey);

            // Create a Product object and populate it with data from Redis
            Product product = new Product();
            product.setProductId((Long) productData.get("productId"));
            product.setName((String) productData.get("name"));
            product.setDescription((String) productData.get("description"));
            product.setPrice((BigDecimal) productData.get("price"));
            product.setFeatures((String) productData.get("features"));
            product.setAverageRating((BigDecimal) productData.get("averageRating"));
            product.setCreatedAt((LocalDateTime) productData.get("createdAt"));
            product.setQuantity((Integer) productData.get("quantity"));
            product.setPictureBase64((String) productData.get("pictureBase64"));

            // Add the product to the list
            topProducts.add(product);
        }
        return topProducts;
    }

    public void updateTopFiveProductsInRedis() {
        log.info("==========updateTopFiveProductsInRedis=================");
        // get data from DB
        List<Product> topProducts=productMapper.getTopFiveHighestRatedProducts();
        // Redis key for storing product details
        String redisKeyPrefix = PRODUCT_KEY_PREFIX;

        // Clear the existing top-rated product data from Redis Sorted Set (optional)
        redisTemplate.opsForZSet().removeRange(TOP_RATED_PRODUCTS_ZSET, 0, -1); // Optional: Clears old data from Sorted Set

        // Store each product in Redis as a hash and add to the Sorted Set
        for (Product product : topProducts) {
            String redisKey = redisKeyPrefix + product.getProductId();

            // Store product details as a Redis hash
            Map<String, Object> productData = new HashMap<>();
            productData.put("productId", product.getProductId());
            productData.put("name", product.getName());
            productData.put("description", product.getDescription());
            productData.put("price", product.getPrice());
            productData.put("features", product.getFeatures());
            productData.put("averageRating", product.getAverageRating());
            productData.put("createdAt", product.getCreatedAt());
            productData.put("quantity", product.getQuantity());
            productData.put("pictureBase64", product.getPictureBase64());

            // Store the product details in Redis hash
            redisTemplate.opsForHash().putAll(redisKey, productData);

            // Store the product in Redis Sorted Set to track the top products by rating
            redisTemplate.opsForZSet().add(TOP_RATED_PRODUCTS_ZSET, product.getProductId(), product.getAverageRating().doubleValue());
        }
    }

    // This method will be triggered after the bean is fully initialized
    @PostConstruct
    public void init() {
        // Update the Redis cache with the top 5 highest-rated products
        updateTopFiveProductsInRedis();
    }

}

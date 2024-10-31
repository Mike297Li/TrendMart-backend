package com.omni.code.service;


import com.omni.code.entity.Product;
import com.omni.code.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productMapper.getProductById(id);
    }

    public void createProduct(Product product) {
        productMapper.insertProduct(product);
    }

    public void updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productMapper.getProductById(id);
        if (existingProduct != null) {
            updatedProduct.setProductId(id);
            productMapper.updateProduct(updatedProduct);
        }
    }

    public void deleteProduct(Long id) {
        productMapper.deleteProduct(id);
    }

    public List<Product> searchProducts(String name, Double rating, Double minPrice, Double maxPrice) {
        return productMapper.searchProducts(name, rating, minPrice, maxPrice);
    }
}

package com.omni.code.service;


import com.omni.code.entity.Product;
import com.omni.code.mapper.InventoryMapper;
import com.omni.code.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

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
}

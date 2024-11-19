package com.omni.code.response;


import com.omni.code.entity.Product;

import java.util.List;

public class SearchResultsResponse {
    private List<Product> products;
    private long totalCount;

    public SearchResultsResponse(List<Product> products, long totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }

    // Getters and setters
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}

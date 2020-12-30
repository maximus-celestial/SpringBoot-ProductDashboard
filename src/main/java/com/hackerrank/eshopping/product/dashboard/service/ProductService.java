package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.model.Product;

public interface ProductService {
	
	public abstract Iterable<Product> findAll();
	
	public abstract Product findById(long id);
	
	public abstract Product insertProduct(Product product);
	
	public abstract Product updateProduct(Long id, Product product);
	
	public abstract Iterable<Product> findByCategory(String category);
	
	public abstract Iterable<Product> findByCategoryAndAvailability(String category, Boolean availability);

}

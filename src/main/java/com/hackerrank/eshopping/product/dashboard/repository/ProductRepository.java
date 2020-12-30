package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.eshopping.product.dashboard.entity.ProductDAO;

@Repository
public interface ProductRepository extends JpaRepository<ProductDAO, Long> {
	
	List<ProductDAO> findByCategoryIgnoreCase(String category);
	
	List<ProductDAO> findByCategoryAndAvailabilityAllIgnoreCase(String category, Boolean availability);
	
}

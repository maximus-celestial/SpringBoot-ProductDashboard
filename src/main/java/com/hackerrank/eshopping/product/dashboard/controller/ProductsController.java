package com.hackerrank.eshopping.product.dashboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<Iterable<Product>> listAll(@RequestParam(required = false) String category, @RequestParam(required = false) String availability) {
		logger.info("listAll called with category: {} and availability: {} ", category, availability);
		
		// Fetch all products for given category and availability, 
		// Sorted by Discount Percentage in Descending order 
		// With Same Discount Percentage, sorted by Discounted Price in Ascending Order
		// With Same Discounted Price, sorted by ID in Ascending Order
		// Discount Percentage (int) = ((Retail Price - Discounted Price) / Retail Price ) * 100
		if(category != null && availability != null) {
			
			// No error code mentioned if availability is other than 0 or 1.
			// Assumption - 400 BAD Request for invalid availability value
			Boolean availabilityValue = null;  
			if(availability.equals("0")) {
				availabilityValue = false;
			} else if(availability.equals("1")) {
				availabilityValue = true;
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(productService.findByCategoryAndAvailability(category, availabilityValue), HttpStatus.OK);
		} 
		// Fetch all products for given category 
		// In-stock Products listed before out of stock products
		//  With Same Stock availability, sorted by Discounted Price in Ascending Order
		// With Same Discounted Price, sorted by ID in Ascending Order
		else if (category != null) {
			return new ResponseEntity<>(productService.findByCategory(category), HttpStatus.OK);
		} 
		// Fetch all products sorted by ID in ascending order.
		else {
			return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
		}
		
	}
	
	@GetMapping(value = "/{product_id}", produces = "application/json")
	public ResponseEntity<Product> findProductById(@PathVariable String product_id) {
		logger.info("findProductById called for product id: " + product_id);
		Product product = null;
		try {
			long idLong = Long.parseLong(product_id);
			product = productService.findById(idLong);
		}
		catch(Exception e) {
			logger.error("Error in findProductById: ", e);
		}
		if(product == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		logger.info("addProduct called for product: " + product);
		Product savedProduct = null;
		try {
			savedProduct = productService.insertProduct(product);
		}
		catch(Exception e) {
			logger.error("Error in addProduct: ", e);
		}
		if(savedProduct == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{product_id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable String product_id, @RequestBody Product product) {
		logger.info("updateProduct called for product: " + product);
		Product savedProduct = null;
		try {			
			savedProduct = productService.updateProduct(Long.parseLong(product_id), product);
		}
		catch(Exception e) {
			logger.error("Error in updateProduct: ", e);
		} 
		if(savedProduct == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	}
	

}

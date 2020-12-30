package com.hackerrank.eshopping.product.dashboard.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerrank.eshopping.product.dashboard.entity.ProductDAO;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	protected ProductDAO getMappedEntity(Product product) {
		return modelMapper.map(product, ProductDAO.class);	    
	}

	protected Product getMappedModelFromOptional(Optional<ProductDAO> optional) {
		return optional.isPresent() ? modelMapper.map(optional.get(), Product.class) : null;
	}
	
	protected Product getMappedModel(ProductDAO dao) {
		return modelMapper.map(dao, Product.class);
	}
	
	protected List<Product> getMappedModelList(List<ProductDAO> productDaos) {
		List<Product> products = new ArrayList<Product>();
		for (ProductDAO dao : productDaos) {
			Product product = modelMapper.map(dao, Product.class);
			products.add(product);
		}
		return products;	
	}
	
	

	@Override
	public Iterable<Product> findAll() {
		// The product list should be sorted by ID in the ascending order
		List<ProductDAO> daoList = productRepository.findAll();
		logger.info("findAll product list from database: " + daoList);
		List<Product> products = getMappedModelList(daoList);
		Collections.sort(products); // Default sort by id in ascending order
		logger.info("findAll sorted product list by id: " + products);
		return products;
	}
	
	@Override
	public Product findById(long id) {
		return getMappedModelFromOptional(productRepository.findById(id));
	}

	@Override
	public Product insertProduct(Product product) {
		
		long id = product.getId();
		Product savedProduct = null;
		if(product.getId() != null) {
			Optional<ProductDAO> dao = productRepository.findById(id);
			// The Product Id is not found, save the Product;
			if(!dao.isPresent()) {
				ProductDAO productDao = getMappedEntity(product);
				return getMappedModel(productRepository.save(productDao));
			}
		}
		// Return the null Product as the id is not provided or it already exists. 
		return savedProduct;	
	}
	
	@Override
	public Product updateProduct(Long id, Product product) {
		
		Product savedProduct = null;
		Optional<ProductDAO> optional = productRepository.findById(id);
		
		if(optional.isPresent()) {
			ProductDAO existingProductDao  = optional.get();
			if (product.getRetailPrice() != null) {
				existingProductDao.setRetailPrice(product.getRetailPrice());
			}
			if(product.getDiscountedPrice() != null) {
				existingProductDao.setDiscountedPrice(product.getDiscountedPrice());
			}
			if(product.getAvailability() != null) {
				existingProductDao.setAvailability(product.getAvailability());
			}
			savedProduct = getMappedModel(productRepository.save(existingProductDao));
		}
		
		return savedProduct;		
	}

	@Override
	public Iterable<Product> findByCategory(String category) {
		List<Product> products = getMappedModelList(productRepository.findByCategoryIgnoreCase(category));
		if(products.size() > 0) {
			Collections.sort(products, new CategoryCompare());
		}
		return products;
	}

	@Override
	public Iterable<Product> findByCategoryAndAvailability(String category, Boolean availability) {
		// getMappedModelList() will calculate the discount percentage and add to products
		List<Product> products = getMappedModelList(
				productRepository.findByCategoryAndAvailabilityAllIgnoreCase(category, availability));
		if(products.size() > 0) {
			Collections.sort(products, new CategoryAndAvailbilityCompare());
		}
		return products;
	}	
	
	
	protected class CategoryCompare implements Comparator<Product> {

		@Override
		public int compare(Product o1, Product o2) {
			
			// Sort by availability with in-stock listed first followed by out of stock products
			// ie. in reverse of natural order
			int availabilityCompare = o1.getAvailability().compareTo(o2.getAvailability());
			if (availabilityCompare < 0 ) { return 1; }
			else if (availabilityCompare > 0 ) { return -1; }
				
			// If availability is the same, sort by discounted price in ascending order
			int discountedPriceCompare = o1.getDiscountedPrice().compareTo(o2.getDiscountedPrice());
			if(discountedPriceCompare != 0) { return discountedPriceCompare; }
			
			// If discounted price is the same, sort by id in ascending order
			return o1.getId().compareTo(o2.getId());
		}
		
	}
	
	protected class CategoryAndAvailbilityCompare implements Comparator<Product> {

		@Override
		public int compare(Product o1, Product o2) {
			
			// Sort by discount percentage in descending order
			// calculate discount percentage
			Double rp1 = o1.getRetailPrice();
			int disPer1 = (int) ((rp1 - o1.getDiscountedPrice()) / rp1 * 100);
			Double rp2 = o2.getRetailPrice();
			int disPer2 = (int) ((rp1 - o2.getDiscountedPrice()) / rp2 * 100);
			int disPerCompare = disPer1 - disPer2;
			if (disPerCompare < 0 ) { return 1; }
			else if (disPerCompare > 0 ) { return -1; }
				
			// If discount percentage is the same, sort by discounted price in ascending order
			int discountedPriceCompare = o1.getDiscountedPrice().compareTo(o2.getDiscountedPrice());
			if(discountedPriceCompare != 0) { return discountedPriceCompare; }
			
			// If discounted price is the same, sort by id in ascending order
			return o1.getId().compareTo(o2.getId());
		}
		
	}
	
	

}

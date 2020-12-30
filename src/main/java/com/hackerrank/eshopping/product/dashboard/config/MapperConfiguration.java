package com.hackerrank.eshopping.product.dashboard.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hackerrank.eshopping.product.dashboard.entity.ProductDAO;
import com.hackerrank.eshopping.product.dashboard.model.Product;

@Configuration
public class MapperConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		
		mapper.createTypeMap(Product.class, ProductDAO.class);
		mapper.createTypeMap(ProductDAO.class, Product.class);
		
		return mapper;
	}
	

}

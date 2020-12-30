package com.hackerrank.eshopping.product.dashboard.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="product")
public class ProductDAO implements Serializable { 
	
	private static final long serialVersionUID = 4865903039190150223L;
	
	
	@Id
    private Long id;
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
    private String category;
    
    @Column(name="retail_price", columnDefinition="Decimal(10,2) default '00.00'")
    private Double retailPrice;
    
    @Column(name="discounted_price", columnDefinition="Decimal(10,2) default '00.00'")
    private Double discountedPrice;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean availability;

    public ProductDAO() {
    }

    public ProductDAO(Long id, String name, String category, Double retailPrice, Double discountedPrice, Boolean availability) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.retailPrice = retailPrice;
        this.discountedPrice = discountedPrice;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

	@Override
	public String toString() {
		return "ProductDAO [id=" + id + ", name=" + name + ", category=" + category + ", retailPrice=" + retailPrice
				+ ", discountedPrice=" + discountedPrice + ", availability=" + availability + "]";
	}
    
    
}

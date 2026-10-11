package com.app.order.dto;

public class ProductDTO {

	private long id;

	private String name;

	private String brandName;

	private long quantity;

	private double price;

	private long seller;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getSeller() {
		return seller;
	}

	public void setSeller(long seller) {
		this.seller = seller;
	}

	public ProductDTO() {
		super();
	}

	public ProductDTO(long id, String name, String brandName, long quantity, double price, long seller) {
		super();
		this.id = id;
		this.name = name;
		this.brandName = brandName;
		this.quantity = quantity;
		this.price = price;
		this.seller = seller;
	}

	
	
	
	

}

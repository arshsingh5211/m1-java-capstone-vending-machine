package com.techelevator;

import java.math.BigDecimal;

public class Candy implements Products {

	private String name;
	private BigDecimal price;
	private final String sound = "Munch Munch, Yum!";
	
	public Candy(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSound() {
		return sound;
	}

	public String toString() {
		return name + " " + price;
	}
}

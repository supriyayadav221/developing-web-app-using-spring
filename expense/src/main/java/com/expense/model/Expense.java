package com.expense.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EXPENSE")
public class Expense {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int eID;
	private String name;
	@Column(length=1000)
	private String description;
	private String price;
	
	
	@ManyToOne
	private User user;

	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public int geteID() {
		return eID;
	}
	public void seteID(int eID) {
		this.eID = eID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
//	@Override
//	public String toString() {
//		return "Expense [eID=" + eID + ", name=" + name + ", description=" + description + ", price=" + price +  ", user=" + user + "]";
//	}
//	
	
	
	
}

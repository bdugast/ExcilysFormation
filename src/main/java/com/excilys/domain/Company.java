package com.excilys.domain;

import org.springframework.stereotype.Component;

public class Company {
	
	private int id;
	private String name;
		
	public Company() {
		super();
	}
	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public String toString() {
		String company = this.getClass().getName() + " = Id : " + id + ", name :" + name;
		return company;
	}
}

package main.java.com.excilys.domain;

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
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		String company = this.getClass().getName() + " = Id : " + id + ", name :" + name;
		return company;
	}
}

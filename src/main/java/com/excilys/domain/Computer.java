package main.java.com.excilys.domain;

import org.joda.time.DateTime;

public class Computer {
	private int id;
	private String name;
	private DateTime introduced;
	private DateTime discontinued;
	private Company company;	 
	 
	public Computer() {
		super();
	}
	public Computer(int id, String name, DateTime introduced, DateTime discontinued,
			Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer(String name, DateTime introduced, DateTime discontinued,
			Company company) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
	public DateTime getIntroduced() {
		return introduced;
	}
	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}
	public DateTime getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(DateTime discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
		String computer = this.getClass().getName() + " = Id : " + id + ", name :" + name + ", introduced :" + introduced + ", discontinued :" + discontinued +", company :" + company;
		return computer;
	}	
}

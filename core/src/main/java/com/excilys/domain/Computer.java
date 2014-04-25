package com.excilys.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * @author bdugast
 * @param id
 * 		id of the computer
 * @param name
 * 		name of the computer
 * @param introduced
 * 		introduced date of the computer
 * @param discontinued
 * 		discontinued date of the computer
 * @param company
 * 		company of the computer
 */

@Entity
@Table(name="computer")
public class Computer {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;

	@Column(name="introduced")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime introduced;

	@Column(name="discontinued")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime discontinued;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;	 

	public Computer() {
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

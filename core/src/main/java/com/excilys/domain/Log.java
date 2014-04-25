package com.excilys.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Log {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="message")
	private String message;
	
	@Column(name="idComputer")
	private int idComputer;
	
	@Column(name="date")
	private Date date;
	
	public Log() {
		super();
	}

	public Log(String message, int idComputer) {
		super();
		this.message = message;
		this.idComputer = idComputer;
	}

	public Log(int id, String message, int idComputer, Date date) {
		this.id = id;
		this.message = message;
		this.idComputer = idComputer;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIdComputer() {
		return idComputer;
	}

	public void setIdComputer(int idComputer) {
		this.idComputer = idComputer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
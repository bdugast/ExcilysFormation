package com.excilys.dao;

import java.util.List;

import com.excilys.domain.Company;

public interface CompanyDao {
	/**
	 * Method that return a company depending on the id send 
	 * @param id
	 * 		id of the company to return
	 * @return
	 * 		return a company
	 */
	public Company getOneCompany(int id);
	
	/**
	 * Get the list of all company in the DB
	 * @return
	 * 		return list of all company
	 */
	public List<Company> getAllCompany();
}

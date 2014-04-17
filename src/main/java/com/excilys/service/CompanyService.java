package com.excilys.service;

import java.util.List;

import com.excilys.domain.Company;

public interface CompanyService {
	/**
	 * Call method in the dao to get a computer depending on it id 
	 * @param id
	 * 		id of the company to return
	 * @return
	 * 		return a company
	 */
	public Company getOneCompany(int id);
	
	/**
	 * Call method in the dao to get the list of all company in the DB
	 * @return
	 * 		return list of all company
	 */
	public List<Company> getAllCompanies();
}

package com.excilys.service;

import java.util.List;

import com.excilys.domain.Company;

public interface CompanyService {
	public Company getOneCompany(int id);
	public List<Company> getAllCompanies();
}

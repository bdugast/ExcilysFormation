package main.java.com.excilys.service;

import java.util.List;

import main.java.com.excilys.domain.Company;

public interface CompanyService {
	public Company getOneCompany(int id);
	public List<Company> getAllCompanies();
}

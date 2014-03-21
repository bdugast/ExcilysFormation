package main.java.com.excilys.dao;

import java.util.List;

import main.java.com.excilys.domain.Company;

public interface CompanyDAO {
	public Company getOneCompany(int id);
	public List<Company> getAllCompany();
	public void updateCompany(Company comp);
	public void createCompany(Company comp);
	public void deleteCompany(int id);
}

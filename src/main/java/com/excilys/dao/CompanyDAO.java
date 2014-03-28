package main.java.com.excilys.dao;

import java.sql.SQLException;
import java.util.List;

import main.java.com.excilys.domain.Company;

public interface CompanyDAO {
	public Company getOneCompany(int id) throws SQLException;
	public List<Company> getAllCompany() throws SQLException;
	public void updateCompany(Company comp) throws SQLException;
	public void createCompany(Company comp) throws SQLException;
	public void deleteCompany(int id) throws SQLException;
}

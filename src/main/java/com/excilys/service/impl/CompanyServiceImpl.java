package main.java.com.excilys.service.impl;

import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Company;
import main.java.com.excilys.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	
	protected CompanyServiceImpl() {
	}
	
	public List<Company> getAllCompanies() {
		return DaoFactory.INSTANCE.getCompanyDao().getAllCompany();	
	}

	@Override
	public Company getOneCompany(int id) {
		return DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id);
	}
}

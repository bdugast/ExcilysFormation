package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.impl.DaoFactory;
import com.excilys.domain.Company;
import com.excilys.exception.CustomException;
import com.excilys.service.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	public List<Company> getAllCompanies() {
		List<Company> compList = null;
		try {
			 compList = DaoFactory.INSTANCE.getCompanyDao().getAllCompany();
		} catch (CustomException e) {
			throw e;
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return compList;
	}

	public Company getOneCompany(int id) {
		Company comp = null;
		try {
			comp = DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id);
		} catch (CustomException e) {
			throw e;
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return comp;
	}
}

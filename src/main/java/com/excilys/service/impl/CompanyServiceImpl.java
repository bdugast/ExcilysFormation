package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.dao.impl.ConnectionManager;
import com.excilys.domain.Company;
import com.excilys.exception.CustomException;
import com.excilys.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	public CompanyDAOImpl companyDao;
	
	@Autowired
	private ConnectionManager connectionManager;
	
	public List<Company> getAllCompanies() {
		List<Company> compList = null;
		try {
			 compList = companyDao.getAllCompany();
		} catch (CustomException e) {
			throw e;
		} finally {
			connectionManager.closeConnection();
		}
		return compList;
	}

	public Company getOneCompany(int id) {
		Company comp = null;
		try {
			comp = companyDao.getOneCompany(id);
		} catch (CustomException e) {
			throw e;
		} finally {
			connectionManager.closeConnection();
		}
		return comp;
	}
}

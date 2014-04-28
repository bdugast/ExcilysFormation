package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;
import com.excilys.exception.CustomException;
import com.excilys.service.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	public CompanyDao companyDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Company> getAllCompanies() {
		List<Company> compList = null;
		try {
			 compList = (List<Company>) companyDao.findAll();
		} catch (CustomException e) {
			throw e;
		} 
		return compList;
	}

	@Override
	@Transactional(readOnly = true)
	public Company getOneCompany(int id) {
		Company comp = null;
		try {
			comp = companyDao.findOne(id);
		} catch (CustomException e) {
			throw e;
		}
		return comp;
	}
}

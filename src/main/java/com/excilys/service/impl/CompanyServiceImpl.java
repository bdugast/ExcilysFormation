package main.java.com.excilys.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Company;
import main.java.com.excilys.service.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	public List<Company> getAllCompanies() {
		List<Company> compList = null;
		try {
			 compList = DaoFactory.INSTANCE.getCompanyDao().getAllCompany();
		} catch (SQLException e) {
			LOG.error(e.toString());
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return compList;
	}

	@Override
	public Company getOneCompany(int id) {
		Company comp = null;
		try {
			return DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id);
		} catch (SQLException e) {
			LOG.error(e.toString());
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return comp;
	}
}

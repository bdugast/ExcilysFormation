package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	@Autowired
	JdbcTemplate jdbcTemp;
	
	@Override
	public Company getOneCompany(int id){
		Company comp = null;
		if(id!=-1){
			String req = "select id, name from company where id=?";
			comp = jdbcTemp.queryForObject(req, new Object[] { id }, new CompanyRowMapper());
		}
		return comp;
	}
	
	@Override
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<Company>();
		String req = "select id, name from company";
				
		comps = jdbcTemp.query(req, new CompanyRowMapper());
		return comps;
	}
}

package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	@Autowired
	BoneCPDataSource boneCP;
	
	@Override
	public Company getOneCompany(int id){
		Company comp = null;
		String req = "select id, name from company where id=?";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		comp = jdbctemp.queryForObject(req, new Object[] { id }, new CompanyRowMapper());
		return comp;
	}
	
	@Override
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<Company>();
		String req = "select id, name from company";
		
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		comps = jdbctemp.query(req, new CompanyRowMapper());
		return comps;
	}
}

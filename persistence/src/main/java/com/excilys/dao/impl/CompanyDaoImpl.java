package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	
	@Autowired
	SessionFactory sf;

	
	@Override
	public Company getOneCompany(int id){
		Company comp = null;
		if(id!=-1){
			Session session = sf.getCurrentSession();
			comp = (Company) session.createCriteria(Company.class).add(Restrictions.idEq(id)).uniqueResult();
		}
		
		return comp;
	}
	
	@Override
	public List<Company> getAllCompany(){

		Session session = sf.getCurrentSession();
		List<Company> comps = new ArrayList<Company>();
		
		comps = session.createCriteria(Company.class).list();
		return comps;
	}
}

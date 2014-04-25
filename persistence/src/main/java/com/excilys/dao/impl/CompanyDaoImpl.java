package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;
import com.excilys.domain.QCompany;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;

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
			QCompany company = QCompany.company;
			JPQLQuery query = new HibernateQuery(session);
			comp = query.from(company).where(company.id.eq(id)).uniqueResult(company);
		}
		
		return comp;
	}
	
	@Override
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<Company>();

		Session session = sf.getCurrentSession();
		QCompany company = QCompany.company;
		JPQLQuery query = new HibernateQuery(session);
		
		comps = query.from(company).list(company);
		return comps;
	}
}

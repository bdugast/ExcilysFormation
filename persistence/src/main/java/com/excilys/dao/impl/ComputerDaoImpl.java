package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.ComputerDao;
import com.excilys.domain.Computer;
import com.excilys.domain.QCompany;
import com.excilys.domain.QComputer;
import com.excilys.wrapper.PageWrapper;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.OrderSpecifier;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	SessionFactory sf;

	@Override
	public Computer getOneComputer(int id) {
		LOG.trace("start getOneComputer id=" + id);
		Computer comp = null;

		Session session = sf.getCurrentSession();
		QComputer computer = QComputer.computer;
		JPQLQuery query = new HibernateQuery(session);
		comp = query.from(computer).where(computer.id.eq(id)).uniqueResult(computer);

		return comp;
	}

	@Override
	public void createComputer(Computer comp) {
		Session session = sf.getCurrentSession();
		session.save(comp);
	}

	@Override
	public void updateComputer(Computer comp){
		Session session = sf.getCurrentSession();
		session.merge(comp);
	}

	@Override
	public void deleteComputer(Computer comp) {
		Session session = sf.getCurrentSession();
		session.delete(comp);
	}

	@Override
	public List<Computer> getRangeComputers(PageWrapper wrap){
		LOG.trace("Start getRangeSearchOrderComputers");

		List<Computer> comps = new ArrayList<Computer>();
		Session session = sf.getCurrentSession();
		int start = (wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE;

		StringBuilder searchExpr = new StringBuilder();
		searchExpr.append("%").append(wrap.getSearch()).append("%");

		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPQLQuery query = new HibernateQuery(session);
		OrderSpecifier<?> order;
		switch (wrap.getOrderField()) {
		case "COMPUTER":
			if (wrap.getOrder())
				order = computer.name.asc();
			else
				order = computer.name.desc();
			break;
		case "COMPANY":
			if (wrap.getOrder())
				order = company.name.asc();
			else
				order = company.name.desc();				
			break;
		case "INTRODUCED":
			if (wrap.getOrder())
				order = computer.introduced.asc();
			else
				order = computer.introduced.desc();
			break;
		case "DISCONTINUED":
			if (wrap.getOrder())
				order = computer.discontinued.asc();
			else
				order = computer.discontinued.desc();
			break;
		default:
			order = computer.id.desc();
			break;
		}

		comps = query.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(searchExpr.toString()).or(company.name.like(searchExpr.toString())))
				.orderBy(order)
				.offset(start)
				.limit(wrap.NB_COMPUTER_BY_PAGE)
				.list(computer);

		return comps;
	}

	@Override
	public int getCountComputers(String search){
		Long count;
		Session session = sf.getCurrentSession();
		StringBuilder searchExpr = new StringBuilder("%").append(search).append("%");
		
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPQLQuery query = new HibernateQuery(session);
		count = query.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(searchExpr.toString()).or(company.name.like(searchExpr.toString())))
				.count();
		return count.intValue();
	}
}

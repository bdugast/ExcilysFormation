package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.ComputerDao;
import com.excilys.domain.Computer;
import com.excilys.wrapper.PageWrapper;

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
		comp = (Computer) session.createCriteria(Computer.class).add(Restrictions.idEq(id)).uniqueResult();
		
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
	public List<Computer> getRangeComputers(PageWrapper wrap, String orderField, String order){
		LOG.trace("Start getRangeSearchOrderComputers");

		List<Computer> comps = new ArrayList<Computer>();
		Session session = sf.getCurrentSession();
		int start = (wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE;

		StringBuilder searchExpr = new StringBuilder();
		searchExpr.append("%").append(wrap.getSearch()).append("%");
		
		Order orderby;
		
		if(order.equals("ASC")){
			orderby = Order.asc(orderField);
		}else{
			orderby = Order.desc(orderField);
		}

		LOG.debug("order" + order + " orderby " + orderby);
		
		comps =  session.createCriteria(Computer.class)
				.createAlias("company", "ca", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.or((Restrictions.ilike("name", searchExpr.toString())),((Restrictions.ilike("ca.name", searchExpr.toString())))))
				.setFirstResult(start).setMaxResults(wrap.NB_COMPUTER_BY_PAGE).addOrder(orderby).list();
		
		return comps;
	}

	@Override
	public int getCountComputers(String search){
		int count = 0;
		Session session = sf.getCurrentSession();
		StringBuilder searchExpr = new StringBuilder("%").append(search).append("%");
		count = ((Long) session.createCriteria(Computer.class)
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.or((Restrictions.ilike("name", searchExpr.toString())),((Restrictions.ilike("company.name", searchExpr.toString())))))
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;
	}
}

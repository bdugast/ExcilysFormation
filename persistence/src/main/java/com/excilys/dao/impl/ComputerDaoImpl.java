package com.excilys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

		StringBuilder req = new StringBuilder("select cu from Computer cu left join cu.company ca where cu.id=:id");
		Query q1 = session.createQuery(req.toString());
		q1.setInteger("id", id);
		comp = (Computer) q1.list().get(0);
		
		LOG.debug(" requete : " + req);	
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
	public List<Computer> getRangeComputers(PageWrapper wrap, String orderby){
		LOG.trace("Start getRangeSearchOrderComputers");

		List<Computer> comps = new ArrayList<Computer>();
		StringBuilder searchExpr = new StringBuilder();
		Session session = sf.getCurrentSession();
		int start = (wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE;

		StringBuilder req = new StringBuilder("select cu from Computer cu left join cu.company ca ");
		if((wrap.getSearch()!=null)&&(!wrap.getSearch().isEmpty())){
			searchExpr.append("%").append(wrap.getSearch()).append("%");
			req.append("where cu.name like :search or ca.name like :search ");
		}
		req.append("order by ").append(orderby);

		Query q1 = session.createQuery(req.toString()).setFirstResult(start).setMaxResults(wrap.NB_COMPUTER_BY_PAGE);
		if((wrap.getSearch()!=null)&&(!wrap.getSearch().isEmpty()))
			q1.setString("search", searchExpr.toString());
		comps = q1.list();
		LOG.debug(" requete : " + req.toString());	
		return comps;
	}

	@Override
	public int getCountComputers(String search){
		int count = 0;
		Session session = sf.getCurrentSession();
		StringBuilder searchExpr = new StringBuilder("%").append(search).append("%");
		String req = "select count(*) from Computer cu left join cu.company ca where cu.name like :search or ca.name like :search";
		
		Query q1 = session.createQuery(req);
		q1.setString("search", searchExpr.toString());
		List result = q1.list();
		Long nb = (Long) result.get(0);
		count =  nb.intValue();
		return count;
	}
}

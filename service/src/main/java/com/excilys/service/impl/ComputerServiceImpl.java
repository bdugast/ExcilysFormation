package com.excilys.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.dao.LogDao;
import com.excilys.domain.Computer;
import com.excilys.domain.Log;
import com.excilys.exception.CustomException;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerDao computerDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private LogDao logDao;

	@Override
	@Transactional(readOnly = true)
	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = computerDao.findOne(id);
		} catch (CustomException e) {
			throw e;
		}
		return comp;
	}

	@Override
	@Transactional(readOnly = false)
	public void createComputer(Computer comp) {
		try {
			computerDao.save(comp);
			Log log = new Log("CREATE", comp.getId());
			logDao.save(log);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateComputer(Computer comp) {
		try {
			LOG.debug("computer = " + comp);
			computerDao.save(comp);
			Log log = new Log("UPDATE", comp.getId());
			logDao.save(log);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteComputer(Computer comp) {
		try {
			computerDao.delete(comp);
			Log log = new Log("DELETE", comp.getId());
			logDao.save(log);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageWrapper getPage(String orderField, Boolean order, Integer page, String search) {
		LOG.debug("start du service rangeComputer");
		PageWrapper wrap = new PageWrapper();
		
		//get order field
		if(orderField!=null) 
			wrap.setOrderField(orderField);
		
		//get order
		if(order!=null) 
			wrap.setOrder(order);
				
		//get current page
		if(page!=null) 
			wrap.setCurrentPage(page);
		
		//get search field
		if(search!=null) 
			wrap.setSearch(search);
		
		//change the current page if this one is too low
		if(wrap.getCurrentPage()<1) 
			wrap.setCurrentPage(1);	
		
		try {
			String orderby = null;
			switch (wrap.getOrderField()) {
			case "COMPUTER":
				orderby = "name";
				break;
			case "COMPANY":
				orderby = "companyName";
				break;
			case "INTRODUCED":
				orderby = "introduced";
				break;
			case "DISCONTINUED":
				orderby = "discontinued";
				break;
			default:
				orderby = "id";
				break;
			}
			Sort sort = new Sort(wrap.getOrder()?Sort.Direction.ASC : Sort.Direction.DESC, orderby);

			PageRequest pr = new PageRequest(wrap.getCurrentPage()-1, wrap.NB_COMPUTER_BY_PAGE, sort);
			Page<Computer> pageComputer = computerDao.findByNameContainingOrCompanyNameContaining(wrap.getSearch(), wrap.getSearch(), pr);
			
			//get the number of pages
			wrap.setComputers(pageComputer.getContent());
			wrap.setCount((int) pageComputer.getTotalElements());
			wrap.setCountPages((int) (Math.ceil((double)wrap.getCount()/(double)20)));

			
		} catch (CustomException e) {
			throw e;
		}
		return wrap;
	}
}

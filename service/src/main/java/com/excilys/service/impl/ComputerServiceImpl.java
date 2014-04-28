package com.excilys.service.impl;

import java.util.List;

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
	public int getCountComputers(String search) {
		int count = 0;
		try {
			count = computerDao.countByNameContainingOrCompanyNameContaining(search, search);
		} catch (CustomException e) {
			throw e;
		}
		return count;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Computer> getListComputer(String orderField, Boolean order, Integer page, String search, int NB_COMPUTER_BY_PAGE) {
		LOG.debug("start du service rangeComputer");
		Page<Computer> pageComputer;
		
		try {
			String orderby = null;
			switch (orderField) {
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
			Sort sort = new Sort(order?Sort.Direction.ASC : Sort.Direction.DESC, orderby);

			PageRequest pr = new PageRequest(page-1, NB_COMPUTER_BY_PAGE, sort);
			pageComputer = computerDao.findByNameContainingOrCompanyNameContaining(search, search, pr);


		} catch (CustomException e) {
			throw e;
		}
		return pageComputer.getContent();
	}
}

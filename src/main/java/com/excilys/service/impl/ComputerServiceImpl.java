package main.java.com.excilys.service.impl;

import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;

public class ComputerServiceImpl {
	
	protected ComputerServiceImpl() {
	}
	
	public List<Computer> getAllComputers() {
		return DaoFactory.getComputerDao().getAllComputer();	
	}
}

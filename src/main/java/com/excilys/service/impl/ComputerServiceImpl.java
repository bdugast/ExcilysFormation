package main.java.com.excilys.service.impl;

import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.ComputerService;

public class ComputerServiceImpl implements ComputerService{
	
	protected ComputerServiceImpl() {
	}
	
	public List<Computer> getAllComputers() {
		return DaoFactory.getComputerDao().getAllComputer();	
	}

	@Override
	public Computer getOneComputer(int id) {
		return DaoFactory.getComputerDao().getOneComputer(id);
	}

	@Override
	public void createComputer(Computer comp) {
		DaoFactory.getComputerDao().createComputer(comp);		
	}

	@Override
	public void updateComputer(Computer comp) {
		DaoFactory.getComputerDao().updateComputer(comp);
	}

	@Override
	public void deleteComputer(int id) {
		DaoFactory.getComputerDao().deleteComputer(id);
	}
}

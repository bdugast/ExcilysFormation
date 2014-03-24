package main.java.com.excilys.service;

import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerService {
	public List<Computer> getAllComputers();
	public Computer getOneComputer(int id);
	public void createComputer(Computer comp);
	public void updateComputer(Computer comp);
	public void deleteComputer(int id);
}

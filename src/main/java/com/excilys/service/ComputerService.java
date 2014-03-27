package main.java.com.excilys.service;

import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerService {
	public List<Computer> getAllComputers();
	public Computer getOneComputer(int id);
	public List<Computer> getRangeComputers(int start, int nb);
	public List<Computer> getRangeSearchComputers(int start, int nb, String search);
	public List<Computer> getRangeSearchOrderComputers(int start, int nb, String search, String orderField,boolean order);
	public void createComputer(Computer comp);
	public void updateComputer(Computer comp);
	public void deleteComputer(int id);
	public int getCountComputer();
	public int getCountComputerSearch(String name);
}

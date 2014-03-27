package main.java.com.excilys.dao;

import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerDAO {
	public Computer getOneComputer(int id);
	public List<Computer> getRangeSearchOrderComputers(int start, int nb, String search, String orderby);
	public void updateComputer(Computer comp);
	public void createComputer(Computer comp);
	public void deleteComputer(int id);
	public int getCountComputerSearch(String search);
}

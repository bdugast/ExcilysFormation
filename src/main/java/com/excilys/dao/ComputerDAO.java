package main.java.com.excilys.dao;

import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerDAO {
	public Computer getOneComputer(int id);
	public List<Computer> getAllComputer();
	public List<Computer> getRangeComputers(int start, int nb);
	public void updateComputer(Computer comp);
	public void createComputer(Computer comp);
	public void deleteComputer(int id);
	public int getCountComputer();
}

package main.java.com.excilys.dao;

import java.sql.Connection;
import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerDAO {
	public Computer getOneComputer(int id);
	public List<Computer> getRangeSearchOrderComputers(int start, int nb, String search, String orderby);
	public void updateComputer(Computer comp, Connection conn);
	public int createComputer(Computer comp, Connection conn);
	public void deleteComputer(int id, Connection conn);
	public int getCountComputerSearch(String search);
}

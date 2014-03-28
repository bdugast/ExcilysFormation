package main.java.com.excilys.dao;

import java.sql.SQLException;
import java.util.List;

import main.java.com.excilys.domain.Computer;

public interface ComputerDAO {
	public Computer getOneComputer(int id) throws SQLException ;
	public List<Computer> getRangeSearchOrderComputers(int start, int nb, String search, String orderby) throws SQLException ;
	public void updateComputer(Computer comp) throws SQLException ;
	public int createComputer(Computer comp) throws SQLException ;
	public void deleteComputer(int id) throws SQLException ;
	public int getCountComputerSearch(String search) throws SQLException ;
}

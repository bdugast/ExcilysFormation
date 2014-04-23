package com.excilys.dao;

import java.util.List;

import com.excilys.domain.Computer;
import com.excilys.wrapper.PageWrapper;

public interface ComputerDao {
	/**
	 * Method that get a computer depending on the id
	 * @param id
	 * 		id of the computer we want to get
	 * @return
	 * 		return the computer depending on it id
	 */
	public Computer getOneComputer(int id);
	
	/**
	 * get a list a computer depending on the start, the nb of computer (that doesn't change), search field, and orderfield
	 * @param start
	 * 		first computer to get
	 * @param nb
	 * 		number of computer to get
	 * @param search
	 * 		search parameter for the request
	 * @param orderby
	 * 		the order by field for the request
	 * @return
	 * 		return a list of computer
	 */
	public List<Computer> getRangeComputers(PageWrapper wrap, String orderby);
	
	/**
	 * update a computer based on the new computer send to the method
	 * @param comp
	 * 		computer that will be update with new parameters, and the good id (that can't be change)
	 */
	public void updateComputer(Computer comp);
	
	/**
	 * Add a computer in the database and return the id of the computer to add it in logs.
	 * @param comp
	 * 		computer that will be add in the database
	 * @return
	 * 		return the generated key from the computer added to put it in the log base
	 */
	public int createComputer(Computer comp);
	
	/**
	 * Delete a computer in the database depending on the id
	 * @param id
	 * 		id of the computer to delete
	 */
	public void deleteComputer(int id);
	
	/**
	 * Get the number of computer in the database and return it
	 * @param search
	 * 		search depending on the string of the search
	 * @return
	 * 		return the number of computer
	 */
	public int getCountComputers(String search);
}

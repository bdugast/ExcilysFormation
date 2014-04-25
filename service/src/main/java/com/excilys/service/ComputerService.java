package com.excilys.service;

import java.util.List;

import com.excilys.domain.Computer;
import com.excilys.wrapper.PageWrapper;

public interface ComputerService {
	/**
	 * Call the method in dao to get a computer depending on the id
	 * @param id
	 * 		id of the computer we want to get
	 * @return
	 * 		return the computer depending on it id
	 */
	public Computer getOneComputer(int id);
	
	/**
	 * Call the method in dao to get a list a computer depending on the start,
	 *  the nb of computer (that doesn't change), search field, and orderfield
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
	public List<Computer> getRangeComputers(PageWrapper wrap);
	
	/**
	 * call the method in the dao to update a computer based on the new computer send to the method
	 * @param comp
	 * 		computer that will be update with new parameters, and the good id (that can't be change)
	 */
	public void createComputer(Computer comp);
	
	/**
	 * call the method in the dao to add a computer in the database and return the id of the computer to add it in logs.
	 * @param comp
	 * 		computer that will be add in the database
	 * @return
	 * 		return the generated key from the computer added to put it in the log base
	 */
	public void updateComputer(Computer comp);
	
	/**
	 * call the method in the dao to delete a computer in the database depending on the id
	 * @param id
	 * 		id of the computer to delete
	 */
	public void deleteComputer(Computer comp);
	
	/**
	 * call the method in the dao to get the number of computer in the database and return it
	 * @param search
	 * 		search depending on the string of the search
	 * @return
	 * 		return the number of computer
	 */
	public int getCountComputers(String name);
}

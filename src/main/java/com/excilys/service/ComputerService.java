package com.excilys.service;

import java.util.List;

import com.excilys.domain.Computer;
import com.excilys.wrapper.PageWrapper;

public interface ComputerService {
	public Computer getOneComputer(int id);
	public List<Computer> getRangeSearchOrderComputers(PageWrapper wrap);
	public void createComputer(Computer comp);
	public void updateComputer(Computer comp);
	public void deleteComputer(int id);
	public int getCountComputerSearch(String name);
}

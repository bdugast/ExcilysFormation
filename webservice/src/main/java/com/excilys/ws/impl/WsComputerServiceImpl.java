package com.excilys.ws.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;
import com.excilys.ws.WsComputerService;

@WebService(endpointInterface="com.excilys.ws.WsComputerService")
public class WsComputerServiceImpl implements WsComputerService{

	@Autowired
	ComputerService computerService;
	
	@Override
	public Computer getOneComputer(int id) {
		return computerService.getOneComputer(id);
	}

	@Override
	public ArrayList<Computer> getListComputer(String orderField, Boolean order, Integer page, String search, int NB_COMPUTER_BY_PAGE) {
		return (ArrayList<Computer>) computerService.getListComputer(orderField, order, page, search, NB_COMPUTER_BY_PAGE);
	}

	@Override
	public void createComputer(Computer comp) {
		computerService.createComputer(comp);
	}

	@Override
	public void updateComputer(Computer comp) {
		computerService.updateComputer(comp);
	}

	@Override
	public void deleteComputer(Computer comp) {
		computerService.deleteComputer(comp);
	}

	@Override
	public int getCountComputers(String name) {
		return computerService.getCountComputers(name);
	}
}

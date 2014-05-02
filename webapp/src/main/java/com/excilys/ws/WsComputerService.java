package com.excilys.ws;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.domain.Computer;

@WebService
@SOAPBinding(style=Style.RPC)
public interface WsComputerService {

	@WebMethod Computer getOneComputer(int id);
	@WebMethod ArrayList<Computer> getListComputer(String orderField, Boolean order, Integer page, String search, int NB_COMPUTER_BY_PAGE);
	@WebMethod void createComputer(Computer comp);
	@WebMethod void updateComputer(Computer comp);
	@WebMethod void deleteComputer(Computer comp);
	@WebMethod int getCountComputers(String name);
}
package com.excilys.rs;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.excilys.domain.Computer;

@Produces("application/xml")
@Service("RsComputerService")
@Path("/computer")
public interface RsComputerService {

	@GET
	@Path("/id/{id}")
	Computer getOneComputer(@PathParam("id") int id);
	
	ArrayList<Computer> getListComputer(String orderField, Boolean order, Integer page, String search, int NB_COMPUTER_BY_PAGE);
	 void createComputer(Computer comp);
	 void updateComputer(Computer comp);
	 void deleteComputer(Computer comp);
	
	@GET
	@Path("/count/{name}")
	int getCountComputers(@PathParam("name") String name);
}
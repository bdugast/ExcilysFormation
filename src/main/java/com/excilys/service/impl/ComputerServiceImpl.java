package main.java.com.excilys.service.impl;

import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.ComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerServiceImpl implements ComputerService {

	String previousOrderField = "";
	Boolean previousOrder = true;
	int previousStart = 0;
	
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

	protected ComputerServiceImpl() {
	}

	public List<Computer> getAllComputers() {
		return DaoFactory.getComputerDao().getAllComputer();
	}

	@Override
	public Computer getOneComputer(int id) {
		return DaoFactory.getComputerDao().getOneComputer(id);
	}

	@Override
	public void createComputer(Computer comp) {
		DaoFactory.getComputerDao().createComputer(comp);
	}

	@Override
	public void updateComputer(Computer comp) {
		DaoFactory.getComputerDao().updateComputer(comp);
	}

	@Override
	public void deleteComputer(int id) {
		DaoFactory.getComputerDao().deleteComputer(id);
	}

	@Override
	public List<Computer> getRangeComputers(int start, int nb) {
		return DaoFactory.getComputerDao().getRangeComputers(start, nb);
	}

	@Override
	public List<Computer> getRangeSearchComputers(int start, int nb,
			String search) {
		return DaoFactory.getComputerDao().getRangeSearchComputers(start, nb,
				search);
	}

	@Override
	public int getCountComputer() {
		return DaoFactory.getComputerDao().getCountComputer();
	}

	@Override
	public int getCountComputerSearch(String search) {
		return DaoFactory.getComputerDao().getCountComputerSearch(search);
	}

	@Override
	public List<Computer> getRangeSearchOrderComputers(int start, int nb, String search, String orderField) {
		
		StringBuilder orderby = new StringBuilder();		
		if ((this.previousOrderField.equals(orderField))&&(previousStart==start)){
				LOG.debug("orderField : " + orderField);
				previousOrder = !previousOrder;
		}
		if ((!this.previousOrderField.equals(orderField))){
			this.previousOrderField = orderField;
			previousOrder = true;
		}
		previousStart = start;
		String orderb;
		switch (previousOrderField) {
		case "COMPUTER":
			orderb = "cu.name";
			break;
		case "COMPANY":
			orderb = "ca.name";
			break;
		case "INTRODUCED":
			orderb = "cu.introduced";
			break;
		case "DISCONTINUED":
			orderb = "cu.discontinued";
			break;
		default:
			orderb = "cu.id";
			break;
		}
		orderby.append(orderb);
		orderby.append(" ");
		if (previousOrder)
			orderby.append("DESC");
		else
			orderby.append("ASC");

		return DaoFactory.getComputerDao().getRangeSearchOrderComputers(start,	nb, search, orderby.toString());
	}

}

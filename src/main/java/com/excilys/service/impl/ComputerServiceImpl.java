package main.java.com.excilys.service.impl;

import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.ComputerService;
import main.java.com.excilys.wrapper.PageWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerServiceImpl implements ComputerService {

	String previousOrderField = "";
	Boolean previousOrder = true;
	int previousStart = 0;
	
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

	protected ComputerServiceImpl() {
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
	public int getCountComputerSearch(String search) {
		return DaoFactory.getComputerDao().getCountComputerSearch(search);
	}

	@Override
	public List<Computer> getRangeSearchOrderComputers(PageWrapper wrap) {
		
		StringBuilder orderby = new StringBuilder();
		String orderb;
		switch (wrap.getOrderField()) {
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
		if (wrap.getOrder())
			orderby.append("ASC");
		else
			orderby.append("DESC");

		return DaoFactory.getComputerDao().getRangeSearchOrderComputers(((wrap.getCurrentPage()-1)*wrap.NB_COMPUTER_BY_PAGE), wrap.NB_COMPUTER_BY_PAGE, wrap.getSearch(), orderby.toString());
	}

}

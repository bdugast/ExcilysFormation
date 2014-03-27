package main.java.com.excilys.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.ComputerService;
import main.java.com.excilys.wrapper.PageWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;
	
	static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Override
	public Computer getOneComputer(int id) {
		return DaoFactory.INSTANCE.getComputerDao().getOneComputer(id);
	}

	@Override
	public void createComputer(Computer comp) {
		DaoFactory.INSTANCE.getComputerDao().createComputer(comp);
	}

	@Override
	public void updateComputer(Computer comp) {
		DaoFactory.INSTANCE.getComputerDao().updateComputer(comp);
	}

	@Override
	public void deleteComputer(int id) {
		DaoFactory.INSTANCE.getComputerDao().deleteComputer(id);
	}

	@Override
	public int getCountComputerSearch(String search) {
		return DaoFactory.INSTANCE.getComputerDao().getCountComputerSearch(search);
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

		return DaoFactory.INSTANCE.getComputerDao().getRangeSearchOrderComputers(((wrap.getCurrentPage()-1)*wrap.NB_COMPUTER_BY_PAGE), wrap.NB_COMPUTER_BY_PAGE, wrap.getSearch(), orderby.toString());
	}

	public HashMap<String, String> checkForm(String name, String introduced, String discontinued, String companyId) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String, String> errorHash = new HashMap<>();
		
		if(name.length()<2) errorHash.put("nameError", "Name lenght must be more than 2 characters");
        try {
        	df.parse(introduced);
		} catch (ParseException e) {
        	errorHash.put("introducedError", "Format incorrect yyyy-mm-dd");
		}
        try {
        	df.parse(discontinued);
		} catch (ParseException e) {
			errorHash.put("discontinuedError", "Format incorrect yyyy-mm-dd");
		}
        try { 
            int id = Integer.parseInt(companyId);
            DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id);
        } catch(NumberFormatException e) { 
			errorHash.put("companyError", "Company does not exist");
        }
		return errorHash;
	}

}

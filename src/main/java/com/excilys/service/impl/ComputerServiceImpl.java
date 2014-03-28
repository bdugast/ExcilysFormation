package main.java.com.excilys.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import main.java.com.excilys.dao.impl.DaoFactory;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.ComputerService;
import main.java.com.excilys.wrapper.PageWrapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Override
	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = DaoFactory.INSTANCE.getComputerDao().getOneComputer(id);
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
		return comp;
	}

	@Override
	public void createComputer(Computer comp) {
		Connection conn = DaoFactory.INSTANCE.getConnection();
		int idComp;
		try {
			conn.setAutoCommit(false);
			idComp = DaoFactory.INSTANCE.getComputerDao().createComputer(comp, conn);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("CREATE", idComp, conn);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.toString());
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error(e.toString());
			}
		}
	}

	@Override
	public void updateComputer(Computer comp) {
		Connection conn = DaoFactory.INSTANCE.getConnection();
		try {
			conn.setAutoCommit(false);
			DaoFactory.INSTANCE.getComputerDao().updateComputer(comp, conn);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("UPDATE", comp.getId(), conn);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.toString());
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error(e.toString());
			}
		}
	}

	@Override
	public void deleteComputer(int id) {
		Connection conn = DaoFactory.INSTANCE.getConnection();
		try {
			conn.setAutoCommit(false);
			DaoFactory.INSTANCE.getComputerDao().deleteComputer(id, conn);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("DELETE", id, conn);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.toString());
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error(e.toString());
			}
		}
	}

	@Override
	public int getCountComputerSearch(String search) {
		int count = 0;
		try {
			count = DaoFactory.INSTANCE.getComputerDao().getCountComputerSearch(search);
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
		return count;
	}

	@Override
	public List<Computer> getRangeSearchOrderComputers(PageWrapper wrap) {

		List<Computer> compList = null;
		
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

		try {
			 compList = DaoFactory.INSTANCE.getComputerDao().getRangeSearchOrderComputers(
							((wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE),
							wrap.NB_COMPUTER_BY_PAGE, wrap.getSearch(),
							orderby.toString());
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
		return compList;
	}

	public HashMap<String, String> checkForm(String name, String introduced,
			String discontinued, String companyId) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		HashMap<String, String> errorHash = new HashMap<>();

		if (name.length() < 2)
			errorHash.put("nameError",
					"Name lenght must be more than 2 characters");
		LOG.debug("introduced " + introduced.toString());
		if (!introduced.equals("")) {
			try {
				fmt.parseDateTime(introduced);
			} catch (Exception e) {
				errorHash.put("introducedError", "Format incorrect yyyy-mm-dd");
			}
		}
		LOG.debug("discontinued " + discontinued.toString());
		if (!discontinued.equals("")) {
			try {
				fmt.parseDateTime(discontinued);
			} catch (Exception e) {
				errorHash.put("discontinuedError",
						"Format incorrect yyyy-mm-dd");
			}
		}
		LOG.debug("company " + companyId.toString());
		if (!companyId.equals("")) {
			int id = 0;
			try {
				id = Integer.valueOf(companyId);
			} catch (NumberFormatException e) {
				errorHash.put("companyError", "Company does not exist");
			}
			try {
				if (id != 0	&& DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id) == null)
					errorHash.put("companyError", "Company does not exist");
			} catch (SQLException e) {
				LOG.error(e.toString());
			}
		}
		return errorHash;
	}

}

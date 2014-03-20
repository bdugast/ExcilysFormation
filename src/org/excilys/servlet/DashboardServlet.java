package org.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.excilys.DAO.DaoFactory;
import org.excilys.domain.Company;
import org.excilys.domain.Computer;

public class DashboardServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		List<Computer> computers = new ArrayList<>();
		computers = DaoFactory.getComputerDao().getAllComputer();
		List<Company> companies = new ArrayList<>();
		companies = DaoFactory.getCompanyDao().getAllCompany();
		req.setAttribute("companies", companies);
		req.setAttribute("computers", computers);

		getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req,resp);
			
		}
}

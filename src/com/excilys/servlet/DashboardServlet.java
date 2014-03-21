package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DAO.DaoFactory;
import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class DashboardServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		List<Computer> computers = new ArrayList<Computer>();
		computers = DaoFactory.getComputerDao().getAllComputer();
		req.setAttribute("computers", computers);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req,resp);
			
		}
}

package main.java.com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.impl.ServiceFactory;

public class DashboardServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		List<Computer> computers = ServiceFactory.getComputerService().getAllComputers();
		req.setAttribute("computers", computers);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req,resp);
			
		}
}

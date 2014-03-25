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
	
	final static int NB_COMPUTER_BY_PAGE = 20;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//Récupérer la page actuelle
		int currentPage = Integer.valueOf(req.getParameter("page"));	
		//Compter le nombre d'objet
		int count = ServiceFactory.getComputerService().getCountComputer();
		//Savoir le nombre de pages
		Double countPages = Math.ceil(count/20)+1;
		//Get 20 ordinateurs en fonction de la page with fucking limit	
		List<Computer> computers = ServiceFactory.getComputerService().getRangeComputers(((currentPage-1)*NB_COMPUTER_BY_PAGE), NB_COMPUTER_BY_PAGE);
		req.setAttribute("computers", computers);
		req.setAttribute("countComputers", count);
        req.setAttribute("countPages", countPages);
        req.setAttribute("currentPage", currentPage);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req,resp);
			
		}
}

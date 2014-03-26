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
		int currentPage = 1;
		String search = "";
		
		//Récupérer la page actuelle
		if(req.getParameter("page")!=null) currentPage = Integer.valueOf(req.getParameter("page"));
		
		//Récupérer le champ recherche
		if(req.getParameter("search")!=null) search = req.getParameter("search");
		
		//Compter le nombre d'objet
		int count = ServiceFactory.getComputerService().getCountComputerSearch(search);

		//Savoir le nombre de pages
		int countPages = (int) (Math.ceil((double)count/(double)20));
		
		//On modifie la page si la current page est trop haute ou trop basse
		if(currentPage>countPages) currentPage = countPages;
		if(currentPage<1) currentPage=1;
		
		//Get 20 ordinateurs en fonction de la page with fucking limit	
		List<Computer> computers = ServiceFactory.getComputerService().getRangeSearchComputers(((currentPage-1)*NB_COMPUTER_BY_PAGE), NB_COMPUTER_BY_PAGE, search);
		
		req.setAttribute("search", search);
		req.setAttribute("computers", computers);
		req.setAttribute("countComputers", count);
        req.setAttribute("countPages", countPages);
        req.setAttribute("currentPage", currentPage);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req,resp);
			
		}
}

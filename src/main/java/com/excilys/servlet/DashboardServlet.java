package main.java.com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.impl.ServiceFactory;
import main.java.com.excilys.wrapper.PageWrapper;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PageWrapper wrap = new PageWrapper();
		
		//Order field
		if(req.getParameter("orderField")!=null) wrap.setOrderField(req.getParameter("orderField"));
		
		//Order
		if(req.getParameter("order")!=null) wrap.setOrder(Boolean.valueOf(req.getParameter("order")));
				
		//Récupérer la page actuelle
		if(req.getParameter("page")!=null) wrap.setCurrentPage(Integer.valueOf(req.getParameter("page")));
		
		//Récupérer le champ recherche
		if(req.getParameter("search")!=null) wrap.setSearch(req.getParameter("search"));
		
		//Compter le nombre d'objet
		wrap.setCount(ServiceFactory.INSTANCE.getComputerService().getCountComputerSearch(wrap.getSearch()));

		//Savoir le nombre de pages
		wrap.setCountPages((int) (Math.ceil((double)wrap.getCount()/(double)20)));
		
		//On modifie la page si la current page est trop haute ou trop basse
		if(wrap.getCurrentPage()>wrap.getCountPages()) wrap.setCurrentPage(wrap.getCountPages());
		if(wrap.getCurrentPage()<1) wrap.setCurrentPage(1);
		
		//Get 20 ordinateurs en fonction de la page with fucking limit	
		List<Computer> computers = ServiceFactory.INSTANCE.getComputerService().getRangeSearchOrderComputers(wrap);
		
		req.setAttribute("wrap", wrap);
		req.setAttribute("computers", computers);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req,resp);
		}
}

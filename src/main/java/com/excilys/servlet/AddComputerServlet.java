package main.java.com.excilys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.domain.Company;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.impl.ServiceFactory;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/add")
public class AddComputerServlet extends HttpServlet{
	
	static final Logger LOG = LoggerFactory.getLogger(AddComputerServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Company> companies = ServiceFactory.INSTANCE.getCompanyService().getAllCompanies();
		req.setAttribute("companies", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		DateTime dateIntroduced = null;
		DateTime dateDiscontinued = null;
		HashMap<String, String> hashError = new HashMap<>();
		hashError = ServiceFactory.INSTANCE.getComputerService().checkForm(req.getParameter("name"), req.getParameter("introduced"), req.getParameter("discontinued"),req.getParameter("company"));
		if(hashError.isEmpty()){
			String name = req.getParameter("name");
			Company company = new Company();
			if(req.getParameter("introduced")!="") dateIntroduced = new DateTime(req.getParameter("introduced"));
			if(req.getParameter("discontinued")!="") dateDiscontinued = new DateTime(req.getParameter("discontinued"));
			LOG.trace("name " + name);
			LOG.trace("dateIntroduced " + dateIntroduced);
			LOG.trace("dateDiscontinued " + dateDiscontinued);
					
			if(req.getParameter("company")!="") company = ServiceFactory.INSTANCE.getCompanyService().getOneCompany(Integer.valueOf(req.getParameter("company")));
			Computer comp = new Computer(name, dateIntroduced, dateDiscontinued, company);
			ServiceFactory.INSTANCE.getComputerService().createComputer(comp);
					
			resp.sendRedirect("dashboard");
		}else{
			List<Company> companies = ServiceFactory.INSTANCE.getCompanyService().getAllCompanies();
			req.setAttribute("errors", hashError);
			LOG.debug("errors " + hashError.toString());
			req.setAttribute("companies", companies);
			req.setAttribute("name", req.getParameter("name"));
			req.setAttribute("introduced", req.getParameter("introduced"));
			req.setAttribute("discontinued", req.getParameter("discontinued"));
			req.setAttribute("companyId", req.getParameter("company"));
			
			getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req,resp);
		}
	}	
}

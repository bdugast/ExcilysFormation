package main.java.com.excilys.servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.domain.Company;
import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.impl.ServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateComputerServlet extends HttpServlet{

	static final Logger LOG = LoggerFactory.getLogger(AddComputerServlet.class);
	int id = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		id = Integer.valueOf(req.getParameter("id"));
		Computer comp = ServiceFactory.getComputerService().getOneComputer(id);
		req.setAttribute("computer", comp);
		List<Company> companies = ServiceFactory.getCompanyService().getAllCompanies();
		req.setAttribute("companies", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Computer comp = new Computer();
		comp.setId(id);
		comp.setName(req.getParameter("name"));
		SimpleDateFormat formatBDD = new SimpleDateFormat("yyyy-MM-dd");
		try {			
			if(req.getParameter("introduced")!="") comp.setIntroduced(new java.sql.Date(formatBDD.parse(req.getParameter("introduced")).getTime()));
			else comp.setIntroduced(null);
			if(req.getParameter("discontinued")!="") comp.setDiscontinued(new java.sql.Date(formatBDD.parse(req.getParameter("discontinued")).getTime()));
			else comp.setDiscontinued(null);
			LOG.trace("Introduced : " + comp.getIntroduced());
			LOG.trace("Discontinued : " + comp.getDiscontinued());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(req.getParameter("company")!="") comp.setCompany(ServiceFactory.getCompanyService().getOneCompany(Integer.valueOf(req.getParameter("company"))));
		else comp.setCompany(new Company());
		
		ServiceFactory.getComputerService().updateComputer(comp);
		
		resp.sendRedirect("dashboard?page=1");
	}
}

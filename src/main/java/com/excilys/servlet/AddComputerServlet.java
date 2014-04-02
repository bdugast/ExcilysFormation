package com.excilys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.service.impl.CompanyServiceImpl;
import com.excilys.service.impl.ComputerServiceImpl;

@WebServlet("/add")
public class AddComputerServlet extends HttpServlet{
	
	static final Logger LOG = LoggerFactory.getLogger(AddComputerServlet.class);
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	CompanyServiceImpl companyService;
	
	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Company> companies = companyService.getAllCompanies();
		req.setAttribute("companies", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		DateTime dateIntroduced = null;
		DateTime dateDiscontinued = null;
		HashMap<String, String> hashError = new HashMap<>();
		hashError = computerService.checkForm(req.getParameter("name"), req.getParameter("introduced"), req.getParameter("discontinued"),req.getParameter("company"));
		if(hashError.isEmpty()){
			String name = req.getParameter("name");
			Company company = new Company();
			if(req.getParameter("introduced")!="") dateIntroduced = new DateTime(req.getParameter("introduced"));
			if(req.getParameter("discontinued")!="") dateDiscontinued = new DateTime(req.getParameter("discontinued"));
			LOG.trace("name " + name);
			LOG.trace("dateIntroduced " + dateIntroduced);
			LOG.trace("dateDiscontinued " + dateDiscontinued);
					
			if(req.getParameter("company")!="") company = companyService.getOneCompany(Integer.valueOf(req.getParameter("company")));
			Computer comp = new Computer(name, dateIntroduced, dateDiscontinued, company);
			computerService.createComputer(comp);
					
			resp.sendRedirect("dashboard");
		}else{
			List<Company> companies = companyService.getAllCompanies();
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

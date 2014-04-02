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

@WebServlet("/update")
public class UpdateComputerServlet extends HttpServlet{

	
	static final Logger LOG = LoggerFactory.getLogger(UpdateComputerServlet.class);
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
		
		Computer comp = computerService.getOneComputer(Integer.valueOf(req.getParameter("id")));
		
		req.setAttribute("id", comp.getId());
		req.setAttribute("name", comp.getName());
		req.setAttribute("introduced", comp.getIntroduced());
		req.setAttribute("discontinued", comp.getDiscontinued());
		req.setAttribute("companyId", comp.getCompany().getId());
		
		List<Company> companies = companyService.getAllCompanies();
		req.setAttribute("companies", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HashMap<String, String> hashError = new HashMap<>();
		hashError = computerService.checkForm(req.getParameter("name"), req.getParameter("introduced"), req.getParameter("discontinued"),req.getParameter("company"));
		if(hashError.isEmpty()){
			Computer comp = new Computer();
			comp.setId(Integer.valueOf(req.getParameter("id")));
			comp.setName(req.getParameter("name"));
			if(req.getParameter("introduced")!="") comp.setIntroduced(new DateTime(req.getParameter("introduced")));
			else comp.setIntroduced(null);
			if(req.getParameter("discontinued")!="") comp.setDiscontinued(new DateTime(req.getParameter("discontinued")));
			else comp.setDiscontinued(null);
			LOG.trace("Introduced : " + comp.getIntroduced());
			LOG.trace("Discontinued : " + comp.getDiscontinued());
			if(req.getParameter("company")!="") comp.setCompany(companyService.getOneCompany(Integer.valueOf(req.getParameter("company"))));
			else comp.setCompany(new Company());
			computerService.updateComputer(comp);
			
			resp.sendRedirect("dashboard");
		}else{
			List<Company> companies = companyService.getAllCompanies();
			req.setAttribute("companies", companies);
			req.setAttribute("errors", hashError);
			req.setAttribute("id", req.getParameter("id"));
			req.setAttribute("name", req.getParameter("name"));
			req.setAttribute("introduced", req.getParameter("introduced"));
			req.setAttribute("discontinued", req.getParameter("discontinued"));
			req.setAttribute("companyId", req.getParameter("company"));
			
			getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(req,resp);
		}
	}
}

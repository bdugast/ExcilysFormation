package com.excilys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.impl.CompanyServiceImpl;
import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

@WebServlet("/add")
public class AddComputerServlet extends HttpServlet{
	
	static final Logger LOG = LoggerFactory.getLogger(AddComputerServlet.class);
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	CompanyServiceImpl companyService;
	@Autowired
	ComputerValidator computerValidator;
	
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
		
		ComputerDto compDto = ComputerDto.builder()
                .name(req.getParameter("name"))
                .introduced(req.getParameter("introduced"))
                .discontinued(req.getParameter("discontinued"))
                .companyId(Integer.parseInt(req.getParameter("company"))).build();
		
		HashMap<String, String> hashError = new HashMap<>();
		hashError = computerValidator.validate(compDto);
		
		if(hashError.isEmpty()){
			computerService.createComputer(computerMapper.fromDto(compDto));
			resp.sendRedirect("dashboard?msg=successAdd");
		}else{
			List<Company> companies = companyService.getAllCompanies();
			req.setAttribute("errors", hashError);
			LOG.debug("errors " + hashError.toString());
			req.setAttribute("companies", companies);
			req.setAttribute("ComputerDto", compDto);
			
			getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req,resp);
		}
	}	
}

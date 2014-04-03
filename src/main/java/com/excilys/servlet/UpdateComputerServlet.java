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

@WebServlet("/update")
public class UpdateComputerServlet extends HttpServlet{

	
	static final Logger LOG = LoggerFactory.getLogger(UpdateComputerServlet.class);
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	CompanyServiceImpl companyService;
	@Autowired
	ComputerValidator compVal;
	
	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ComputerDto compDto = computerMapper.toDto(computerService.getOneComputer(Integer.valueOf(req.getParameter("id"))));
		
		req.setAttribute("compDto", compDto);
		List<Company> companies = companyService.getAllCompanies();
		req.setAttribute("companies", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ComputerDto compDto = ComputerDto.builder()
				.id(Integer.parseInt(req.getParameter("id")))
                .name(req.getParameter("name"))
                .introduced(req.getParameter("introduced"))
                .discontinued(req.getParameter("discontinued"))
                .companyId(Integer.parseInt(req.getParameter("company"))).build();
		
		LOG.debug("compDto " + compDto.getId() + " " + compDto.getName() + compDto.getIntroduced() + compDto.getDiscontinued() + compDto.getCompanyId());
		
		HashMap<String, String> hashError = new HashMap<>();
		hashError = compVal.validate(compDto);
		LOG.debug("validation : " + compVal.validateId(String.valueOf(compDto.getId())));
		if(!compVal.validateId(String.valueOf(compDto.getId())))
			hashError.put("idError", "L'identifiant est incorrecte");
		
		LOG.debug("hashError " + hashError.toString());
		
		if(hashError.isEmpty()){
			computerService.updateComputer(computerMapper.fromDto(compDto));
			resp.sendRedirect("dashboard?msg=successUp");
		}else{
			List<Company> companies = companyService.getAllCompanies();
			req.setAttribute("companies", companies);
			req.setAttribute("errors", hashError);
			req.setAttribute("compDto", compDto);
			
			getServletContext().getRequestDispatcher("/WEB-INF/updateComputer.jsp").forward(req,resp);
		}
	}
}

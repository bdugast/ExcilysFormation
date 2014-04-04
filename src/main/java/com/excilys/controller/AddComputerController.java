package com.excilys.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.impl.CompanyServiceImpl;
import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/add")
public class AddComputerController{
	
	static final Logger LOG = LoggerFactory.getLogger(AddComputerController.class);
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	CompanyServiceImpl companyService;
	@Autowired
	ComputerValidator computerValidator;
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Company> companies = companyService.getAllCompanies();
		req.setAttribute("companies", companies);
		
		ModelAndView mav = new ModelAndView("addComputer");
		mav.addObject("req", req);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ModelAndView mav;
		
		ComputerDto compDto = ComputerDto.builder()
                .name(req.getParameter("name"))
                .introduced(req.getParameter("introduced"))
                .discontinued(req.getParameter("discontinued"))
                .companyId(Integer.parseInt(req.getParameter("company"))).build();
		
		HashMap<String, String> hashError = new HashMap<>();
		hashError = computerValidator.validate(compDto);
		
		if(hashError.isEmpty()){
			LOG.debug("successAdd");
			computerService.createComputer(computerMapper.fromDto(compDto));
			mav = new ModelAndView("redirect:/dashboard");
			mav.addObject("msg", "successAdd");
			return mav;
		}else{
			LOG.debug("failAdd");
			List<Company> companies = companyService.getAllCompanies();
			req.setAttribute("errors", hashError);
			LOG.debug("errors " + hashError.toString());
			req.setAttribute("companies", companies);
			req.setAttribute("ComputerDto", compDto);
			
			mav = new ModelAndView("addComputer");
			mav.addObject("req", req);
			return mav;
		}
	}	
}

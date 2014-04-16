package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/add")
public class AddComputerController{
	
	static final Logger LOG = LoggerFactory.getLogger(AddComputerController.class);
	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	ComputerValidator computerValidator;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap map)
			throws ServletException, IOException {
		
		List<Company> companies = companyService.getAllCompanies();
		map.addAttribute("companies", companies);
		map.addAttribute("compDto", new ComputerDto());
		
		return "addComputer";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute("compDto") ComputerDto compDto, BindingResult result, ModelMap map)
			throws ServletException, IOException {
		
		if(!result.hasErrors()){
			Company company;
			if(companyService.getOneCompany(compDto.getCompanyId()) != null) company = companyService.getOneCompany(compDto.getCompanyId());
			else company = null;
			LOG.debug("successAdd");
			computerService.createComputer(computerMapper.fromDto(compDto,company));
			return "redirect:dashboard?msg=successAdd";
		}else{
			LOG.debug("failAdd");
			List<Company> companies = companyService.getAllCompanies();
			
			map.put("result", result);
			map.put("companies", companies);
			map.put("compDto", compDto);
	        return "addComputer";
			
		}
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
}

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

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.IdValidator;

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
	IdValidator idValidator;
	
	
	/**
	 * When the page "add" is called, this method is called and show addComputer.jsp with information that are needed
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap map)
			throws ServletException, IOException {
		
		List<Company> companies = companyService.getAllCompanies();
		map.addAttribute("companies", companies);
		map.addAttribute("compDto", new ComputerDto());
		
		return "addComputer";
	}
	
	
	/**
	 * This method doPost is called when we push the button "add" in the "add" page, this button start
	 * the process to add a computer into the database, it check if there is error, and redirect to the 
	 * dashboard if the computer is well added or to the same page if there is a problem in the check on the back
	 * 
	 * @param compDto
	 * 		the computer to add in the database, the computer is check thanks to annotation in the class compDto.
	 * @param result
	 * 		get all return errors from the check
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute("compDto") ComputerDto compDto, BindingResult result, ModelMap map)
			throws ServletException, IOException {
		
		if(!result.hasErrors()){
			Company company = null;
			int companyId = Integer.valueOf(compDto.getCompanyId());
			if(companyService.getOneCompany(companyId) != null) company = companyService.getOneCompany(companyId);
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
	
	/**
	 * ExceptionHandler that redirect any error catch to a custom error page
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
}

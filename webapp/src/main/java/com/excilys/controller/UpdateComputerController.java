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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.IdValidator;

@Controller
@RequestMapping("/update")
public class UpdateComputerController {

	
	static final Logger LOG = LoggerFactory.getLogger(UpdateComputerController.class);
	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	IdValidator validator;
	
	/**
	 * method called when we push modify button, if the id is correct, it called the updateComputer page,
	 * if not, we return to the dashboard with a fail message.
	 * @param req
	 * 		req allow to get fields in the address bar
	 * @param map
	 * 		preparation of the return
	 * @return 
	 * 		string of the good jsp page.
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap map, @RequestParam("id") String id)
			throws ServletException, IOException {

		if(validator.validateId(id)){
			
			ComputerDto compDto = computerMapper.toDto(computerService.getOneComputer(Integer.valueOf(id)));
			List<Company> companies = companyService.getAllCompanies();

			map.addAttribute("companies", companies);
			map.addAttribute("compDto", compDto);
			
			return "updateComputer";
		}else{
			return "redirect:/dashboard?msg=failUp";
		}
	}
	
	/**
	 * Update of a company in the database, if there is no error on the check in compDto, 
	 * else, we return to the same page with everything that was set before
	 * @param compDto
	 * 		the computer to add in the database, the computer is check thanks to annotation in the class compDto.
	 * @param result
	 * 		get all return errors from the check
	 * @return 
	 * 		string of the good jsp page.
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute("compDto") ComputerDto compDto, BindingResult result, ModelMap map)
			throws ServletException, IOException {
		
		LOG.debug("compDto " + compDto.getId() + " " + compDto.getName() + compDto.getIntroduced() + compDto.getDiscontinued() + compDto.getCompanyId());
		
		if(!result.hasErrors()){
			Company company;
			int companyId = Integer.valueOf(compDto.getCompanyId());
			if(companyService.getOneCompany(companyId) != null) company = companyService.getOneCompany(companyId);
			else company = null;
			computerService.updateComputer(computerMapper.fromDto(compDto, company));
			return "redirect:dashboard?msg=successUp";
		}else{
			List<Company> companies = companyService.getAllCompanies();
			
			map.put("result", result);
			map.put("companies", companies);
			map.put("compDto", compDto);
	        return "updateComputer";
		}
	}
	
	/**
	 * ExceptionHandler that redirect any error catch to a custom error page
	 */
//	@ExceptionHandler(Exception.class)
//	public String handleAllException(Exception ex) {
//		return "error";
//	}
}

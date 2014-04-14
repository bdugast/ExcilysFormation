package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

import com.excilys.domain.Company;
import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.impl.CompanyServiceImpl;
import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/update")
public class UpdateComputerController {

	
	static final Logger LOG = LoggerFactory.getLogger(UpdateComputerController.class);
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	CompanyServiceImpl companyService;
	@Autowired
	ComputerValidator compVal;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest req, ModelMap map)
			throws ServletException, IOException {

		if(compVal.validateId(req.getParameter("id"))){
			
			ComputerDto compDto = computerMapper.toDto(computerService.getOneComputer(Integer.valueOf(req.getParameter("id"))));
			List<Company> companies = companyService.getAllCompanies();

			map.addAttribute("companies", companies);
			map.addAttribute("compDto", compDto);
			
			return "updateComputer";
		}else{
			return "redirect:/dashboard?msg=failUp";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute("compDto") ComputerDto compDto, BindingResult result, ModelMap map)
			throws ServletException, IOException {
		
		LOG.debug("compDto " + compDto.getId() + " " + compDto.getName() + compDto.getIntroduced() + compDto.getDiscontinued() + compDto.getCompanyId());
		
		if(!result.hasErrors()){
			computerService.updateComputer(computerMapper.fromDto(compDto));
			return "redirect:dashboard?msg=successUp";
		}else{
			List<Company> companies = companyService.getAllCompanies();
			
			map.put("result", result);
			map.put("companies", companies);
			map.put("compDto", compDto);
	        return "updateComputer";
		}
	}
}

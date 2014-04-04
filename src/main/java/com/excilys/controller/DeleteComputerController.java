package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/delete")
public class DeleteComputerController {

	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerValidator computerValidator;

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest req)
			throws ServletException, IOException {

		if (computerValidator.validateId(req.getParameter("id"))) {
			computerService.deleteComputer(Integer.valueOf(req.getParameter("id")));
			return "redirect:/dashboard?msg=successDel";
		} else {
			return "redirect:/dashboard?msg=failDel";
		}
	}
}

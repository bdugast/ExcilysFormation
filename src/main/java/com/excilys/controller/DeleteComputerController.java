package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/delete")
public class DeleteComputerController {

	@Autowired
	ComputerService computerService;
	@Autowired
	ComputerValidator computerValidator;

	
	/**
	 * method called when we push delete button, if the id is correct, it delete the computer and return a success message,
	 * if not, we return to the dashboard with a fail message.
	 * @param req
	 * 		req allow to get fields in the address bar
	 * @throws ServletException
	 * @throws IOException
	 */
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
	
	/**
	 * ExceptionHandler that redirect any error catch to a custom error page
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
}

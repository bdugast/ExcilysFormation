package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;
import com.excilys.validator.IdValidator;

@Controller
@RequestMapping("/delete")
public class DeleteComputerController {

	@Autowired
	ComputerService computerService;
	@Autowired
	IdValidator idValidator;

	
	/**
	 * method called when we push delete button, if the id is correct, it delete the computer and return a success message,
	 * if not, we return to the dashboard with a fail message.
	 * @param req
	 * 		req allow to get fields in the address bar
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(@RequestParam("id") String id)
			throws ServletException, IOException {

		if (idValidator.validateId(id)) {
			Computer comp = computerService.getOneComputer(Integer.valueOf(id));
			computerService.deleteComputer(comp);
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
		return "view/error";
	}
}

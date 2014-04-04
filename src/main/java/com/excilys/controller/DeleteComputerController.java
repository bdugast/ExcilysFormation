package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ModelAndView mav;

		if (computerValidator.validateId(req.getParameter("id"))) {
			computerService.deleteComputer(Integer.valueOf(req.getParameter("id")));
			mav = new ModelAndView("redirect:/dashboard");
			mav.addObject("msg", "successDel");
			return mav;
		} else {
			mav = new ModelAndView("redirect:/dashboard");
			mav.addObject("msg", "failDel");
			return mav;
		}
	}
}

package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

@WebServlet("/delete")
public class DeleteComputerServlet extends HttpServlet {
	
	@Autowired
	ComputerServiceImpl computerService;
	@Autowired
	ComputerValidator computerValidator;

	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(computerValidator.validateId(req.getParameter("id"))){			
			computerService.deleteComputer(Integer.valueOf(req.getParameter("id")));
			resp.sendRedirect("dashboard?msg=successDel");
		}else{
			resp.sendRedirect("dashboard?msg=failUp");
		}
	}
}

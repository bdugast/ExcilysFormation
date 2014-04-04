package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.domain.Computer;
import com.excilys.service.impl.ComputerServiceImpl;
import com.excilys.wrapper.PageWrapper;

@Controller
@RequestMapping("/dashboard")
public class DashboardController{
	
	@Autowired
	ComputerServiceImpl computerService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PageWrapper wrap = new PageWrapper();
		
		//Order field
		if(req.getParameter("orderField")!=null) wrap.setOrderField(req.getParameter("orderField"));
		
		//Order
		if(req.getParameter("order")!=null) wrap.setOrder(Boolean.valueOf(req.getParameter("order")));
				
		//Récupérer la page actuelle
		if(req.getParameter("page")!=null) wrap.setCurrentPage(Integer.valueOf(req.getParameter("page")));
		
		//Récupérer le champ recherche
		if(req.getParameter("search")!=null) wrap.setSearch(req.getParameter("search"));
		
		//Compter le nombre d'objet
		wrap.setCount(computerService.getCountComputerSearch(wrap.getSearch()));

		//Savoir le nombre de pages
		wrap.setCountPages((int) (Math.ceil((double)wrap.getCount()/(double)20)));
		
		//On modifie la page si la current page est trop haute ou trop basse
		if(wrap.getCurrentPage()>wrap.getCountPages()) wrap.setCurrentPage(wrap.getCountPages());
		if(wrap.getCurrentPage()<1) wrap.setCurrentPage(1);
		
		//Get 20 ordinateurs en fonction de la page with fucking limit and search
		List<Computer> computers = computerService.getRangeSearchOrderComputers(wrap);
		
		if(req.getParameter("msg")!=null){
			if(req.getParameter("msg").equals("successAdd")) req.setAttribute("valide", "Computer successfully add");
			if(req.getParameter("msg").equals("successUp")) req.setAttribute("valide", "Computer successfully update");
			if(req.getParameter("msg").equals("successDel")) req.setAttribute("valide", "Computer successfully delete");
			if(req.getParameter("msg").equals("failUp")) req.setAttribute("fail", "FAIL!!! Invalid computer!!!");
			if(req.getParameter("msg").equals("failDel")) req.setAttribute("fail", "FAIL!!! Invalid computer!!!");
		}
		
		req.setAttribute("wrap", wrap);
		req.setAttribute("computers", computers);

		
		ModelAndView mav = new ModelAndView("dashboard");
		mav.addObject("req", req);
		return mav;
		}
}

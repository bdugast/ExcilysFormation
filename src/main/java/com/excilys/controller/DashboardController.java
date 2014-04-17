package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.dto.ComputerDto;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;


/**
 * @author bdugast
 *  
 *
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController{
	
	@Autowired
	ComputerService computerService;
	@Autowired
	ComputerMapper computerMapper;
	
	/**
	 * This doGet method get all informations in the wrap in order to show the good computers on the screen
	 * get the order field, the order, the current page, the search string, and get the count of computer
	 * to show the good number of page in the pagination, and a list of computerDto
	 * @param req
	 * 		req allow to get fields in the address bar
	 * @param map
	 * 		preparation of the return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest req, ModelMap map)
			throws ServletException, IOException {
		PageWrapper wrap = new PageWrapper();
		
		//get order field
		if(req.getParameter("orderField")!=null) wrap.setOrderField(req.getParameter("orderField"));
		
		//get order
		if(req.getParameter("order")!=null) wrap.setOrder(Boolean.valueOf(req.getParameter("order")));
				
		//get current page
		if(req.getParameter("page")!=null) wrap.setCurrentPage(Integer.valueOf(req.getParameter("page")));
		
		//get search field
		if(req.getParameter("search")!=null) wrap.setSearch(req.getParameter("search"));
		
		//get the number of computer
		wrap.setCount(computerService.getCountComputers(wrap.getSearch()));

		//get the number of pages
		wrap.setCountPages((int) (Math.ceil((double)wrap.getCount()/(double)20)));
		
		//change the current page if this one is too high or too low
		if(wrap.getCurrentPage()>wrap.getCountPages()) wrap.setCurrentPage(wrap.getCountPages());
		if(wrap.getCurrentPage()<1) wrap.setCurrentPage(1);
		
		//get 20 computer with the search, the order field, the order, and the limit
		List<ComputerDto> computers = computerMapper.toListCompDto(computerService.getRangeComputers(wrap));
		
		if(req.getParameter("msg")!=null){
			if(req.getParameter("msg").equals("successAdd")) map.addAttribute("valide", "Computer successfully add");
			if(req.getParameter("msg").equals("successUp")) map.addAttribute("valide", "Computer successfully update");
			if(req.getParameter("msg").equals("successDel")) map.addAttribute("valide", "Computer successfully delete");
			if(req.getParameter("msg").equals("failUp")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
			if(req.getParameter("msg").equals("failDel")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
		}
		
		map.addAttribute("wrap", wrap);
		map.addAttribute("computers", computers);
		
		return "dashboard";
		}
	
	
	/**
	 * ExceptionHandler that redirect any error catch to a custom error page
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
}

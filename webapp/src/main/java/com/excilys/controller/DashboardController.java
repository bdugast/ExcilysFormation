package com.excilys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.domain.Computer;
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
	 * @param map
	 * 		preparation of the return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap map, 
			@RequestParam(value="orderField", required=false) String orderField,
			@RequestParam(value="order", required=false) Boolean order,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="msg", required=false) String msg
			)
			throws IOException {
		PageWrapper wrap = new PageWrapper();

		//get order field
		if(orderField!=null)
			wrap.setOrderField(orderField);

		//get order
		if(order!=null)
			wrap.setOrder(order);

		//get current page
		if(page!=null)
			wrap.setCurrentPage(page);

		//get search field
		if(search!=null)
			wrap.setSearch(search);

		//get the number of computer
		wrap.setCount(computerService.getCountComputers(wrap.getSearch()));

		//get the number of pages
		wrap.setCountPages((int) (Math.ceil((double)wrap.getCount()/(double)20)));

		//change the current page if this one is too high or too low
		if(wrap.getCurrentPage()>wrap.getCountPages())
			wrap.setCurrentPage(wrap.getCountPages());
		if(wrap.getCurrentPage()<1)
			wrap.setCurrentPage(1);
		
		//get 20 computer with the search, the order field, the order, and the limit
		List<Computer> comps = computerService.getListComputer(wrap.getOrderField(), wrap.getOrder(), wrap.getCurrentPage(), wrap.getSearch(), wrap.NB_COMPUTER_BY_PAGE);
		System.out.println("coucou " + comps);
		wrap.setComputers(computerMapper.toListCompDto(comps));
		
		
		if(msg!=null){
			if(msg.equals("successAdd")) map.addAttribute("valide", "Computer successfully add");
			if(msg.equals("successUp")) map.addAttribute("valide", "Computer successfully update");
			if(msg.equals("successDel")) map.addAttribute("valide", "Computer successfully delete");
			if(msg.equals("failUp")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
			if(msg.equals("failDel")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
		}
		
		map.addAttribute("wrap", wrap);
		
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

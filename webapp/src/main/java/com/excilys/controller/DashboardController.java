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
		
		PageWrapper wrap = computerService.getPage(orderField, order, page, search);
		
		//get 20 computer with the search, the order field, the order, and the limit
		List<ComputerDto> computers = computerMapper.toListCompDto(wrap.getComputers());
		
		if(msg!=null){
			if(msg.equals("successAdd")) map.addAttribute("valide", "Computer successfully add");
			if(msg.equals("successUp")) map.addAttribute("valide", "Computer successfully update");
			if(msg.equals("successDel")) map.addAttribute("valide", "Computer successfully delete");
			if(msg.equals("failUp")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
			if(msg.equals("failDel")) map.addAttribute("fail", "FAIL!!! Invalid computer!!!");
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

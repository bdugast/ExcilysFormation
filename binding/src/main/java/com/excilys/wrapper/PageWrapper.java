package com.excilys.wrapper;

import java.util.List;

import com.excilys.dto.ComputerDto;

/**
 * @author bdugast
 * @param order
 * 		order desc or asc
 * @param orderField
 * 		field to order
 * @param search
 * 		field of search
 * @param count
 * 		count of computers in the DB
 * @param countPages
 * 		number of pages needed
 * @param currentPage
 * 		current page number
 */
public class PageWrapper {
	public final int NB_COMPUTER_BY_PAGE = 20;
	private boolean order;
	private String orderField;
	private String search;
	private int count;
    private int countPages;
    private int currentPage;
    private List<ComputerDto> computers;
       
	public PageWrapper() {
		this.order = false; 
		this.orderField = "";
		this.search = "";
		this.count = 0;
		this.countPages = 0;
		this.currentPage = 1;
	}
	
	public PageWrapper(boolean order, String orderField, String search,
			int count, int countPages, int currentPage) {
		this.order = order;
		this.orderField = orderField;
		this.search = search;
		this.count = count;
		this.countPages = countPages;
		this.currentPage = currentPage;
	}
	
	public boolean getOrder() {
		return order;
	}
	public void setOrder(boolean order) {
		this.order = order;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCountPages() {
		return countPages;
	}
	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<ComputerDto> getComputers() {
		return computers;
	}
	public void setComputers(List<ComputerDto> computers) {
		this.computers = computers;
	}    
}

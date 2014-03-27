package main.java.com.excilys.wrapper;

public class PageWrapper {
	public final static int NB_COMPUTER_BY_PAGE = 20;
	private Boolean order;
	private String orderField;
	private String search;
	private int count;
    private int countPages;
    private int currentPage;
       
	public PageWrapper() {
		this.order = false; 
		this.orderField = "";
		this.search = "";
		this.count = 0;
		this.countPages = 0;
		this.currentPage = 1;
	}
	
	public PageWrapper(Boolean order, String orderField, String search,
			int count, int countPages, int currentPage) {
		this.order = order;
		this.orderField = orderField;
		this.search = search;
		this.count = count;
		this.countPages = countPages;
		this.currentPage = currentPage;
	}
	
	public Boolean getOrder() {
		return order;
	}
	public void setOrder(Boolean order) {
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
}

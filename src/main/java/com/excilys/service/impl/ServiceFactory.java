package main.java.com.excilys.service.impl;


public class ServiceFactory {
	private static ServiceFactory SF = null;
	private static ComputerServiceImpl computerService = null;
	private static CompanyServiceImpl companyService = null;

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		if (SF == null) {
			SF = new ServiceFactory();
		}
		return SF;
	}
	
	public static ComputerServiceImpl getComputerService(){
		if(computerService == null)	computerService = new ComputerServiceImpl();
		return computerService;
	}
	
	public static CompanyServiceImpl getCompanyService(){
		if(companyService == null)	companyService = new CompanyServiceImpl();
		return companyService;
	}
}

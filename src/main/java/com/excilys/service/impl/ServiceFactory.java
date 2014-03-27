package main.java.com.excilys.service.impl;


public enum ServiceFactory {
	INSTANCE;
		
	public ComputerServiceImpl getComputerService(){
		return ComputerServiceImpl.INSTANCE;
	}
	
	public CompanyServiceImpl getCompanyService(){
		return CompanyServiceImpl.INSTANCE;
	}
}

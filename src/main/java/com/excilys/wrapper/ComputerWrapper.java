package main.java.com.excilys.wrapper;

import java.util.List;

import main.java.com.excilys.domain.Computer;

public class ComputerWrapper {
	List<Computer> computers;

	public ComputerWrapper() {
		super();
	}
	public ComputerWrapper(List<Computer> computers) {
		super();
		this.computers = computers;
	}

	public List<Computer> getComputers() {
		return computers;
	}
	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
}

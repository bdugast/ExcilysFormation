package com.excilys.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.domain.Computer;

public interface ComputerDao extends CrudRepository<Computer, Integer>{
	
	Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName, Pageable pageable);
}

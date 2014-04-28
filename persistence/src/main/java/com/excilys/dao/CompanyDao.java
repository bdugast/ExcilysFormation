package com.excilys.dao;

import org.springframework.data.repository.CrudRepository;

import com.excilys.domain.Company;

public interface CompanyDao extends CrudRepository<Company, Integer>{
}

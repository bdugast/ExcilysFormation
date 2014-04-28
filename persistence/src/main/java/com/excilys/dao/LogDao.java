package com.excilys.dao;

import org.springframework.data.repository.CrudRepository;

import com.excilys.domain.Log;


public interface LogDao extends CrudRepository<Log, Long>{
}

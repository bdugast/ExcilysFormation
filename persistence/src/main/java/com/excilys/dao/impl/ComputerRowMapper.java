package com.excilys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Computer comp = new Computer();
		comp.setId(rs.getInt(1));
		comp.setName(rs.getString(2));
		if(rs.getTimestamp(3)==null) comp.setIntroduced(null);
		else comp.setIntroduced(new DateTime(rs.getTimestamp(3)));				
		if(rs.getTimestamp(4)==null) comp.setDiscontinued(null);
		else comp.setDiscontinued(new DateTime(rs.getTimestamp(4)));
		comp.setCompany(new Company(rs.getInt(5), rs.getString(6)));
		return comp;
	}

}

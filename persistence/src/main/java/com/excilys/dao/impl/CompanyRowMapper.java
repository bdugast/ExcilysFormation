package com.excilys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.domain.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company comp = new Company();
		comp.setId(rs.getInt(1));
		comp.setName(rs.getString(2));
		return comp;
	}

}

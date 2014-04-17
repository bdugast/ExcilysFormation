package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.dao.CompanyDao;
import com.excilys.domain.Company;
import com.excilys.exception.CustomException;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
	@Autowired
	BoneCPDataSource boneCP;
	
	@Override
	public Company getOneCompany(int id){
		Company comp = null;
		Connection conn = DataSourceUtils.getConnection(boneCP);
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement("select id, name from company where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				comp = new Company();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
			}

			LOG.debug("company : " + comp);
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}
		return comp;
	}
	
	@Override
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<Company>();
		Connection conn = DataSourceUtils.getConnection(boneCP);
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, name from company");
			
			while(rs.next()){
				Company comp = new Company(rs.getInt("id"),rs.getString("name"));
				comps.add(comp);
			}
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}
		return comps;
	}
}

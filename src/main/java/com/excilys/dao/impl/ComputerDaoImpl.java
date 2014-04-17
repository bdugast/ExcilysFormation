package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.dao.ComputerDao;
import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.exception.CustomException;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	@Autowired
	BoneCPDataSource boneCP;

	@Override
	public Computer getOneComputer(int id) {
		LOG.trace("start getOneComputer id=" + id);
		Computer comp = null;
		Connection conn =  DataSourceUtils.getConnection(boneCP);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try{
			String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.id=?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setInt(1, id);
			LOG.debug("requete stmt " + stmt.toString());
			rs = stmt.executeQuery();
	
			while (rs.next()) {
				comp = new Computer();
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				if(rs.getTimestamp(3)==null) comp.setIntroduced(null);
				else comp.setIntroduced(dtf.parseDateTime(rs.getString(3)));				
				if(rs.getTimestamp(4)==null) comp.setDiscontinued(null);
				else comp.setDiscontinued(new DateTime(rs.getTimestamp(4)));
				comp.setCompany(new Company(rs.getInt(5), rs.getString(6)));
			}

		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}
		return comp;
	}
	
	@Override
	public int createComputer(Computer comp) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int key = 0;
		
		try{
			String req = "insert into computer values(null,?,?,?,?)";
			LOG.debug("requete SQL : " + req);
			stmt =  DataSourceUtils.getConnection(boneCP).prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, comp.getName());
			
			if (comp.getIntroduced() == null)
				stmt.setNull(2, Types.NULL);
			else
				stmt.setTimestamp(2, new Timestamp(comp.getIntroduced().getMillis()));
			
			if (comp.getDiscontinued() == null)
				stmt.setNull(3, Types.NULL);
			else
				stmt.setTimestamp(3, new Timestamp(comp.getDiscontinued().getMillis()));
			
			if (comp.getCompany() == null || comp.getCompany().getId() == 0)
				stmt.setNull(4, Types.NULL);
			else
				stmt.setObject(4, comp.getCompany().getId());
			
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt(1);
			}	
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}
		return key;
	}

	@Override
	public void updateComputer(Computer comp){
		LOG.trace("Start updateComputer");
		PreparedStatement stmt = null;
		
		try{
			String req = "update computer set name=?, introduced=?, discontinued=?, company_id=? where computer.id=?";
			LOG.debug("requete SQL : " + req);
			stmt =  DataSourceUtils.getConnection(boneCP).prepareStatement(req);
			stmt.setString(1, comp.getName());
			if (comp.getIntroduced() == null)
				stmt.setNull(2, Types.NULL);
			else
				stmt.setTimestamp(2, new Timestamp(comp.getIntroduced().getMillis()));
			if (comp.getDiscontinued() == null)
				stmt.setNull(3, Types.NULL);
			else
				stmt.setTimestamp(3, new Timestamp(comp.getDiscontinued().getMillis()));
			if (comp.getCompany() == null){
				stmt.setNull(4, Types.NULL);
			}else
				stmt.setObject(4, comp.getCompany().getId());
			stmt.setInt(5, comp.getId());
			
			LOG.debug("requete stmt : " + stmt);
			stmt.executeUpdate();
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(null, stmt);			
		}
	}

	@Override
	public void deleteComputer(int id) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;
		try{
			String req = "delete from computer where id=?";
			LOG.debug("requete SQL : " + req);
			stmt =  DataSourceUtils.getConnection(boneCP).prepareStatement(req);
			stmt.setInt(1, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.executeUpdate();
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(null, stmt);			
		}
	}

	@Override
	public List<Computer> getRangeComputers(int start, int nb,	String search, String orderby){
		LOG.trace("Start getRangeSearchOrderComputers");
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn =  DataSourceUtils.getConnection(boneCP);
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuilder searchWord = new StringBuilder("%" + search + "%");
		try{
			StringBuilder req = new StringBuilder("select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.name like ? or ca.name like ? order by "+orderby+" limit ?,?");
			LOG.debug("requete SQL : " + req.toString());
			stmt = conn.prepareStatement(req.toString());
			stmt.setString(1, searchWord.toString());
			stmt.setString(2, searchWord.toString());
			stmt.setInt(3, start);
			stmt.setInt(4, nb);
			LOG.debug("requete stmt : " + stmt);
			rs = stmt.executeQuery();
	
			while (rs.next()) {
				Computer comp = new Computer();
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				if(rs.getTimestamp(3)==null) comp.setIntroduced(null);
				else comp.setIntroduced(new DateTime(rs.getTimestamp(3)));
				if(rs.getTimestamp(4)==null) comp.setDiscontinued(null);
				else comp.setDiscontinued(new DateTime(rs.getTimestamp(4)));
				comp.setCompany(new Company(rs.getInt(5), rs.getString(6)));
	
				comps.add(comp);
			}
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}

		return comps;
	}

	@Override
	public int getCountComputers(String search){
		int count = 0;
		Connection conn =  DataSourceUtils.getConnection(boneCP);
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuilder searchWord = new StringBuilder("%" + search + "%");
		try{	
			String req = "select count(*) from computer as cu left join company as ca on cu.company_id=ca.id where cu.name like ? or ca.name like ?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setString(1, searchWord.toString());
			stmt.setString(2, searchWord.toString());
			LOG.debug("requete stmt : " + stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		}catch(SQLException e){
			throw new CustomException("erreur SQL", e.getMessage());
		}finally{
			ConnectionManager.closeDAO(rs, stmt);			
		}
		return count;
	}
}

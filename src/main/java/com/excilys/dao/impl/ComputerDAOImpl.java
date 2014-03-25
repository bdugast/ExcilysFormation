package main.java.com.excilys.dao.impl;


import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.dao.ComputerDAO;
import main.java.com.excilys.domain.Company;
import main.java.com.excilys.domain.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerDAOImpl implements ComputerDAO{

	static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	protected ComputerDAOImpl() {
	}
	
	public Computer getOneComputer(int id){
		LOG.trace("start getOneComputer id="+id);
		Computer comp = new Computer();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null; 
		PreparedStatement stmt = null;
		try {
			LOG.debug("try catch de la requête");
			String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.id=?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setInt(1, id);
			LOG.debug("requete stmt " + stmt.toString());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				comp.setIntroduced(rs.getDate(3));
				comp.setDiscontinued(rs.getDate(4));
				comp.setCompany(new Company(rs.getInt(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			LOG.error("catch sqlException");
			e.printStackTrace();
		}finally{
			LOG.trace("Finally, close all");
			DaoFactory.closeAll(conn, rs, stmt);
		}
		return comp;
	}
	
	public List<Computer> getAllComputer(){
		LOG.trace("Start getAllComputer");
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id order by cu.id DESC";
			LOG.debug("requete SQL : " + req);
			rs = stmt.executeQuery(req);
			
			while(rs.next()){
				Computer comp = new Computer();
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				comp.setIntroduced(rs.getDate(3));
				comp.setDiscontinued(rs.getDate(4));
				comp.setCompany(new Company(rs.getInt(5),rs.getString(6)));
				comps.add(comp);
			}
		} catch (SQLException e) {
			LOG.error("catch sqlException : " +e);
		}finally{
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.closeAll(conn, rs, stmt);
		}

		return comps;
	}
	
	public void createComputer(Computer comp){
		LOG.trace("Start createComputer");
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String req = "insert into computer values(null,?,?,?,?)";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setString(1, comp.getName());
			if(comp.getIntroduced()==null) stmt.setNull(2, Types.NULL);
			else stmt.setDate(2, comp.getIntroduced());
			if(comp.getDiscontinued()==null) stmt.setNull(3, Types.NULL);
			else stmt.setDate(3, comp.getDiscontinued());
			if(comp.getCompany().getId()==0) stmt.setNull(4, Types.NULL);
			else stmt.setObject(4, comp.getCompany().getId());
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
		} catch (SQLException e) {
			LOG.error("catch sqlException");
			e.printStackTrace();
		}finally{
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.closeAll(conn, null, stmt);
		}
	}

	public void updateComputer(Computer comp){
		LOG.trace("Start updateComputer");
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String req = "update computer set name=?, introduced=?, discontinued=?, company_id=? where computer.id=?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setString(1, comp.getName());
			if(comp.getIntroduced()==null) stmt.setNull(2, Types.NULL);
			else stmt.setDate(2, comp.getIntroduced());
			if(comp.getDiscontinued()==null) stmt.setNull(3, Types.NULL);
			else stmt.setDate(3, comp.getDiscontinued());
			LOG.debug("company ID : " + comp.getCompany().getId());
			if(comp.getCompany().getId()==0) stmt.setNull(4, Types.NULL);
			else stmt.setObject(4, comp.getCompany().getId());
			stmt.setInt(5, comp.getId());
			LOG.debug("requete stmt : " + stmt);
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error("catch sqlException"+e);
		}finally{
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.closeAll(conn, null, stmt);
		}
	}
	
	public void deleteComputer(int id){
		LOG.trace("Start createComputer");
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String req = "delete from computer where id=?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setInt(1, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error("catch sqlException");
			e.printStackTrace();
		}finally{
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.closeAll(conn, null, stmt);
		}
	}

	@Override
	public List<Computer> getRangeComputers(int start, int nb) {
		LOG.trace("Start getAllComputer");
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id limit ?,?";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setInt(1, start);
			stmt.setInt(2, nb);
			LOG.debug("requete stmt : " + stmt);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Computer comp = new Computer();
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				comp.setIntroduced(rs.getDate(3));
				comp.setDiscontinued(rs.getDate(4));
				comp.setCompany(new Company(rs.getInt(5),rs.getString(6)));
				comps.add(comp);
			}
		} catch (SQLException e) {
			LOG.error("catch sqlException : " +e);
		}finally{
			LOG.trace("Finally getRangeComputers");
			DaoFactory.closeAll(conn, rs, stmt);
		}

		return comps;
	}

	public int getCountComputer() {
		int count = 0;
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from computer");
			
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOG.error("catch sqlException : " +e);
		}finally{
			LOG.trace("Finally getRangeComputers");
			DaoFactory.closeAll(conn, rs, stmt);
		}
		return count;
	}
}

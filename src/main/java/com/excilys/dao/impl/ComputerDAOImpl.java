package main.java.com.excilys.dao.impl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		LOG.debug("start getOneComputer id="+id);
		Computer comp = new Computer();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null; 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu join company as ca on cu.company_id=ca.id where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				comp.setId(rs.getInt(1));
				comp.setName(rs.getString(2));
				comp.setIntroduced(rs.getDate(3));
				comp.setDiscontinued(rs.getDate(4));
				comp.setCompany(new Company(rs.getInt(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, rs, stmt);
		}
		return comp;
	}
	
	public List<Computer> getAllComputer(){
		if (LOG.isTraceEnabled()) {
			LOG.trace("Test: TRACE level message.");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Test: DEBUG level message.");
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Test: INFO level message.");
		}
		if (LOG.isWarnEnabled()) {
			LOG.warn("Test: WARN level message.");
		}
		if (LOG.isErrorEnabled()) {
			LOG.error("Test: ERROR level message.");
		}
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			LOG.debug("Start getAllComputer");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id order by cu.id");
			
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
			e.printStackTrace();
		}finally{
			LOG.debug("Finally getAllComputer ListComputer");
			DaoFactory.closeAll(conn, rs, stmt);
		}

		return comps;
	}
	

	public void updateComputer(Computer comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("udpate computer set name=?, introduced=?, discontinued=?, company_id=? where id=?");
			stmt.setString(1, comp.getName());
			stmt.setDate(2, (Date) comp.getIntroduced());
			stmt.setDate(3, (Date) comp.getDiscontinued());
			stmt.setObject(4, comp.getCompany());
			stmt.setInt(5, comp.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, null, stmt);
		}
	}
	
	public void createComputer(Computer comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("insert into computer values(null,?,?,?,?)");
			stmt.setString(1, comp.getName());
			stmt.setDate(2, (Date) comp.getIntroduced());
			stmt.setDate(3, (Date) comp.getDiscontinued());
			stmt.setObject(4, comp.getCompany());
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, null, stmt);
		}
	}
	
	public void deleteComputer(int id){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from computer where id=?)");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, null, stmt);
		}
	}
}

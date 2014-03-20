package org.excilys.DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.excilys.domain.Computer;

public class ComputerDAO {
	
	protected ComputerDAO() {
	}
	
	public Computer getOneComputer(int id){
		Computer comp = new Computer();
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select * from computer where id=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setIntroduced(rs.getDate("introduced"));
				comp.setDiscontinued(rs.getDate("discontinued"));
				comp.setCompanyId(rs.getInt("company_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return comp;
	}
	
	public List<Computer> getAllComputer(){
		List<Computer> comps = new ArrayList<>();
		Connection conn = DaoFactory.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from computer");
			
			while(rs.next()){
				Computer comp = new Computer();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setIntroduced(rs.getDate("introduced"));
				comp.setDiscontinued(rs.getDate("discontinued"));
				comp.setCompanyId(rs.getInt("company_id"));
				comps.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}

		return comps;
	}
	

	public int updateComputer(Computer comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("udpate computer set name=?, introduced=?, discontinued=?, company_id=? where id=?");
			stmt.setString(1, comp.getName());
			stmt.setDate(2, (Date) comp.getIntroduced());
			stmt.setDate(3, (Date) comp.getDiscontinued());
			stmt.setInt(4, comp.getCompanyId());
			stmt.setInt(5, comp.getId());
			
			nbRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return nbRows;
	}
	
	public int addComputer(Computer comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("insert into computer values(null,?,?,?,?)");
			stmt.setString(1, comp.getName());
			stmt.setDate(2, (Date) comp.getIntroduced());
			stmt.setDate(3, (Date) comp.getDiscontinued());
			stmt.setInt(4, comp.getCompanyId());
			
			nbRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return nbRows;
	}
	
	public int deleteComputer(Computer comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("delete from computer where id=?)");
			stmt.setInt(1, comp.getId());
			nbRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return nbRows;
	}
}

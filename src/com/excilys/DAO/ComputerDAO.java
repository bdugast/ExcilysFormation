package com.excilys.DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerDAO {
	
	protected ComputerDAO() {
	}
	
	public Computer getOneComputer(int id){
		Computer comp = new Computer();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null; 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select cu.id as cuid, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name as companyName ca from computer cu inner join company as ca on cu.company_id=ca.id where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setIntroduced(rs.getDate("introduced"));
				comp.setDiscontinued(rs.getDate("discontinued"));
				comp.setCompany(new Company(rs.getInt("company_id"),rs.getString("companyName")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, rs, stmt);
		}
		return comp;
	}
	
	public List<Computer> getAllComputer(){
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn = DaoFactory.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select cu.id as cuid, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name as companyName ca from computer cu inner join company as ca on cu.company_id=ca.id");
			
			while(rs.next()){
				Computer comp = new Computer();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setIntroduced(rs.getDate("introduced"));
				comp.setDiscontinued(rs.getDate("discontinued"));
				comp.setCompany(new Company(rs.getInt("company_id"),rs.getString("companyName")));
				comps.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
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

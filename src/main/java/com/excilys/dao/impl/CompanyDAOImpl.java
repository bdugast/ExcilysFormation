package main.java.com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.dao.CompanyDAO;
import main.java.com.excilys.domain.Company;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;
	
	public Company getOneCompany(int id){
		Company comp = null;
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select id, name from company where id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				comp = new Company();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.INSTANCE.closeAll(conn, rs, stmt);
		}
		return comp;
	}
	
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<Company>();
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, name from company");
			
			while(rs.next()){
				Company comp = new Company(rs.getInt("id"),rs.getString("name"));
				comps.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.INSTANCE.closeAll(conn, rs, stmt);
		}

		return comps;
	}

	public void updateCompany(Company comp){
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("udpate company set name=? where id=?");
			stmt.setString(1, comp.getName());
			stmt.setInt(5, comp.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.INSTANCE.closeAll(conn, null, stmt);
		}
	}
	
	public void createCompany(Company comp){
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement("insert into company values(null,?)");
			stmt.setString(1, comp.getName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.INSTANCE.closeAll(conn, null, stmt);
		}
	}
	
	public void deleteCompany(int id){
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement("delete from company where id=?)");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.INSTANCE.closeAll(conn, null, stmt);
		}
	}
}

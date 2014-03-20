package org.excilys.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.excilys.domain.Company;

public class CompanyDAO {
	protected CompanyDAO() {
	}
	
	public Company getOneCompany(int id){
		Company comp = new Company();
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select * from company where id=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return comp;
	}
	
	public List<Company> getAllCompany(){
		List<Company> comps = new ArrayList<>();
		Connection conn = DaoFactory.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from company");
			
			while(rs.next()){
				Company comp = new Company();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comps.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}

		return comps;
	}
	

	public int updateCompany(Company comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("udpate company set name=? where id=?");
			stmt.setString(1, comp.getName());
			stmt.setInt(5, comp.getId());
			
			nbRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return nbRows;
	}
	
	public int addCompany(Company comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("insert into company values(null,?)");
			stmt.setString(1, comp.getName());
			
			nbRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DaoFactory.closeAll(conn, stmt);
		}
		return nbRows;
	}
	
	public int deleteCompany(Company comp){
		Connection conn = DaoFactory.getConnection();
		PreparedStatement stmt = null;
		int nbRows = 0;
		
		try {
			stmt = conn.prepareStatement("delete from company where id=?)");
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

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
	
	public Company getOneCompany(int id) throws SQLException{
		Company comp = null;
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement("select id, name from company where id=?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		
		while(rs.next()){
			comp = new Company();
			comp.setId(rs.getInt("id"));
			comp.setName(rs.getString("name"));
		}
		DaoFactory.INSTANCE.closeDAO(rs, stmt);
		return comp;
	}
	
	public List<Company> getAllCompany() throws SQLException{
		List<Company> comps = new ArrayList<Company>();
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select id, name from company");
		
		while(rs.next()){
			Company comp = new Company(rs.getInt("id"),rs.getString("name"));
			comps.add(comp);
		}
		DaoFactory.INSTANCE.closeDAO(rs, stmt);
		return comps;
	}

	public void updateCompany(Company comp) throws SQLException{
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;
		
		stmt = conn.prepareStatement("udpate company set name=? where id=?");
		stmt.setString(1, comp.getName());
		stmt.setInt(5, comp.getId());
		stmt.executeUpdate();
		DaoFactory.INSTANCE.closeDAO(null, stmt);
	}
	
	public void createCompany(Company comp) throws SQLException{
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;

		stmt = conn.prepareStatement("insert into company values(null,?)");
		stmt.setString(1, comp.getName());
		stmt.executeUpdate();
		DaoFactory.INSTANCE.closeDAO(null, stmt);
	}
	
	public void deleteCompany(int id) throws SQLException{
		Connection conn = DaoFactory.INSTANCE.getConnection();
		PreparedStatement stmt = null;
		
		stmt = conn.prepareStatement("delete from company where id=?)");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		DaoFactory.INSTANCE.closeDAO(null, stmt);
	}
}

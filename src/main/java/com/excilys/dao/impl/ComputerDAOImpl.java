package main.java.com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.dao.ComputerDAO;
import main.java.com.excilys.domain.Company;
import main.java.com.excilys.domain.Computer;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAOImpl.class);

	public Computer getOneComputer(int id) throws SQLException {
		LOG.trace("start getOneComputer id=" + id);
		Computer comp = new Computer();
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.id=?";
		LOG.debug("requete SQL : " + req);
		stmt = conn.prepareStatement(req);
		stmt.setInt(1, id);
		LOG.debug("requete stmt " + stmt.toString());
		rs = stmt.executeQuery();

		while (rs.next()) {
			comp.setId(rs.getInt(1));
			comp.setName(rs.getString(2));
			if(rs.getTimestamp(3)==null) comp.setIntroduced(null);
			else comp.setIntroduced(new DateTime(rs.getTimestamp(3)));
			if(rs.getTimestamp(4)==null) comp.setDiscontinued(null);
			else comp.setDiscontinued(new DateTime(rs.getTimestamp(4)));
			comp.setCompany(new Company(rs.getInt(5), rs.getString(6)));
		}
		DaoFactory.INSTANCE.closeDAO(rs, stmt);
		return comp;
	}

	public int createComputer(Computer comp) throws SQLException {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int key = 0;

		String req = "insert into computer values(null,?,?,?,?)";
		LOG.debug("requete SQL : " + req);
		stmt = DaoFactory.INSTANCE.getConnection().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, comp.getName());
		
		if (comp.getIntroduced() == null)
			stmt.setNull(2, Types.NULL);
		else
			stmt.setTimestamp(2, new Timestamp(comp.getIntroduced().getMillis()));
		
		if (comp.getDiscontinued() == null)
			stmt.setNull(3, Types.NULL);
		else
			stmt.setTimestamp(3, new Timestamp(comp.getDiscontinued().getMillis()));
		
		if (comp.getCompany().getId() == 0)
			stmt.setNull(4, Types.NULL);
		else
			stmt.setObject(4, comp.getCompany().getId());
		
		LOG.debug("requete stmt : " + stmt);
		stmt.execute();
		
		rs = stmt.getGeneratedKeys();
		if(rs.next()){
			key = rs.getInt(1);
		}	
		DaoFactory.INSTANCE.closeDAO(rs, stmt);
		return key;
	}

	public void updateComputer(Computer comp) throws SQLException {
		LOG.trace("Start updateComputer");
		PreparedStatement stmt = null;

		String req = "update computer set name=?, introduced=?, discontinued=?, company_id=? where computer.id=?";
		LOG.debug("requete SQL : " + req);
		stmt = DaoFactory.INSTANCE.getConnection().prepareStatement(req);
		stmt.setString(1, comp.getName());
		if (comp.getIntroduced() == null)
			stmt.setNull(2, Types.NULL);
		else
			stmt.setTimestamp(2, new Timestamp(comp.getIntroduced().getMillis()));
		if (comp.getDiscontinued() == null)
			stmt.setNull(3, Types.NULL);
		else
			stmt.setTimestamp(3, new Timestamp(comp.getDiscontinued().getMillis()));
		LOG.debug("company ID : " + comp.getCompany().getId());
		if (comp.getCompany().getId() == 0)
			stmt.setNull(4, Types.NULL);
		else
			stmt.setObject(4, comp.getCompany().getId());
		stmt.setInt(5, comp.getId());
		LOG.debug("requete stmt : " + stmt);
		stmt.executeUpdate();
	
		DaoFactory.INSTANCE.closeDAO(null, stmt);
	}

	public void deleteComputer(int id) throws SQLException {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;

		String req = "delete from computer where id=?";
		LOG.debug("requete SQL : " + req);
		stmt = DaoFactory.INSTANCE.getConnection().prepareStatement(req);
		stmt.setInt(1, id);
		LOG.debug("requete stmt : " + stmt);
		stmt.executeUpdate();
	
		DaoFactory.INSTANCE.closeDAO(null, stmt);
	}

	@Override
	public List<Computer> getRangeSearchOrderComputers(int start, int nb,	String search, String orderby) throws SQLException {
		LOG.trace("Start getRangeSearchComputer");
		List<Computer> comps = new ArrayList<Computer>();
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuilder searchWord = new StringBuilder("%" + search + "%");

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
	
		DaoFactory.INSTANCE.closeDAO(rs, stmt);

		return comps;
	}

	@Override
	public int getCountComputerSearch(String search) throws SQLException {
		int count = 0;
		Connection conn = DaoFactory.INSTANCE.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuilder searchWord = new StringBuilder("%" + search + "%");

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
		DaoFactory.INSTANCE.closeDAO(rs, stmt);

		return count;
	}
}

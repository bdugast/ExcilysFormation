package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.dao.ComputerDao;
import com.excilys.domain.Computer;
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

		String req = "select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.id=?";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		comp = jdbctemp.queryForObject(req, new Object[] { id }, new ComputerRowMapper());
		
		return comp;
	}
	
	@Override
	public int createComputer(final Computer comp) {
		LOG.trace("Start createComputer");
		final String req = "insert into computer values(null,?,?,?,?)";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbctemp.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement stmt = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
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

				return stmt;
			}
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public void updateComputer(Computer comp){
		LOG.trace("Start updateComputer");

		String req = "update computer set name=?, introduced=?, discontinued=?, company_id=? where computer.id=?";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		Object[] obj = new Object[5];
		obj[0] = comp.getName();
		
		if (comp.getIntroduced() == null) obj[1] = Types.NULL;
		else obj[1] = new Timestamp(comp.getIntroduced().getMillis());
		
		if (comp.getDiscontinued() == null) obj[2] = Types.NULL;
		else obj[2] = new Timestamp(comp.getDiscontinued().getMillis());
			
		LOG.debug(""+comp.getCompany());
		if (comp.getCompany() == null) obj[3] = null;
		else obj[3] = comp.getCompany().getId();
		
		obj[4] = comp.getId();
		
		LOG.debug(""+obj[3]);
		
		jdbctemp.update(req, obj);
	}

	@Override
	public void deleteComputer(int id) {
		String req = "delete from computer where id=?";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		jdbctemp.update(req, new Object[] { id });
	}

	@Override
	public List<Computer> getRangeComputers(int start, int nb,	String search, String orderby){
		LOG.trace("Start getRangeSearchOrderComputers");
		List<Computer> comps = new ArrayList<Computer>();

		StringBuilder searchWord = new StringBuilder("%" + search + "%");
		StringBuilder req = new StringBuilder("select cu.id, cu.name, cu.introduced, cu.discontinued, cu.company_id, ca.name ca from computer as cu left join company as ca on cu.company_id=ca.id where cu.name like ? or ca.name like ? order by "+orderby+" limit ?,?");
		
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		comps = jdbctemp.query(req.toString(), new Object[] { searchWord, searchWord, start, nb },  new ComputerRowMapper());
		
		return comps;
	}

	@Override
	public int getCountComputers(String search){
		int count = 0;
		StringBuilder searchWord = new StringBuilder("%" + search + "%");
		String req = "select count(*) from computer as cu left join company as ca on cu.company_id=ca.id where cu.name like ? or ca.name like ?";
		JdbcTemplate jdbctemp = new JdbcTemplate(boneCP);
		
		count = jdbctemp.queryForObject(req, new Object[] { searchWord, searchWord }, Integer.class);
		return count;
	}
}

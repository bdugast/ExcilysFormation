package main.java.com.excilys.dao;

import java.sql.Connection;

public interface LogDAO {
	public void insertMessageLog(String message, int idComp, Connection conn);
}

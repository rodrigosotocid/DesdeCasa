package com.rodrigo.model.dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

public class ConnectionManager {
	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getCanonicalName());
	private static Connection conn;

	public static Connection getConnection() {

		conn = null;

		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");

			if (ds == null) {
				throw new Exception("Data source no encontrado!");
			}

			conn = ds.getConnection();

		} catch (Exception e) {

			LOGGER.fatal(e);
		}

		return conn;

	}
}

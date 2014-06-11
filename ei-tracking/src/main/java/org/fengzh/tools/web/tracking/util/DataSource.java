package org.fengzh.tools.web.tracking.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSource extends BasicDataSource {

	@Override
	public synchronized void close() throws SQLException {
		Driver driver = DriverManager.getDriver(getUrl());
		super.close();
		if (driver != null) {
			DriverManager.deregisterDriver(driver);
		}
	}

}

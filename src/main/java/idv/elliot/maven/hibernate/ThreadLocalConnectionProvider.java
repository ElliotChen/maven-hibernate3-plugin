package idv.elliot.maven.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

public class ThreadLocalConnectionProvider implements ConnectionProvider {
	private static ThreadLocal<DataSource> dataSource = new ThreadLocal<DataSource>();
	
	public void close() throws HibernateException {
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties properties) throws HibernateException {
		
	}

	public Connection getConnection() throws SQLException {
		return dataSource.get().getConnection();
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

	public static DataSource getDataSource() {
		return dataSource.get();
	}

	public static void setDataSource(DataSource dataSource) {
		ThreadLocalConnectionProvider.dataSource.set(dataSource);
	}
	

}

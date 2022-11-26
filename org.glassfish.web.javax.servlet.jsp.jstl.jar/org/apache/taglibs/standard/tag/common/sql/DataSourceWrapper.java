package org.apache.taglibs.standard.tag.common.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.taglibs.standard.resources.Resources;

public class DataSourceWrapper implements DataSource {
   private Driver driver;
   private String jdbcURL;
   private String userName;
   private String password;

   public void setDriverClassName(String driverClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      Object instance = Class.forName(driverClassName, true, Thread.currentThread().getContextClassLoader()).newInstance();
      if (instance instanceof Driver) {
         this.driver = (Driver)instance;
      }

   }

   public void setJdbcURL(String jdbcURL) {
      this.jdbcURL = jdbcURL;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Connection getConnection() throws SQLException {
      Connection conn = null;
      if (this.driver != null) {
         Properties props = new Properties();
         if (this.userName != null) {
            props.put("user", this.userName);
         }

         if (this.password != null) {
            props.put("password", this.password);
         }

         conn = this.driver.connect(this.jdbcURL, props);
      }

      if (conn == null) {
         if (this.userName != null) {
            conn = DriverManager.getConnection(this.jdbcURL, this.userName, this.password);
         } else {
            conn = DriverManager.getConnection(this.jdbcURL);
         }
      }

      return conn;
   }

   public Connection getConnection(String username, String password) throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public int getLoginTimeout() throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public PrintWriter getLogWriter() throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public void setLoginTimeout(int seconds) throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public synchronized void setLogWriter(PrintWriter out) throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return false;
   }

   public Object unwrap(Class iface) throws SQLException {
      throw new SQLException(Resources.getMessage("NOT_SUPPORTED"));
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException(Resources.getMessage("NOT_SUPPORTED"));
   }
}

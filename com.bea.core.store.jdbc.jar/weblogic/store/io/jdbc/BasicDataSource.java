package weblogic.store.io.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.sql.XADataSource;

public class BasicDataSource implements DataSource {
   private static final String USER_PROPERTY_NAME = "user";
   private static final String PASSWORD_PROPERTY_NAME = "password";
   private Driver dbDriver;
   private PrintWriter debugWriter;
   private String url;
   private Properties properties;
   private String password;

   public BasicDataSource(String url, String driverClass, Properties props, String password) throws SQLException {
      this.url = url;
      this.properties = props;
      this.password = password;
      this.loadDriver(driverClass);
   }

   private void loadDriver(String driverClassName) throws SQLException {
      if (driverClassName != null && driverClassName.length() != 0) {
         Class driverClass;
         try {
            driverClass = Class.forName(driverClassName);
         } catch (ClassNotFoundException var7) {
            throw makeSQLException("JDBC driver class \"" + driverClassName + "\" not found", var7);
         }

         try {
            this.dbDriver = (Driver)driverClass.newInstance();
         } catch (InstantiationException var4) {
            throw makeSQLException("JDBC driver instance of class \"" + driverClassName + "\" cannot be created: " + var4, var4);
         } catch (IllegalAccessException var5) {
            throw makeSQLException("JDBC driver instance of class \"" + driverClassName + "\" cannot be created: " + var5, var5);
         } catch (ClassCastException var6) {
            throw makeSQLException("JDBC driver class \"" + driverClassName + "\" does not implement the java.sql.Driver interface", var6);
         }
      } else {
         throw new SQLException("No JDBC driver class was specified");
      }
   }

   public static Properties parsePropertiesString(String props) {
      Properties ret = new Properties();
      if (props == null) {
         return ret;
      } else {
         StringTokenizer tok = new StringTokenizer(props, ";");

         while(tok.hasMoreElements()) {
            String prop = tok.nextToken();
            StringTokenizer propTok = new StringTokenizer(prop, "=");
            String name = null;
            String value = null;
            if (propTok.hasMoreElements()) {
               name = propTok.nextToken();
            }

            if (propTok.hasMoreElements()) {
               value = propTok.nextToken();
            }

            if (name != null) {
               ret.put(name, value);
            }
         }

         return ret;
      }
   }

   public boolean isDataSource() {
      return this.dbDriver instanceof DataSource;
   }

   public boolean isXADataSource() {
      return this.dbDriver instanceof XADataSource;
   }

   public Connection getConnection() throws SQLException {
      String pw;
      if (this.password != null && this.password.length() > 0) {
         pw = this.password;
      } else {
         pw = null;
      }

      return this.connectInternal(this.url, this.properties, (String)null, this.password);
   }

   public Connection getConnection(String userName, String password) throws SQLException {
      return this.connectInternal(this.url, this.properties, userName, password);
   }

   private Connection connectInternal(String url, Properties props, String userName, String password) throws SQLException {
      if (props == null) {
         props = new Properties();
      }

      if (userName != null && userName.length() > 0) {
         props.put("user", userName);
      }

      if (password != null && password.length() > 0) {
         props.put("password", password);
      }

      return this.dbDriver.connect(url, props);
   }

   public int getLoginTimeout() throws SQLException {
      return DriverManager.getLoginTimeout();
   }

   public void setLoginTimeout(int timeout) throws SQLException {
      DriverManager.setLoginTimeout(timeout);
   }

   public PrintWriter getLogWriter() throws SQLException {
      return this.debugWriter;
   }

   public void setLogWriter(PrintWriter writer) throws SQLException {
      this.debugWriter = writer;
   }

   private static SQLException makeSQLException(String msg, Throwable t) {
      SQLException ret = new SQLException(msg);
      ret.initCause(t);
      return ret;
   }

   public Object unwrap(Class interfaces) throws SQLException {
      if (interfaces.isInstance(this)) {
         return this;
      } else {
         throw new SQLException(this + " is not an instance of " + interfaces);
      }
   }

   public boolean isWrapperFor(Class interfaces) throws SQLException {
      return interfaces.isInstance(this);
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }
}

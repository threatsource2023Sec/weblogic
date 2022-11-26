package org.apache.openjpa.jdbc.schema;

import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.util.StoreException;

public class SimpleDriverDataSource implements DriverDataSource {
   private String _connectionDriverName;
   private String _connectionURL;
   private String _connectionUserName;
   private String _connectionPassword;
   private Properties _connectionProperties;
   private Properties _connectionFactoryProperties;
   private Driver _driver;
   private ClassLoader _classLoader;

   public Connection getConnection() throws SQLException {
      return this.getConnection((Properties)null);
   }

   public Connection getConnection(String username, String password) throws SQLException {
      Properties props = new Properties();
      if (username == null) {
         username = this._connectionUserName;
      }

      if (username != null) {
         props.put("user", username);
      }

      if (password == null) {
         password = this._connectionPassword;
      }

      if (password != null) {
         props.put("password", password);
      }

      return this.getConnection(props);
   }

   public Connection getConnection(Properties props) throws SQLException {
      return this.getDriver().connect(this._connectionURL, props);
   }

   public int getLoginTimeout() {
      return 0;
   }

   public void setLoginTimeout(int seconds) {
   }

   public PrintWriter getLogWriter() {
      return DriverManager.getLogWriter();
   }

   public void setLogWriter(PrintWriter out) {
   }

   public void initDBDictionary(DBDictionary dict) {
   }

   public void setConnectionURL(String connectionURL) {
      this._connectionURL = connectionURL;
   }

   public String getConnectionURL() {
      return this._connectionURL;
   }

   public void setConnectionUserName(String connectionUserName) {
      this._connectionUserName = connectionUserName;
   }

   public String getConnectionUserName() {
      return this._connectionUserName;
   }

   public void setConnectionPassword(String connectionPassword) {
      this._connectionPassword = connectionPassword;
   }

   public void setConnectionProperties(Properties props) {
      this._connectionProperties = props;
   }

   public Properties getConnectionProperties() {
      return this._connectionProperties;
   }

   public void setConnectionFactoryProperties(Properties props) {
      this._connectionFactoryProperties = props;
   }

   public Properties getConnectionFactoryProperties() {
      return this._connectionFactoryProperties;
   }

   public List createConnectionDecorators() {
      return null;
   }

   public void setClassLoader(ClassLoader classLoader) {
      this._classLoader = classLoader;
   }

   public ClassLoader getClassLoader() {
      return this._classLoader;
   }

   public void setConnectionDriverName(String connectionDriverName) {
      this._connectionDriverName = connectionDriverName;
   }

   public String getConnectionDriverName() {
      return this._connectionDriverName;
   }

   private Driver getDriver() {
      if (this._driver != null) {
         return this._driver;
      } else {
         try {
            this._driver = DriverManager.getDriver(this._connectionURL);
            if (this._driver != null) {
               return this._driver;
            }
         } catch (Exception var4) {
         }

         try {
            Class.forName(this._connectionDriverName, true, this._classLoader);
         } catch (Exception var3) {
         }

         try {
            this._driver = DriverManager.getDriver(this._connectionURL);
            if (this._driver != null) {
               return this._driver;
            }
         } catch (Exception var2) {
         }

         try {
            Class c = Class.forName(this._connectionDriverName, true, this._classLoader);
            this._driver = (Driver)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(c));
            return this._driver;
         } catch (Exception var5) {
            Exception e = var5;
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               if (var5 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var5).getException();
               }

               throw new StoreException(e);
            }
         }
      }
   }
}

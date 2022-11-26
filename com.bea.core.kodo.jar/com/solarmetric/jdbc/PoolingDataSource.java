package com.solarmetric.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.openjpa.lib.jdbc.DataSourceLogs;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;

public class PoolingDataSource implements DataSource, Closeable {
   private static final Localizer _loc = Localizer.forPackage(PoolingDataSource.class);
   private final ConnectionPool _connPool;
   private final ConnectionRequestInfo _info;
   private final Properties _props;
   private final DataSourceLogs _logs;
   private Driver _driver;
   private String _driverName;
   private String _url;
   private int _timeout;
   private PrintWriter _out;
   private boolean _connected;
   private ClassLoader _loader;

   public PoolingDataSource() {
      this(new ConnectionPoolImpl());
   }

   public PoolingDataSource(ConnectionPool pool) {
      this._info = new ConnectionRequestInfo();
      this._props = new Properties();
      this._logs = new DataSourceLogs();
      this._driver = null;
      this._driverName = null;
      this._url = null;
      this._timeout = 10;
      this._out = null;
      this._connected = false;
      this._loader = null;
      this._connPool = pool;
      pool.setDataSource(this);
   }

   public DataSourceLogs getLogs() {
      return this._logs;
   }

   public void setClassLoader(ClassLoader loader) {
      this._loader = loader;
   }

   public ClassLoader getClassLoader() {
      return this._loader;
   }

   public ConnectionPool getConnectionPool() {
      return this._connPool;
   }

   public String getConnectionUserName() {
      return this._info.getUsername();
   }

   public void setConnectionUserName(String val) {
      this._info.setUsername(val);
   }

   public void setConnectionPassword(String val) {
      this._info.setPassword(val);
   }

   public String getConnectionURL() {
      return this._url;
   }

   public void setConnectionURL(String url) {
      this._url = url;
   }

   public Properties getConnectionProperties() {
      Properties props = new Properties();
      props.putAll(this._props);
      return props;
   }

   public void setConnectionProperties(Properties props) {
      this._props.clear();
      if (props != null) {
         this._props.putAll(props);
      }

   }

   public String getConnectionDriverName() {
      return this._driverName;
   }

   public void setConnectionDriverName(String val) {
      this._driverName = val;
   }

   public int getLoginTimeout() {
      return this._timeout;
   }

   public void setLoginTimeout(int timeout) {
      this._timeout = timeout;
   }

   public PrintWriter getLogWriter() {
      return this._out;
   }

   public void setLogWriter(PrintWriter out) {
      this._out = out;
   }

   public Connection getConnection() throws SQLException {
      return this._connPool.getConnection(this._info);
   }

   public Connection getConnection(String user, String pass) throws SQLException {
      return this._connPool.getConnection(new ConnectionRequestInfo(user, pass));
   }

   public void close() {
      this._connPool.close();
   }

   public Driver getDriver() throws SQLException {
      if (this._driver != null) {
         return this._driver;
      } else {
         Class driverClass = null;

         try {
            driverClass = Class.forName(this._driverName, true, this._loader != null ? this._loader : Thread.currentThread().getContextClassLoader());
         } catch (Exception var6) {
         }

         try {
            if (driverClass == null) {
               driverClass = Class.forName(this._driverName, true, Thread.currentThread().getContextClassLoader());
            }
         } catch (Exception var5) {
         }

         try {
            this._driver = DriverManager.getDriver(this._url);
         } catch (SQLException var7) {
            if (driverClass == null) {
               throw var7;
            }

            try {
               this._driver = (Driver)driverClass.newInstance();
            } catch (Exception var4) {
               throw var7;
            }
         }

         return this._driver;
      }
   }

   protected Connection newConnection(ConnectionRequestInfo cri) throws SQLException {
      Properties props = this.getConnectionProperties();
      if (cri.getUsername() != null) {
         props.setProperty("user", cri.getUsername());
      }

      if (cri.getPassword() != null) {
         props.setProperty("password", cri.getPassword());
      }

      Connection conn = null;
      Exception err = null;
      long start = System.currentTimeMillis();
      int retries = 9;

      for(int i = 0; i < retries && conn == null; ++i) {
         try {
            conn = this.getDriver().connect(this._url, props);
            if (conn == null) {
               throw new SQLException(_loc.get("poolds-null", this.getDriver().getClass().getName(), this._url).getMessage());
            }

            this._connected = true;
         } catch (Exception var12) {
            if (err == null) {
               err = var12;
            }

            if (!this._connected || System.currentTimeMillis() > start + (long)(this._timeout * 1000)) {
               break;
            }

            if (i < retries - 1) {
               try {
                  Thread.sleep((long)((i + 1) * 200));
               } catch (InterruptedException var11) {
               }
            }
         }
      }

      if (conn == null) {
         if (err instanceof SQLException) {
            throw (SQLException)err;
         } else if (err != null) {
            throw new SQLException(err.toString());
         } else {
            throw new NullPointerException();
         }
      } else {
         return conn;
      }
   }

   public String toString() {
      return "PoolingDataSource <url=" + this.getConnectionURL() + "; pool=" + this._connPool + ">";
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return false;
   }

   public Object unwrap(Class iface) throws SQLException {
      throw new SQLException();
   }
}

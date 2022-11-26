package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.jdbc.JDBCLogger;

public class ConnectionEnvFactory extends JDBCResourceFactoryImpl {
   String title = "";
   private Driver loadedDriver = null;
   private DataSource loadedDataSource = null;
   private boolean isDataSource;
   private boolean weKnowIsDataSource = false;
   private boolean isIdentity;

   public long getConnectTime() {
      return this.connTime;
   }

   private Object loadDriver(String driver, String url) throws Exception {
      if (this.weKnowIsDataSource) {
         if (this.loadedDriver != null) {
            return this.loadedDriver;
         }

         if (this.loadedDataSource != null) {
            return this.loadedDataSource;
         }
      }

      try {
         Object obj = DataSourceUtil.loadDriver(driver, this.getPool().getClassLoader());
         if (obj instanceof Driver) {
            this.weKnowIsDataSource = true;
            this.isDataSource = false;
            Driver driver1 = (Driver)obj;
            if (!driver1.acceptsURL(url)) {
               throw new ResourceSystemException("The driver " + driver + " does not accept URL " + url);
            }

            this.loadedDriver = driver1;
         } else {
            if (!(obj instanceof DataSource)) {
               throw new ResourceSystemException("Class " + driver + " is not a javax.sql.DataSource or java.sql.Driver");
            }

            this.weKnowIsDataSource = true;
            this.isDataSource = true;
            this.loadedDataSource = (DataSource)obj;
         }
      } catch (Exception var5) {
         JDBCUtil.parseException(var5, url, driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
      } catch (UnsatisfiedLinkError var6) {
         throw new ResourceSystemException(JDBCUtil.makeUleMsg(driver, var6.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
      }

      return this.loadedDriver != null ? this.loadedDriver : this.loadedDataSource;
   }

   private DataSource getNewDataSource() throws Exception {
      Object obj = DataSourceUtil.loadDriver(this.driver);
      if (!(obj instanceof DataSource)) {
         throw new ResourceSystemException("Class " + this.driver + " is not a javax.sql.DataSource");
      } else {
         return (DataSource)obj;
      }
   }

   public ConnectionEnvFactory(JDBCConnectionPool connectionPool, String appName, String moduleName, String compName, Properties props) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CEF:CEF (10) appName = " + appName);
      }

      this.pool = connectionPool;
      if (this.pool.getProperties() != null) {
         this.driverProps = (Properties)this.pool.getProperties().clone();
      }

      this.poolParams = props;
      this.driver = this.pool.getDriverVersion();
      this.poolname = props.getProperty("name");
      this.appname = appName;
      this.moduleName = moduleName;
      this.compName = compName;
      this.title = "JDBC Connection Pool " + this.poolname;
      this.url = props.getProperty("Url");
      this.isIdentity = this.pool.isIdentityBasedConnectionPoolingEnabled();

      try {
         this.loadDriver(this.driver, this.url);
      } catch (Exception var7) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <*  CEF:CEF (30) throws " + var7.getMessage());
         }

         if (var7 instanceof ResourceSystemException) {
            throw (ResourceSystemException)var7;
         }

         throw new ResourceSystemException(var7.getMessage());
      }

      this.initialize();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CEF:CEF (40)");
      }

   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new ConnectionEnv(poolParams);
      return cc;
   }

   protected void setConnection(ConnectionEnv cc, PooledResourceInfo connInfo) throws ResourceException {
      cc.setConnection(this.makeConnection(false, cc.getDriverProperties(), cc.getDataSource(), connInfo));
   }

   protected void setDataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws Exception {
      if (this.pool.isIdentityBasedConnectionPoolingEnabled() || connInfo instanceof PropertiesConnectionInfo && ((PropertiesConnectionInfo)connInfo).getAdditionalProperties() != null || connInfo instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)connInfo).getAdditionalProperties() != null || this.driverProps != null && this.driverProps.containsKey("IMPERSONATE")) {
         Properties p = (Properties)((Properties)this.driverProps.clone());
         if (connInfo instanceof PropertiesConnectionInfo && ((PropertiesConnectionInfo)connInfo).getAdditionalProperties() != null) {
            p.putAll(((PropertiesConnectionInfo)connInfo).getAdditionalProperties());
         } else if (connInfo instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)connInfo).getAdditionalProperties() != null) {
            p.putAll(((HAPooledResourceInfo)connInfo).getAdditionalProperties());
         } else {
            ConnectionInfo connInfo;
            if (connInfo != null) {
               try {
                  p.setProperty("user", ((ConnectionInfo)connInfo).getUsername());
                  p.setProperty("password", ((ConnectionInfo)connInfo).getPassword());
                  String wl_id = ((ConnectionInfo)connInfo).getWLUserID();
                  if (wl_id != null && !"".equals(wl_id)) {
                     p.setProperty("IMPERSONATE", wl_id);
                  } else {
                     connInfo = new ConnectionInfo(p.getProperty("user"), p.getProperty("password"), p.getProperty("IMPERSONATE"));
                     cc.setPooledResourceInfo(connInfo);
                  }
               } catch (Throwable var5) {
               }
            } else {
               connInfo = new ConnectionInfo(p.getProperty("user"), p.getProperty("password"), p.getProperty("IMPERSONATE"));
               cc.setPooledResourceInfo(connInfo);
            }
         }

         if (this.isDataSource()) {
            cc.setDataSource(this.getNewDataSource());
         }

         cc.setDriverProperties(p);
      } else {
         cc.setDriverProperties(this.driverProps);
         if (this.isDataSource()) {
            cc.setDataSource(this.loadedDataSource);
         }
      }

   }

   private boolean isDataSource() throws Exception {
      if (!this.weKnowIsDataSource) {
         this.loadDriver(this.driver, this.url);
      }

      return this.isDataSource;
   }

   protected String getUrl(PooledResourceInfo info) {
      return this.url;
   }

   protected String getInstanceName(PooledResourceInfo info) throws ResourceException {
      return null;
   }

   private Connection makeConnection(final boolean isRefresh, final Properties driverProp, final DataSource identityDataSource, final PooledResourceInfo info) throws ResourceException {
      try {
         return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws ResourceException {
               return ConnectionEnvFactory.this.makeConnection0(isRefresh, driverProp, identityDataSource, info);
            }
         });
      } catch (PrivilegedActionException var6) {
         throw (ResourceException)var6.getException();
      }
   }

   private Connection makeConnection0(boolean isRefresh, Properties driverProp, DataSource identityDataSource, PooledResourceInfo info) throws ResourceException {
      Connection conn = null;
      if (this.delaySecs > 0) {
         if (this.delaySecs > 2) {
            JDBCLogger.logDelayingBeforeConnection(this.delaySecs, this.poolname);
         }

         try {
            Thread.sleep((long)(1000 * this.delaySecs));
         } catch (InterruptedException var44) {
         }
      }

      String url = this.getUrl(info);
      if (JdbcDebug.JDBCRAC.isDebugEnabled() && this.pool != null && HAUtil.isHADataSource(this.pool.getJDBCDataSource())) {
         JdbcDebug.JDBCRAC.debug("ConnectionEnvFactory [" + this.poolname + "]: making connection for " + url);
      }

      String instanceName = this.getInstanceName(info);
      if (instanceName != null && url != null && url.indexOf("INSTANCE_NAME") != -1) {
         instanceName = null;
      }

      long connBeganAt = 0L;
      long connEndedAt = 0L;
      this.connTime = 0L;
      this.acquireCreateRequestPermit();

      try {
         String m;
         try {
            Properties props;
            if (this.isDataSource()) {
               DataSource datasource = (DataSource)this.loadDriver(this.driver, url);
               if (this.isIdentity) {
                  datasource = identityDataSource;
               }

               if (url != null) {
                  this.driverProps.put("url", url);
               }

               JDBCUtil.setOracleProps(this.pool, url, this.driverProps, this.driver);
               props = this.driverProps;
               if (instanceName != null) {
                  props = (Properties)props.clone();
                  props.put("oracle.jdbc.targetInstanceName", instanceName);
               }

               DataSourceUtil.initProps(datasource.getClass().getName(), datasource, props);
               connBeganAt = System.currentTimeMillis();

               try {
                  conn = datasource.getConnection();
               } finally {
                  if (instanceName != null) {
                     props.remove("oracle.jdbc.targetInstanceName");
                     DataSourceUtil.initProps(datasource.getClass().getName(), datasource, props);
                  }

               }

               connEndedAt = System.currentTimeMillis();
            } else {
               Driver d = (Driver)this.loadDriver(this.driver, url);
               connBeganAt = System.currentTimeMillis();
               JDBCUtil.setOracleProps(this.pool, url, driverProp, this.driver);
               props = driverProp;
               if (instanceName != null) {
                  props = (Properties)driverProp.clone();
                  props.put("oracle.jdbc.targetInstanceName", instanceName);
               }

               conn = d.connect(url, props);
               if (conn == null) {
                  conn = DriverManager.getConnection(url, props);
               }

               connEndedAt = System.currentTimeMillis();
            }

            boolean setIsoOK = true;
            if (conn != null && this.desiredDefaultIsolationLevel != -1) {
               setIsoOK = false;

               try {
                  conn.setTransactionIsolation(this.desiredDefaultIsolationLevel);
                  setIsoOK = true;
               } catch (SQLException var48) {
                  m = var48.getMessage();
                  setIsoOK = true;
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("Ignoring DBMS driver exception: " + var48.getMessage());
                  }
               } finally {
                  if (!setIsoOK) {
                     conn.close();
                  }

               }
            }
         } catch (Exception var50) {
            Exception e2 = var50;

            try {
               JDBCUtil.parseException(e2, url, this.driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
            } catch (Exception var45) {
               if (var45 instanceof ResourceSystemException) {
                  throw (ResourceSystemException)var45;
               }

               if (this.pool.isEnabled()) {
                  m = JDBCLogger.logError(var45.getMessage(), this.poolname);
                  JDBCLogger.logStackTraceId(m, var45);
               }

               m = "";
               if (this.poolname != null) {
                  m = " for datasource '" + JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName) + "'";
               }

               throw new ResourceException("Could not create pool connection" + m + ". The DBMS driver exception was: " + var45.getMessage(), var45);
            } catch (UnsatisfiedLinkError var46) {
               throw new ResourceSystemException(JDBCUtil.makeUleMsg(this.driver, var46.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
            }
         } catch (Throwable var51) {
            String pool = "";
            if (this.poolname != null) {
               pool = " for datasource '" + JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName) + "'";
            }

            throw new ResourceSystemException("Could not create pool connection" + pool + ". The DBMS driver exception was: " + var51.getMessage());
         }
      } finally {
         this.releaseCreateRequestPermit();
      }

      if (connEndedAt > 0L) {
         this.connTime = connEndedAt - connBeganAt;
      }

      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         if (isRefresh) {
            JDBCLogger.logConnRefreshedInfo(this.poolname);
         } else {
            JDBCLogger.logConnCreatedInfo(this.poolname);
         }
      }

      return conn;
   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      ConnectionEnv cc = (ConnectionEnv)resource;
      boolean needDetach = false;
      if (!cc.destroyed) {
         try {
            ConnectionState oldState = cc.getState();
            cc.cleanup();
            cc.clearCache();
            cc.setState(oldState);
            JDBCLogger.logConnClosedInfo(this.poolname);

            try {
               if (!cc.conn.jconn.getAutoCommit()) {
                  cc.conn.jconn.rollback();
               }
            } catch (Exception var12) {
            }

            cc.conn.jconn.close();
         } catch (Exception var13) {
         }
      }

      this.weKnowIsDataSource = false;
      cc.setPooledResourceInfo((PooledResourceInfo)null);
      cc.setConnection(this.makeConnection(true, cc.getDriverProperties(), cc.getDataSource(), cc.getPooledResourceInfo()));
      if (cc.drcpEnabled) {
         cc.OracleAttachServerConnection();
         needDetach = true;
      }

      cc.setConnectionLate();
      cc.autoCommit = true;

      try {
         cc.setInitialIsolationLevel(cc.conn.jconn.getTransactionIsolation());
      } catch (Exception var11) {
         throw new ResourceException(var11.getMessage());
      } finally {
         if (needDetach) {
            this.doDRCPDetachIfNeeded(cc);
         }

      }

      cc.setConnectTime(this.connTime);
      cc.destroyed = false;
      cc.enabled = true;
      cc.lastSuccessfulConnectionUse = System.currentTimeMillis();
   }

   public void updateCredential(String p) {
      if (this.driverProps != null && this.driverProps.containsKey("password")) {
         this.driverProps.setProperty("password", p);
      }

   }
}

package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import javax.sql.XADataSource;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.jta.DataSource;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.XAConnection;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtils;

public class XAConnectionEnvFactory extends JDBCResourceFactoryImpl {
   XADataSource xaDataSrc = null;
   private String xaResName;
   private boolean xaResourceIsMDS = false;
   private static final boolean QUALIFY_RM_NAME = Boolean.parseBoolean(System.getProperty("weblogic.jdbc.qualifyRMName", "true"));
   private boolean nativeXA = false;
   private int driverMajorVersion = 0;
   private int driverMinorVersion = 0;
   private boolean versionFlag = false;

   public XAConnectionEnvFactory(JDBCConnectionPool connectionPool, String appname, String moduleName, String compName, String mpName, Properties props) throws ResourceException, SQLException {
      this.pool = connectionPool;
      this.driverProps = this.pool.getProperties();
      this.poolParams = props;
      this.driver = this.pool.getDriverVersion();
      this.poolname = props.getProperty("name");
      this.appname = appname;
      this.moduleName = moduleName;
      this.compName = compName;
      this.url = props.getProperty("Url");
      String debugLevel = props.getProperty("DebugLevel");
      if (debugLevel != null) {
         JdbcDebug.setDebugLevel(this.poolname, Integer.parseInt(debugLevel));
      }

      this.xaDataSrc = this.getXADataSrc(this.driver, this.driverProps);
      if (mpName != null) {
         this.xaResName = mpName;
         this.xaResourceIsMDS = true;
      } else {
         this.xaResName = JDBCUtil.getDecoratedName(this.poolname, appname, moduleName, compName);
      }

      if (QUALIFY_RM_NAME) {
         this.xaResName = this.xaResName + "_" + JDBCHelper.getHelper().getDomainName();
      }

      this.initialize();
   }

   public XADataSource getXADataSource() throws SQLException {
      return this.getXADataSrc(this.driver, this.driverProps);
   }

   protected void setXADataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws SQLException {
      if (this.pool.isIdentityBasedConnectionPoolingEnabled() || connInfo instanceof PropertiesConnectionInfo && ((PropertiesConnectionInfo)connInfo).getAdditionalProperties() != null || connInfo instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)connInfo).getAdditionalProperties() != null) {
         Properties p = (Properties)((Properties)this.driverProps.clone());
         if (connInfo instanceof PropertiesConnectionInfo && ((PropertiesConnectionInfo)connInfo).getAdditionalProperties() != null) {
            p.putAll(((PropertiesConnectionInfo)connInfo).getAdditionalProperties());
         } else if (connInfo instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)connInfo).getAdditionalProperties() != null) {
            p.putAll(((HAPooledResourceInfo)connInfo).getAdditionalProperties());
         } else if (connInfo != null) {
            try {
               p.setProperty("user", ((ConnectionInfo)connInfo).getUsername());
               p.setProperty("password", ((ConnectionInfo)connInfo).getPassword());
               String wl_id = ((ConnectionInfo)connInfo).getWLUserID();
               if (wl_id != null && !"".equals(wl_id)) {
                  p.setProperty("IMPERSONATE", wl_id);
               }
            } catch (Throwable var5) {
            }
         } else {
            cc.setPooledResourceInfo(new ConnectionInfo(p.getProperty("user"), p.getProperty("password")));
         }

         cc.setXADataSource(this.getXADataSrc(this.driver, p));
         cc.setDriverProperties(p);
      } else {
         cc.setXADataSource(this.xaDataSrc);
         cc.setDriverProperties(this.driverProps);
      }

   }

   protected void setConnection(ConnectionEnv cc, PooledResourceInfo connInfo) throws ResourceException {
      cc.setConnection(this.makeConnection(cc, true));
   }

   protected void setDbVersion(ConnectionEnv cc) {
      if (!this.versionFlag) {
         this.versionFlag = true;

         try {
            DatabaseMetaData dm = cc.conn.jconn.getMetaData();
            if (dm != null) {
               this.driverMajorVersion = dm.getDriverMajorVersion();
               this.driverMinorVersion = dm.getDriverMinorVersion();
            }
         } catch (SQLException var3) {
         }
      }

   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      this.refreshResource(resource, true);
   }

   public void refreshResource(PooledResource resource, boolean reregister) throws ResourceException {
      ConnectionEnv cc = (ConnectionEnv)resource;
      boolean needDetach = false;
      if (cc != null) {
         XAConnection oldConn = null;
         XAException xaError = cc.getXAExceptionDuringTesting();
         if (xaError != null && xaError.errorCode != -7 && xaError.errorCode != -3) {
            reregister = false;
         }

         if (!cc.destroyed) {
            ConnectionState oldState = cc.getState();
            cc.cleanup();
            cc.clearCache();
            cc.setState(oldState);
            oldConn = (XAConnection)((XAConnection)(cc.conn == null ? null : cc.conn.jconn));
            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.enter(this.poolname, "Refreshing connection " + oldConn + ", dataSrc:" + this.pool.getJTADataSource());
            }

            if (oldConn != null) {
               try {
                  JDBCLogger.logConnClosedInfo(this.poolname);
                  oldConn.close();
               } catch (Throwable var19) {
               }
            }
         }

         if (this.pool.isIdentityBasedConnectionPoolingEnabled()) {
            try {
               DataSourceUtil.initProps(this.poolname, cc.getXADataSource(), cc.getDriverProperties());
            } catch (Throwable var18) {
            }
         }

         try {
            XAConnection newConn = (XAConnection)this.makeConnection(cc, false);
            cc.setConnection(newConn);
            if (cc.drcpEnabled) {
               cc.OracleAttachServerConnection();
               needDetach = true;
            }

            cc.setConnectionLate();
            cc.lastSuccessfulConnectionUse = System.currentTimeMillis();
            cc.autoCommit = true;
            cc.destroyed = false;
            cc.enabled = true;

            try {
               ConnectionHolder conn = cc.conn;
               if (conn != null) {
                  Connection jconn = conn.jconn;
                  if (jconn != null) {
                     cc.setInitialIsolationLevel(jconn.getTransactionIsolation());
                  }
               }
            } catch (Exception var20) {
               throw new ResourceException(StackTraceUtils.throwable2StackTrace(var20));
            } finally {
               if (needDetach) {
                  this.doDRCPDetachIfNeeded(cc);
               }

            }

            if (this.xaResourceIsMDS) {
               reregister = false;
            }

            if (reregister && this.pool.getJTADataSource() != null) {
               try {
                  ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).setResourceHealthy(this.xaResName);
               } catch (Exception var17) {
               }
            }

            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.leave(this.poolname, "Refreshing connection " + oldConn + " returns " + newConn);
            }

         } catch (ResourceException var22) {
            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.err((String)this.poolname, "Refreshing connection " + oldConn, var22);
            }

            throw var22;
         }
      }
   }

   protected XADataSource getXADataSrc(String driver, Properties p) throws SQLException {
      XADataSource ret = null;

      String s;
      String prop;
      try {
         if (JdbcDebug.isEnabled((String)this.poolname, 10)) {
            JdbcDebug.enter(this.poolname, "Creating XADataSource, driver:" + driver);
         }

         ret = (XADataSource)DataSourceUtil.loadDriver(driver, this.getPool().getClassLoader());
         if (JdbcDebug.isEnabled((String)this.poolname, 10)) {
            JdbcDebug.leave(this.poolname, "XADataSource created.");
         }

         if (p.get("dataSourceName") == null) {
            p.put("dataSourceName", this.poolname);
         }

         if (p.get("server") != null && p.get("serverName") != null && !p.get("server").equals(p.get("serverName"))) {
            throw new SQLException("server '" + p.get("server") + "' and serverName '" + p.get("serverName") + "' properties must have the same value");
         }

         if (p.get("serverName") == null && p.get("server") != null) {
            p.put("serverName", p.get("server"));
         }

         if (this.url != null && !this.url.equals("")) {
            String drvPropUrl = (String)p.get("url");
            if (drvPropUrl != null && !drvPropUrl.equals("")) {
               if (!drvPropUrl.equals(this.url)) {
                  throw new SQLException("URL specified in connection pool properties '" + this.url + "' is different from that specified in driver properties '" + drvPropUrl + "'.");
               }
            } else {
               p.put("url", this.url);
            }
         }

         int j;
         int i;
         if (this.url != null && (i = this.url.indexOf(";")) > 0) {
            for(s = this.url.substring(i + 1) + ";"; (j = s.indexOf(";")) >= 0; s = s.substring(j + 1)) {
               prop = s.substring(0, j);
               int k = prop.indexOf("=");
               if (k > 0 && k + 1 < prop.length()) {
                  String name = prop.substring(0, k);
                  if (p.get(name) == null) {
                     if (!name.equalsIgnoreCase("SpyAttributes")) {
                        p.put(name, prop.substring(k + 1));
                     } else {
                        String value = prop.substring(k + 1);

                        for(s = s.substring(j + 1); (j = s.indexOf(";")) >= 0; s = s.substring(j + 1)) {
                           prop = s.substring(0, j);
                           k = prop.indexOf("=");
                           if (k > 0 && k + 1 < prop.length()) {
                              name = prop.substring(0, k).toLowerCase(Locale.ENGLISH);
                              if (!name.equals("log") && !name.equals("load") && !name.equals("linelimit") && !name.equals("logis") && !name.equals("logtname") && !name.equals("timestamp")) {
                                 break;
                              }

                              value = value + ";" + prop;
                           }
                        }

                        p.put("SpyAttributes", value);
                        j = -1;
                     }
                  }
               }
            }
         }

         if (JdbcDebug.isEnabled((String)this.poolname, 10)) {
            JdbcDebug.log(this.poolname, "XADataSource props:" + DataSourceUtil.removeClearTextPassword(p));
         }

         JDBCUtil.setOracleProps(this.pool, this.url, p, driver);
         DataSourceUtil.initProps(this.poolname, ret, p);
         if (driver.equals("oracle.jdbc.xa.client.OracleXADataSource") && ("true".equalsIgnoreCase(p.getProperty("nativeXA")) || "true".equalsIgnoreCase(p.getProperty("NativeXA")))) {
            this.nativeXA = true;
         }
      } catch (Exception var12) {
         Exception e = var12;
         s = var12.getMessage();

         try {
            JDBCUtil.parseException(e, this.url, driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
         } catch (Exception var11) {
            prop = var11.getMessage();
            if (prop != null) {
               throw new SQLException(prop);
            }

            throw new SQLException(s);
         }
      } catch (UnsatisfiedLinkError var13) {
         throw new SQLException(JDBCUtil.makeUleMsg(driver, var13.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
      }

      return ret;
   }

   public boolean isNativeXA() {
      return this.nativeXA;
   }

   private Connection makeConnection(final ConnectionEnv cc, final boolean create) throws ResourceException {
      try {
         return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws ResourceException {
               return XAConnectionEnvFactory.this.makeConnection0(cc, create);
            }
         });
      } catch (PrivilegedActionException var4) {
         throw (ResourceException)var4.getException();
      }
   }

   private Connection makeConnection0(ConnectionEnv cc, boolean create) throws ResourceException {
      javax.sql.XAConnection conn = null;
      long connBeganAt = 0L;
      long connEndedAt = 0L;
      this.connTime = 0L;
      if (this.delaySecs > 0) {
         if (this.delaySecs > 2) {
            JDBCLogger.logDelayingBeforeConnection(this.delaySecs, this.poolname);
         }

         try {
            Thread.sleep((long)(1000 * this.delaySecs));
         } catch (InterruptedException var23) {
         }
      }

      this.acquireCreateRequestPermit();

      Exception newConn;
      String connHandler;
      try {
         if (JdbcDebug.isEnabled((String)this.poolname, 10)) {
            JdbcDebug.enter(this.poolname, "XADataSource.getXAConnection");
         }

         connBeganAt = System.currentTimeMillis();
         if (this.nativeXA) {
            synchronized(this) {
               conn = cc.getXADataSource().getXAConnection();
            }
         } else {
            conn = cc.getXADataSource().getXAConnection();
         }

         connEndedAt = System.currentTimeMillis();
         if (JdbcDebug.isEnabled((String)this.poolname, 10)) {
            JdbcDebug.leave(this.poolname, "XADataSource.getXAConnection rtns " + conn);
         }
      } catch (Exception var26) {
         newConn = var26;

         try {
            JDBCUtil.parseException(newConn, this.url, this.driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
         } catch (Exception var24) {
            if (var24 instanceof ResourceSystemException) {
               throw (ResourceSystemException)var24;
            }

            if (this.pool.isEnabled()) {
               String id = JDBCLogger.logError(var24.getMessage(), this.poolname);
               JDBCLogger.logStackTraceId(id, var24);
            }

            conn = null;
            throw new ResourceException(var24.getMessage(), var24);
         } catch (UnsatisfiedLinkError var25) {
            throw new ResourceSystemException(JDBCUtil.makeUleMsg(this.driver, var25.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
         }
      } catch (Throwable var27) {
         connHandler = "";
         if (this.poolname != null) {
            connHandler = " for datasource '" + JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName) + "'";
         }

         throw new ResourceSystemException("Could not create pool connection" + connHandler + ". The DBMS driver exception was: " + var27.getMessage(), var27);
      } finally {
         this.releaseCreateRequestPermit();
      }

      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         if (create) {
            JDBCLogger.logXAConnCreatedInfo(this.poolname);
         } else {
            JDBCLogger.logXAConnRefreshedInfo(this.poolname);
         }
      }

      newConn = null;
      connHandler = null;

      XAConnection newConn;
      Connection connHandler;
      try {
         connHandler = conn.getConnection();
         if (connHandler == null) {
            throw new Exception("Failed to get connection for " + this.poolname);
         }

         if (this.desiredDefaultIsolationLevel != -1) {
            connHandler.setTransactionIsolation(this.desiredDefaultIsolationLevel);
         }

         newConn = (XAConnection)JDBCWrapperFactory.getWrapper("weblogic.jdbc.wrapper.XAConnection", connHandler, false);
      } catch (Exception var29) {
         JDBCLogger.logStackTrace(var29);
         conn = null;
         throw new ResourceException(StackTraceUtils.throwable2StackTrace(var29));
      }

      newConn.init(conn, cc, connHandler, this.poolname);
      newConn.setDataSource(this.pool.getJTADataSource());
      if (connEndedAt > 0L) {
         this.connTime = connEndedAt - connBeganAt;
      }

      try {
         if (newConn.getAutoCommit()) {
            newConn.setAutoCommit(false);
         }

         return newConn;
      } catch (Exception var21) {
         throw new ResourceException(var21.getMessage());
      }
   }

   void unregisterResource(boolean blocking) throws SystemException {
      if (!this.xaResourceIsMDS) {
         ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(this.xaResName, blocking);
      }
   }

   void registerResource() throws SystemException {
      DataSource jtaDataSource = this.pool.getJTADataSource();
      if (jtaDataSource != null) {
         Hashtable registrationProperties = jtaDataSource.getXARegistrationProperties();
         if (registrationProperties == null) {
            registrationProperties = new Hashtable();
            registrationProperties.put("weblogic.transaction.registration.type", "dynamic");
         }

         registrationProperties.put("weblogic.transaction.resource.type", "datasource");
         ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(this.xaResName, jtaDataSource, registrationProperties);
      }

   }

   public int getDriverMajorVersion() {
      return this.driverMajorVersion;
   }

   public int getDriverMinorVersion() {
      return this.driverMinorVersion;
   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new ConnectionEnv(poolParams, true);
      return cc;
   }

   public void updateCredential(String p) throws SQLException {
      if (this.driverProps != null && this.driverProps.containsKey("password")) {
         this.driverProps.setProperty("password", p);
         if (this.xaDataSrc != null) {
            DataSourceUtil.initProp(this.xaDataSrc.getClass().getName(), this.xaDataSrc, "password", p);
         }
      }

   }
}

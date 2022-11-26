package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.PooledConnection;
import weblogic.utils.StackTraceUtils;

public class PooledConnectionEnvFactory extends JDBCResourceFactoryImpl {
   protected ConnectionPoolDataSource cpDataSrc;

   public PooledConnectionEnvFactory(JDBCConnectionPool pool, String appname, String moduleName, String compName, Properties poolParams) throws ResourceException, SQLException {
      this.pool = pool;
      this.appname = appname;
      this.moduleName = moduleName;
      this.compName = compName;
      this.poolParams = poolParams;
      this.driverProps = pool.getProperties();
      this.driver = pool.getDriverVersion();
      this.poolname = poolParams.getProperty("name");
      this.url = poolParams.getProperty("Url");
      this.initialize();
      String strVal = poolParams.getProperty("DebugLevel");
      if (strVal != null) {
         JdbcDebug.setDebugLevel(this.poolname, Integer.parseInt(strVal));
      }

      this.cpDataSrc = this.getCPDataSrc(this.driverProps);
   }

   protected void setDataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws SQLException {
      if (!this.pool.isIdentityBasedConnectionPoolingEnabled() && (!(connInfo instanceof PropertiesConnectionInfo) || ((PropertiesConnectionInfo)connInfo).getAdditionalProperties() == null) && (!(connInfo instanceof HAPooledResourceInfo) || ((HAPooledResourceInfo)connInfo).getAdditionalProperties() == null) && !this.driverProps.containsKey("IMPERSONATE")) {
         cc.setConnectionPoolDataSource(this.cpDataSrc);
         cc.setDriverProperties(this.driverProps);
      } else {
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

         cc.setConnectionPoolDataSource(this.getCPDataSrc(p));
         cc.setDriverProperties(p);
      }

   }

   protected void setConnection(ConnectionEnv cc, PooledResourceInfo connInfo) throws ResourceException {
      cc.setConnection(this.makeConnection(cc, true));
   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      boolean needDetach = false;
      ConnectionEnv cc = (ConnectionEnv)resource;
      ConnectionState oldState = cc.getState();
      cc.cleanup();
      cc.clearCache();
      cc.setState(oldState);

      PooledConnection newConn;
      try {
         newConn = (PooledConnection)cc.conn.jconn;
         JDBCLogger.logConnClosedInfo(this.poolname);
         newConn.close();
      } catch (Throwable var15) {
      }

      if (this.pool.isIdentityBasedConnectionPoolingEnabled()) {
         try {
            DataSourceUtil.initProps(this.poolname, cc.getXADataSource(), cc.getDriverProperties());
         } catch (Throwable var14) {
         }
      }

      try {
         newConn = (PooledConnection)this.makeConnection(cc, false);
         cc.setConnection(newConn);
         if (cc.drcpEnabled) {
            cc.OracleAttachServerConnection();
            needDetach = true;
         }

         cc.setConnectionLate();
         cc.autoCommit = true;

         try {
            cc.setInitialIsolationLevel(cc.conn.jconn.getTransactionIsolation());
         } catch (Exception var13) {
            throw new ResourceException(StackTraceUtils.throwable2StackTrace(var13));
         }
      } catch (ResourceException var16) {
         throw var16;
      } finally {
         if (needDetach) {
            this.doDRCPDetachIfNeeded(cc);
         }

      }

      cc.setConnectTime(this.connTime);
      cc.destroyed = false;
      cc.enabled = true;
   }

   protected ConnectionPoolDataSource getCPDataSrc(Properties p) throws SQLException {
      ConnectionPoolDataSource ret = null;

      String s;
      String prop;
      try {
         ret = (ConnectionPoolDataSource)DataSourceUtil.loadDriver(this.driver, this.getPool().getClassLoader());
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

         JDBCUtil.setOracleProps(this.pool, this.url, p, this.driver);
         DataSourceUtil.initProps(this.poolname, ret, p);
      } catch (Exception var11) {
         Exception e = var11;
         s = var11.getMessage();

         try {
            JDBCUtil.parseException(e, this.url, this.driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
         } catch (Exception var10) {
            prop = var10.getMessage();
            if (prop != null) {
               throw new SQLException(prop);
            }

            throw new SQLException(s);
         }
      } catch (UnsatisfiedLinkError var12) {
         throw new SQLException(JDBCUtil.makeUleMsg(this.driver, var12.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
      }

      return ret;
   }

   private Connection makeConnection(final ConnectionEnv cc, final boolean create) throws ResourceException {
      try {
         return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws ResourceException {
               return PooledConnectionEnvFactory.this.makeConnection0(cc, create);
            }
         });
      } catch (PrivilegedActionException var4) {
         throw (ResourceException)var4.getException();
      }
   }

   private Connection makeConnection0(ConnectionEnv cc, boolean create) throws ResourceException {
      javax.sql.PooledConnection conn = null;
      long connBeganAt = 0L;
      long connEndedAt = 0L;
      this.connTime = 0L;
      if (this.delaySecs > 0) {
         if (this.delaySecs > 2) {
            JDBCLogger.logDelayingBeforeConnection(this.delaySecs, this.poolname);
         }

         try {
            Thread.sleep((long)(1000 * this.delaySecs));
         } catch (InterruptedException var20) {
         }
      }

      this.acquireCreateRequestPermit();

      Exception newConn;
      String connHandler;
      try {
         connBeganAt = System.currentTimeMillis();
         conn = cc.getConnectionPoolDataSource().getPooledConnection();
         connEndedAt = System.currentTimeMillis();
      } catch (Exception var23) {
         newConn = var23;

         try {
            JDBCUtil.parseException(newConn, this.url, this.driver, JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName));
         } catch (Exception var21) {
            if (var21 instanceof ResourceSystemException) {
               throw (ResourceSystemException)var21;
            }

            if (this.pool.isEnabled()) {
               String id = JDBCLogger.logError(var21.getMessage(), this.poolname);
               JDBCLogger.logStackTraceId(id, var21);
            }

            conn = null;
            throw new ResourceException(var21.getMessage());
         } catch (UnsatisfiedLinkError var22) {
            throw new ResourceSystemException(JDBCUtil.makeUleMsg(this.driver, var22.getMessage(), JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName)));
         }
      } catch (Throwable var24) {
         connHandler = "";
         if (this.poolname != null) {
            connHandler = " for datasource '" + JDBCUtil.getDecoratedName(this.poolname, this.appname, this.moduleName, this.compName) + "'";
         }

         throw new ResourceSystemException("Could not create pool connection" + connHandler + ". The DBMS driver exception was: " + var24.getMessage());
      } finally {
         this.releaseCreateRequestPermit();
      }

      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         if (create) {
            JDBCLogger.logConnCreatedInfo(this.poolname);
         } else {
            JDBCLogger.logConnRefreshedInfo(this.poolname);
         }
      }

      newConn = null;
      connHandler = null;

      PooledConnection newConn;
      Connection connHandler;
      try {
         connHandler = conn.getConnection();
         newConn = (PooledConnection)JDBCWrapperFactory.getWrapper("weblogic.jdbc.wrapper.PooledConnection", connHandler, false);
      } catch (Exception var19) {
         JDBCLogger.logStackTrace(var19);
         conn = null;
         throw new ResourceException(StackTraceUtils.throwable2StackTrace(var19));
      }

      newConn.init(conn, cc, connHandler);
      if (connEndedAt > 0L) {
         this.connTime = connEndedAt - connBeganAt;
      }

      return newConn;
   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new ConnectionEnv(poolParams);
      return cc;
   }

   public void updateCredential(String p) throws SQLException {
      if (this.driverProps != null && this.driverProps.containsKey("password")) {
         this.driverProps.setProperty("password", p);
         if (this.cpDataSrc != null) {
            DataSourceUtil.initProp(this.cpDataSrc.getClass().getName(), this.cpDataSrc, "password", p);
         }
      }

   }
}

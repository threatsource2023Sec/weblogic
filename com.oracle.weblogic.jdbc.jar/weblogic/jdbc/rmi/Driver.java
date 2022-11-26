package weblogic.jdbc.rmi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jdbc.JDBCTextTextFormatter;
import weblogic.jdbc.common.internal.RmiDataSource;
import weblogic.jndi.Environment;
import weblogic.jndi.WLInitialContextFactory;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.http.HttpParsing;

/** @deprecated */
@Deprecated
public final class Driver extends RMIWrapperImpl implements java.sql.Driver {
   private static final String URL_BASE = "jdbc:weblogic:rmi";
   private static final String DATA_SOURCE_NAME_PROP = "weblogic.jdbc.datasource";
   private static final String SERVER_URL_PROP = "weblogic.server.url";
   private static final String USER = "weblogic.user";
   private static final String PASSWORD = "weblogic.credential";
   private static final String REMOTEDATASOURCE = "weblogic.jts.remotedatasource";
   private static final String PARTITION_NAME_PROP = "weblogic.partition.name";
   private boolean debug = false;
   private DebugLogger JDBCRMIDriver = null;

   public Connection connect(String url, Properties info) throws SQLException {
      Connection ret = null;
      String methodName = "getConnection";
      Object[] params = new Object[]{url, info};

      try {
         this.preInvocationHandler(methodName, params);
         String propName;
         String dsName;
         String user;
         if (info != null) {
            propName = "weblogic.jdbc.verbose";
            dsName = info.getProperty(propName);
            if (dsName != null) {
               try {
                  this.debug = Boolean.valueOf(dsName);
               } catch (Exception var17) {
                  user = "The Property " + propName + " must be a true or false.";
                  throw new SQLException(user);
               }
            }

            if (this.debug) {
               this.JDBCRMIDriver = DebugLogger.createUnregisteredDebugLogger("JDBCRMIDriver", true);
            }
         }

         if (this.debug) {
            propName = "time=" + System.currentTimeMillis() + " : connect\n\turl=" + url + "\n\tinfo=" + info;
            this.JDBCRMIDriver.debug(propName);
         }

         if (!this.acceptsURL(url)) {
            this.postInvocationHandler(methodName, params, (Object)null);
            return null;
         }

         int qindex = url.indexOf(63);
         if (qindex != -1) {
            HttpParsing.parseQueryString(url.substring(qindex + 1), info);
            url.substring(0, qindex);
         }

         if (this.debug) {
            dsName = "time=" + System.currentTimeMillis() + " : getting context";
            this.JDBCRMIDriver.debug(dsName);
         }

         dsName = info.getProperty("weblogic.jdbc.datasource");
         String serverUrl = info.getProperty("weblogic.server.url");
         user = info.getProperty("weblogic.user");
         Object password = info.getProperty("weblogic.credential");
         if (dsName == null) {
            throw new SQLException("You must define weblogic.jdbc.datasource");
         }

         if (serverUrl == null) {
            throw new SQLException("You must define weblogic.server.url");
         }

         if (this.debug) {
            this.JDBCRMIDriver.debug("weblogic.jdbc.datasource=" + dsName);
            this.JDBCRMIDriver.debug("weblogic.server.url=" + serverUrl);
         }

         InitialContext ctx;
         Environment ds;
         String partitionName;
         try {
            ds = new Environment();
            partitionName = WLInitialContextFactory.class.getName();
            ds.setInitialContextFactory(partitionName);
            ds.setProviderUrl(serverUrl);
            if (user != null) {
               ds.setSecurityPrincipal(user);
            }

            if (password != null) {
               ds.setSecurityCredentials(password);
            }

            ctx = new InitialContext(ds.getProperties());
         } catch (NamingException var18) {
            throw new SQLException(var18.toString());
         }

         if (this.debug) {
            String msg = "time=" + System.currentTimeMillis() + " : got context,";
            this.JDBCRMIDriver.debug(msg);
         }

         ds = null;

         try {
            if (this.debug) {
               partitionName = "time=" + System.currentTimeMillis() + " : lookup " + dsName;
               this.JDBCRMIDriver.debug(partitionName);
            }

            partitionName = info.getProperty("weblogic.partition.name");
            DataSource ds;
            if (partitionName != null) {
               ds = (DataSource)ctx.lookup("partition:" + partitionName + "/" + dsName);
            } else {
               ds = (DataSource)ctx.lookup(dsName);
            }

            if (ds != null && info.getProperty("weblogic.jts.remotedatasource") != null && ds instanceof RmiDataSource) {
               Throwable pinnedRefError = null;

               try {
                  ds = (DataSource)ServerHelper.getStubWithPinnedRef((RmiDataSource)ds, serverUrl);
               } catch (Throwable var16) {
                  pinnedRefError = var16;
               }

               if (ds == null || ds instanceof RmiDataSource) {
                  throw new RuntimeException("CR106162: WLS RMI runtime failed to give us a remote DataSource to " + serverUrl, pinnedRefError);
               }
            }

            if (ds != null) {
               String msg;
               if (this.debug) {
                  msg = "time=" + System.currentTimeMillis() + " : got " + dsName;
                  this.JDBCRMIDriver.debug(msg);
                  msg = "time=" + System.currentTimeMillis() + " : getting connection";
                  this.JDBCRMIDriver.debug(msg);
               }

               ret = ds.getConnection();
               if (this.debug) {
                  msg = "time=" + System.currentTimeMillis() + " : got connection";
                  this.JDBCRMIDriver.debug(msg);
               }
            }
         } catch (NamingException var19) {
            throw new SQLException(var19.toString());
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var20) {
         this.invocationExceptionHandler(methodName, params, var20);
      }

      return ret;
   }

   public boolean acceptsURL(String url) throws SQLException {
      boolean ret = true;
      String methodName = "acceptsURL";
      Object[] params = new Object[]{url};

      try {
         this.preInvocationHandler(methodName, params);
         if (url.startsWith("jdbc:weblogic:rmi")) {
            ret = true;
         } else {
            ret = false;
         }

         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      String methodName = "DriverPropertyInfo";
      Object[] params = new Object[]{url, info};

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return null;
   }

   public int getMajorVersion() {
      String methodName = "getMajorVersion";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, new Integer(1));
      } catch (Exception var4) {
      }

      return 1;
   }

   public int getMinorVersion() {
      String methodName = "getMinorVersion";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, new Integer(2));
      } catch (Exception var4) {
      }

      return 2;
   }

   public boolean jdbcCompliant() {
      String methodName = "jdbcCompliant";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, new Boolean(true));
      } catch (Exception var4) {
      }

      return true;
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }

   static {
      try {
         DriverManager.registerDriver(new Driver());
      } catch (SQLException var1) {
         DriverManager.println((new JDBCTextTextFormatter()).driverLoadingError(var1.getClass().getName(), var1.getMessage()));
      }

   }
}

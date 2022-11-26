package weblogic.jdbc.pool;

import java.security.AccessController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import weblogic.jdbc.JDBCTextTextFormatter;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ConnectionHolder;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.PoolConnection;
import weblogic.jdbc.wrapper.XAConnection;
import weblogic.kernel.KernelStatus;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

/** @deprecated */
@Deprecated
public final class Driver implements java.sql.Driver {
   private static final AuthenticatedSubject KERNELID = getKernelID();
   private PrincipalAuthenticator pa = null;

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public Connection connect(String url, Properties dbprops) throws SQLException {
      if (!this.acceptsURL(url)) {
         return null;
      } else {
         String poolID = null;
         String poolScope = null;
         String appName = null;
         String moduleName = null;
         Properties labels = null;
         String compName = null;
         String username = null;
         String password = null;
         boolean useDatabaseCredentials = false;
         if (dbprops != null) {
            poolID = (String)dbprops.get("connectionPoolID");
            poolScope = (String)dbprops.get("connectionPoolScope");
            labels = (Properties)dbprops.get("RequestedLabels");
            appName = (String)dbprops.get("applicationName");
            moduleName = (String)dbprops.get("moduleName");
            compName = (String)dbprops.get("compName");
            username = (String)dbprops.get("user");
            password = (String)dbprops.get("password");
            useDatabaseCredentials = Boolean.valueOf((String)dbprops.get("useDatabaseCredentials"));
         }

         if (poolID == null) {
            poolID = url.substring(url.lastIndexOf(":") + 1);
            if (poolID.equals("pool")) {
               throw new SQLException("connectionPoolID not set in properties or url");
            }
         }

         if (poolScope != null && poolScope.equalsIgnoreCase("application")) {
            String appNameFromClassLoader = JDBCHelper.getHelper().getCurrentApplicationName();
            if (appNameFromClassLoader != null) {
               appName = appNameFromClassLoader;
            }
         }

         ConnectionEnv cc = null;

         try {
            AuthenticatedSubject subject = null;
            if ((username != null || password != null) && (!useDatabaseCredentials || username == null) && !dbprops.containsKey("IMPERSONATE")) {
               SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
               if (this.pa == null) {
                  String realmName = "weblogicDEFAULT";
                  this.pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNELID, realmName, ServiceType.AUTHENTICATION);
               }

               subject = this.pa.authenticate(handler);
            } else {
               subject = SecurityServiceManager.getCurrentSubject(KERNELID);
            }

            cc = ConnectionPoolManager.reserve(subject, poolID, appName, moduleName, compName, labels, username, password);
            if (cc != null && cc.conn != null && cc.conn.jconn instanceof XAConnection) {
               ConnectionPoolManager.release(cc);
               throw new Exception("WebLogic Pool Driver doesn't support XA driver; please change your config to use a Non-XA driver");
            }
         } catch (Exception var16) {
            if (!KernelStatus.isServer()) {
               throw new SQLException("The pool driver only works within the WebLogic server, it cannot be called directly in a client. Please Use RMI Driver/DataSource instead.");
            }

            JDBCUtil.wrapAndThrowResourceException(var16, "Pool connect failed");
         }

         return this.allocateConnection(cc);
      }
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      String id = (String)info.get("connectionPoolID");
      if (id == null) {
         id = url.substring(url.lastIndexOf(":") + 1);
      }

      if ("pool".equals(id)) {
         throw new SQLException("connectionPoolID not set in properties or url");
      } else {
         DriverPropertyInfo[] dpis = new DriverPropertyInfo[]{new DriverPropertyInfo("connectionPoolID", id), null, null};
         dpis[0].description = "The ID of the connection pool";
         dpis[0].required = true;
         dpis[1] = new DriverPropertyInfo("user", (String)info.get("user"));
         dpis[1].description = "The WebLogic user name";
         dpis[2] = new DriverPropertyInfo("password", (String)info.get("password"));
         dpis[2].description = "The WebLogic password for the user supplied";
         return dpis;
      }
   }

   public int getMajorVersion() {
      return 1;
   }

   public int getMinorVersion() {
      return 22;
   }

   public boolean jdbcCompliant() {
      return true;
   }

   protected Connection allocateConnection(ConnectionEnv cc) throws SQLException {
      ConnectionHolder ch = null;
      if (cc != null) {
         ch = cc.conn;
      }

      if (ch != null && ch.jconn != null) {
         PoolConnection poolConnection = (PoolConnection)JDBCWrapperFactory.getWrapper(0, ch.jconn, false);
         poolConnection.init(cc);
         cc.setResourceCleanupHandler(poolConnection);
         return (Connection)poolConnection;
      } else {
         throw new SQLException("Connection no longer valid: " + cc);
      }
   }

   public boolean acceptsURL(String url) {
      if (url == null) {
         return false;
      } else {
         return url.startsWith("jdbc:weblogic:pool");
      }
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

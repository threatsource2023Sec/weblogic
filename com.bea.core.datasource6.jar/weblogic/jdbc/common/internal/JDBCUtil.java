package weblogic.jdbc.common.internal;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourceDeadException;
import weblogic.common.resourcepool.ResourceDisabledException;
import weblogic.common.resourcepool.ResourceLimitException;
import weblogic.common.resourcepool.ResourcePermissionsException;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.common.resourcepool.ResourceUnavailableException;
import weblogic.common.resourcepool.ResourceUnusableException;
import weblogic.descriptor.SettableBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCTextTextFormatter;
import weblogic.jdbc.extensions.ConnectionDeadSQLException;
import weblogic.jdbc.extensions.ConnectionUnavailableSQLException;
import weblogic.jdbc.extensions.PoolDisabledSQLException;
import weblogic.jdbc.extensions.PoolLimitSQLException;
import weblogic.jdbc.extensions.PoolPermissionsSQLException;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.kernel.KernelStatus;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.JDBCResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class JDBCUtil {
   private static final String CREATE_INTERMEDIATE_CONTEXTS = "weblogic.jndi.createIntermediateContexts";
   private static final String REPLICATE_BINDINGS = "weblogic.jndi.replicateBindings";
   static final String RES_TYPE_CP = "ConnectionPool";
   static final String RES_TYPE_MP = "MultiPool";
   static final String OP_ADMIN = "admin";
   static final String OP_RESET = "reset";
   static final String OP_RESERVE = "reserve";
   static final String OP_SHRINK = "shrink";
   private static JDBCTextTextFormatter fmt;
   static final DebugLogger JDBCInternal = DebugLogger.getDebugLogger("DebugJDBCInternal");
   private static final String ERR_MSG_NO_DRIVER = "No suitable driver";
   private static final String ERR_MSG_BAD_PORT = "Invalid number format for port number";
   private static final String ERR_MSG_BAD_LOGIN = "ORA-01017";
   private static final String ERR_MSG_LINK_ERR = "java.lang.UnsatisfiedLinkError";
   private static String SDP_PROP = "oracle.net.SDP";
   private static String OPTIMIZEUTF8CONVERSION_PROP = "oracle.jdbc.OptimizeUtf8Conversion";

   public static void bindAll(Context ctx, String[] names, Object o) throws NamingException {
      if (JDBCHelper.getHelper().isJNDIEnabled()) {
         String realName = null;
         Context isolatedContext = null;

         for(int lcv = 0; lcv < names.length; ++lcv) {
            if (realName == null) {
               realName = names[lcv].trim();
               if (realName != null) {
                  bindDeeply(ctx, realName, o);
               }
            } else {
               String tok = names[lcv].trim();
               Object toBind = JDBCHelper.getHelper().createJNDIAlias(realName, o);
               if (isolatedContext == null) {
                  isolatedContext = getIsolatedContext();
               }

               bindDeeply(isolatedContext, tok, toBind);
            }
         }

      }
   }

   public static void localBindAll(Context ctx, String[] names, Object o) throws NamingException {
      if (JDBCHelper.getHelper().isJNDIEnabled()) {
         String realName = null;

         for(int lcv = 0; lcv < names.length; ++lcv) {
            if (realName == null) {
               realName = names[lcv].trim();
               if (realName == null) {
                  return;
               }

               realName = "jdbc/" + realName;
               bindDeeply(ctx, realName, o);
            } else {
               String tok = names[lcv].trim();
               bindDeeply(ctx, "jdbc/" + tok, new LinkRef("java:app/" + realName));
            }
         }

      }
   }

   public static void unBindAll(Context ctx, String[] names) throws NamingException {
      if (JDBCHelper.getHelper().isJNDIEnabled()) {
         for(int lcv = 0; lcv < names.length; ++lcv) {
            String tok = names[lcv].trim();
            ctx.unbind(tok);
         }

      }
   }

   public static void localUnBindAll(Context ctx, String[] names) throws NamingException {
      if (JDBCHelper.getHelper().isJNDIEnabled()) {
         for(int lcv = 0; lcv < names.length; ++lcv) {
            String tok = names[lcv].trim();
            ctx.unbind("jdbc/" + tok);
         }

      }
   }

   public static void bindDeeply(Context ctx, String name, Object o) throws NamingException {
      if (JDBCHelper.getHelper().isJNDIEnabled()) {
         String orig_name = name;

         while(true) {
            int i = name.indexOf(47);
            if (i < 0) {
               ctx.bind(name, o);
               return;
            }

            String first = name.substring(0, i);
            name = name.substring(i + 1);
            Object c = null;

            try {
               c = ctx.lookup(first);
            } catch (NameNotFoundException var8) {
               c = null;
            }

            if (c == null) {
               ctx = ctx.createSubcontext(first);
            } else {
               if (!(c instanceof Context)) {
                  if (c instanceof RmiDataSource) {
                     throw new NameAlreadyBoundException("Datasource " + ((RmiDataSource)c).getPoolName() + " already bound at the " + first + " level of " + orig_name);
                  }

                  throw new NameAlreadyBoundException("A " + c.getClass() + " is already bound at the " + first + " level of " + orig_name);
               }

               ctx = (Context)c;
            }
         }
      }
   }

   static Context getIsolatedContext() throws NamingException {
      if (!JDBCHelper.getHelper().isJNDIEnabled()) {
         return null;
      } else {
         Properties props = new Properties();
         props.setProperty("weblogic.jndi.createIntermediateContexts", Boolean.TRUE.toString());
         props.setProperty("weblogic.jndi.replicateBindings", Boolean.FALSE.toString());
         return new InitialContext(props);
      }
   }

   public static Context getContext() throws NamingException {
      if (!JDBCHelper.getHelper().isJNDIEnabled()) {
         return null;
      } else {
         Properties props = new Properties();
         props.setProperty("weblogic.jndi.createIntermediateContexts", Boolean.TRUE.toString());
         props.setProperty("weblogic.jndi.replicateBindings", Boolean.TRUE.toString());
         return new InitialContext(props);
      }
   }

   public static void checkPermission(AuthenticatedSubject currentSubject, AuthenticatedSubject kernelId, AuthorizationManager am, String resType, String resName, String appName, String moduleName, String compName, String opcode) throws ResourceException {
      checkPermission(currentSubject, kernelId, am, resType, resName, appName, moduleName, compName, opcode, (String)null, (Resource)null);
   }

   public static void checkPermission(AuthenticatedSubject currentSubject, AuthenticatedSubject kernelId, AuthorizationManager am, String resType, String resName, String appName, String moduleName, String compName, String opcode, String opName) throws ResourceException {
      checkPermission(currentSubject, kernelId, am, resType, resName, appName, moduleName, compName, opcode, opName, (Resource)null);
   }

   public static void checkPermission(AuthenticatedSubject currentSubject, AuthenticatedSubject kernelId, AuthorizationManager am, String resType, String resName, String appName, String moduleName, String compName, String opcode, String opName, Resource resource) throws ResourceException {
      if (JDBCInternal.isDebugEnabled()) {
         JDBCInternal.debug(" > JDBCUtil:checkPermission (10) resType = " + resType + ", resName = " + resName + ", appName = " + appName + ", opcode = " + opcode + ", opName = " + opName + ", resource = " + resource + ", moduleName = " + moduleName + ", compName = " + compName);
      }

      if (!KernelStatus.isJ2eeClient() && !KernelStatus.isDeployer()) {
         if (currentSubject == null) {
            currentSubject = SecurityServiceManager.getCurrentSubject(kernelId);
         }

         String m;
         if (resource == null) {
            m = moduleName;
            if (compName != null) {
               m = moduleName + "@" + compName;
            }

            resource = new JDBCResource(appName, m, resType, resName, opcode);
         }

         if (!am.isAccessAllowed(currentSubject, (Resource)resource, new ResourceIDDContextWrapper())) {
            if (JDBCInternal.isDebugEnabled()) {
               JDBCInternal.debug(" <* JDBCUtil:checkPermission(20) failed");
            }

            m = moduleName;
            if (moduleName == null) {
               m = "none";
            }

            String a = appName;
            if (appName == null) {
               a = "none";
            }

            throw new ResourcePermissionsException("User \"" + SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(kernelId)) + "\" does not have permission to perform operation \"" + opcode + (opName != null ? "." + opName : "") + "\" on resource \"" + resName + "\" of module \"" + m + "\" of application \"" + a + "\" of type \"" + resType + "\"");
         } else {
            if (JDBCInternal.isDebugEnabled()) {
               JDBCInternal.debug(" < JDBCUtil:checkPermission (30) passed");
            }

         }
      }
   }

   public static void wrapAndThrowResourceException(Exception e, String msg) throws SQLException {
      if (e instanceof ResourceDeadException) {
         throw new ConnectionDeadSQLException(e.toString());
      } else if (e instanceof ResourceDisabledException) {
         throw new PoolDisabledSQLException(e.toString());
      } else if (e instanceof ResourceLimitException) {
         throw new PoolLimitSQLException(e.toString());
      } else if (e instanceof ResourcePermissionsException) {
         throw new PoolPermissionsSQLException(e.toString());
      } else if (e instanceof ResourceUnavailableException) {
         throw new ConnectionUnavailableSQLException(e.toString());
      } else if (e instanceof ResourceUnusableException) {
         throw new PoolUnavailableSQLException(e.toString());
      } else if (e instanceof FeatureNotSupportedException) {
         throw new SQLFeatureNotSupportedException(e.toString());
      } else {
         Throwable t;
         if (msg != null) {
            t = (new SQLException(msg + " : " + e.toString())).initCause(e);
         } else {
            t = (new SQLException(e.toString())).initCause(e);
         }

         throw (SQLException)t;
      }
   }

   public static JDBCTextTextFormatter getTextFormatter() {
      if (fmt == null) {
         fmt = new JDBCTextTextFormatter();
      }

      return fmt;
   }

   static final void parseException(Exception t, String url, String driver, String pool) throws Exception {
      String msg = null;
      String forPool = "";
      if (pool != null) {
         forPool = " for datasource '" + pool + "'";
      }

      if (t instanceof InstantiationException) {
         msg = "Cannot instantiate driver " + driver + forPool + ".";
      } else if (t instanceof IllegalAccessException) {
         msg = "Cannot instantiate driver " + driver + forPool + ".";
      } else if (t instanceof ClassNotFoundException) {
         msg = "Cannot load driver class " + driver + forPool + ".";
      } else if (t instanceof SQLException) {
         if (t.getMessage().indexOf("No suitable driver") != -1) {
            msg = "No registered driver accepts URL " + url + forPool + ".";
         } else if (t.getMessage().indexOf("java.lang.UnsatisfiedLinkError") != -1) {
            msg = makeUleMsg(driver, msg, pool);
         } else if (t.getMessage().indexOf("Invalid number format for port number") != -1) {
            msg = "Invalid port number for URL " + url + forPool;
         } else if (t.getMessage().indexOf("ORA-01017") != -1) {
            msg = makeNAMsg(forPool, t.getMessage());
         }
      }

      if (msg != null) {
         throw new ResourceSystemException(msg);
      } else {
         throw t;
      }
   }

   private static String makeNAMsg(String forPool, String error_msg) {
      String msg = "\n Could not create connection" + forPool + ".";
      msg = msg + "\n";
      msg = msg + "\n The returned message is: " + error_msg;
      msg = msg + "\n It is likely that the login or password is not valid.";
      msg = msg + "\n It is also possible that something else is invalid in";
      msg = msg + "\n the configuration or that the database is not available.";
      return msg;
   }

   static String convertPropertiesToString(Properties props) {
      StringBuffer sb = new StringBuffer();
      Enumeration e = props.propertyNames();

      while(e.hasMoreElements()) {
         String propName = (String)e.nextElement();
         if (!"password".equals(propName.toLowerCase(Locale.ENGLISH))) {
            sb.append(propName);
            sb.append("=");
            sb.append(props.getProperty(propName));
            sb.append(";");
         }
      }

      return sb.toString();
   }

   static String makeUleMsg(String driver, String err_msg, String pool) {
      if (pool != null) {
         pool = " for datasource '" + pool + "'";
      }

      String msg = "\n Cannot load driver class " + driver + pool + ".";
      msg = msg + "\n";
      msg = msg + "\n If this is a type-4 JDBC driver, it could occur if the JDBC";
      msg = msg + "\n driver is not in the system CLASSPATH.";
      msg = msg + "\n";
      msg = msg + "\n If this is a type-2 JDBC driver, it may also indicate that";
      msg = msg + "\n the Driver native layers(DBMS client lib or driver DLL)";
      msg = msg + "\n have not been installed properly on your system";
      msg = msg + "\n or in your PATH environment variable.";
      msg = msg + "\n This is most likely caused by one of the following:";
      msg = msg + "\n 1. The native layer SO, SL, or DLL could not be found.";
      msg = msg + "\n 2. The file permissions on the native layer SO, SL, or DLL";
      msg = msg + "\n    have not been set properly.";
      msg = msg + "\n 3. The native layer SO, SL, or DLL exists, but is either";
      msg = msg + "\n    invalid or corrupted.\n";
      msg = msg + "\n For more information, read the installation documentation";
      msg = msg + "\n for your JDBC Driver.\n";
      msg = msg + "";
      return msg;
   }

   public static Properties getProperties(JDBCPropertyBean[] props) {
      return getProperties((JDBCDataSourceBean)null, props, (String)null);
   }

   public static Properties getProperties(JDBCDataSourceBean dsBean, JDBCPropertyBean[] props) {
      return getProperties(dsBean, props, (String)null);
   }

   public static Properties getProperties(JDBCDataSourceBean dsBean, JDBCPropertyBean[] props, String datasourceName) {
      if (props == null) {
         return null;
      } else {
         Properties driverProps = new Properties();

         for(int i = 0; i < props.length; ++i) {
            if (props[i].getName() != null) {
               String s = getPropValue(dsBean, props[i], datasourceName);
               if (s != null) {
                  driverProps.setProperty(props[i].getName(), s);
               }
            }
         }

         return driverProps;
      }
   }

   public static String getPropValue(JDBCDataSourceBean dsBean, JDBCPropertyBean prop, String datasourceName) {
      if (prop == null) {
         return null;
      } else {
         String value = prop.getSysPropValue();
         if (value == null) {
            value = prop.getEncryptedValue();
            if (value == null) {
               value = prop.getValue();
            }

            return value;
         } else {
            boolean found = false;
            String var = "${pid}";
            String s;
            int i;
            String name;
            if (value.indexOf(var) != -1) {
               found = true;
               name = ManagementFactory.getRuntimeMXBean().getName();
               i = name.indexOf(64);
               if (i != -1) {
                  for(s = name.substring(0, i); (i = value.indexOf(var)) != -1; value = value.substring(0, i) + s + value.substring(i + var.length())) {
                  }
               }
            }

            var = "${machine}";
            if (value.indexOf(var) != -1) {
               found = true;
               name = ManagementFactory.getRuntimeMXBean().getName();
               i = name.indexOf(64);
               if (i != -1) {
                  for(s = name.substring(i + 1); (i = value.indexOf(var)) != -1; value = value.substring(0, i) + s + value.substring(i + var.length())) {
                  }
               }
            }

            var = "${user.name}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = null;

               try {
                  s = System.getProperty("user.name");
               } catch (Exception var11) {
               }

               if (s != null) {
                  while((i = value.indexOf(var)) != -1) {
                     value = value.substring(0, i) + s + value.substring(i + var.length());
                  }
               }
            }

            var = "${os.name}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = null;

               try {
                  s = System.getProperty("os.name");
               } catch (Exception var10) {
               }

               if (s != null) {
                  while((i = value.indexOf(var)) != -1) {
                     value = value.substring(0, i) + s + value.substring(i + var.length());
                  }
               }
            }

            var = "${datasourcename}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = getUnqualifiedName(dsBean);
               if (s == null) {
                  s = datasourceName;
               }

               if (s != null) {
                  while((i = value.indexOf(var)) != -1) {
                     value = value.substring(0, i) + s + value.substring(i + var.length());
                  }
               }
            }

            var = "${partition}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = getPartitionName(dsBean);
               if (s == null) {
                  s = "DOMAIN";
               }

               while((i = value.indexOf(var)) != -1) {
                  value = value.substring(0, i) + s + value.substring(i + var.length());
               }
            }

            var = "${servername}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = JDBCHelper.getHelper().getServerName();
               if (s != null) {
                  while((i = value.indexOf(var)) != -1) {
                     value = value.substring(0, i) + s + value.substring(i + var.length());
                  }
               }
            }

            var = "${domainname}";
            if (value.indexOf(var) != -1) {
               found = true;
               s = JDBCHelper.getHelper().getDomainName();
               if (s != null) {
                  while((i = value.indexOf(var)) != -1) {
                     value = value.substring(0, i) + s + value.substring(i + var.length());
                  }
               }
            }

            var = "${serverport}";
            int port;
            if (value.indexOf(var) != -1) {
               found = true;
               port = JDBCHelper.getHelper().getServerPort();
               if (port != -1) {
                  for(s = "" + port; (i = value.indexOf(var)) != -1; value = value.substring(0, i) + s + value.substring(i + var.length())) {
                  }
               }
            }

            var = "${serversslport}";
            if (value.indexOf(var) != -1) {
               found = true;
               port = JDBCHelper.getHelper().getServerSslPort();
               if (port != -1) {
                  for(s = "" + port; (i = value.indexOf(var)) != -1; value = value.substring(0, i) + s + value.substring(i + var.length())) {
                  }
               }
            }

            if (!found) {
               try {
                  value = System.getProperty(value);
               } catch (Exception var9) {
                  return null;
               }

               if (value == null) {
                  return null;
               }
            }

            return value;
         }
      }
   }

   public static boolean isInternalPropertySet(JDBCDataSourceBean dsBean, String propName) {
      if (dsBean == null) {
         return false;
      } else {
         JDBCPropertyBean prop = dsBean.getInternalProperties().lookupProperty(propName);
         String s = getPropValue(dsBean, prop, (String)null);
         return s == null ? false : Boolean.valueOf(s);
      }
   }

   public static void setOracleProps(JDBCConnectionPool pool, String url, Properties args, String driver) {
      if (driver.startsWith("oracle.jdbc.replay")) {
         args.put("oracle.jdbc.internal.useWLSStmtCache", "true");
      }

      if (url != null && url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
         args.put(SDP_PROP, "true");
      } else {
         try {
            args.remove(SDP_PROP);
         } catch (Exception var6) {
         }
      }

      if (pool != null && pool.isOracleOptimizeUtf8Conversion()) {
         args.put(OPTIMIZEUTF8CONVERSION_PROP, "true");
      } else {
         try {
            args.remove(OPTIMIZEUTF8CONVERSION_PROP);
         } catch (Exception var5) {
         }
      }

   }

   public static void checkSdp(String url, Properties properties) {
      if (properties != null && url != null && url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
         properties.put(SDP_PROP, "true");
      }

   }

   public static String getDecoratedName(String name, String appName, String moduleName, String compName) {
      if (name == null) {
         return null;
      } else if (name.startsWith("java:global")) {
         return name;
      } else {
         if (name.startsWith("java:app")) {
            compName = null;
            moduleName = null;
         } else if (name.startsWith("java:module")) {
            compName = null;
         } else if (name.startsWith("java:comp")) {
         }

         if (appName != null) {
            if (moduleName != null) {
               return compName != null ? appName + "@" + moduleName + "@" + compName + "@" + name : appName + "@" + moduleName + "@" + name;
            } else {
               return !name.startsWith("java:") ? appName + "@" + moduleName + "@" + name : appName + "@" + name;
            }
         } else {
            return name;
         }
      }
   }

   public static String getPartitionName(JDBCDataSourceBean dsBean) {
      if (dsBean != null) {
         JDBCPropertiesBean internalProps = dsBean.getInternalProperties();
         if (internalProps != null) {
            JDBCPropertyBean partitionNameProp = internalProps.lookupProperty("PartitionName");
            if (partitionNameProp != null) {
               return partitionNameProp.getValue();
            }
         }
      }

      return null;
   }

   public static String getUnqualifiedName(JDBCDataSourceBean dsBean) {
      if (dsBean != null) {
         JDBCPropertiesBean internalProps = dsBean.getInternalProperties();
         if (internalProps != null) {
            JDBCPropertyBean unqualifiedNameProp = internalProps.lookupProperty("UnqualifiedName");
            if (unqualifiedNameProp != null) {
               return unqualifiedNameProp.getValue();
            }
         }
      }

      return null;
   }

   public static boolean isCrossPartitionEnabled(JDBCDataSourceBean dsBean) {
      if (dsBean != null) {
         JDBCPropertyBean crossPartitionEnabledProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.crossPartitionEnabled");
         if (crossPartitionEnabledProp != null) {
            String val = crossPartitionEnabledProp.getValue();
            if (val != null) {
               return Boolean.valueOf(val);
            }
         }
      }

      return false;
   }

   public static int getLegacyType(JDBCDataSourceBean dsBean) {
      int legacyType = 0;
      String val = getInternalProperty(dsBean, "LegacyType");
      if (val != null) {
         legacyType = Integer.parseInt(val);
      }

      return legacyType;
   }

   public static String getInternalProperty(JDBCDataSourceBean dsBean, String propName) {
      JDBCPropertyBean prop = null;

      try {
         prop = dsBean.getInternalProperties().lookupProperty(propName);
      } catch (Exception var4) {
      }

      return prop == null ? null : prop.getValue();
   }

   public static void setDriverProperties(JDBCDataSourceBean dsBean, Properties driverProps) {
      JDBCPropertiesBean props = dsBean.getJDBCDriverParams().getProperties();
      JDBCPropertyBean[] properties = props.getProperties();
      int lcv;
      if (driverProps == null) {
         if (properties != null) {
            for(lcv = 0; lcv < properties.length; ++lcv) {
               props.destroyProperty(properties[lcv]);
            }

         }
      } else {
         if (properties != null) {
            for(lcv = 0; lcv < properties.length; ++lcv) {
               props.destroyProperty(properties[lcv]);
            }
         }

         Enumeration e = driverProps.propertyNames();

         while(e.hasMoreElements()) {
            String propName = (String)e.nextElement();
            if ("password".equals(propName)) {
               dsBean.getJDBCDriverParams().setPassword(driverProps.getProperty(propName));
            } else {
               props.createProperty(propName, driverProps.getProperty(propName));
            }
         }

      }
   }

   public static String getSharedPoolJNDIName(JDBCDataSourceBean dsBean) {
      if (dsBean.getJDBCDriverParams() != null && dsBean.getJDBCDriverParams().getProperties() != null) {
         JDBCPropertyBean sharedPoolJNDINameProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.sharedPoolJNDIName");
         return sharedPoolJNDINameProp != null && sharedPoolJNDINameProp.getValue() != null ? sharedPoolJNDINameProp.getValue() : null;
      } else {
         return null;
      }
   }

   public static boolean isSharedPool(JDBCDataSourceBean dsBean) {
      if (dsBean.getJDBCDriverParams() != null && dsBean.getJDBCDriverParams().getProperties() != null) {
         JDBCPropertyBean sharedPoolProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.sharedPool");
         return sharedPoolProp != null && sharedPoolProp.getValue() != null ? Boolean.valueOf(sharedPoolProp.getValue()) : false;
      } else {
         return false;
      }
   }

   public static boolean usesSharedPool(JDBCDataSourceBean dsBean) {
      return getSharedPoolJNDIName(dsBean) != null;
   }

   public static String getConnectionPoolName(JDBCDataSourceBean dsBean) {
      return dsBean.getName();
   }

   public static String getPDBName(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean pdbNameProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.pdbName");
      return pdbNameProp != null && pdbNameProp.getValue() != null ? pdbNameProp.getValue() : null;
   }

   public static String getPDBServiceName(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean pdbServiceNameProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.pdbServiceName");
      return pdbServiceNameProp != null && pdbServiceNameProp.getValue() != null ? pdbServiceNameProp.getValue() : null;
   }

   public static String getPDBRoleJDBCPropertyName(String roleName) {
      return "weblogic.jdbc.pdbRole." + roleName;
   }

   public static String getPDBProxyUserJDBCPropertyName(String proxyName) {
      return "weblogic.jdbc.pdbProxy." + proxyName;
   }

   public static List getRoleNames(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean[] properties = dsBean.getJDBCDriverParams().getProperties().getProperties();
      List roleNames = new ArrayList();
      JDBCPropertyBean[] var3 = properties;
      int var4 = properties.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         JDBCPropertyBean prop = var3[var5];
         String propName = prop.getName();
         if (propName != null && propName.startsWith("weblogic.jdbc.pdbRole.") && propName.length() > "weblogic.jdbc.pdbRole.".length()) {
            String roleName = propName.substring("weblogic.jdbc.pdbRole.".length());
            roleNames.add(roleName);
         }
      }

      return roleNames;
   }

   public static String getRolePassword(JDBCDataSourceBean dsBean, String roleName) {
      JDBCPropertyBean rolePasswordProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty(getPDBRoleJDBCPropertyName(roleName));
      if (rolePasswordProp != null) {
         if (rolePasswordProp.getEncryptedValue() != null) {
            return rolePasswordProp.getEncryptedValue();
         }

         if (rolePasswordProp.getValue() != null) {
            return rolePasswordProp.getValue();
         }
      }

      return null;
   }

   public static String getProxyUser(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean[] properties = dsBean.getJDBCDriverParams().getProperties().getProperties();
      JDBCPropertyBean[] var2 = properties;
      int var3 = properties.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JDBCPropertyBean prop = var2[var4];
         String propName = prop.getName();
         if (propName != null && propName.startsWith("weblogic.jdbc.pdbProxy.") && propName.length() > "weblogic.jdbc.pdbProxy.".length()) {
            return propName.substring("weblogic.jdbc.pdbProxy.".length());
         }
      }

      return null;
   }

   public static String getProxyPassword(JDBCDataSourceBean dsBean, String proxyName) {
      JDBCPropertyBean proxyPasswordProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty(getPDBProxyUserJDBCPropertyName(proxyName));
      if (proxyPasswordProp != null) {
         if (proxyPasswordProp.getEncryptedValue() != null) {
            return proxyPasswordProp.getEncryptedValue();
         }

         if (proxyPasswordProp.getValue() != null) {
            return proxyPasswordProp.getValue();
         }
      }

      return null;
   }

   public static boolean isStartupCritical(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean criticalResourceProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.critical");
      return criticalResourceProp != null && criticalResourceProp.getValue() != null ? Boolean.valueOf(criticalResourceProp.getValue()) : false;
   }

   public static int getStartupRetryCount(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean retryCountProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.startupRetryCount");
      if (retryCountProp != null && retryCountProp.getValue() != null) {
         int count = Integer.parseInt(retryCountProp.getValue());
         if (count > 0) {
            return count;
         }
      }

      return 0;
   }

   public static int getStartupRetryDelaySeconds(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean retryDelayProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.startupRetryDelaySeconds");
      if (retryDelayProp != null && retryDelayProp.getValue() != null) {
         int seconds = Integer.parseInt(retryDelayProp.getValue());
         if (seconds > 0) {
            return seconds;
         }
      }

      return 0;
   }

   public static boolean isContinueMakeResourceAttemptsAfterFailure(JDBCDataSourceBean dsBean) {
      JDBCPropertyBean continueMakeResourceProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.continueMakeResourceAttemptsAfterFailure");
      return continueMakeResourceProp != null && continueMakeResourceProp.getValue() != null ? Boolean.valueOf(continueMakeResourceProp.getValue()) : false;
   }

   public static String getPDBInstanceGroupName(String pdbName, String instanceName) {
      return pdbName + "@" + instanceName;
   }

   public static String getServicePDBGroupName(String serviceName, String pdbName) {
      return serviceName + "@" + pdbName;
   }

   public static String getServicePDBInstanceGroupName(String serviceName, String pdbName, String instanceName) {
      return serviceName + "@" + pdbName + "@" + instanceName;
   }

   public static String getBeanAttributeName(Method method) {
      String name;
      if (method.getName().startsWith("get")) {
         name = method.getName().substring(3);
      } else if (method.getName().startsWith("is")) {
         name = method.getName().substring(2);
      } else {
         name = method.getName();
      }

      return name;
   }

   public static Map diff(Class beanif, SettableBean bean1, SettableBean bean2) throws ResourceException {
      Map diffs = new HashMap();
      Method[] methods = beanif.getDeclaredMethods();
      Method[] var5 = methods;
      int var6 = methods.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method method = var5[var7];
         Class retType = method.getReturnType();
         if (!retType.equals(Void.class) && method.getParameterTypes().length == 0 && (retType.isPrimitive() || retType.equals(String[].class) || retType.equals(String.class))) {
            try {
               Object value1 = method.invoke(bean1, (Object[])null);
               Object value2 = method.invoke(bean2, (Object[])null);
               if (value1 != null || value2 != null) {
                  if (retType.isArray()) {
                     if (!Arrays.equals((Object[])((Object[])value1), (Object[])((Object[])value2))) {
                        diffs.put(getBeanAttributeName(method), Arrays.asList(Arrays.toString((Object[])((Object[])value1)), Arrays.toString((Object[])((Object[])value2))));
                     }
                  } else if (value1 != null && !value1.equals(value2) || value2 != null && !value2.equals(value1)) {
                     diffs.put(getBeanAttributeName(method), Arrays.asList(value1 != null ? value1.toString() : "null", value2 != null ? value2.toString() : "null"));
                  }
               }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var12) {
               throw new ResourceException(var12);
            }
         }
      }

      return diffs;
   }

   public static boolean isEqualIgnoringCase(String a, String b) {
      if (a == null && b == null) {
         return true;
      } else {
         return a != null ? a.equalsIgnoreCase(b) : false;
      }
   }

   public static String getDataSourceType(JDBCDataSourceBean bean) {
      String type = bean.getDatasourceType();
      if (type != null && type.length() > 0) {
         return type;
      } else {
         JDBCDriverParamsBean driverParams = bean.getJDBCDriverParams();
         JDBCPropertiesBean jdbcProperties = driverParams.getProperties();
         if (jdbcProperties != null) {
            JDBCPropertyBean typeProp = jdbcProperties.lookupProperty("weblogic.jdbc.type");
            if (typeProp != null) {
               type = typeProp.getValue();
               if (type != null && type.length() > 0) {
                  return type;
               }
            }
         }

         JDBCOracleParamsBean oracleParams = bean.getJDBCOracleParams();
         if (oracleParams.isActiveGridlink() || oracleParams.isFanEnabled() || oracleParams.getOnsNodeList() != null && oracleParams.getOnsNodeList().length() > 0) {
            return "AGL";
         } else {
            JDBCDataSourceParamsBean dataSourceParams = bean.getJDBCDataSourceParams();
            String dataSourceList = dataSourceParams.getDataSourceList();
            return dataSourceList != null && dataSourceList.length() > 0 ? "MDS" : "GENERIC";
         }
      }
   }
}

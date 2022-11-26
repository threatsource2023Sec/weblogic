package weblogic.jdbc.common.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

public final class DataSourceUtil {
   static EncryptionService es = null;
   static ClearOrEncryptedService ces = null;

   public static void initProps(String dsName, Object ds, Properties props) throws SQLException {
      boolean doFCF = false;
      String ONSConfigurationprop = null;
      Enumeration e = props.propertyNames();

      while(e.hasMoreElements()) {
         String propName = (String)e.nextElement();
         if (propName.equals("FastConnectionFailoverEnabled")) {
            doFCF = true;
         } else if (propName.equals("ConnectionCachingEnabled")) {
            doFCF = true;
         } else if (propName.equals("ConnectionCacheName")) {
            doFCF = true;
         } else if (propName.equals("ONSConfiguration")) {
            doFCF = true;
            ONSConfigurationprop = props.getProperty(propName);
         } else if (!isInternalProperty(propName)) {
            initProp(dsName, ds, propName, props.getProperty(propName));
         }
      }

      if (doFCF) {
         initProp(dsName, ds, "ConnectionCachingEnabled", "true");
         initProp(dsName, ds, "FastConnectionFailoverEnabled", "true");
         Integer foo = ds.hashCode();
         initProp(dsName, ds, "ConnectionCacheName", foo.toString());
         initProp(dsName, ds, "ONSConfiguration", ONSConfigurationprop);
      }

      try {
         Method m = ds.getClass().getMethod("setConnectionProperties", props.getClass());
         m.invoke(ds, props);
      } catch (Throwable var7) {
      }

   }

   static boolean isInternalProperty(String propName) {
      return propName.equals("weblogic.jdbc.firstResourceCommit") || propName.equals("weblogic.jdbc.commitOutcomeEnabled") || propName.equals("weblogic.jdbc.commitOutcomeRetryMaxSeconds") || propName.equals("weblogic.jdbc.localResourceAssignmentEnabled") || propName.equals("weblogic.jdbc.attachNetworkTimeout") || propName.equals("weblogic.jdbc.critical") || propName.equals("weblogic.jdbc.startupRetryCount") || propName.equals("weblogic.jdbc.startupRetryDelaySeconds") || propName.equals("weblogic.jdbc.continueMakeResourceAttemptsAfterFailure") || propName.equals("weblogic.jdbc.sharedPool") || propName.equals("weblogic.jdbc.sharedPoolJNDIName") || propName.equals("weblogic.jdbc.pdbName") || propName.equals("weblogic.jdbc.pdbServiceName") || propName.startsWith("weblogic.jdbc.pdbProxy.") || propName.startsWith("weblogic.jdbc.pdbRole.") || propName.startsWith("weblogic.jdbc.maxConcurrentCreateRequests") || propName.startsWith("weblogic.jdbc.concurrentCreateRequestsTimeoutSeconds") || propName.equals("weblogic.jdbc.ProxyUseAuthUser") || propName.equals("weblogic.jdbc.profileConnectionLeakTimeoutSeconds") || propName.equals("weblogic.jdbc.drainTimeout");
   }

   static void initProp(String dsName, Object ds, String propName, Object value) throws SQLException {
      Object e = null;
      boolean var17 = false;

      boolean t;
      label305: {
         label306: {
            label307: {
               label308: {
                  try {
                     var17 = true;
                     Method[] meths = ds.getClass().getMethods();
                     Method meth = null;
                     String setterName = "set" + propName;
                     Class paramType = null;

                     for(int i = 0; i < meths.length; ++i) {
                        if (meths[i].getName().equalsIgnoreCase(setterName) && meths[i].getParameterTypes().length == 1) {
                           meth = meths[i];
                           paramType = meths[i].getParameterTypes()[0];
                           break;
                        }
                     }

                     if (meth != null) {
                        Object val = value;
                        if (value instanceof String) {
                           if (paramType == Integer.TYPE) {
                              val = Integer.valueOf((String)value);
                           } else if (paramType == Boolean.TYPE) {
                              val = Boolean.valueOf((String)value);
                           }
                        }

                        meth.invoke(ds, val);
                        var17 = false;
                     } else {
                        meth = ds.getClass().getMethod("setProperty", String.class, Object.class);
                        meth.invoke(ds, propName, value);
                        var17 = false;
                     }
                     break label305;
                  } catch (IllegalAccessException var18) {
                     e = var18;
                     var17 = false;
                     break label308;
                  } catch (NoSuchMethodException var19) {
                     e = var19;
                     var17 = false;
                     break label307;
                  } catch (InvocationTargetException var20) {
                     e = var20;
                     var17 = false;
                     break label306;
                  } catch (Exception var21) {
                     e = var21;
                     var17 = false;
                  } finally {
                     if (var17) {
                        boolean var11 = true;
                        if (var11) {
                           if (e == null) {
                              if ("password".equals(propName)) {
                                 JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
                              } else {
                                 JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
                              }
                           } else if (propName != null && !propName.equalsIgnoreCase("url")) {
                              JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
                           }
                        }

                     }
                  }

                  t = true;
                  if (t) {
                     if (e == null) {
                        if ("password".equals(propName)) {
                           JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
                        } else {
                           JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
                        }

                        return;
                     } else {
                        if (propName != null && !propName.equalsIgnoreCase("url")) {
                           JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
                           return;
                        }

                        return;
                     }
                  }

                  return;
               }

               t = true;
               if (t) {
                  if (e == null) {
                     if ("password".equals(propName)) {
                        JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
                     } else {
                        JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
                     }

                     return;
                  } else {
                     if (propName != null && !propName.equalsIgnoreCase("url")) {
                        JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
                        return;
                     }

                     return;
                  }
               }

               return;
            }

            t = true;
            if (t) {
               if (e == null) {
                  if ("password".equals(propName)) {
                     JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
                  } else {
                     JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
                  }

                  return;
               } else {
                  if (propName != null && !propName.equalsIgnoreCase("url")) {
                     JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
                     return;
                  }

                  return;
               }
            }

            return;
         }

         t = true;
         if (t) {
            if (e == null) {
               if ("password".equals(propName)) {
                  JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
               } else {
                  JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
               }

               return;
            } else {
               if (propName != null && !propName.equalsIgnoreCase("url")) {
                  JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
                  return;
               }

               return;
            }
         }

         return;
      }

      t = true;
      if (t) {
         if (e == null) {
            if ("password".equals(propName)) {
               JdbcDebug.log(dsName, "Property '" + propName + "' set: ***");
            } else {
               JdbcDebug.log(dsName, "Property '" + propName + "' set: " + value);
            }
         } else if (propName != null && !propName.equalsIgnoreCase("url")) {
            JdbcDebug.log(dsName, "Cannot set property '" + propName + "': " + value);
         }
      }

   }

   public static boolean isXADataSource(String driver) {
      return isXADataSource(driver, (ClassLoader)null);
   }

   public static boolean isXADataSource(String driver, ClassLoader classLoader) {
      boolean isXA = false;

      try {
         isXA = XADataSource.class.isAssignableFrom(loadDriverClass(driver, classLoader));
      } catch (ClassNotFoundException var4) {
      } catch (NullPointerException var5) {
      }

      return isXA;
   }

   public static Object loadDriver(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      return loadDriver(className, (ClassLoader)null);
   }

   public static Object loadDriver(String className, ClassLoader classLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      Object obj = null;

      try {
         if (classLoader != null) {
            obj = Class.forName(className, true, classLoader).newInstance();
         } else {
            obj = Class.forName(className, true, Thread.currentThread().getContextClassLoader()).newInstance();
         }
      } catch (ClassNotFoundException var4) {
         obj = Class.forName(className).newInstance();
         if (obj == null) {
            throw var4;
         }
      }

      return obj;
   }

   public static Class loadDriverClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
      Class obj = null;

      try {
         if (classLoader != null) {
            obj = Class.forName(className, true, classLoader);
         } else {
            obj = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
         }
      } catch (ClassNotFoundException var4) {
         obj = Class.forName(className);
         if (obj == null) {
            throw var4;
         }
      }

      return obj;
   }

   public static boolean isConnectionPoolDataSource(String driver, ClassLoader classLoader) {
      boolean isPooled = false;

      try {
         isPooled = ConnectionPoolDataSource.class.isAssignableFrom(loadDriverClass(driver, classLoader));
      } catch (ClassNotFoundException var4) {
      }

      return isPooled;
   }

   public static Properties removeClearTextPassword(Properties p) {
      if (p == null) {
         return null;
      } else {
         Properties newP = new Properties();
         String pwdKey = "password";
         if (!p.containsKey(pwdKey)) {
            return p;
         } else {
            Object key;
            Object value;
            for(Iterator var3 = p.keySet().iterator(); var3.hasNext(); newP.put(key, value)) {
               key = var3.next();
               value = p.get(key);
               if (pwdKey.equals(key)) {
                  value = "***";
               }
            }

            return newP;
         }
      }
   }

   public static void onsPing(String host, int port, String walletFile, char[] WalletPw) throws Exception {
      Object helper = null;
      Method method = null;
      Class c = Class.forName("oracle.ons.ONSHelper");
      Constructor con = c.getConstructor();
      helper = con.newInstance((Object[])null);
      method = c.getMethod("onsPing", String.class, Integer.TYPE, String.class, char[].class);
      Object[] arglist = new Object[]{host, port, walletFile, WalletPw};

      try {
         method.invoke(helper, arglist);
      } catch (InvocationTargetException var11) {
         Throwable t = var11.getCause();
         if (t != null && Exception.class.isAssignableFrom(t.getClass())) {
            throw (Exception)t;
         } else {
            throw new Exception("Error", var11);
         }
      }
   }

   public static String testConnection(final String driver, final String url, final String username, final String password, final Properties properties) throws Exception {
      try {
         return (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return DataSourceUtil.testConnection0(driver, url, username, password, properties);
            }
         });
      } catch (PrivilegedActionException var6) {
         throw var6.getException();
      }
   }

   private static String testConnection0(String driver, String url, String username, String password, Properties properties) throws ClassNotFoundException, SQLException {
      Connection conn = null;
      XAConnection xaConn = null;
      if (properties == null) {
         properties = new Properties();
      } else {
         properties = (Properties)properties.clone();
      }

      if (url != null) {
         url = url.trim();
      }

      if (url != null && url.length() != 0) {
         Properties csfprops = null;

         try {
            csfprops = DataSourceConnectionPoolConfig.getCsfProps(url);
         } catch (Exception var29) {
            throw new SQLException("Failed to get csf properties", var29);
         }

         if (csfprops != null) {
            username = csfprops.getProperty("user");
            password = csfprops.getProperty("password");
            url = csfprops.getProperty("url");
         }

         String drvPropUrl = (String)properties.get("url");
         if (drvPropUrl != null && !drvPropUrl.equals("")) {
            if (!drvPropUrl.equals(url)) {
               if (csfprops == null) {
                  throw new SQLException("URL specified in connection pool properties '" + url + "' is different from that specified in driver properties '" + drvPropUrl + "'.");
               }

               properties.put("url", url);
            }
         } else {
            properties.put("url", url);
         }

         boolean hasPool = url.toUpperCase().matches("(.*\\(SERVER=POOLED\\).*)|(.*:POOLED)");
         boolean isOracle = url.startsWith("jdbc:oracle:");
         boolean hasConnectionClass = properties.get("oracle.jdbc.DRCPConnectionClass") != null;
         if (isOracle && hasPool != hasConnectionClass) {
            throw new SQLException("oracle.jdbc.DRCPConnectionClass must be specified if and only if SERVER=POOLED");
         } else {
            if (driver != null) {
               driver = driver.trim();
            }

            if (driver != null && driver.length() != 0) {
               if (password != null) {
                  password = password.trim();
                  if (password.length() > 0) {
                     properties.put("password", password);
                  }
               }

               if (username != null && username.length() > 0) {
                  properties.put("user", username);
                  if (!url.startsWith("jdbc:sqlserver:")) {
                     properties.put("username", username);
                  }
               }

               JDBCUtil.checkSdp(url, properties);
               Object driverInstance = null;

               try {
                  driverInstance = loadDriver(driver);
               } catch (Exception var28) {
                  throw new ClassNotFoundException("Cannot load driver: " + driver);
               }

               String var14;
               try {
                  if (driverInstance instanceof Driver) {
                     conn = ((Driver)driverInstance).connect(url, properties);
                  } else if (driverInstance instanceof XADataSource) {
                     if (properties.get("dataSourceName") == null) {
                        properties.put("dataSourceName", "XAConnectionTest");
                     }

                     if (properties.get("server") != null && properties.get("serverName") != null && !properties.get("server").equals(properties.get("serverName"))) {
                        throw new SQLException("server '" + properties.get("server") + "' and serverName '" + properties.get("serverName") + "' properties must have the same value");
                     }

                     if (properties.get("serverName") == null && properties.get("server") != null) {
                        properties.put("serverName", properties.get("server"));
                     }

                     XADataSource xaDS = (XADataSource)driverInstance;
                     initProps((String)null, xaDS, properties);
                     xaConn = xaDS.getXAConnection();
                     if (xaConn == null) {
                        throw new SQLException("Could not establish a XA connection");
                     }

                     conn = xaConn.getConnection();
                  } else {
                     if (!(driverInstance instanceof DataSource)) {
                        throw new SQLException(driver + " is not supported because it doesn't implement java.sql.Driver, javax.sql.DataSource, or javax.sql.XADataSource");
                     }

                     if (properties.get("dataSourceName") == null) {
                        properties.put("dataSourceName", "ConnectionTest");
                     }

                     if (properties.get("server") != null && properties.get("serverName") != null && !properties.get("server").equals(properties.get("serverName"))) {
                        throw new SQLException("server '" + properties.get("server") + "' and serverName '" + properties.get("serverName") + "' properties must have the same value");
                     }

                     if (properties.get("serverName") == null && properties.get("server") != null) {
                        properties.put("serverName", properties.get("server"));
                     }

                     DataSource DS = (DataSource)driverInstance;
                     initProps((String)null, DS, properties);
                     conn = DS.getConnection();
                  }

                  if (conn == null) {
                     throw new SQLException("Can not make a database connection to the given URL " + url);
                  }

                  try {
                     DatabaseMetaData metaData = conn.getMetaData();
                     var14 = metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion();
                  } catch (SQLException var30) {
                     if (var30.getMessage().indexOf("Metadata accessor information") != -1) {
                        var14 = "Sybase connection created but JDBC metadata stored procedures not installed in DBMS";
                        return var14;
                     }

                     throw var30;
                  }
               } catch (SQLException var31) {
                  throw var31;
               } catch (Throwable var32) {
                  throw new SQLException("Could not establish a connection because of " + var32);
               } finally {
                  try {
                     if (conn != null) {
                        conn.close();
                     }

                     if (xaConn != null) {
                        xaConn.close();
                     }
                  } catch (Exception var27) {
                  }

               }

               return var14;
            } else {
               throw new SQLException("JDBC Driver not specified");
            }
         }
      } else {
         throw new SQLException("JDBC URL not specified");
      }
   }

   public static String testConnection(String driver, String url, String username, byte[] encPassword, Properties properties) throws Exception {
      if (encPassword != null) {
         if (es == null) {
            es = SerializedSystemIni.getExistingEncryptionService();
            if (es == null) {
               throw new Exception("Security system error - service");
            }
         }

         if (ces == null) {
            ces = new ClearOrEncryptedService(es);
            if (ces == null) {
               throw new Exception("Security system error - service");
            }
         }

         encPassword = ces.decryptBytes(encPassword);
         if (encPassword == null) {
            throw new Exception("Security system error - error descrypting password");
         }
      }

      String password = null;
      if (encPassword != null) {
         password = new String(encPassword);
      }

      return testConnection(driver, url, username, password, properties);
   }
}

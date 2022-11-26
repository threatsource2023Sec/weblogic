package weblogic.security.service.internal;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.NamedSQLConnectionNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.jdbc.common.internal.DataSourceUtil;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

class DataSourceManager {
   private LoggerSpi logger;
   private String dataSourceName = null;
   private String dataSourceJNDIName = null;
   private DataSource dataSource = null;
   private JDBCDriverParamsBean[] driverBeans = null;

   private final void logDebug(String msg) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(msg);
      }

   }

   private final void logDebug(String msg, Exception e) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(msg, e);
      }

   }

   private DataSourceManager() {
   }

   DataSourceManager(LoggerSpi logger, String theName, AuthenticatedSubject kernelId) throws NamedSQLConnectionNotFoundException {
      this.logger = logger;
      this.initialize(theName, kernelId);
   }

   synchronized void updateDataSource(String theName, AuthenticatedSubject kernelId) throws NamedSQLConnectionNotFoundException {
      this.initialize(theName, kernelId);
   }

   private synchronized void initialize(String theName, AuthenticatedSubject kernelId) throws NamedSQLConnectionNotFoundException {
      this.logDebug("DataSourceManager:initialize(" + theName + ")");
      if (theName == null) {
         this.logDebug("DataSourceManager null datasource name");
         this.logger.error(SecurityLogger.getNullDataSourceName());
         throw new NamedSQLConnectionNotFoundException(SecurityLogger.getNullDataSourceName());
      } else if (this.dataSourceName != null && this.dataSource != null && this.dataSourceJNDIName != null && theName.equalsIgnoreCase(this.dataSourceName)) {
         this.logDebug("No need to reinitialize the DataSourceManager");
      } else {
         this.dataSourceName = theName;
         this.dataSource = null;
         this.dataSourceJNDIName = null;
         JDBCDataSourceBean dataSourceBean = null;
         DomainMBean factory = ManagementService.getRuntimeAccess(kernelId).getDomain();
         JDBCSystemResourceMBean[] jdbcResources = factory.getJDBCSystemResources();
         JDBCDataSourceBean poolBean;
         int lcv;
         if (jdbcResources != null) {
            for(int lcv = 0; lcv < jdbcResources.length; ++lcv) {
               JDBCSystemResourceMBean mbean = jdbcResources[lcv];
               poolBean = mbean.getJDBCResource();
               if (poolBean != null) {
                  lcv = JDBCUtil.getLegacyType(poolBean);
                  if (poolBean.getName().equals(theName) && (lcv == 0 || lcv == 3 || lcv == 4)) {
                     dataSourceBean = poolBean;
                     break;
                  }
               }
            }
         }

         if (dataSourceBean == null) {
            this.logDebug("DataSourceManager unknown/invalid datasource name: " + theName);
            this.logger.error(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
            throw new NamedSQLConnectionNotFoundException(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
         } else {
            String[] jndiNames = dataSourceBean.getJDBCDataSourceParams().getJNDINames();
            if (jndiNames != null && jndiNames.length != 0) {
               this.dataSourceJNDIName = jndiNames[0];
            } else {
               this.dataSourceJNDIName = null;
            }

            String poolName = JDBCUtil.getInternalProperty(dataSourceBean, "LegacyPoolName");
            if (poolName == null) {
               String dataSourceList = dataSourceBean.getJDBCDataSourceParams().getDataSourceList();
               if (dataSourceList == null) {
                  this.driverBeans = new JDBCDriverParamsBean[1];
                  this.driverBeans[0] = dataSourceBean.getJDBCDriverParams();
               } else {
                  this.driverBeans = this.getDriverBeans(jdbcResources, dataSourceList, 0);
               }
            } else {
               this.logDebug("Looking up Legacy pool config, pool name " + poolName);
               poolBean = null;
               if (jdbcResources != null) {
                  for(lcv = 0; lcv < jdbcResources.length; ++lcv) {
                     JDBCSystemResourceMBean mbean = jdbcResources[lcv];
                     JDBCDataSourceBean dsBean = mbean.getJDBCResource();
                     if (dsBean != null) {
                        int legacyType = JDBCUtil.getLegacyType(dsBean);
                        if (dsBean.getName().equals(poolName) && (legacyType == 1 || legacyType == 2)) {
                           poolBean = dsBean;
                           break;
                        }
                     }
                  }
               }

               if (poolBean == null) {
                  this.logDebug("DataSourceManager unknown/invalid pool name");
                  this.logger.error(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
                  throw new NamedSQLConnectionNotFoundException(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
               }

               String dataSourceList = poolBean.getJDBCDataSourceParams().getDataSourceList();
               if (dataSourceList == null) {
                  this.driverBeans = new JDBCDriverParamsBean[1];
                  this.driverBeans[0] = poolBean.getJDBCDriverParams();
               } else {
                  this.driverBeans = this.getDriverBeans(jdbcResources, dataSourceList, 1);
               }
            }

            if (this.driverBeans == null) {
               this.logDebug("DataSourceManager unknown/invalid configuration");
               this.logger.error(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
               throw new NamedSQLConnectionNotFoundException(SecurityLogger.getUnableToLocateDataSourceConfig(theName));
            } else {
               this.debugDataSourceInfo();
            }
         }
      }
   }

   private JDBCDriverParamsBean[] getDriverBeans(JDBCSystemResourceMBean[] mbeans, String poolList, int reqdLegacyType) {
      StringTokenizer st = new StringTokenizer(poolList, ",");
      JDBCDriverParamsBean[] driverBeans = new JDBCDriverParamsBean[st.countTokens()];
      int found = 0;

      while(true) {
         while(st.hasMoreTokens()) {
            String poolName = st.nextToken();

            for(int lcv = 0; lcv < mbeans.length; ++lcv) {
               JDBCDataSourceBean currBean = mbeans[lcv].getJDBCResource();
               if (currBean != null && JDBCUtil.getLegacyType(currBean) == reqdLegacyType && poolName.equals(currBean.getName())) {
                  driverBeans[found++] = currBean.getJDBCDriverParams();
                  break;
               }
            }
         }

         if (found == 0) {
            return null;
         }

         return driverBeans;
      }
   }

   synchronized void debugDataSourceInfo() {
      if (this.logger.isDebugEnabled()) {
         this.logDebug("Datasource info for " + this.dataSourceName);
         this.logDebug("    Available = " + (this.dataSource == null ? "false" : "true"));
         if (this.driverBeans != null) {
            this.logDebug("    Number of Pools found: " + this.driverBeans.length);

            for(int i = 0; i < this.driverBeans.length; ++i) {
               if (this.driverBeans[i] == null) {
                  this.logDebug("    driverBeans[" + i + "] is null");
               } else {
                  this.logDebug("    driverBeans[" + i + "]:");
                  this.logDebug("        DriverName = " + this.driverBeans[i].getDriverName());
                  this.logDebug("        URL = " + this.driverBeans[i].getUrl());
                  this.logDebug("        Properties = " + this.driverBeans[i].getProperties());
               }
            }
         } else {
            this.logDebug("    No driverBeans found");
         }

      }
   }

   private synchronized void setDisabled() {
      this.dataSource = null;
   }

   private synchronized boolean isAvailable() {
      if (this.dataSource != null) {
         this.logDebug("DataSourceManager.isAvailable: Datasource found, is available");
         return true;
      } else {
         this.logDebug("DataSourceManager.isAvailable: Datasource not found, update availability");
         this.updateAvailability();
         return this.dataSource != null;
      }
   }

   private synchronized void updateAvailability() {
      if (this.dataSource == null) {
         Context initCtx = null;

         try {
            this.logDebug("Looking up " + this.dataSourceJNDIName);
            initCtx = new InitialContext();
            this.dataSource = (DataSource)initCtx.lookup(this.dataSourceJNDIName);
         } catch (NamingException var3) {
            this.logDebug("Lookup for datasource failed: " + this.dataSourceJNDIName, var3);
            this.dataSource = null;
         }

      }
   }

   synchronized Connection getConnection() throws SQLException {
      this.logDebug("DataSourceManager.getConnection()");
      Connection connection = null;
      if (this.isAvailable()) {
         this.logDebug("DataSourceManager.getConnection: datasource available get from datasource");

         try {
            connection = this.dataSource.getConnection();
         } catch (Exception var3) {
            this.setDisabled();
            this.logDebug("datasource is disabled, trying direct connection", var3);
            return this.getDirectConnection();
         }

         this.logDebug("DataSourceManager.getConnection: datasource connection success");
         return connection;
      } else {
         this.logDebug("DataSourceManager.getConnection: datasource not available get direct connection");
         return this.getDirectConnection();
      }
   }

   private synchronized Connection getDirectConnection() throws SQLException {
      this.logDebug("DataSourceManager.getDirectConnection()");
      Connection connection = null;
      if (this.driverBeans == null) {
         this.logDebug("No driverBeans found, can't do direct connection");
         return null;
      } else {
         for(int i = 0; i < this.driverBeans.length; ++i) {
            try {
               boolean isXADriver = false;
               String driverName = this.driverBeans[i].getDriverName();
               Class driverClass = null;

               try {
                  driverClass = Class.forName(driverName);
                  isXADriver = XADataSource.class.isAssignableFrom(driverClass);
               } catch (ClassNotFoundException var9) {
                  this.logDebug("Unable to load driver " + driverName, var9);
                  SecurityLogger.logDatasourceConnectionError(driverName, var9);
                  continue;
               }

               String password = this.driverBeans[i].getPassword();
               String url = this.driverBeans[i].getUrl();
               Properties properties = JDBCUtil.getProperties(this.driverBeans[i].getProperties().getProperties());
               if (properties != null) {
                  properties.setProperty("url", url);
                  this.logDebug("DataSourceManager.getDirectConnection: properties to getConnection: " + properties);
                  properties.setProperty("password", password);
               } else {
                  this.logDebug("DataSourceManager.getDirectConnection: Null properties to getConnection");
               }

               if (isXADriver) {
                  this.logDebug("DataSourceManager.getDirectConnection: Get connection for XA driver");
                  connection = this.getDirectConnectionXADriver(driverName, driverClass, properties);
               } else {
                  this.logDebug("DataSourceManager.getDirectConnection: Get connection for Non-XA driver");
                  connection = DriverManager.getConnection(url, properties);
               }

               if (connection != null) {
                  break;
               }
            } catch (SQLException var10) {
               this.logger.debug("DataSourceManager.getDirectConnection: FAILURE ", var10);
               SecurityLogger.logDatasourceConnectionError(this.driverBeans[i].getUrl(), var10);
            }
         }

         this.logDebug("DataSourceManager.getDirectConnection: returns connection " + connection);
         return connection;
      }
   }

   private synchronized Connection getDirectConnectionXADriver(String driverName, Class driverClass, Properties properties) {
      if (driverClass == null) {
         this.logDebug("DataSourceManager.getDirectConnectionXADriver: No driver class found");
         return null;
      } else {
         try {
            Constructor driverClassConstructor = driverClass.getConstructor();
            Object driverInstance = driverClassConstructor.newInstance();
            if (driverInstance instanceof XADataSource) {
               this.logDebug("DataSourceManager.getDirectConnectionXADriver: Is an XADataSource driver, getting connection");
               DataSourceUtil.initProps(driverName, driverInstance, properties);
               XADataSource xaDataSource = (XADataSource)driverInstance;
               return xaDataSource.getXAConnection().getConnection();
            }

            this.logDebug("DataSourceManager.getDirectConnectionXADriver: Is not an XADataSource driver");
         } catch (NoSuchMethodException var7) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: No default constructor found for XA driver ", var7);
         } catch (InstantiationException var8) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: Problem constructing XA Driver ", var8);
         } catch (IllegalAccessException var9) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: Problem constructing XA Driver ", var9);
         } catch (IllegalArgumentException var10) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: Problem constructing XA Driver ", var10);
         } catch (InvocationTargetException var11) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: Problem constructing XA Driver ", var11);
         } catch (SecurityException var12) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: SecurityException accessing construtor ", var12);
         } catch (SQLException var13) {
            this.logger.debug("DataSourceManager.getDirectConnectionXADriver: SQLException getting connection ", var13);
            SecurityLogger.logDatasourceConnectionError(driverName, var13);
         }

         return null;
      }
   }
}

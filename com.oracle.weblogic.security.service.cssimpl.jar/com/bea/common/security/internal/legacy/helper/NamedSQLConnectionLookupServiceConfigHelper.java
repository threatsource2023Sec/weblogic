package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.NamedSQLConnectionLookupServiceImpl;
import com.bea.common.security.servicecfg.NamedSQLConnectionLookupServiceConfig;
import com.bea.common.security.servicecfg.NamedSQLConnectionPoolConfig;
import java.util.Properties;
import weblogic.management.security.RealmMBean;

class NamedSQLConnectionLookupServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return NamedSQLConnectionLookupServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static NamedSQLConnectionPoolConfig getNamedSQLConnectionPoolConfig(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword) {
      return new NamedSQLConnectionPoolConfigImpl(poolName, jdbcDriverClassName, connectionPoolCapacity, connectionPoolTimeout, jdbcConnectionURL, jdbcConnectionProperties, databaseUserLogin, databaseUserPassword);
   }

   static NamedSQLConnectionPoolConfig getNamedSQLConnectionPoolConfig(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, boolean automaticFailoverEnabled, int primaryRetryInterval, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword, String backupJDBCConnectionURL, Properties backupJDBCConnectionProperties, String backupDatabaseUserLogin, String backupDatabaseUserPassword) {
      return new NamedSQLConnectionPoolConfigImpl(poolName, jdbcDriverClassName, connectionPoolCapacity, connectionPoolTimeout, automaticFailoverEnabled, primaryRetryInterval, jdbcConnectionURL, jdbcConnectionProperties, databaseUserLogin, databaseUserPassword, backupJDBCConnectionURL, backupJDBCConnectionProperties, backupDatabaseUserLogin, backupDatabaseUserPassword);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, NamedSQLConnectionPoolConfig[] namedSQLConnectionPoolConfigs) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, NamedSQLConnectionLookupServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(namedSQLConnectionPoolConfigs));
   }

   static class NamedSQLConnectionPoolConfigImpl implements NamedSQLConnectionPoolConfig {
      private String poolName;
      private String jdbcDriverClassName;
      private int connectionPoolCapacity;
      private int connectionPoolTimeout;
      private boolean automaticFailoverEnabled;
      private int primaryRetryInterval;
      private String jdbcConnectionURL;
      private Properties jdbcConnectionProperties;
      private String databaseUserLogin;
      private String databaseUserPassword;
      private String backupJDBCConnectionURL;
      private Properties backupJDBCConnectionProperties;
      private String backupDatabaseUserLogin;
      private String backupDatabaseUserPassword;

      private NamedSQLConnectionPoolConfigImpl(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword) {
         this.poolName = poolName;
         this.jdbcDriverClassName = jdbcDriverClassName;
         this.connectionPoolCapacity = connectionPoolCapacity;
         this.connectionPoolTimeout = connectionPoolTimeout;
         this.automaticFailoverEnabled = false;
         this.jdbcConnectionURL = jdbcConnectionURL;
         this.jdbcConnectionProperties = jdbcConnectionProperties;
         this.databaseUserLogin = databaseUserLogin;
         this.databaseUserPassword = databaseUserPassword;
      }

      private NamedSQLConnectionPoolConfigImpl(String poolName, String jdbcDriverClassName, int connectionPoolCapacity, int connectionPoolTimeout, boolean automaticFailoverEnabled, int primaryRetryInterval, String jdbcConnectionURL, Properties jdbcConnectionProperties, String databaseUserLogin, String databaseUserPassword, String backupJDBCConnectionURL, Properties backupJDBCConnectionProperties, String backupDatabaseUserLogin, String backupDatabaseUserPassword) {
         this.poolName = poolName;
         this.jdbcDriverClassName = jdbcDriverClassName;
         this.connectionPoolCapacity = connectionPoolCapacity;
         this.connectionPoolTimeout = connectionPoolTimeout;
         this.automaticFailoverEnabled = automaticFailoverEnabled;
         this.primaryRetryInterval = primaryRetryInterval;
         this.jdbcConnectionURL = jdbcConnectionURL;
         this.jdbcConnectionProperties = jdbcConnectionProperties;
         this.databaseUserLogin = databaseUserLogin;
         this.databaseUserPassword = databaseUserPassword;
         this.backupJDBCConnectionURL = backupJDBCConnectionURL;
         this.backupJDBCConnectionProperties = backupJDBCConnectionProperties;
         this.backupDatabaseUserLogin = backupDatabaseUserLogin;
         this.backupDatabaseUserPassword = backupDatabaseUserPassword;
      }

      public String getPoolName() {
         return this.poolName;
      }

      public String getJDBCDriverClassName() {
         return this.jdbcDriverClassName;
      }

      public int getConnectionPoolCapacity() {
         return this.connectionPoolCapacity;
      }

      public int getConnectionPoolTimeout() {
         return this.connectionPoolTimeout;
      }

      public boolean isAutomaticFailoverEnabled() {
         return this.automaticFailoverEnabled;
      }

      public int getPrimaryRetryInterval() {
         return this.primaryRetryInterval;
      }

      public String getJDBCConnectionURL() {
         return this.jdbcConnectionURL;
      }

      public Properties getJDBCConnectionProperties() {
         return this.jdbcConnectionProperties;
      }

      public String getDatabaseUserLogin() {
         return this.databaseUserLogin;
      }

      public String getDatabaseUserPassword() {
         return this.databaseUserPassword;
      }

      public String getBackupJDBCConnectionURL() {
         return this.backupJDBCConnectionURL;
      }

      public Properties getBackupJDBCConnectionProperties() {
         return this.backupJDBCConnectionProperties;
      }

      public String getBackupDatabaseUserLogin() {
         return this.backupDatabaseUserLogin;
      }

      public String getBackupDatabaseUserPassword() {
         return this.backupDatabaseUserPassword;
      }

      // $FF: synthetic method
      NamedSQLConnectionPoolConfigImpl(String x0, String x1, int x2, int x3, String x4, Properties x5, String x6, String x7, Object x8) {
         this(x0, x1, x2, x3, x4, x5, x6, x7);
      }

      // $FF: synthetic method
      NamedSQLConnectionPoolConfigImpl(String x0, String x1, int x2, int x3, boolean x4, int x5, String x6, Properties x7, String x8, String x9, String x10, Properties x11, String x12, String x13, Object x14) {
         this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13);
      }
   }

   private static class ConfigImpl implements NamedSQLConnectionLookupServiceConfig {
      private NamedSQLConnectionPoolConfig[] namedSQLConnectionPoolConfigs;

      public ConfigImpl(NamedSQLConnectionPoolConfig[] namedSQLConnectionPoolConfigs) {
         this.namedSQLConnectionPoolConfigs = namedSQLConnectionPoolConfigs;
      }

      public NamedSQLConnectionPoolConfig[] getNamedSQLConnectionPoolConfigs() {
         return this.namedSQLConnectionPoolConfigs;
      }
   }
}

package com.bea.common.security.servicecfg;

import java.util.Properties;

public interface NamedSQLConnectionPoolConfig {
   String getPoolName();

   String getJDBCDriverClassName();

   int getConnectionPoolCapacity();

   int getConnectionPoolTimeout();

   boolean isAutomaticFailoverEnabled();

   int getPrimaryRetryInterval();

   String getJDBCConnectionURL();

   Properties getJDBCConnectionProperties();

   String getDatabaseUserLogin();

   String getDatabaseUserPassword();

   String getBackupJDBCConnectionURL();

   Properties getBackupJDBCConnectionProperties();

   String getBackupDatabaseUserLogin();

   String getBackupDatabaseUserPassword();
}

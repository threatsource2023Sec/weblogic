package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.internal.utils.database.ASIDBPool;
import com.bea.common.security.internal.utils.database.ASIDBPoolConnection;
import com.bea.common.security.internal.utils.database.ASIFailoverDBPools;
import com.bea.common.security.internal.utils.database.NamedSQLConnection;
import com.bea.common.security.internal.utils.database.NamedSQLConnectionImpl;
import com.bea.common.security.service.NamedSQLConnectionLookupService;
import com.bea.common.security.service.NamedSQLConnectionNotFoundException;
import com.bea.common.security.servicecfg.NamedSQLConnectionLookupServiceConfig;
import com.bea.common.security.servicecfg.NamedSQLConnectionPoolConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class NamedSQLConnectionLookupServiceImpl implements ServiceLifecycleSpi, NamedSQLConnectionLookupService {
   private LoggerSpi logger;
   private HashMap pools = new HashMap();

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.NamedSQLConnectionLookupService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof NamedSQLConnectionLookupServiceConfig) {
         NamedSQLConnectionLookupServiceConfig myconfig = (NamedSQLConnectionLookupServiceConfig)config;
         NamedSQLConnectionPoolConfig[] poolConfigs = myconfig.getNamedSQLConnectionPoolConfigs();
         if (poolConfigs != null && poolConfigs.length != 0) {
            for(int i = 0; i < poolConfigs.length; ++i) {
               this.pools.put(poolConfigs[i].getPoolName(), this.setupPools(this.logger, poolConfigs[i]));
            }
         } else if (debug) {
            this.logger.debug(ServiceLogger.getConfigurationMissingRequiredInfo(method, "NamedSQLConnectionLookupServiceConfig", "NamedSQLConnectionPoolConfigs"));
         }

         if (debug) {
            this.logger.debug(method + " done");
         }

         return Delegator.getProxy((Class)NamedSQLConnectionLookupService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "NamedSQLConnectionLookupServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

      Iterator itr = this.pools.values().iterator();

      while(itr.hasNext()) {
         ((ASIFailoverDBPools)itr.next()).shutdown();
      }

      this.pools.clear();
   }

   public Connection getConnection(String connectionName) throws SQLException, NamedSQLConnectionNotFoundException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      ASIFailoverDBPools thePool = (ASIFailoverDBPools)this.pools.get(connectionName);
      if (thePool != null) {
         ASIDBPoolConnection asiConnection = thePool.checkoutConnection();
         if (asiConnection == null) {
            if (debug) {
               this.logger.debug(method + " checkoutConnection returned null");
            }

            return null;
         } else {
            if (debug) {
               this.logger.debug(method + " checkoutConnection returning wrappered connection");
            }

            return NamedSQLConnectionImpl.createNamedSQLConnectionImpl(connectionName, asiConnection);
         }
      } else {
         if (debug) {
            this.logger.debug(method + " didn't find a connection");
         }

         throw new NamedSQLConnectionNotFoundException(ServiceLogger.getCouldNotGetConnectionForName(connectionName));
      }
   }

   public void releaseConnection(Connection connection) throws SQLException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".releaseConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (connection == null) {
         if (debug) {
            this.logger.debug(method + " null connection supplied");
         }

      } else if (!(connection instanceof NamedSQLConnection)) {
         if (debug) {
            this.logger.debug(method + " connection was not a NamedSQLConnection, can't find the name of the pool to return it to");
         }

      } else {
         NamedSQLConnection connectionImpl = (NamedSQLConnection)connection;
         ASIFailoverDBPools thePool = (ASIFailoverDBPools)this.pools.get(connectionImpl.getName());
         if (thePool != null) {
            thePool.checkinConnection(connectionImpl.getASIConnection());
            if (debug) {
               this.logger.debug(method + " connection checked back into " + connectionImpl.getName());
            }

         } else {
            if (debug) {
               this.logger.debug(method + " didn't find the pool named: " + connectionImpl.getName());
            }

            throw new NamedSQLConnectionNotFoundException(ServiceLogger.getCouldNotGetConnectionForName(connectionImpl.getName()));
         }
      }
   }

   private ASIFailoverDBPools setupPools(LoggerSpi logger, NamedSQLConnectionPoolConfig poolConfig) throws ServiceInitializationException {
      boolean debug = logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".setupPools" : null;
      if (debug) {
         logger.debug(method);
      }

      try {
         ASIDBPool backupPool = null;
         Properties primaryProperties = poolConfig.getJDBCConnectionProperties();
         if (poolConfig.getDatabaseUserLogin() != null) {
            primaryProperties.put("user", poolConfig.getDatabaseUserLogin());
         }

         if (poolConfig.getDatabaseUserPassword() != null) {
            primaryProperties.put("password", poolConfig.getDatabaseUserPassword());
         }

         Class.forName(poolConfig.getJDBCDriverClassName()).newInstance();
         ASIDBPool primaryPool = new ASIDBPool(logger, poolConfig.getJDBCDriverClassName(), poolConfig.getJDBCConnectionURL(), primaryProperties, 1, poolConfig.getConnectionPoolCapacity(), (long)poolConfig.getConnectionPoolTimeout());
         if (poolConfig.isAutomaticFailoverEnabled()) {
            if (debug) {
               logger.debug(method + "automatic failover is enabled, setup backup pool");
            }

            Properties backupProperties = poolConfig.getBackupJDBCConnectionProperties();
            backupProperties.put("user", poolConfig.getBackupDatabaseUserLogin());
            backupProperties.put("password", poolConfig.getBackupDatabaseUserPassword());
            backupPool = new ASIDBPool(logger, poolConfig.getJDBCDriverClassName(), poolConfig.getBackupJDBCConnectionURL(), backupProperties, 0, poolConfig.getConnectionPoolCapacity(), (long)poolConfig.getConnectionPoolTimeout());
         } else if (debug) {
            logger.debug(method + "automatic failover is not enabled");
         }

         ASIDBPool[] backupPools = null;
         if (backupPool != null) {
            backupPools = new ASIDBPool[]{backupPool};
         }

         return new ASIFailoverDBPools(logger, primaryPool, backupPools, (long)poolConfig.getPrimaryRetryInterval(), poolConfig.isAutomaticFailoverEnabled());
      } catch (ClassNotFoundException var9) {
         throw new ServiceInitializationException(var9);
      } catch (InstantiationException var10) {
         throw new ServiceInitializationException(var10);
      } catch (IllegalAccessException var11) {
         throw new ServiceInitializationException(var11);
      } catch (RuntimeException var12) {
         throw new ServiceInitializationException(var12);
      }
   }
}

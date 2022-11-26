package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.NamedSQLConnectionLookupService;
import com.bea.common.security.service.NamedSQLConnectionNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class NamedSQLConnectionLookupServiceImpl implements ServiceLifecycleSpi, NamedSQLConnectionLookupService {
   private LoggerSpi logger;
   private HashMap dataSourceManagers = new HashMap();
   private AuthenticatedSubject kernelId;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.NamedSQLConnectionLookupService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof NamedSQLConnectionLookupServiceConfig) {
         NamedSQLConnectionLookupServiceConfig myConfig = (NamedSQLConnectionLookupServiceConfig)config;
         this.kernelId = myConfig.getKernelId();
         if (this.kernelId == null) {
            throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("NamedSQLConnectionLookupServiceConfig.getKernelId"));
         } else {
            if (debug) {
               this.logger.debug(method + " done");
            }

            return Delegator.getInstance((Class)NamedSQLConnectionLookupService.class, this);
         }
      } else {
         throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("NamedSQLConnectionLookupServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   public Connection getConnection(String connectionName) throws SQLException, NamedSQLConnectionNotFoundException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (connectionName != null) {
         DataSourceManager manager = (DataSourceManager)this.dataSourceManagers.get(connectionName);
         if (manager == null) {
            manager = new DataSourceManager(this.logger, connectionName, this.kernelId);
            this.dataSourceManagers.put(connectionName, manager);
         }

         if (debug) {
            this.logger.debug(method + " getting connection from DataSourceManager");
         }

         return manager.getConnection();
      } else {
         if (debug) {
            this.logger.debug(method + " didn't find a connection");
         }

         throw new NamedSQLConnectionNotFoundException("TBDI18N: Connection was not found for " + connectionName);
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

      } else {
         connection.close();
      }
   }
}

package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.NamedSQLConnectionLookupService;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.AuditorService;
import weblogic.security.spi.JDBCConnectionService;
import weblogic.security.spi.JDBCConnectionServiceException;
import weblogic.security.spi.SecurityServicesJDBC;

public class ExtendedSecurityServicesImpl implements ExtendedSecurityServices, SecurityServicesJDBC {
   private Services services = null;
   private AuditorService legacyAuditorService = null;
   private AuditService cssAuditService = null;
   private IdentityService identityService = null;
   private LoggerService loggerService = null;
   private LegacyConfigInfoSpi legacyConfigInfo = null;
   private JDBCConnectionService jdbcConnService = null;

   public ExtendedSecurityServicesImpl(LoggerService loggerService, Services services, LegacyConfigInfoSpi legacyConfigInfo, String auditServiceName, String identityServiceName) throws ServiceInitializationException {
      if (services == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullObject("Services"));
      } else {
         this.loggerService = loggerService;
         this.services = services;
         this.legacyConfigInfo = legacyConfigInfo;
         if (auditServiceName != null && auditServiceName.length() > 0) {
            this.cssAuditService = (AuditService)services.getService(auditServiceName);
            if (this.cssAuditService == null) {
               throw new IllegalArgumentException(ServiceLogger.getNullObject("AuditService"));
            }

            if (this.cssAuditService.isAuditEnabled()) {
               this.legacyAuditorService = new LegacyAuditorServiceAdapter(this.cssAuditService);
            }
         }

         this.identityService = (IdentityService)services.getService(identityServiceName);
      }
   }

   public Services getServices() {
      return this.services;
   }

   public LoggerSpi getLogger(String name) {
      return this.loggerService.getLogger(name);
   }

   public AuditService getAuditService() {
      return this.cssAuditService;
   }

   public IdentityService getIdentityService() {
      return this.identityService;
   }

   public LegacyConfigInfoSpi getLegacyConfig() {
      return this.legacyConfigInfo;
   }

   public AuditorService getAuditorService() {
      return this.legacyAuditorService;
   }

   public JDBCConnectionService getJDBCConnectionService() throws JDBCConnectionServiceException {
      if (this.jdbcConnService == null) {
         this.jdbcConnService = new JDBCConnectionServiceImpl(this.getNamedSQLConnectionLookupService());
      }

      return this.jdbcConnService;
   }

   private NamedSQLConnectionLookupService getNamedSQLConnectionLookupService() throws JDBCConnectionServiceException {
      String namedSQLConnectionLookupName = null;
      NamedSQLConnectionLookupService namedSQLService = null;
      namedSQLConnectionLookupName = this.legacyConfigInfo.getNamedSQLConnectionLookupServiceName();
      if (namedSQLConnectionLookupName == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullObject("NamedSQLConnectionLookupService"));
      } else {
         try {
            namedSQLService = (NamedSQLConnectionLookupService)this.services.getService(namedSQLConnectionLookupName);
            if (namedSQLService == null) {
               throw new ServiceInitializationException("The service engine failed to retrieve the NamedSQLConnectionLookupService");
            } else {
               return namedSQLService;
            }
         } catch (ServiceInitializationException var4) {
            throw new JDBCConnectionServiceException(ServiceLogger.getNamedJDBCServiceInitFailed(), var4);
         }
      }
   }

   private static class LegacyAuditorServiceAdapter implements AuditorService {
      private AuditService auditService = null;

      public LegacyAuditorServiceAdapter(AuditService auditService) {
         if (auditService == null) {
            throw new IllegalArgumentException(ServiceLogger.getNullObject("AuditService"));
         } else {
            this.auditService = auditService;
         }
      }

      public void providerAuditWriteEvent(AuditEvent event) {
         this.auditService.writeEvent(event);
      }
   }
}

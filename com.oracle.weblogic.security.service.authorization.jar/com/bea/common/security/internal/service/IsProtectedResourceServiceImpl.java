package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IsProtectedResourceService;
import com.bea.common.security.servicecfg.IsProtectedResourceServiceConfig;
import com.bea.common.security.spi.AccessDecisionProvider;
import javax.security.auth.Subject;
import weblogic.security.spi.AccessDecision;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class IsProtectedResourceServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private AccessDecision[] accessDecisions;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IsProtectedResourceService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof IsProtectedResourceServiceConfig) {
         IsProtectedResourceServiceConfig myconfig = (IsProtectedResourceServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (this.auditService == null) {
            throw new ServiceConfigurationException(ServiceLogger.getServiceNotFound(auditServiceName, method));
         } else {
            if (debug) {
               this.logger.debug(method + " got AuditService " + dependentServices.getServiceLoggingName(auditServiceName));
            }

            String[] names = myconfig.getAccessDecisionProviderNames();
            if (names == null) {
               throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "AccessDecisionProviderNames"));
            } else {
               this.accessDecisions = new AccessDecision[names.length];

               for(int i = 0; i < names.length; ++i) {
                  AccessDecisionProvider provider = (AccessDecisionProvider)dependentServices.getService(names[i]);
                  if (debug) {
                     this.logger.debug(method + " got AccessDecisionProvider " + dependentServices.getServiceLoggingName(names[i]));
                  }

                  this.accessDecisions[i] = provider.getAccessDecision();
               }

               return new ServiceImpl();
            }
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "IsProtectedResourceServiceConfig"));
      }
   }

   public void shutdown() {
   }

   private void writeAuditEvent(AuditSeverity severity, Identity identity, Resource resource, Exception ex) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditResourceProtectedEventImpl(severity, identity, resource, ex));
      }

   }

   private final class ServiceImpl implements IsProtectedResourceService {
      private ServiceImpl() {
      }

      public boolean isProtectedResource(Identity identity, Resource resource) {
         boolean debug = IsProtectedResourceServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".isProtectedResource" : null;
         if (debug) {
            IsProtectedResourceServiceImpl.this.logger.debug(method + " Identity=" + identity);
         }

         if (debug) {
            IsProtectedResourceServiceImpl.this.logger.debug(method + " Resource=" + resource);
         }

         Subject subject = identity.getSubject();

         for(int i = 0; i < IsProtectedResourceServiceImpl.this.accessDecisions.length; ++i) {
            try {
               if (IsProtectedResourceServiceImpl.this.accessDecisions[i].isProtectedResource(subject, resource)) {
                  if (debug) {
                     IsProtectedResourceServiceImpl.this.logger.debug(method + " AccessDecision said this is a protected resource, returning true");
                  }

                  IsProtectedResourceServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, identity, resource, (Exception)null);
                  return true;
               }

               if (debug) {
                  IsProtectedResourceServiceImpl.this.logger.debug(method + " AccessDecision said this is not a protected resource, continuing");
               }
            } catch (Exception var8) {
               if (debug) {
                  IsProtectedResourceServiceImpl.this.logger.debug(method + " received an exception, continuing to indicate resource protected. Exception: " + var8);
               }

               IsProtectedResourceServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, identity, resource, var8);
               return true;
            }
         }

         if (debug) {
            IsProtectedResourceServiceImpl.this.logger.debug(method + " returning false since no AccessDecision said this is a protected resource");
         }

         IsProtectedResourceServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, identity, resource, (Exception)null);
         return false;
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}

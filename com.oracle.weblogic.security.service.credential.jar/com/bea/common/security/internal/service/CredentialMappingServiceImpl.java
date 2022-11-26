package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.servicecfg.CredentialMappingServiceConfig;
import java.security.Key;
import java.util.ArrayList;
import javax.security.auth.Subject;
import weblogic.security.KeyPairCredential;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.CredentialMapperV2;
import weblogic.security.spi.Resource;

public class CredentialMappingServiceImpl implements ServiceLifecycleSpi, CredentialMappingService {
   private LoggerSpi logger;
   private AuditService auditService;
   private CredentialMapperV2[] credentialMappers;
   private PasswordCredentialHelper pcHelper = new PasswordCredentialHelper();

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CredentialMappingService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof CredentialMappingServiceConfig) {
         CredentialMappingServiceConfig myconfig = (CredentialMappingServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String[] names = myconfig.getCredentialMapperNames();
         if (names != null && names.length >= 1) {
            this.credentialMappers = new CredentialMapperV2[names.length];

            for(int i = 0; i < names.length; ++i) {
               CredentialMapperV2 provider = (CredentialMapperV2)dependentServices.getService(names[i]);
               if (debug) {
                  this.logger.debug(method + " got CredentialMapperV2 " + names[i]);
               }

               this.credentialMappers[i] = provider;
            }

            return Delegator.getProxy(CredentialMappingService.class, this);
         } else {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "CredentialMapperV2Names"));
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "CredentialMappingServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Object[] getCredentials(Identity requestor, Identity initiator, Resource resource, ContextHandler handler, String credType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      Subject rs = null;
      if (requestor != null) {
         rs = requestor.getSubject();
      }

      Subject is = null;
      if (initiator != null) {
         is = initiator.getSubject();
      }

      String[] theCredentialTypes = new String[]{credType};
      ArrayList creds = new ArrayList();

      for(int i = 0; i < this.credentialMappers.length; ++i) {
         try {
            Object[] c = this.credentialMappers[i].getCredentials(rs, is, resource, handler, credType);

            for(int j = 0; c != null && j < c.length; ++j) {
               creds.add(c[j]);
            }
         } catch (RuntimeException var15) {
            if (debug) {
               this.logger.debug(method + " failure.", var15);
            }

            if (this.auditService.isAuditEnabled()) {
               this.writeAuditEvent(requestor, initiator, (String)null, resource, handler, theCredentialTypes, (Object[])null, var15);
            }
         }
      }

      Object[] credentials = creds.toArray();
      if (this.auditService.isAuditEnabled()) {
         this.writeAuditEvent(requestor, initiator, (String)null, resource, handler, theCredentialTypes, credentials, (Exception)null);
      }

      return credentials;
   }

   public Object[] getCredentials(Identity requestor, String initiator, Resource resource, ContextHandler handler, String credType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      Subject rs = null;
      if (requestor != null) {
         rs = requestor.getSubject();
      }

      String[] theCredentialTypes = new String[]{credType};
      ArrayList creds = new ArrayList();

      for(int i = 0; i < this.credentialMappers.length; ++i) {
         try {
            Object c = this.credentialMappers[i].getCredential(rs, initiator, resource, handler, credType);
            if (c != null) {
               creds.add(c);
            }
         } catch (RuntimeException var13) {
            if (debug) {
               this.logger.debug(method + " failure.", var13);
            }

            if (this.auditService.isAuditEnabled()) {
               this.writeAuditEvent(requestor, (Identity)null, initiator, resource, handler, theCredentialTypes, (Object[])null, var13);
            }
         }
      }

      Object[] credentials = creds.toArray();
      if (this.auditService.isAuditEnabled()) {
         this.writeAuditEvent(requestor, (Identity)null, initiator, resource, handler, theCredentialTypes, credentials, (Exception)null);
      }

      return credentials;
   }

   private void writeAuditEvent(Identity requestor, Identity initiatorIdentity, String initiator, Resource resource, ContextHandler contextHandler, String[] credTypes, Object[] credentials, Exception exception) {
      AuditSeverity severity = AuditSeverity.INFORMATION;
      Object[] creds = null;
      if (credentials != null && credentials.length > 0) {
         severity = AuditSeverity.SUCCESS;
         creds = new Object[credentials.length];

         for(int i = 0; i < creds.length; ++i) {
            if (credentials[i] instanceof KeyPairCredential) {
               creds[i] = new KeyPairCredential((Key)null, ((KeyPairCredential)credentials[i]).getCertificateChain());
            } else {
               creds[i] = this.pcHelper.mapToNewPasswordCredential(credentials[i]);
               if (creds[i] == null) {
                  creds[i] = credentials[i];
               }
            }
         }
      }

      if (exception != null) {
         severity = AuditSeverity.FAILURE;
      }

      this.auditService.writeEvent(new AuditCredentialMappingEventImpl(severity, requestor, initiatorIdentity, initiator, resource, contextHandler, credTypes, creds, exception));
   }
}

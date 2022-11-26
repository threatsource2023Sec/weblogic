package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ManageableServiceLifecycleSpi;
import com.bea.common.engine.SecurityServiceRuntimeException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.SecurityProviderClassLoaderService;
import com.bea.common.security.legacy.SecurityProviderWrapper;
import weblogic.management.security.ProviderMBean;
import weblogic.security.spi.SecurityProvider;

public class SecurityProviderImpl implements ManageableServiceLifecycleSpi {
   private LoggerService loggerService = null;
   private LoggerSpi logger;
   private ProviderMBean mbean = null;
   private SecurityProvider securityProvider;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.loggerService = (LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME);
      this.logger = this.loggerService.getLogger("com.bea.common.security.service.AuditService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      SecurityProviderConfig myconfig = (SecurityProviderConfig)config;
      SecurityProviderClassLoaderService classLoaderService = (SecurityProviderClassLoaderService)dependentServices.getService(SecurityProviderClassLoaderService.SERVICE_NAME);
      ClassLoader classLoader = classLoaderService.getClassLoader(myconfig.getProviderMBean());
      String provClassName = myconfig.getProviderClassName();
      if (provClassName != null && provClassName.length() != 0) {
         try {
            Object o = Class.forName(provClassName, true, classLoader).newInstance();
            this.securityProvider = o instanceof SecurityProviderWrapper ? ((SecurityProviderWrapper)o).getProvider(classLoader) : (SecurityProvider)o;
         } catch (Exception var10) {
            throw new SecurityServiceRuntimeException(ServiceLogger.getFailedToInstantiate(provClassName), var10);
         }

         ExtendedSecurityServices securityServices = new ExtendedSecurityServicesImpl(this.loggerService, dependentServices, myconfig.getLegacyConfigInfo(), myconfig.getAuditServiceName(), myconfig.getIdentityServiceName());
         this.securityProvider.initialize(myconfig.getProviderMBean(), securityServices);
         this.mbean = myconfig.getProviderMBean();
         return this.securityProvider;
      } else {
         throw new SecurityServiceRuntimeException(ServiceLogger.getNoSecurityProviderClassName(myconfig.getProviderMBean().getName()));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      this.securityProvider.shutdown();
   }

   public Object getManagementObject() {
      return this.mbean;
   }
}

package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASLoginService;
import com.bea.common.security.servicecfg.JAASLoginServiceConfig;
import com.bea.common.security.utils.CSSPlatformProxy;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;

public class JAASLoginServiceImpl implements ServiceLifecycleSpi, JAASLoginService {
   private AuditService auditService;
   private IdentityService identityService;
   private boolean isOnWLS = false;
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASLoginService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof JAASLoginServiceConfig) {
         JAASLoginServiceConfig myconfig = (JAASLoginServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         name = myconfig.getIdentityServiceName();
         this.identityService = (IdentityService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got IdentityService " + name);
         }

         if (CSSPlatformProxy.getInstance().isOnWLS()) {
            this.isOnWLS = true;
         }

         return Delegator.getProxy(JAASLoginService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "JAASLoginServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Identity login(final String configurationName, final CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".login" : null;
      ClassLoader cl = this.getClass().getClassLoader();
      if (debug) {
         this.logger.debug(method + " ClassLoader=" + cl);
      }

      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      if (debug) {
         this.logger.debug(method + " ThreadContext ClassLoader Original=" + ccl);
      }

      if (!this.isOnWLS) {
         Thread.currentThread().setContextClassLoader(cl);
      }

      Identity var11;
      try {
         LoginContext context;
         try {
            context = (LoginContext)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws LoginException {
                  return new LoginContext(configurationName, callbackHandler);
               }
            });
            if (debug) {
               this.logger.debug(method + " created LoginContext");
            }
         } catch (PrivilegedActionException var15) {
            throw (LoginException)var15.getException();
         }

         if (debug) {
            this.logger.debug(method + " ThreadContext ClassLoader Current=" + Thread.currentThread().getContextClassLoader());
         }

         context.login();
         if (debug) {
            this.logger.debug(method + " logged in");
         }

         Subject subject = context.getSubject();
         if (debug) {
            this.logger.debug(method + " subject=" + subject);
         }

         Identity identity = this.identityService.getIdentityFromSubject(subject);
         if (debug) {
            this.logger.debug(method + " identity=" + identity);
         }

         var11 = identity;
      } finally {
         if (!this.isOnWLS) {
            Thread.currentThread().setContextClassLoader(ccl);
         }

      }

      return var11;
   }
}

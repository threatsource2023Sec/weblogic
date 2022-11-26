package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.JAASLoginService;
import com.bea.common.security.utils.UsernameUtils;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import weblogic.security.SecurityLogger;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.LoginServerNotAvailableException;

public class WLSJAASLoginServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private JAASLoginService baseService;
   private UserLockoutRuntimeService userLockoutRuntimeService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".init";
         if (debug) {
            this.logger.debug(method);
         }

         if (config == null) {
            throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied(method));
         } else if (!(config instanceof WLSJAASLoginServiceConfig)) {
            throw new ServiceConfigurationException(SecurityLogger.getNotInstanceof("PolicyConsumerServiceConfig"));
         } else {
            WLSJAASLoginServiceConfig myconfig = (WLSJAASLoginServiceConfig)config;
            String name = myconfig.getAuditServiceName();
            this.auditService = (AuditService)dependentServices.getService(name);
            if (this.auditService == null) {
               throw new ServiceConfigurationException(SecurityLogger.getServiceNotFound("AuditService", name));
            } else {
               if (debug) {
                  this.logger.debug(method + " got AuditService " + name);
               }

               name = myconfig.getJAASLoginServiceName();
               this.baseService = (JAASLoginService)dependentServices.getService(name);
               if (this.baseService == null) {
                  throw new ServiceConfigurationException(SecurityLogger.getServiceNotFound("JAASLoginService", name));
               } else {
                  if (debug) {
                     this.logger.debug(method + " got JAASLoginService " + name);
                  }

                  name = myconfig.getUserLockoutRuntimeServiceName();
                  this.userLockoutRuntimeService = (UserLockoutRuntimeService)dependentServices.getService(name);
                  if (this.userLockoutRuntimeService == null) {
                     throw new ServiceConfigurationException(SecurityLogger.getServiceNotFound("UserLockoutRuntimeService", name));
                  } else {
                     if (debug) {
                        this.logger.debug(method + " got UserLockoutRuntimeService " + name);
                     }

                     return new ServiceImpl();
                  }
               }
            }
         }
      }
   }

   public void shutdown() {
   }

   private final class CallbackHandlerWrapper implements CallbackHandler {
      private CallbackHandler delegate;
      private boolean gotUserNameFromDelegate;
      private String userName;
      private String identityDomain;

      private CallbackHandlerWrapper(CallbackHandler callbackHandler) {
         this.delegate = callbackHandler;
      }

      private String getUserName() {
         return this.userName;
      }

      private String getIdentityDomain() {
         return this.identityDomain;
      }

      public synchronized void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         this.delegate.handle(callbacks);

         for(int i = 0; !this.gotUserNameFromDelegate && callbacks != null && i < callbacks.length; ++i) {
            if (callbacks[i] instanceof NameCallback) {
               NameCallback nameCallback = (NameCallback)callbacks[i];
               this.userName = nameCallback.getName();
               this.gotUserNameFromDelegate = true;
            } else if (callbacks[i] instanceof IdentityDomainUserCallback) {
               IdentityDomainUserCallback userCallback = (IdentityDomainUserCallback)callbacks[i];
               IdentityDomainNames iddNames = userCallback.getUser();
               this.userName = iddNames.getName();
               this.identityDomain = iddNames.getIdentityDomain();
               this.gotUserNameFromDelegate = true;
            }
         }

      }

      // $FF: synthetic method
      CallbackHandlerWrapper(CallbackHandler x1, Object x2) {
         this(x1);
      }
   }

   private final class ServiceImpl implements JAASLoginService {
      private ServiceImpl() {
      }

      public Identity login(String configurationName, CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
         boolean debug = WLSJAASLoginServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".authenticate" : null;
         if (debug) {
            WLSJAASLoginServiceImpl.this.logger.debug(method);
         }

         CallbackHandlerWrapper wrapper = WLSJAASLoginServiceImpl.this.new CallbackHandlerWrapper(callbackHandler);

         Identity identity;
         String identityDomain;
         try {
            identity = WLSJAASLoginServiceImpl.this.baseService.login(configurationName, wrapper, contextHandler);
            if (debug) {
               WLSJAASLoginServiceImpl.this.logger.debug(method + " authenticate succeeded for user " + wrapper.getUserName() + ", Identity=" + identity);
            }
         } catch (LoginServerNotAvailableException var11) {
            throw var11;
         } catch (LoginException var12) {
            if (debug) {
               WLSJAASLoginServiceImpl.this.logger.debug(method + " authenticate failed for user " + wrapper.getUserName());
            }

            identityDomain = wrapper.getUserName();
            String identityDomainx = wrapper.getIdentityDomain();
            if (identityDomain != null && !WLSJAASLoginServiceImpl.this.userLockoutRuntimeService.isLocked(identityDomain, identityDomainx)) {
               WLSJAASLoginServiceImpl.this.userLockoutRuntimeService.logFailure(wrapper.getUserName(), wrapper.getIdentityDomain());
            }

            throw var12;
         }

         String userName = wrapper.getUserName();
         identityDomain = wrapper.getIdentityDomain();
         if (userName != null) {
            if (WLSJAASLoginServiceImpl.this.userLockoutRuntimeService.isLocked(userName, identityDomain)) {
               if (debug) {
                  WLSJAASLoginServiceImpl.this.logger.debug(method + " login succeeded but " + UsernameUtils.formatUserName(userName, identityDomain) + " was previouly locked out.  Throwing LoginException");
               }

               throw new LoginException();
            }

            if (debug) {
               WLSJAASLoginServiceImpl.this.logger.debug(method + " login succeeded and " + UsernameUtils.formatUserName(userName, identityDomain) + " was not previously locked out");
            }

            WLSJAASLoginServiceImpl.this.userLockoutRuntimeService.logSuccess(wrapper.getUserName(), wrapper.getIdentityDomain());
         }

         return identity;
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}

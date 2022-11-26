package com.bea.common.security.internal.service;

import com.bea.common.engine.SecurityServiceRuntimeException;
import com.bea.common.logger.spi.LoggerSpi;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public final class LoginModuleWrapper implements LoginModule {
   private LoginModule delegate;
   private LoggerSpi logger;

   public static AppConfigurationEntry wrap(AppConfigurationEntry delegate, ClassLoader classLoader, LoggerSpi logger) {
      boolean debug = logger.isDebugEnabled();
      String method = debug ? LoginModuleWrapper.class.getName() + ".wrap" : null;
      if (debug) {
         logger.debug(method + " LoginModuleClassName=" + delegate.getLoginModuleName());
      }

      if (debug) {
         logger.debug(method + " ClassLoader=" + classLoader.toString());
      }

      if (debug) {
         logger.debug(method + " ControlFlag=" + delegate.getControlFlag());
      }

      HashMap wrappedOptions = new HashMap();
      wrappedOptions.put("delegateLoginModuleName", delegate.getLoginModuleName());
      wrappedOptions.put("delegateClassLoader", classLoader);
      wrappedOptions.put("delegateOptions", delegate.getOptions());
      wrappedOptions.put("logger", logger);
      return new AppConfigurationEntry(LoginModuleWrapper.class.getName(), delegate.getControlFlag(), wrappedOptions);
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map wrappedOptions) {
      String delegateLoginModuleName = (String)wrappedOptions.get("delegateLoginModuleName");
      ClassLoader delegateClassLoader = (ClassLoader)wrappedOptions.get("delegateClassLoader");
      this.logger = (LoggerSpi)wrappedOptions.get("logger");
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".initialize" : null;
      if (debug) {
         this.logger.debug(method + " LoginModuleClassName=" + delegateLoginModuleName);
      }

      if (debug) {
         this.logger.debug(method + " ClassLoader=" + delegateClassLoader.toString());
      }

      try {
         this.delegate = (LoginModule)Class.forName(delegateLoginModuleName, true, delegateClassLoader).newInstance();
         if (debug) {
            this.logger.debug(method + " created delegate login module");
         }
      } catch (ClassNotFoundException var10) {
         if (debug) {
            this.logger.debug(method + " exception: ", var10);
         }

         throw new SecurityServiceRuntimeException(var10);
      } catch (IllegalAccessException var11) {
         if (debug) {
            this.logger.debug(method + " exception: ", var11);
         }

         throw new SecurityServiceRuntimeException(var11);
      } catch (InstantiationException var12) {
         if (debug) {
            this.logger.debug(method + " exception: ", var12);
         }

         throw new SecurityServiceRuntimeException(var12);
      }

      AccessController.doPrivileged(new DelegateInitializer(subject, callbackHandler, sharedState, (Map)wrappedOptions.get("delegateOptions")));
      if (debug) {
         this.logger.debug(method + " delegated");
      }

   }

   public boolean login() throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.delegate.getClass().getName() + ".login" : null;
      if (debug) {
         this.logger.debug(method);
      }

      boolean rtn;
      try {
         rtn = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws LoginException {
               return new Boolean(LoginModuleWrapper.this.delegate.login());
            }
         });
      } catch (PrivilegedActionException var5) {
         if (debug) {
            this.logger.debug(method + " exception: ", var5);
         }

         throw (LoginException)var5.getException();
      }

      if (debug) {
         this.logger.debug(method + " delegated, returning " + rtn);
      }

      return rtn;
   }

   public boolean commit() throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.delegate.getClass().getName() + ".commit" : null;
      if (debug) {
         this.logger.debug(method);
      }

      boolean rtn;
      try {
         rtn = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws LoginException {
               return new Boolean(LoginModuleWrapper.this.delegate.commit());
            }
         });
      } catch (PrivilegedActionException var5) {
         if (debug) {
            this.logger.debug(method + " exception: ", var5);
         }

         throw (LoginException)var5.getException();
      }

      if (debug) {
         this.logger.debug(method + " delegated, returning " + rtn);
      }

      return rtn;
   }

   public boolean abort() throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.delegate.getClass().getName() + ".abort" : null;
      if (debug) {
         this.logger.debug(method);
      }

      boolean rtn;
      try {
         rtn = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws LoginException {
               return new Boolean(LoginModuleWrapper.this.delegate.abort());
            }
         });
      } catch (PrivilegedActionException var5) {
         if (debug) {
            this.logger.debug(method + " exception: ", var5);
         }

         throw (LoginException)var5.getException();
      }

      if (debug) {
         this.logger.debug(method + " delegated, returning " + rtn);
      }

      return rtn;
   }

   public boolean logout() throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.delegate.getClass().getName() + ".logout" : null;
      if (debug) {
         this.logger.debug(method);
      }

      boolean rtn;
      try {
         rtn = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws LoginException {
               return new Boolean(LoginModuleWrapper.this.delegate.logout());
            }
         });
      } catch (PrivilegedActionException var5) {
         if (debug) {
            this.logger.debug(method + " exception: ", var5);
         }

         throw (LoginException)var5.getException();
      }

      if (debug) {
         this.logger.debug(method + " delegated, returning " + rtn);
      }

      return rtn;
   }

   private final class DelegateInitializer implements PrivilegedAction {
      private Subject subject;
      private CallbackHandler callbackHandler;
      private Map sharedState;
      private Map options;

      private DelegateInitializer(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
         this.subject = subject;
         this.callbackHandler = callbackHandler;
         this.sharedState = sharedState;
         this.options = options;
      }

      public Object run() {
         LoginModuleWrapper.this.delegate.initialize(this.subject, this.callbackHandler, this.sharedState, this.options);
         return null;
      }

      // $FF: synthetic method
      DelegateInitializer(Subject x1, CallbackHandler x2, Map x3, Map x4, Object x5) {
         this(x1, x2, x3, x4);
      }
   }
}

package com.bea.common.security.internal.service;

import com.bea.common.logger.spi.LoggerSpi;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.ContextHandlerCallback;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.service.ContextHandler;

final class CallbackHandlerWrapper implements CallbackHandler {
   private CallbackHandler delegate;
   private ContextHandler contextHandler;
   private boolean haveUserName;
   private boolean haveIDDUser;
   private String userName;
   private IdentityDomainNames user;
   private LoggerSpi logger;

   public CallbackHandlerWrapper(CallbackHandler callbackHandler, ContextHandler contextHandler, LoggerSpi logger) {
      this.delegate = callbackHandler;
      this.contextHandler = contextHandler;
      this.logger = logger;
      boolean debug = logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".constructor" : null;
      if (debug) {
         logger.debug(method);
      }

   }

   synchronized String getUserName() {
      return this.userName;
   }

   synchronized IdentityDomainNames getUser() {
      return this.user;
   }

   public synchronized void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".handle" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (callbacks != null && callbacks.length >= 1) {
         int nameCallbackIndex = -1;
         int iddUserCallbackIndex = -1;

         for(int i = 0; callbacks != null && i < callbacks.length; ++i) {
            if (callbacks[i] instanceof ContextHandlerCallback) {
               if (debug) {
                  this.logger.debug(method + " callbacks[" + i + "] locally handling ContextCallbackHandler");
               }

               ((ContextHandlerCallback)callbacks[i]).setContextHandler(this.contextHandler);
            } else {
               if (debug) {
                  this.logger.debug(method + " callbacks[" + i + "] will be delegated");
               }

               if (!this.haveIDDUser && iddUserCallbackIndex == -1 && callbacks[i] instanceof IdentityDomainUserCallback) {
                  if (debug) {
                     this.logger.debug(method + " callbacks[" + i + "] will use IdentityDomainUserCallback to retrieve user");
                  }

                  iddUserCallbackIndex = i;
               }

               if (!this.haveUserName && nameCallbackIndex == -1 && callbacks[i] instanceof NameCallback) {
                  if (debug) {
                     this.logger.debug(method + " callbacks[" + i + "] will use NameCallback to retrieve name");
                  }

                  nameCallbackIndex = i;
               }
            }
         }

         if (debug) {
            this.logger.debug(method + " will delegate all callbacks");
         }

         this.delegate.handle(callbacks);
         if (debug) {
            this.logger.debug(method + " delegated callbacks");
         }

         if (iddUserCallbackIndex != -1) {
            this.user = ((IdentityDomainUserCallback)callbacks[iddUserCallbackIndex]).getUser();
            this.userName = this.user.getName();
            this.haveIDDUser = true;
            this.haveUserName = true;
            if (debug) {
               this.logger.debug(method + " got user from callbacks[" + iddUserCallbackIndex + "], User=" + this.user);
            }
         } else if (nameCallbackIndex != -1) {
            this.userName = ((NameCallback)callbacks[nameCallbackIndex]).getName();
            this.user = new IdentityDomainNames(this.userName, (String)null);
            this.haveUserName = true;
            if (debug) {
               this.logger.debug(method + " got username from callbacks[" + nameCallbackIndex + "], UserName=" + this.userName);
            }
         } else if (debug) {
            this.logger.debug(method + " did not get username or IDD user from a callback");
         }

      } else {
         if (debug) {
            this.logger.debug(method + " returning since did not receive any callback handlers");
         }

      }
   }
}

package com.bea.security.providers.utils;

import com.bea.common.security.SecurityLogger;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;

public class IdentityDomainNameCallbackHandler implements CallbackHandler {
   private String userName;
   private IdentityDomainNames user;
   private boolean isIdd = false;

   public IdentityDomainNameCallbackHandler(String userName, String identityDomain) {
      this.userName = userName;
      if (identityDomain != null && !identityDomain.isEmpty()) {
         this.user = new IdentityDomainNames(userName, identityDomain);
         this.isIdd = true;
      }

   }

   public IdentityDomainNameCallbackHandler(IdentityDomainNames user) {
      this.user = user;
      this.isIdd = true;
   }

   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (callbacks[i] instanceof NameCallback && !this.isIdd) {
            NameCallback nc = (NameCallback)callbacks[i];
            nc.setName(this.userName);
         } else {
            if (!(callbacks[i] instanceof IdentityDomainUserCallback) || !this.isIdd) {
               throw new UnsupportedCallbackException(callbacks[i], SecurityLogger.getUnrecognizedIACallback());
            }

            IdentityDomainUserCallback cb = (IdentityDomainUserCallback)callbacks[i];
            cb.setUser(this.user);
         }
      }

   }
}

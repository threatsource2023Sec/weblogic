package com.bea.security.providers.utils;

import com.bea.common.security.SecurityLogger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class NameCallbackHandler implements CallbackHandler {
   private String userName;

   public NameCallbackHandler(String user) {
      this.userName = user;
   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (!(callbacks[i] instanceof NameCallback)) {
            throw new UnsupportedCallbackException(callbacks[i], SecurityLogger.getUnrecognizedIACallback());
         }

         NameCallback nc = (NameCallback)callbacks[i];
         nc.setName(this.userName);
      }

   }
}

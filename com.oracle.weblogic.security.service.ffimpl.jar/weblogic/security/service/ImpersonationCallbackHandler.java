package weblogic.security.service;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.ContextHandlerCallback;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.utils.PartitionUtils;

final class ImpersonationCallbackHandler implements CallbackHandler {
   private IdentityDomainNames user = null;
   private String userName = null;
   private final ContextHandler contextHandler;
   private final String message = "Unrecognized Callback";

   ImpersonationCallbackHandler(String userName, ContextHandler contextHandler) {
      if (userName != null && !userName.isEmpty()) {
         if (IdentityDomainNamesEncoder.isEncodedNames(userName)) {
            this.user = IdentityDomainNamesEncoder.decodeNames(userName);
         } else {
            String identityDomain = PartitionUtils.getCurrentIdentityDomain();
            if (identityDomain != null && !identityDomain.isEmpty()) {
               this.user = new IdentityDomainNames(userName, identityDomain);
            } else {
               this.userName = userName;
            }
         }
      }

      this.contextHandler = contextHandler;
   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (callbacks[i] instanceof NameCallback) {
            NameCallback cb = (NameCallback)callbacks[i];
            if (this.user != null) {
               throw new UnsupportedCallbackException(cb, "Unrecognized Callback: " + cb.getClass());
            }

            cb.setName(this.userName);
         } else if (callbacks[i] instanceof IdentityDomainUserCallback) {
            IdentityDomainUserCallback iddcb = (IdentityDomainUserCallback)callbacks[i];
            if (this.user == null) {
               throw new UnsupportedCallbackException(iddcb, "Unrecognized Callback: " + iddcb.getClass());
            }

            iddcb.setUser(this.user);
         } else if (callbacks[i] instanceof ContextHandlerCallback) {
            ContextHandlerCallback cb = (ContextHandlerCallback)callbacks[i];
            cb.setContextHandler(this.contextHandler);
         }
      }

   }
}

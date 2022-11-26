package weblogic.security.providers.saml;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.ContextHandlerCallback;
import weblogic.security.auth.callback.GroupCallback;
import weblogic.security.auth.callback.IdentityDomainGroupCallback;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.providers.utils.SAMLIANameCallback;
import weblogic.security.spi.VirtualUserHandler;

public class SAMLIACallbackHandler implements CallbackHandler, VirtualUserHandler {
   private static LoggerSpi LOGGER = null;
   private String name = null;
   private Collection groups = null;
   private boolean allowVirtual = false;
   private IdentityDomainNames iddUser;
   private String identityDomain;
   private boolean isIdd = false;

   private final void logDebug(String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("SAMLIACallbackHandler: " + msg);
      }

   }

   private static void setLogger(LoggerSpi logger) {
      LOGGER = logger;
   }

   public SAMLIACallbackHandler(LoggerSpi logger, boolean allowVirtual, String name, Collection groups, String identityDomain) {
      setLogger(logger);
      this.logDebug("SAMLIACallbackHandler(" + allowVirtual + ", " + name + ", " + groups + ", " + identityDomain + ")");
      this.allowVirtual = allowVirtual;
      this.name = name;
      if (identityDomain != null && !identityDomain.isEmpty()) {
         this.identityDomain = identityDomain;
         this.iddUser = new IdentityDomainNames(name, identityDomain);
         this.isIdd = true;
      }

      if (groups != null) {
         this.groups = this.convertGroups(groups);
      }

   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (callbacks[i] instanceof NameCallback && !this.isIdd) {
            NameCallback nc = (NameCallback)callbacks[i];
            nc.setName(this.name);
            this.logDebug("callback[" + i + "]: NameCallback: setName(" + this.name + ")");
         } else if (callbacks[i] instanceof IdentityDomainUserCallback && this.isIdd) {
            IdentityDomainUserCallback cb = (IdentityDomainUserCallback)callbacks[i];
            cb.setUser(this.iddUser);
            this.logDebug("callback[" + i + "]: IdentityDomainUserCallback: setUser(" + this.iddUser + ")");
         } else if (callbacks[i] instanceof SAMLIANameCallback) {
            SAMLIANameCallback snc = (SAMLIANameCallback)callbacks[i];
            snc.setName(this.name);
            snc.setAllowVirtualUser(this.allowVirtual);
            this.logDebug("callback[" + i + "]: SAMLIANameCallback: setName(" + this.name + "), setAllowVirtualUser(" + this.allowVirtual + ")");
         } else if (callbacks[i] instanceof GroupCallback && !this.isIdd) {
            if (this.groups != null && !this.groups.isEmpty()) {
               GroupCallback gc = (GroupCallback)callbacks[i];
               gc.setValue(this.groups);
               this.logDebug("callback[" + i + "]: GroupCallback: setValue(" + this.groups + ")");
            }
         } else if (callbacks[i] instanceof IdentityDomainGroupCallback && this.isIdd) {
            if (this.groups != null && !this.groups.isEmpty()) {
               IdentityDomainGroupCallback gc = (IdentityDomainGroupCallback)callbacks[i];
               gc.setGroups(this.groups);
               this.logDebug("callback[" + i + "]: IdentityDomainGroupCallback: setValue(" + this.groups + ")");
            }
         } else {
            if (!(callbacks[i] instanceof ContextHandlerCallback)) {
               this.logDebug("callback[" + i + "]: Throwing exception: Unsupported callback: " + callbacks[i].getClass().getName());
               throw new UnsupportedCallbackException(callbacks[i]);
            }

            this.logDebug("callback[" + i + "]: ContextHandlerCallback");
         }
      }

   }

   private Collection convertGroups(Collection groups) {
      if (!this.isIdd) {
         return new ArrayList(groups);
      } else {
         this.logDebug("converting groups to IdentityDomainNames");
         Collection iddGroups = new ArrayList();
         Iterator var3 = groups.iterator();

         while(var3.hasNext()) {
            Object group = var3.next();
            if (group instanceof IdentityDomainNames) {
               iddGroups.addAll(groups);
               break;
            }

            if (group instanceof String) {
               iddGroups.add(new IdentityDomainNames((String)group, this.identityDomain));
            }
         }

         if (iddGroups.isEmpty()) {
            this.logDebug("unable to convert any groups to groups with an identity domain for identity domain: " + this.identityDomain + " and groups: " + groups);
            return null;
         } else {
            return iddGroups;
         }
      }
   }

   public boolean isVirtualUserAllowed() {
      return this.allowVirtual;
   }
}

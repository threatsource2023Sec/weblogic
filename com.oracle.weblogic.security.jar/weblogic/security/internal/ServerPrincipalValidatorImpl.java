package weblogic.security.internal;

import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import weblogic.security.HMAC;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.principal.WLSServerIdentity;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.PrincipalValidator;
import weblogic.utils.LocatorUtilities;

public class ServerPrincipalValidatorImpl implements PrincipalValidator {
   private byte[] secret;

   public boolean validate(Principal principal) throws SecurityException {
      if (!(principal instanceof WLSServerIdentity)) {
         return false;
      } else {
         WLSServerIdentity wlsServerIdentity = (WLSServerIdentity)principal;
         byte[] signature = wlsServerIdentity.getSignature();
         if (signature == null) {
            return false;
         } else {
            byte[] data = wlsServerIdentity.getSignedData();
            byte[] salt = wlsServerIdentity.getSalt();
            return HMAC.verify(signature, data, this.getSecret(), salt);
         }
      }
   }

   public boolean sign(Principal principal) {
      if (!(principal instanceof WLSServerIdentity)) {
         return false;
      } else {
         WLSServerIdentity wlsServerIdentity = (WLSServerIdentity)principal;
         SecurityServiceManager.checkKernelPermission();
         byte[] data = wlsServerIdentity.getSignedData();
         byte[] salt = wlsServerIdentity.getSalt();
         wlsServerIdentity.setSignature(HMAC.digest(data, this.getSecret(), salt));
         return true;
      }
   }

   public Class getPrincipalBaseClass() {
      return WLSServerIdentity.class;
   }

   private byte[] getSecret() {
      if (this.secret == null) {
         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });
         this.secret = runtimeAccess.getDomain().getSecurityConfiguration().getCredential().getBytes();
      }

      return this.secret;
   }
}

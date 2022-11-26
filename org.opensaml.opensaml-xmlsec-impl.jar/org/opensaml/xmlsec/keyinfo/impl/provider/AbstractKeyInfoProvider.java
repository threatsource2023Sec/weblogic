package org.opensaml.xmlsec.keyinfo.impl.provider;

import java.security.Key;
import javax.annotation.Nullable;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoCredentialContext;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoProvider;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;

public abstract class AbstractKeyInfoProvider implements KeyInfoProvider {
   @Nullable
   protected Key extractKeyValue(@Nullable Credential cred) {
      if (cred != null) {
         if (cred.getPublicKey() != null) {
            return cred.getPublicKey();
         }

         if (cred.getSecretKey() != null) {
            return cred.getSecretKey();
         }

         if (cred.getPrivateKey() != null) {
            return cred.getPrivateKey();
         }
      }

      return null;
   }

   @Nullable
   protected KeyInfoCredentialContext buildCredentialContext(@Nullable KeyInfoResolutionContext kiContext) {
      return kiContext != null ? new KeyInfoCredentialContext(kiContext.getKeyInfo()) : null;
   }
}

package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class DecryptionParameters extends WhitelistBlacklistParameters {
   @Nullable
   private KeyInfoCredentialResolver dataKeyInfoCredentialResolver;
   @Nullable
   private KeyInfoCredentialResolver kekKeyInfoCredentialResolver;
   @Nullable
   private EncryptedKeyResolver encryptedKeyResolver;

   @Nullable
   public KeyInfoCredentialResolver getDataKeyInfoCredentialResolver() {
      return this.dataKeyInfoCredentialResolver;
   }

   public void setDataKeyInfoCredentialResolver(@Nullable KeyInfoCredentialResolver resolver) {
      this.dataKeyInfoCredentialResolver = resolver;
   }

   @Nullable
   public KeyInfoCredentialResolver getKEKKeyInfoCredentialResolver() {
      return this.kekKeyInfoCredentialResolver;
   }

   public void setKEKKeyInfoCredentialResolver(@Nullable KeyInfoCredentialResolver resolver) {
      this.kekKeyInfoCredentialResolver = resolver;
   }

   @Nullable
   public EncryptedKeyResolver getEncryptedKeyResolver() {
      return this.encryptedKeyResolver;
   }

   public void setEncryptedKeyResolver(@Nullable EncryptedKeyResolver resolver) {
      this.encryptedKeyResolver = resolver;
   }
}

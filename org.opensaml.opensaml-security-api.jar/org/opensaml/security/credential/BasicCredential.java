package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;

public class BasicCredential extends AbstractCredential implements MutableCredential {
   public BasicCredential(@Nonnull PublicKey publicKey) {
      this.setPublicKey(publicKey);
   }

   public BasicCredential(@Nonnull PublicKey publicKey, @Nullable PrivateKey privateKey) {
      this.setPublicKey(publicKey);
      if (privateKey != null) {
         this.setPrivateKey(privateKey);
      }

   }

   public BasicCredential(@Nonnull SecretKey secretKey) {
      this.setSecretKey(secretKey);
   }

   protected BasicCredential() {
   }

   public Class getCredentialType() {
      return Credential.class;
   }

   public void setEntityId(@Nullable String newEntityId) {
      super.setEntityId(newEntityId);
   }

   public void setUsageType(@Nonnull UsageType newUsageType) {
      super.setUsageType(newUsageType);
   }

   public void setPublicKey(@Nonnull PublicKey newPublicKey) {
      super.setPublicKey(newPublicKey);
   }

   public void setPrivateKey(@Nonnull PrivateKey newPrivateKey) {
      super.setPrivateKey(newPrivateKey);
   }

   public void setSecretKey(@Nonnull SecretKey newSecretKey) {
      super.setSecretKey(newSecretKey);
   }
}

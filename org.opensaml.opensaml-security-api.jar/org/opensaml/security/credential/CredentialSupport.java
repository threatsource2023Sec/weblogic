package org.opensaml.security.credential;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import org.opensaml.security.x509.BasicX509Credential;

public final class CredentialSupport {
   private CredentialSupport() {
   }

   @Nullable
   public static Key extractEncryptionKey(@Nullable Credential credential) {
      if (credential == null) {
         return null;
      } else {
         return (Key)(credential.getPublicKey() != null ? credential.getPublicKey() : credential.getSecretKey());
      }
   }

   @Nullable
   public static Key extractDecryptionKey(@Nullable Credential credential) {
      if (credential == null) {
         return null;
      } else {
         return (Key)(credential.getPrivateKey() != null ? credential.getPrivateKey() : credential.getSecretKey());
      }
   }

   @Nullable
   public static Key extractSigningKey(@Nullable Credential credential) {
      if (credential == null) {
         return null;
      } else {
         return (Key)(credential.getPrivateKey() != null ? credential.getPrivateKey() : credential.getSecretKey());
      }
   }

   @Nullable
   public static Key extractVerificationKey(@Nullable Credential credential) {
      if (credential == null) {
         return null;
      } else {
         return (Key)(credential.getPublicKey() != null ? credential.getPublicKey() : credential.getSecretKey());
      }
   }

   @Nonnull
   public static BasicCredential getSimpleCredential(@Nonnull SecretKey secretKey) {
      return new BasicCredential(secretKey);
   }

   @Nonnull
   public static BasicCredential getSimpleCredential(@Nonnull PublicKey publicKey, @Nullable PrivateKey privateKey) {
      return privateKey != null ? new BasicCredential(publicKey, privateKey) : new BasicCredential(publicKey);
   }

   @Nonnull
   public static BasicX509Credential getSimpleCredential(@Nonnull X509Certificate cert, @Nullable PrivateKey privateKey) {
      return privateKey != null ? new BasicX509Credential(cert, privateKey) : new BasicX509Credential(cert);
   }
}

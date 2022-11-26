package org.opensaml.security.x509;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.BasicCredential;

public class BasicX509Credential extends BasicCredential implements X509Credential {
   private X509Certificate entityCert;
   private Collection entityCertChain;
   private Collection crls;

   public BasicX509Credential(@Nonnull X509Certificate entityCertificate) {
      this.setEntityCertificate(entityCertificate);
   }

   public BasicX509Credential(@Nonnull X509Certificate entityCertificate, @Nonnull PrivateKey privateKey) {
      this.setEntityCertificate(entityCertificate);
      this.setPrivateKey(privateKey);
   }

   @Nonnull
   public Class getCredentialType() {
      return X509Credential.class;
   }

   @Nullable
   public Collection getCRLs() {
      return this.crls;
   }

   public void setCRLs(@Nullable Collection newCRLs) {
      this.crls = newCRLs;
   }

   @Nonnull
   public X509Certificate getEntityCertificate() {
      return this.entityCert;
   }

   public void setEntityCertificate(@Nonnull X509Certificate newEntityCertificate) {
      Constraint.isNotNull(newEntityCertificate, "Credential certificate cannot be null");
      this.entityCert = newEntityCertificate;
   }

   @Nonnull
   public PublicKey getPublicKey() {
      return this.getEntityCertificate().getPublicKey();
   }

   public void setPublicKey(PublicKey newPublicKey) {
      throw new UnsupportedOperationException("Public key may not be set explicitly on an X509 credential");
   }

   @Nonnull
   public Collection getEntityCertificateChain() {
      if (this.entityCertChain == null) {
         LazySet constructedChain = new LazySet();
         constructedChain.add(this.entityCert);
         return constructedChain;
      } else {
         return this.entityCertChain;
      }
   }

   public void setEntityCertificateChain(@Nonnull Collection newCertificateChain) {
      Constraint.isNotNull(newCertificateChain, "Certificate chain collection cannot be null");
      Constraint.isNotEmpty(newCertificateChain, "Certificate chain collection cannot be empty");
      this.entityCertChain = new ArrayList(newCertificateChain);
   }

   @Nullable
   public SecretKey getSecretKey() {
      return null;
   }

   public void setSecretKey(SecretKey newSecretKey) {
      throw new UnsupportedOperationException("An X509Credential may not contain a secret key");
   }
}

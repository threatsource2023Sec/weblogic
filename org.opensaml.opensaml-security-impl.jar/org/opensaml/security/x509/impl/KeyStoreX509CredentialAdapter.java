package org.opensaml.security.x509.impl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreX509CredentialAdapter extends AbstractCredential implements X509Credential {
   private Logger log = LoggerFactory.getLogger(KeyStoreX509CredentialAdapter.class);
   private final KeyStore keyStore;
   private final String credentialAlias;
   private final char[] keyPassword;

   public KeyStoreX509CredentialAdapter(@Nonnull KeyStore store, @Nonnull String alias, @Nullable char[] password) {
      this.keyStore = (KeyStore)Constraint.isNotNull(store, "Keystore cannot be null");
      this.credentialAlias = (String)Constraint.isNotNull(StringSupport.trimOrNull(alias), "Keystore alias cannot be null or empty");
      this.keyPassword = password;
   }

   @Nullable
   public Collection getCRLs() {
      return Collections.EMPTY_LIST;
   }

   @Nonnull
   public X509Certificate getEntityCertificate() {
      try {
         return (X509Certificate)this.keyStore.getCertificate(this.credentialAlias);
      } catch (KeyStoreException var2) {
         this.log.error("Error accessing {} certificates in keystore", this.credentialAlias, var2);
         return null;
      }
   }

   @Nonnull
   public Collection getEntityCertificateChain() {
      List certsCollection = Collections.EMPTY_LIST;

      try {
         Certificate[] certs = this.keyStore.getCertificateChain(this.credentialAlias);
         if (certs != null) {
            certsCollection = new ArrayList(certs.length);
            Certificate[] var3 = certs;
            int var4 = certs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Certificate cert = var3[var5];
               ((List)certsCollection).add((X509Certificate)cert);
            }
         }
      } catch (KeyStoreException var7) {
         this.log.error("Error accessing {} certificates in keystore", this.credentialAlias, var7);
      }

      return (Collection)certsCollection;
   }

   @Nullable
   public PrivateKey getPrivateKey() {
      try {
         return (PrivateKey)this.keyStore.getKey(this.credentialAlias, this.keyPassword);
      } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException var2) {
         this.log.error("Error accessing {} private key in keystore", this.credentialAlias, var2);
         return null;
      }
   }

   @Nullable
   public PublicKey getPublicKey() {
      return this.getEntityCertificate().getPublicKey();
   }

   @Nonnull
   public Class getCredentialType() {
      return X509Credential.class;
   }

   public void setEntityId(@Nullable String newEntityID) {
      super.setEntityId(newEntityID);
   }

   public void setUsageType(@Nonnull UsageType newUsageType) {
      super.setUsageType(newUsageType);
   }
}

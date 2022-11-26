package org.opensaml.security.x509.impl;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.X509KeyManager;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;

public class X509KeyManagerX509CredentialAdapter extends AbstractCredential implements X509Credential {
   private final String credentialAlias;
   private final X509KeyManager keyManager;

   public X509KeyManagerX509CredentialAdapter(@Nonnull X509KeyManager manager, @Nonnull String alias) {
      this.keyManager = (X509KeyManager)Constraint.isNotNull(manager, "Key manager cannot be null");
      this.credentialAlias = (String)Constraint.isNotNull(StringSupport.trimOrNull(alias), "Entity alias cannot be null");
   }

   @Nullable
   public Collection getCRLs() {
      return Collections.EMPTY_LIST;
   }

   @Nonnull
   public X509Certificate getEntityCertificate() {
      X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
      return certs != null && certs.length > 0 ? certs[0] : null;
   }

   @Nonnull
   public Collection getEntityCertificateChain() {
      X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
      return certs != null && certs.length > 0 ? Arrays.asList(certs) : null;
   }

   @Nullable
   public PrivateKey getPrivateKey() {
      return this.keyManager.getPrivateKey(this.credentialAlias);
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

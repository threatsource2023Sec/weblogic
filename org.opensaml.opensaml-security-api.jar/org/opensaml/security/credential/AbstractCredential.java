package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public abstract class AbstractCredential implements Credential {
   private String entityId;
   private UsageType usageType;
   private Collection keyNames = new LazySet();
   private PublicKey publicKey;
   private SecretKey secretKey;
   private PrivateKey privateKey;
   private final CredentialContextSet credentialContextSet = new CredentialContextSet();

   public AbstractCredential() {
      this.setUsageType(UsageType.UNSPECIFIED);
   }

   @Nullable
   public String getEntityId() {
      return this.entityId;
   }

   @Nullable
   public UsageType getUsageType() {
      return this.usageType;
   }

   @Nonnull
   public Collection getKeyNames() {
      return this.keyNames;
   }

   @Nullable
   public PublicKey getPublicKey() {
      return this.publicKey;
   }

   @Nullable
   public SecretKey getSecretKey() {
      return this.secretKey;
   }

   @Nullable
   public PrivateKey getPrivateKey() {
      return this.privateKey;
   }

   @Nonnull
   public CredentialContextSet getCredentialContextSet() {
      return this.credentialContextSet;
   }

   protected void setEntityId(@Nullable String newEntityID) {
      this.entityId = StringSupport.trimOrNull(newEntityID);
   }

   protected void setUsageType(@Nonnull UsageType newUsageType) {
      Constraint.isNotNull(newUsageType, "Credential usage type cannot be null");
      this.usageType = newUsageType;
   }

   protected void setPublicKey(@Nonnull PublicKey newPublicKey) {
      Constraint.isNull(this.getSecretKey(), "A credential with a secret key cannot contain a public key");
      Constraint.isNotNull(newPublicKey, "Credential public key cannot be null");
      this.publicKey = newPublicKey;
   }

   protected void setPrivateKey(@Nonnull PrivateKey newPrivateKey) {
      Constraint.isNull(this.getSecretKey(), "A credential with a secret key cannot contain a private key");
      Constraint.isNotNull(newPrivateKey, "Credential private key cannot be null");
      this.privateKey = newPrivateKey;
   }

   protected void setSecretKey(@Nonnull SecretKey newSecretKey) {
      Constraint.isNull(this.getPublicKey(), "A credential with a public key cannot contain a secret key");
      Constraint.isNull(this.getPrivateKey(), "A credential with a private key cannot contain a secret key");
      Constraint.isNotNull(newSecretKey, "Credential secret key cannot be null");
      this.secretKey = newSecretKey;
   }
}

package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;

public interface Credential {
   @Nullable
   String getEntityId();

   @Nullable
   UsageType getUsageType();

   @Nonnull
   Collection getKeyNames();

   @Nullable
   PublicKey getPublicKey();

   @Nullable
   PrivateKey getPrivateKey();

   @Nullable
   SecretKey getSecretKey();

   @Nullable
   CredentialContextSet getCredentialContextSet();

   @Nonnull
   Class getCredentialType();
}

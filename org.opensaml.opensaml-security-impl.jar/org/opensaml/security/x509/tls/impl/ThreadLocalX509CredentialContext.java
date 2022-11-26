package org.opensaml.security.x509.tls.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.x509.X509Credential;

public final class ThreadLocalX509CredentialContext {
   private static ThreadLocal currentCredential = new ThreadLocal();

   private ThreadLocalX509CredentialContext() {
   }

   public static void loadCurrent(@Nonnull X509Credential credential) {
      Constraint.isNotNull(credential, "X509Credential may not be null");
      currentCredential.set(credential);
   }

   public static void clearCurrent() {
      currentCredential.remove();
   }

   public static boolean haveCurrent() {
      return currentCredential.get() != null;
   }

   @Nullable
   public static X509Credential getCredential() {
      return (X509Credential)currentCredential.get();
   }
}

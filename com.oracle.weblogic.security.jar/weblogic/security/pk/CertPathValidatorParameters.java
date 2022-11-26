package weblogic.security.pk;

import java.security.cert.CertPathParameters;
import java.security.cert.X509Certificate;
import weblogic.security.SecurityLogger;
import weblogic.security.service.ContextHandler;

public final class CertPathValidatorParameters implements CertPathParameters {
   private String realmName;
   private X509Certificate[] trustedCAs;
   private ContextHandler context;

   public CertPathValidatorParameters(String realmName, X509Certificate[] trustedCAs, ContextHandler context) {
      if (realmName != null && realmName.length() >= 1) {
         this.realmName = realmName;
         this.trustedCAs = trustedCAs;
         this.context = context;
      } else {
         throw new IllegalArgumentException(SecurityLogger.getCertPathValidatorParametersIllegalRealm());
      }
   }

   public String getRealmName() {
      return this.realmName;
   }

   public X509Certificate[] getTrustedCAs() {
      return this.trustedCAs;
   }

   public ContextHandler getContext() {
      return this.context;
   }

   public Object clone() {
      throw new UnsupportedOperationException();
   }
}

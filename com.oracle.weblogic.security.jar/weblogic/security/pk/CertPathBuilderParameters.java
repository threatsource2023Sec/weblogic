package weblogic.security.pk;

import java.security.cert.CertPathParameters;
import java.security.cert.X509Certificate;
import weblogic.security.SecurityLogger;
import weblogic.security.service.ContextHandler;

public final class CertPathBuilderParameters implements CertPathParameters {
   private String realmName;
   private CertPathSelector selector;
   private X509Certificate[] trustedCAs;
   private ContextHandler context;

   public CertPathBuilderParameters(String realmName, CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) {
      if (realmName != null && realmName.length() >= 1) {
         if (selector == null) {
            throw new IllegalArgumentException(SecurityLogger.getCertPathBuilderParametersIllegalCertPathSelector());
         } else {
            this.realmName = realmName;
            this.selector = selector;
            this.trustedCAs = trustedCAs;
            this.context = context;
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getCertPathBuilderParametersIllegalRealm());
      }
   }

   public String getRealmName() {
      return this.realmName;
   }

   public CertPathSelector getSelector() {
      return this.selector;
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

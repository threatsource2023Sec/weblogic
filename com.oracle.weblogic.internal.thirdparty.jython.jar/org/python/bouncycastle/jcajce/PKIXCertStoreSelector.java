package org.python.bouncycastle.jcajce;

import java.io.IOException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import org.python.bouncycastle.util.Selector;

public class PKIXCertStoreSelector implements Selector {
   private final CertSelector baseSelector;

   private PKIXCertStoreSelector(CertSelector var1) {
      this.baseSelector = var1;
   }

   public boolean match(Certificate var1) {
      return this.baseSelector.match(var1);
   }

   public Object clone() {
      return new PKIXCertStoreSelector(this.baseSelector);
   }

   public static Collection getCertificates(PKIXCertStoreSelector var0, CertStore var1) throws CertStoreException {
      return var1.getCertificates(new SelectorClone(var0));
   }

   // $FF: synthetic method
   PKIXCertStoreSelector(CertSelector var1, Object var2) {
      this(var1);
   }

   public static class Builder {
      private final CertSelector baseSelector;

      public Builder(CertSelector var1) {
         this.baseSelector = (CertSelector)var1.clone();
      }

      public PKIXCertStoreSelector build() {
         return new PKIXCertStoreSelector(this.baseSelector);
      }
   }

   private static class SelectorClone extends X509CertSelector {
      private final PKIXCertStoreSelector selector;

      SelectorClone(PKIXCertStoreSelector var1) {
         this.selector = var1;
         if (var1.baseSelector instanceof X509CertSelector) {
            X509CertSelector var2 = (X509CertSelector)var1.baseSelector;
            this.setAuthorityKeyIdentifier(var2.getAuthorityKeyIdentifier());
            this.setBasicConstraints(var2.getBasicConstraints());
            this.setCertificate(var2.getCertificate());
            this.setCertificateValid(var2.getCertificateValid());
            this.setKeyUsage(var2.getKeyUsage());
            this.setMatchAllSubjectAltNames(var2.getMatchAllSubjectAltNames());
            this.setPrivateKeyValid(var2.getPrivateKeyValid());
            this.setSerialNumber(var2.getSerialNumber());
            this.setSubjectKeyIdentifier(var2.getSubjectKeyIdentifier());
            this.setSubjectPublicKey(var2.getSubjectPublicKey());

            try {
               this.setExtendedKeyUsage(var2.getExtendedKeyUsage());
               this.setIssuer(var2.getIssuerAsBytes());
               this.setNameConstraints(var2.getNameConstraints());
               this.setPathToNames(var2.getPathToNames());
               this.setPolicy(var2.getPolicy());
               this.setSubject(var2.getSubjectAsBytes());
               this.setSubjectAlternativeNames(var2.getSubjectAlternativeNames());
               this.setSubjectPublicKeyAlgID(var2.getSubjectPublicKeyAlgID());
            } catch (IOException var4) {
               throw new IllegalStateException("base selector invalid: " + var4.getMessage(), var4);
            }
         }

      }

      public boolean match(Certificate var1) {
         return this.selector == null ? var1 != null : this.selector.match(var1);
      }
   }
}

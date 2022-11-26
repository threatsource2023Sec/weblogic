package org.python.bouncycastle.cert.jcajce;

import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

class NamedCertHelper extends CertHelper {
   private final String providerName;

   NamedCertHelper(String var1) {
      this.providerName = var1;
   }

   protected CertificateFactory createCertificateFactory(String var1) throws CertificateException, NoSuchProviderException {
      return CertificateFactory.getInstance(var1, this.providerName);
   }
}

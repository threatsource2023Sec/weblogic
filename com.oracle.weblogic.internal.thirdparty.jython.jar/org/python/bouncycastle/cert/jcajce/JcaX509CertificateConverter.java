package org.python.bouncycastle.cert.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.cert.X509CertificateHolder;

public class JcaX509CertificateConverter {
   private CertHelper helper = new DefaultCertHelper();

   public JcaX509CertificateConverter() {
      this.helper = new DefaultCertHelper();
   }

   public JcaX509CertificateConverter setProvider(Provider var1) {
      this.helper = new ProviderCertHelper(var1);
      return this;
   }

   public JcaX509CertificateConverter setProvider(String var1) {
      this.helper = new NamedCertHelper(var1);
      return this;
   }

   public X509Certificate getCertificate(X509CertificateHolder var1) throws CertificateException {
      try {
         CertificateFactory var2 = this.helper.getCertificateFactory("X.509");
         return (X509Certificate)var2.generateCertificate(new ByteArrayInputStream(var1.getEncoded()));
      } catch (IOException var3) {
         throw new ExCertificateParsingException("exception parsing certificate: " + var3.getMessage(), var3);
      } catch (NoSuchProviderException var4) {
         throw new ExCertificateException("cannot find required provider:" + var4.getMessage(), var4);
      }
   }

   private class ExCertificateException extends CertificateException {
      private Throwable cause;

      public ExCertificateException(String var2, Throwable var3) {
         super(var2);
         this.cause = var3;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }

   private class ExCertificateParsingException extends CertificateParsingException {
      private Throwable cause;

      public ExCertificateParsingException(String var2, Throwable var3) {
         super(var2);
         this.cause = var3;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}

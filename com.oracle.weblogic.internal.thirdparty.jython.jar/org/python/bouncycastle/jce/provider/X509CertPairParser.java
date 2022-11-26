package org.python.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.x509.CertificatePair;
import org.python.bouncycastle.x509.X509CertificatePair;
import org.python.bouncycastle.x509.X509StreamParserSpi;
import org.python.bouncycastle.x509.util.StreamParsingException;

public class X509CertPairParser extends X509StreamParserSpi {
   private InputStream currentStream = null;

   private X509CertificatePair readDERCrossCertificatePair(InputStream var1) throws IOException, CertificateParsingException {
      ASN1InputStream var2 = new ASN1InputStream(var1);
      ASN1Sequence var3 = (ASN1Sequence)var2.readObject();
      CertificatePair var4 = CertificatePair.getInstance(var3);
      return new X509CertificatePair(var4);
   }

   public void engineInit(InputStream var1) {
      this.currentStream = var1;
      if (!this.currentStream.markSupported()) {
         this.currentStream = new BufferedInputStream(this.currentStream);
      }

   }

   public Object engineRead() throws StreamParsingException {
      try {
         this.currentStream.mark(10);
         int var1 = this.currentStream.read();
         if (var1 == -1) {
            return null;
         } else {
            this.currentStream.reset();
            return this.readDERCrossCertificatePair(this.currentStream);
         }
      } catch (Exception var2) {
         throw new StreamParsingException(var2.toString(), var2);
      }
   }

   public Collection engineReadAll() throws StreamParsingException {
      ArrayList var1 = new ArrayList();

      X509CertificatePair var2;
      while((var2 = (X509CertificatePair)this.engineRead()) != null) {
         var1.add(var2);
      }

      return var1;
   }
}

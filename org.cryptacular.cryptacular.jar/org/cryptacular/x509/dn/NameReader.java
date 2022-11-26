package org.cryptacular.x509.dn;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.X500Name;

public class NameReader {
   private final X509Certificate certificate;

   public NameReader(X509Certificate cert) {
      if (cert == null) {
         throw new IllegalArgumentException("Certificate cannot be null.");
      } else {
         this.certificate = cert;
      }
   }

   public RDNSequence readSubject() {
      return readX500Principal(this.certificate.getSubjectX500Principal());
   }

   public RDNSequence readIssuer() {
      return readX500Principal(this.certificate.getIssuerX500Principal());
   }

   public static RDNSequence readX500Principal(X500Principal principal) {
      X500Name name = X500Name.getInstance(principal.getEncoded());
      RDNSequence sequence = new RDNSequence();
      org.bouncycastle.asn1.x500.RDN[] var3 = name.getRDNs();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         org.bouncycastle.asn1.x500.RDN rdn = var3[var5];
         Attributes attributes = new Attributes();
         AttributeTypeAndValue[] var8 = rdn.getTypesAndValues();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            AttributeTypeAndValue tv = var8[var10];
            attributes.add(tv.getType().getId(), tv.getValue().toString());
         }

         sequence.add(new RDN(attributes));
      }

      return sequence;
   }
}

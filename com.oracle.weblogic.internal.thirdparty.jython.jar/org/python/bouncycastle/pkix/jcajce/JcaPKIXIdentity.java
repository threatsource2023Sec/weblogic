package org.python.bouncycastle.pkix.jcajce;

import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.python.bouncycastle.pkix.PKIXIdentity;

public class JcaPKIXIdentity extends PKIXIdentity {
   private final PrivateKey privKey;
   private final X509Certificate[] certs;

   private static PrivateKeyInfo getPrivateKeyInfo(PrivateKey var0) {
      try {
         return PrivateKeyInfo.getInstance(var0.getEncoded());
      } catch (Exception var2) {
         return null;
      }
   }

   private static X509CertificateHolder[] getCertificates(X509Certificate[] var0) {
      X509CertificateHolder[] var1 = new X509CertificateHolder[var0.length];

      try {
         for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = new JcaX509CertificateHolder(var0[var2]);
         }

         return var1;
      } catch (CertificateEncodingException var3) {
         throw new IllegalArgumentException("Unable to process certificates: " + var3.getMessage());
      }
   }

   public JcaPKIXIdentity(PrivateKey var1, X509Certificate[] var2) {
      super(getPrivateKeyInfo(var1), getCertificates(var2));
      this.privKey = var1;
      this.certs = new X509Certificate[var2.length];
      System.arraycopy(var2, 0, this.certs, 0, var2.length);
   }

   public PrivateKey getPrivateKey() {
      return this.privKey;
   }

   public X509Certificate getX509Certificate() {
      return this.certs[0];
   }
}

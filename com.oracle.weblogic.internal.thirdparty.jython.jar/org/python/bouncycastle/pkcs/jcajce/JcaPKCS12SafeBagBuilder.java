package org.python.bouncycastle.pkcs.jcajce;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.pkcs.PKCS12SafeBagBuilder;
import org.python.bouncycastle.pkcs.PKCSIOException;

public class JcaPKCS12SafeBagBuilder extends PKCS12SafeBagBuilder {
   public JcaPKCS12SafeBagBuilder(X509Certificate var1) throws IOException {
      super(convertCert(var1));
   }

   private static Certificate convertCert(X509Certificate var0) throws IOException {
      try {
         return Certificate.getInstance(var0.getEncoded());
      } catch (CertificateEncodingException var2) {
         throw new PKCSIOException("cannot encode certificate: " + var2.getMessage(), var2);
      }
   }

   public JcaPKCS12SafeBagBuilder(PrivateKey var1, OutputEncryptor var2) {
      super(PrivateKeyInfo.getInstance(var1.getEncoded()), var2);
   }

   public JcaPKCS12SafeBagBuilder(PrivateKey var1) {
      super(PrivateKeyInfo.getInstance(var1.getEncoded()));
   }
}

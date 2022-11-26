package org.python.bouncycastle.cert.jcajce;

import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.X500NameStyle;

public class JcaX500NameUtil {
   public static X500Name getIssuer(X509Certificate var0) {
      return X500Name.getInstance(var0.getIssuerX500Principal().getEncoded());
   }

   public static X500Name getSubject(X509Certificate var0) {
      return X500Name.getInstance(var0.getSubjectX500Principal().getEncoded());
   }

   public static X500Name getIssuer(X500NameStyle var0, X509Certificate var1) {
      return X500Name.getInstance(var0, var1.getIssuerX500Principal().getEncoded());
   }

   public static X500Name getSubject(X500NameStyle var0, X509Certificate var1) {
      return X500Name.getInstance(var0, var1.getSubjectX500Principal().getEncoded());
   }
}

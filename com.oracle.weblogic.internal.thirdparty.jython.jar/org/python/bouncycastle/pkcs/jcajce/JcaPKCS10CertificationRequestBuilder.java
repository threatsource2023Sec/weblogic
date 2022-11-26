package org.python.bouncycastle.pkcs.jcajce;

import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;

public class JcaPKCS10CertificationRequestBuilder extends PKCS10CertificationRequestBuilder {
   public JcaPKCS10CertificationRequestBuilder(X500Name var1, PublicKey var2) {
      super(var1, SubjectPublicKeyInfo.getInstance(var2.getEncoded()));
   }

   public JcaPKCS10CertificationRequestBuilder(X500Principal var1, PublicKey var2) {
      super(X500Name.getInstance(var1.getEncoded()), SubjectPublicKeyInfo.getInstance(var2.getEncoded()));
   }
}

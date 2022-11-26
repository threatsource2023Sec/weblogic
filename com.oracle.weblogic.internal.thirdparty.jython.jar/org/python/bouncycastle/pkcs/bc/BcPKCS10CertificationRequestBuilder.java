package org.python.bouncycastle.pkcs.bc;

import java.io.IOException;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.python.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;

public class BcPKCS10CertificationRequestBuilder extends PKCS10CertificationRequestBuilder {
   public BcPKCS10CertificationRequestBuilder(X500Name var1, AsymmetricKeyParameter var2) throws IOException {
      super(var1, SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(var2));
   }
}

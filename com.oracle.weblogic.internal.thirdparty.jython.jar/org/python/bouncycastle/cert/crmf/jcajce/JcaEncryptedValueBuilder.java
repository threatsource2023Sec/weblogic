package org.python.bouncycastle.cert.crmf.jcajce;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.crmf.EncryptedValue;
import org.python.bouncycastle.cert.crmf.CRMFException;
import org.python.bouncycastle.cert.crmf.EncryptedValueBuilder;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.python.bouncycastle.operator.KeyWrapper;
import org.python.bouncycastle.operator.OutputEncryptor;

public class JcaEncryptedValueBuilder extends EncryptedValueBuilder {
   public JcaEncryptedValueBuilder(KeyWrapper var1, OutputEncryptor var2) {
      super(var1, var2);
   }

   public EncryptedValue build(X509Certificate var1) throws CertificateEncodingException, CRMFException {
      return this.build(new JcaX509CertificateHolder(var1));
   }
}

package org.python.bouncycastle.cms.bc;

import java.io.IOException;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.operator.bc.BcRSAAsymmetricKeyWrapper;

public class BcRSAKeyTransRecipientInfoGenerator extends BcKeyTransRecipientInfoGenerator {
   public BcRSAKeyTransRecipientInfoGenerator(byte[] var1, AlgorithmIdentifier var2, AsymmetricKeyParameter var3) {
      super((byte[])var1, new BcRSAAsymmetricKeyWrapper(var2, var3));
   }

   public BcRSAKeyTransRecipientInfoGenerator(X509CertificateHolder var1) throws IOException {
      super((X509CertificateHolder)var1, new BcRSAAsymmetricKeyWrapper(var1.getSubjectPublicKeyInfo().getAlgorithmId(), var1.getSubjectPublicKeyInfo()));
   }
}

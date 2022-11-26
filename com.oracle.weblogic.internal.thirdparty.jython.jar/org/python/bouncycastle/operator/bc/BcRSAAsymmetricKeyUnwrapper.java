package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.python.bouncycastle.crypto.engines.RSAEngine;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class BcRSAAsymmetricKeyUnwrapper extends BcAsymmetricKeyUnwrapper {
   public BcRSAAsymmetricKeyUnwrapper(AlgorithmIdentifier var1, AsymmetricKeyParameter var2) {
      super(var1, var2);
   }

   protected AsymmetricBlockCipher createAsymmetricUnwrapper(ASN1ObjectIdentifier var1) {
      return new PKCS1Encoding(new RSAEngine());
   }
}

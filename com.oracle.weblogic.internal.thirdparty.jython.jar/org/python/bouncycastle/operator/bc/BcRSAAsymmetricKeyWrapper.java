package org.python.bouncycastle.operator.bc;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.python.bouncycastle.crypto.engines.RSAEngine;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;

public class BcRSAAsymmetricKeyWrapper extends BcAsymmetricKeyWrapper {
   public BcRSAAsymmetricKeyWrapper(AlgorithmIdentifier var1, AsymmetricKeyParameter var2) {
      super(var1, var2);
   }

   public BcRSAAsymmetricKeyWrapper(AlgorithmIdentifier var1, SubjectPublicKeyInfo var2) throws IOException {
      super(var1, PublicKeyFactory.createKey(var2));
   }

   protected AsymmetricBlockCipher createAsymmetricWrapper(ASN1ObjectIdentifier var1) {
      return new PKCS1Encoding(new RSAEngine());
   }
}

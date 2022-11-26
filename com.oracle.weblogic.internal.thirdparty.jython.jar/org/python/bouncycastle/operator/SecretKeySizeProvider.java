package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface SecretKeySizeProvider {
   int getKeySize(AlgorithmIdentifier var1);

   int getKeySize(ASN1ObjectIdentifier var1);
}

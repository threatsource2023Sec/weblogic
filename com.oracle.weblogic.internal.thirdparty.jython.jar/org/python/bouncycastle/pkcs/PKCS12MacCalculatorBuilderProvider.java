package org.python.bouncycastle.pkcs;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface PKCS12MacCalculatorBuilderProvider {
   PKCS12MacCalculatorBuilder get(AlgorithmIdentifier var1);
}

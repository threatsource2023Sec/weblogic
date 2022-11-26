package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface DigestCalculatorProvider {
   DigestCalculator get(AlgorithmIdentifier var1) throws OperatorCreationException;
}

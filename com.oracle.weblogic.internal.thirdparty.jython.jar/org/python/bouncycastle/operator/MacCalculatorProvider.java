package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface MacCalculatorProvider {
   MacCalculator get(AlgorithmIdentifier var1);
}

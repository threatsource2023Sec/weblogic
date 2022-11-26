package org.python.bouncycastle.pkcs;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.OperatorCreationException;

public interface PKCS12MacCalculatorBuilder {
   MacCalculator build(char[] var1) throws OperatorCreationException;

   AlgorithmIdentifier getDigestAlgorithmIdentifier();
}

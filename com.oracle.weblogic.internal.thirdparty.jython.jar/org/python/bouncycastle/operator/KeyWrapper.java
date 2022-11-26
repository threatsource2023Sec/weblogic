package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface KeyWrapper {
   AlgorithmIdentifier getAlgorithmIdentifier();

   byte[] generateWrappedKey(GenericKey var1) throws OperatorException;
}

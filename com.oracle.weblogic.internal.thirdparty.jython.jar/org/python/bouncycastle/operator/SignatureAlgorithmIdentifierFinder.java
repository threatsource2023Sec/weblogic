package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface SignatureAlgorithmIdentifierFinder {
   AlgorithmIdentifier find(String var1);
}

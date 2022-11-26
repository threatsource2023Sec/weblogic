package org.python.bouncycastle.operator;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface InputExpanderProvider {
   InputExpander get(AlgorithmIdentifier var1);
}

package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.operator.OperatorCreationException;

public interface BcDigestProvider {
   ExtendedDigest get(AlgorithmIdentifier var1) throws OperatorCreationException;
}

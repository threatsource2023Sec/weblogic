package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface CMSSignatureEncryptionAlgorithmFinder {
   AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier var1);
}

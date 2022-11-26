package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface CMSSignatureAlgorithmNameGenerator {
   String getSignatureName(AlgorithmIdentifier var1, AlgorithmIdentifier var2);
}

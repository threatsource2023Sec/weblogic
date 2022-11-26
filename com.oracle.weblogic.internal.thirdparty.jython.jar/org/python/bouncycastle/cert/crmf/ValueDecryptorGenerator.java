package org.python.bouncycastle.cert.crmf;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.InputDecryptor;

public interface ValueDecryptorGenerator {
   InputDecryptor getValueDecryptor(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3) throws CRMFException;
}

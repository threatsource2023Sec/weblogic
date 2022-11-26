package org.python.bouncycastle.operator;

import java.io.InputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface InputDecryptor {
   AlgorithmIdentifier getAlgorithmIdentifier();

   InputStream getInputStream(InputStream var1);
}

package org.python.bouncycastle.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface OutputEncryptor {
   AlgorithmIdentifier getAlgorithmIdentifier();

   OutputStream getOutputStream(OutputStream var1);

   GenericKey getKey();
}

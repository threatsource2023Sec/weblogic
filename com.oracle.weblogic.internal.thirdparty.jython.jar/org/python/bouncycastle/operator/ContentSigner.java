package org.python.bouncycastle.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface ContentSigner {
   AlgorithmIdentifier getAlgorithmIdentifier();

   OutputStream getOutputStream();

   byte[] getSignature();
}

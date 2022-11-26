package org.python.bouncycastle.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface ContentVerifier {
   AlgorithmIdentifier getAlgorithmIdentifier();

   OutputStream getOutputStream();

   boolean verify(byte[] var1);
}

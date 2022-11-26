package org.python.bouncycastle.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface MacCalculator {
   AlgorithmIdentifier getAlgorithmIdentifier();

   OutputStream getOutputStream();

   byte[] getMac();

   GenericKey getKey();
}

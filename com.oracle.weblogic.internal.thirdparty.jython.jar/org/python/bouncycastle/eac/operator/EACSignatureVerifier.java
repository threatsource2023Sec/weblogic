package org.python.bouncycastle.eac.operator;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface EACSignatureVerifier {
   ASN1ObjectIdentifier getUsageIdentifier();

   OutputStream getOutputStream();

   boolean verify(byte[] var1);
}

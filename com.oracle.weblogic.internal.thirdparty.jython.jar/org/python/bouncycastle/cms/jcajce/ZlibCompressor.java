package org.python.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.OutputCompressor;

public class ZlibCompressor implements OutputCompressor {
   private static final String ZLIB = "1.2.840.113549.1.9.16.3.8";

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.3.8"));
   }

   public OutputStream getOutputStream(OutputStream var1) {
      return new DeflaterOutputStream(var1);
   }
}

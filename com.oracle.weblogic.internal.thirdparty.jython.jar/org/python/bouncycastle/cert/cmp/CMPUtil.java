package org.python.bouncycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.DEROutputStream;

class CMPUtil {
   static void derEncodeToStream(ASN1Encodable var0, OutputStream var1) {
      DEROutputStream var2 = new DEROutputStream(var1);

      try {
         var2.writeObject(var0);
         var2.close();
      } catch (IOException var4) {
         throw new CMPRuntimeException("unable to DER encode object: " + var4.getMessage(), var4);
      }
   }
}

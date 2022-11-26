package org.python.bouncycastle.cert.crmf;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DEROutputStream;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.cert.CertIOException;

class CRMFUtil {
   static void derEncodeToStream(ASN1Encodable var0, OutputStream var1) {
      DEROutputStream var2 = new DEROutputStream(var1);

      try {
         var2.writeObject(var0);
         var2.close();
      } catch (IOException var4) {
         throw new CRMFRuntimeException("unable to DER encode object: " + var4.getMessage(), var4);
      }
   }

   static void addExtension(ExtensionsGenerator var0, ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws CertIOException {
      try {
         var0.addExtension(var1, var2, var3);
      } catch (IOException var5) {
         throw new CertIOException("cannot encode extension: " + var5.getMessage(), var5);
      }
   }
}

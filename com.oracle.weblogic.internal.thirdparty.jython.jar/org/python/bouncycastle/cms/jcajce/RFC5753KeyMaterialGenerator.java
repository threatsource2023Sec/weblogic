package org.python.bouncycastle.cms.jcajce;

import java.io.IOException;
import org.python.bouncycastle.asn1.cms.ecc.ECCCMSSharedInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.util.Pack;

class RFC5753KeyMaterialGenerator implements KeyMaterialGenerator {
   public byte[] generateKDFMaterial(AlgorithmIdentifier var1, int var2, byte[] var3) {
      ECCCMSSharedInfo var4 = new ECCCMSSharedInfo(var1, var3, Pack.intToBigEndian(var2));

      try {
         return var4.getEncoded("DER");
      } catch (IOException var6) {
         throw new IllegalStateException("Unable to create KDF material: " + var6);
      }
   }
}

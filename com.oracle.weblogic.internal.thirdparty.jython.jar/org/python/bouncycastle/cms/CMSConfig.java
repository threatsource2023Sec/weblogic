package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public class CMSConfig {
   public static void setSigningEncryptionAlgorithmMapping(String var0, String var1) {
      ASN1ObjectIdentifier var2 = new ASN1ObjectIdentifier(var0);
      CMSSignedHelper.INSTANCE.setSigningEncryptionAlgorithmMapping(var2, var1);
   }

   public static void setSigningDigestAlgorithmMapping(String var0, String var1) {
      ASN1ObjectIdentifier var2 = new ASN1ObjectIdentifier(var0);
      CMSSignedHelper.INSTANCE.setSigningDigestAlgorithmMapping(var2, var1);
   }
}

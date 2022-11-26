package org.python.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.security.SignatureException;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DEROctetString;

public class SignatureSpiLe extends SignatureSpi {
   void reverseBytes(byte[] var1) {
      for(int var2 = 0; var2 < var1.length / 2; ++var2) {
         byte var3 = var1[var2];
         var1[var2] = var1[var1.length - 1 - var2];
         var1[var1.length - 1 - var2] = var3;
      }

   }

   protected byte[] engineSign() throws SignatureException {
      byte[] var1 = ASN1OctetString.getInstance(super.engineSign()).getOctets();
      this.reverseBytes(var1);

      try {
         return (new DEROctetString(var1)).getEncoded();
      } catch (Exception var3) {
         throw new SignatureException(var3.toString());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      Object var2 = null;

      byte[] var7;
      try {
         var7 = ((ASN1OctetString)ASN1OctetString.fromByteArray(var1)).getOctets();
      } catch (IOException var6) {
         throw new SignatureException("error decoding signature bytes.");
      }

      this.reverseBytes(var7);

      try {
         return super.engineVerify((new DEROctetString(var7)).getEncoded());
      } catch (SignatureException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new SignatureException(var5.toString());
      }
   }
}

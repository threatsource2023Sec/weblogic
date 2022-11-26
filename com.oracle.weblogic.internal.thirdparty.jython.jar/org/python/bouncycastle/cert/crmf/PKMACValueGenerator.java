package org.python.bouncycastle.cert.crmf;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.crmf.PKMACValue;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.operator.MacCalculator;

class PKMACValueGenerator {
   private PKMACBuilder builder;

   public PKMACValueGenerator(PKMACBuilder var1) {
      this.builder = var1;
   }

   public PKMACValue generate(char[] var1, SubjectPublicKeyInfo var2) throws CRMFException {
      MacCalculator var3 = this.builder.build(var1);
      OutputStream var4 = var3.getOutputStream();

      try {
         var4.write(var2.getEncoded("DER"));
         var4.close();
      } catch (IOException var6) {
         throw new CRMFException("exception encoding mac input: " + var6.getMessage(), var6);
      }

      return new PKMACValue(var3.getAlgorithmIdentifier(), new DERBitString(var3.getMac()));
   }
}

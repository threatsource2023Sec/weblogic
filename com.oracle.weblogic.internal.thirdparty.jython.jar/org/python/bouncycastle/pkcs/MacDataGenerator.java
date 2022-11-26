package org.python.bouncycastle.pkcs;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.pkcs.MacData;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.DigestInfo;
import org.python.bouncycastle.operator.MacCalculator;

class MacDataGenerator {
   private PKCS12MacCalculatorBuilder builder;

   MacDataGenerator(PKCS12MacCalculatorBuilder var1) {
      this.builder = var1;
   }

   public MacData build(char[] var1, byte[] var2) throws PKCSException {
      MacCalculator var3;
      try {
         var3 = this.builder.build(var1);
         OutputStream var4 = var3.getOutputStream();
         var4.write(var2);
         var4.close();
      } catch (Exception var7) {
         throw new PKCSException("unable to process data: " + var7.getMessage(), var7);
      }

      AlgorithmIdentifier var8 = var3.getAlgorithmIdentifier();
      DigestInfo var5 = new DigestInfo(this.builder.getDigestAlgorithmIdentifier(), var3.getMac());
      PKCS12PBEParams var6 = PKCS12PBEParams.getInstance(var8.getParameters());
      return new MacData(var5, var6.getIV(), var6.getIterations().intValue());
   }
}

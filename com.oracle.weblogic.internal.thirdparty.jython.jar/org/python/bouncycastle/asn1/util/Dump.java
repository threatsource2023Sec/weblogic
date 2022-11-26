package org.python.bouncycastle.asn1.util;

import java.io.FileInputStream;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Primitive;

public class Dump {
   public static void main(String[] var0) throws Exception {
      FileInputStream var1 = new FileInputStream(var0[0]);
      ASN1InputStream var2 = new ASN1InputStream(var1);
      ASN1Primitive var3 = null;

      while((var3 = var2.readObject()) != null) {
         System.out.println(ASN1Dump.dumpAsString(var3));
      }

   }
}

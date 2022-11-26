package org.python.bouncycastle.asn1.util;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Primitive;

/** @deprecated */
public class DERDump extends ASN1Dump {
   public static String dumpAsString(ASN1Primitive var0) {
      StringBuffer var1 = new StringBuffer();
      _dumpAsString("", false, var0, var1);
      return var1.toString();
   }

   public static String dumpAsString(ASN1Encodable var0) {
      StringBuffer var1 = new StringBuffer();
      _dumpAsString("", false, var0.toASN1Primitive(), var1);
      return var1.toString();
   }
}

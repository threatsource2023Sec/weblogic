package org.python.bouncycastle.asn1.misc;

import org.python.bouncycastle.asn1.DERBitString;

public class NetscapeCertType extends DERBitString {
   public static final int sslClient = 128;
   public static final int sslServer = 64;
   public static final int smime = 32;
   public static final int objectSigning = 16;
   public static final int reserved = 8;
   public static final int sslCA = 4;
   public static final int smimeCA = 2;
   public static final int objectSigningCA = 1;

   public NetscapeCertType(int var1) {
      super(getBytes(var1), getPadBits(var1));
   }

   public NetscapeCertType(DERBitString var1) {
      super(var1.getBytes(), var1.getPadBits());
   }

   public String toString() {
      return "NetscapeCertType: 0x" + Integer.toHexString(this.data[0] & 255);
   }
}

package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERBitString;

public class KeyUsage extends ASN1Object {
   public static final int digitalSignature = 128;
   public static final int nonRepudiation = 64;
   public static final int keyEncipherment = 32;
   public static final int dataEncipherment = 16;
   public static final int keyAgreement = 8;
   public static final int keyCertSign = 4;
   public static final int cRLSign = 2;
   public static final int encipherOnly = 1;
   public static final int decipherOnly = 32768;
   private DERBitString bitString;

   public static KeyUsage getInstance(Object var0) {
      if (var0 instanceof KeyUsage) {
         return (KeyUsage)var0;
      } else {
         return var0 != null ? new KeyUsage(DERBitString.getInstance(var0)) : null;
      }
   }

   public static KeyUsage fromExtensions(Extensions var0) {
      return getInstance(var0.getExtensionParsedValue(Extension.keyUsage));
   }

   public KeyUsage(int var1) {
      this.bitString = new DERBitString(var1);
   }

   private KeyUsage(DERBitString var1) {
      this.bitString = var1;
   }

   public boolean hasUsages(int var1) {
      return (this.bitString.intValue() & var1) == var1;
   }

   public byte[] getBytes() {
      return this.bitString.getBytes();
   }

   public int getPadBits() {
      return this.bitString.getPadBits();
   }

   public String toString() {
      byte[] var1 = this.bitString.getBytes();
      return var1.length == 1 ? "KeyUsage: 0x" + Integer.toHexString(var1[0] & 255) : "KeyUsage: 0x" + Integer.toHexString((var1[1] & 255) << 8 | var1[0] & 255);
   }

   public ASN1Primitive toASN1Primitive() {
      return this.bitString;
   }
}

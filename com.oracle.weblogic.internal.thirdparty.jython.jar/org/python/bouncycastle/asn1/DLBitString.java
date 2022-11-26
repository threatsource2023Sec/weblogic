package org.python.bouncycastle.asn1;

import java.io.IOException;

public class DLBitString extends ASN1BitString {
   public static ASN1BitString getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof DLBitString)) {
         if (var0 instanceof DERBitString) {
            return (DERBitString)var0;
         } else if (var0 instanceof byte[]) {
            try {
               return (ASN1BitString)fromByteArray((byte[])((byte[])var0));
            } catch (Exception var2) {
               throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
            }
         } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
         }
      } else {
         return (DLBitString)var0;
      }
   }

   public static ASN1BitString getInstance(ASN1TaggedObject var0, boolean var1) {
      ASN1Primitive var2 = var0.getObject();
      return (ASN1BitString)(!var1 && !(var2 instanceof DLBitString) ? fromOctetString(((ASN1OctetString)var2).getOctets()) : getInstance(var2));
   }

   protected DLBitString(byte var1, int var2) {
      this(toByteArray(var1), var2);
   }

   private static byte[] toByteArray(byte var0) {
      byte[] var1 = new byte[]{var0};
      return var1;
   }

   public DLBitString(byte[] var1, int var2) {
      super(var1, var2);
   }

   public DLBitString(byte[] var1) {
      this(var1, 0);
   }

   public DLBitString(int var1) {
      super(getBytes(var1), getPadBits(var1));
   }

   public DLBitString(ASN1Encodable var1) throws IOException {
      super(var1.toASN1Primitive().getEncoded("DER"), 0);
   }

   boolean isConstructed() {
      return false;
   }

   int encodedLength() {
      return 1 + StreamUtil.calculateBodyLength(this.data.length + 1) + this.data.length + 1;
   }

   void encode(ASN1OutputStream var1) throws IOException {
      byte[] var2 = this.data;
      byte[] var3 = new byte[var2.length + 1];
      var3[0] = (byte)this.getPadBits();
      System.arraycopy(var2, 0, var3, 1, var3.length - 1);
      var1.writeEncoded(3, var3);
   }

   static DLBitString fromOctetString(byte[] var0) {
      if (var0.length < 1) {
         throw new IllegalArgumentException("truncated BIT STRING detected");
      } else {
         byte var1 = var0[0];
         byte[] var2 = new byte[var0.length - 1];
         if (var2.length != 0) {
            System.arraycopy(var0, 1, var2, 0, var0.length - 1);
         }

         return new DLBitString(var2, var1);
      }
   }
}

package org.python.bouncycastle.asn1;

import java.io.IOException;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Strings;

/** @deprecated */
public class DERT61UTF8String extends ASN1Primitive implements ASN1String {
   private byte[] string;

   public static DERT61UTF8String getInstance(Object var0) {
      if (var0 instanceof DERT61String) {
         return new DERT61UTF8String(((DERT61String)var0).getOctets());
      } else if (var0 != null && !(var0 instanceof DERT61UTF8String)) {
         if (var0 instanceof byte[]) {
            try {
               return new DERT61UTF8String(((DERT61String)fromByteArray((byte[])((byte[])var0))).getOctets());
            } catch (Exception var2) {
               throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
            }
         } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
         }
      } else {
         return (DERT61UTF8String)var0;
      }
   }

   public static DERT61UTF8String getInstance(ASN1TaggedObject var0, boolean var1) {
      ASN1Primitive var2 = var0.getObject();
      return !var1 && !(var2 instanceof DERT61String) && !(var2 instanceof DERT61UTF8String) ? new DERT61UTF8String(ASN1OctetString.getInstance(var2).getOctets()) : getInstance(var2);
   }

   public DERT61UTF8String(byte[] var1) {
      this.string = var1;
   }

   public DERT61UTF8String(String var1) {
      this(Strings.toUTF8ByteArray(var1));
   }

   public String getString() {
      return Strings.fromUTF8ByteArray(this.string);
   }

   public String toString() {
      return this.getString();
   }

   boolean isConstructed() {
      return false;
   }

   int encodedLength() {
      return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
   }

   void encode(ASN1OutputStream var1) throws IOException {
      var1.writeEncoded(20, this.string);
   }

   public byte[] getOctets() {
      return Arrays.clone(this.string);
   }

   boolean asn1Equals(ASN1Primitive var1) {
      return !(var1 instanceof DERT61UTF8String) ? false : Arrays.areEqual(this.string, ((DERT61UTF8String)var1).string);
   }

   public int hashCode() {
      return Arrays.hashCode(this.string);
   }
}

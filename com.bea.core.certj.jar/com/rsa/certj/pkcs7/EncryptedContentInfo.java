package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import java.io.Serializable;
import java.util.Arrays;

class EncryptedContentInfo implements Serializable, Cloneable {
   private byte[] b;
   private byte[] c;
   private byte[] d;
   private static final int e = 8454144;
   /** @deprecated */
   protected static int a;
   private ASN1Template f;

   /** @deprecated */
   protected EncryptedContentInfo() {
   }

   /** @deprecated */
   protected EncryptedContentInfo(byte[] var1, int var2, int var3, int var4) throws PKCS7Exception {
      try {
         SequenceContainer var5 = new SequenceContainer(var3);
         EndContainer var6 = new EndContainer();
         OIDContainer var7 = new OIDContainer(16777216);
         EncodedContainer var8 = new EncodedContainer(12288);
         EncodedContainer var9 = new EncodedContainer(130816);
         ASN1Container[] var10 = new ASN1Container[]{var5, var7, var8, var9, var6};
         ASN1.berDecode(var1, var2, var10);
         this.b = new byte[var7.dataLen];
         System.arraycopy(var7.data, var7.dataOffset, this.b, 0, var7.dataLen);
         this.c = new byte[var8.dataLen];
         System.arraycopy(var8.data, var8.dataOffset, this.c, 0, var8.dataLen);
         if (var9.dataPresent) {
            int var11 = var4 - var9.dataOffset + var2;
            if (var4 == 0) {
               var11 = 0;
            }

            OctetStringContainer var12;
            try {
               var12 = new OctetStringContainer(8454144, true, 0, var11, (byte[])null, 0, 0);
               ASN1Container[] var13 = new ASN1Container[]{var12};
               ASN1.berDecode(var9.data, var9.dataOffset, var13);
            } catch (ASN_Exception var15) {
               var12 = new OctetStringContainer(10551296, true, 0, var11, (byte[])null, 0, 0);
               ASN1Container[] var14 = new ASN1Container[]{var12};
               ASN1.berDecode(var9.data, var9.dataOffset, var14);
            }

            if (var12.dataPresent) {
               this.d = new byte[var12.dataLen];
               System.arraycopy(var12.data, var12.dataOffset, this.d, 0, var12.dataLen);
            }
         }

      } catch (ASN_Exception var16) {
         throw new PKCS7Exception("Cannot decode the BER of the EncryptedContentInfo.", var16);
      }
   }

   /** @deprecated */
   protected void a(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot set content type: null OID.");
      } else if (var3 != 9) {
         throw new PKCS7Exception("Cannot set content type: unknown OID.");
      } else {
         for(int var4 = 0; var4 < 7; ++var4) {
            if (var1[var4 + var2] != ContentInfo.P7OID[var4]) {
               throw new PKCS7Exception("Cannot set content type: unknown OID.");
            }
         }

         this.b = new byte[var3];
         System.arraycopy(var1, var2, this.b, 0, var3);
      }
   }

   /** @deprecated */
   protected byte[] a() {
      return this.b;
   }

   /** @deprecated */
   protected void b(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 != 0) {
         this.c = new byte[var3];
         System.arraycopy(var1, var2, this.c, 0, var3);
      } else {
         throw new PKCS7Exception("Specified OID is NULL.");
      }
   }

   /** @deprecated */
   protected byte[] b() {
      return this.c;
   }

   /** @deprecated */
   protected void c(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("encrypted content is null.");
      } else {
         this.d = new byte[var3];
         System.arraycopy(var1, var2, this.d, 0, var3);
      }
   }

   /** @deprecated */
   protected byte[] c() {
      return this.d;
   }

   /** @deprecated */
   protected int a(int var1) throws PKCS7Exception {
      a = var1;
      return this.e();
   }

   /** @deprecated */
   protected int d(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         if (this.f == null || var3 != a) {
            this.a(var3);
         }

         int var4 = this.f.derEncode(var1, var2);
         this.f = null;
         return var4;
      } catch (ASN_Exception var5) {
         this.f = null;
         throw new PKCS7Exception("Unable to encode EncryptedContentInfo: ", var5);
      }
   }

   private int e() throws PKCS7Exception {
      try {
         SequenceContainer var1 = new SequenceContainer(a, true, 0);
         EndContainer var2 = new EndContainer();
         OIDContainer var3 = new OIDContainer(16777216, true, 0, this.b, 0, this.b.length);
         EncodedContainer var4 = new EncodedContainer(12288, true, 0, this.c, 0, this.c.length);
         if (this.d != null) {
            OctetStringContainer var5 = new OctetStringContainer(8454144, true, 0, this.d, 0, this.d.length);
            ASN1Container[] var6 = new ASN1Container[]{var1, var3, var4, var5, var2};
            this.f = new ASN1Template(var6);
         } else {
            ASN1Container[] var8 = new ASN1Container[]{var1, var3, var4, var2};
            this.f = new ASN1Template(var8);
         }

         return this.f.derEncodeInit();
      } catch (ASN_Exception var7) {
         throw new PKCS7Exception(var7);
      }
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof EncryptedContentInfo) {
         EncryptedContentInfo var2 = (EncryptedContentInfo)var1;
         return CertJUtils.byteArraysEqual(this.b, var2.b) && CertJUtils.byteArraysEqual(this.c, var2.c) && CertJUtils.byteArraysEqual(this.d, var2.d);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.b);
      var2 = var1 * var2 + Arrays.hashCode(this.d);
      var2 = var1 * var2 + Arrays.hashCode(this.c);
      return var2;
   }

   /** @deprecated */
   protected Object clone() throws CloneNotSupportedException {
      EncryptedContentInfo var1 = new EncryptedContentInfo();
      if (this.b != null) {
         var1.b = new byte[this.b.length];
         System.arraycopy(this.b, 0, var1.b, 0, this.b.length);
      }

      if (this.c != null) {
         var1.c = new byte[this.c.length];
         System.arraycopy(this.c, 0, var1.c, 0, this.c.length);
      }

      if (this.d != null) {
         var1.d = new byte[this.d.length];
         System.arraycopy(this.d, 0, var1.d, 0, this.d.length);
      }

      try {
         if (this.f != null) {
            var1.e();
         }

         return var1;
      } catch (PKCS7Exception var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }

   /** @deprecated */
   protected void d() {
      this.b = null;
      this.c = null;
      this.d = null;
      a = 0;
   }
}

package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import java.util.Arrays;

/** @deprecated */
public class TypeAndValue {
   private byte[] type;
   private byte[] value;
   private int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   protected TypeAndValue(byte[] var1, int var2, int var3) throws CMPException {
      if (var1 == null) {
         throw new CMPException("TypeAndValue.TypeAndValue: ber should not be null.");
      } else {
         SequenceContainer var4 = new SequenceContainer(0);
         OIDContainer var5 = new OIDContainer(16777216);
         EncodedContainer var6 = new EncodedContainer(130816);
         EndContainer var7 = new EndContainer();
         ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};

         try {
            ASN1.berDecode(var1, var2, var8);
         } catch (ASN_Exception var10) {
            throw new CMPException("TypeAndValue.TypeAndValue: BER-decoding failed.", var10);
         }

         this.type = new byte[var5.dataLen];
         System.arraycopy(var5.data, var5.dataOffset, this.type, 0, var5.dataLen);
         if (var6.dataPresent) {
            this.value = new byte[var6.dataLen];
            System.arraycopy(var6.data, var6.dataOffset, this.value, 0, var6.dataLen);
         }

      }
   }

   /** @deprecated */
   public TypeAndValue(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) throws CMPException {
      if (var1 == null) {
         throw new CMPException("TypeAndValue.TypeAndValue: type should not be null.");
      } else {
         this.type = new byte[var3];
         System.arraycopy(var1, var2, this.type, 0, var3);
         if (var4 == null) {
            this.value = null;
         } else {
            this.value = new byte[var6];
            System.arraycopy(var4, var5, this.value, 0, var6);
         }

      }
   }

   /** @deprecated */
   protected int getDERLen(int var1) throws CMPException {
      this.special = var1;

      try {
         SequenceContainer var2 = new SequenceContainer(0, true, 0);
         OIDContainer var3 = new OIDContainer(16777216, true, 0, this.type, 0, this.type.length);
         EncodedContainer var4;
         if (this.value == null) {
            var4 = new EncodedContainer(130816, false, 0, (byte[])null, 0, 0);
         } else {
            var4 = new EncodedContainer(130816, true, 0, this.value, 0, this.value.length);
         }

         EndContainer var5 = new EndContainer();
         ASN1Container[] var6 = new ASN1Container[]{var2, var3, var4, var5};
         this.asn1Template = new ASN1Template(var6);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var7) {
         throw new CMPException("TypeAndValue.getDERLen: DER-encoding failed.", var7);
      }
   }

   /** @deprecated */
   protected int getDEREncoding(byte[] var1, int var2, int var3) throws CMPException {
      if (var1 == null) {
         throw new CMPException("TypeAndValue.getDEREncoding: encoding should not be null.");
      } else {
         int var4;
         try {
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new CMPException("TypeAndValue.getDEREncoding: DER-encoding has 0 length.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
         } catch (ASN_Exception var8) {
            throw new CMPException("TypeAndValue.getDEREncoding: DER-encoding failed.", var8);
         } finally {
            this.asn1Template = null;
         }

         return var4;
      }
   }

   /** @deprecated */
   public byte[] getType() {
      if (this.type == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.type.length];
         System.arraycopy(this.type, 0, var1, 0, this.type.length);
         return var1;
      }
   }

   /** @deprecated */
   public byte[] getValue() {
      if (this.value == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.value.length];
         System.arraycopy(this.value, 0, var1, 0, this.value.length);
         return var1;
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof TypeAndValue) {
         TypeAndValue var2 = (TypeAndValue)var1;
         return CertJUtils.byteArraysEqual(this.type, var2.type) && CertJUtils.byteArraysEqual(this.value, var2.value);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.type);
      var2 = var1 * var2 + Arrays.hashCode(this.value);
      return var2;
   }
}

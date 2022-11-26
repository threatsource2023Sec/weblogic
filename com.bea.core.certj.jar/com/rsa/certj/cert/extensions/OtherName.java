package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class OtherName implements Serializable, Cloneable {
   private static final int VALUE_SPECIAL = 10485760;
   private byte[] typeId;
   private byte[] value;
   private ASN1Template asn1Template;
   private int special;

   /** @deprecated */
   public OtherName() {
   }

   /** @deprecated */
   public OtherName(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) {
      if (var1 != null && var3 != 0) {
         this.typeId = new byte[var3];
         System.arraycopy(var1, var2, this.typeId, 0, var3);
      }

      if (var4 != null && var6 != 0) {
         this.value = new byte[var6];
         System.arraycopy(var4, var5, this.value, 0, var6);
      }

   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            OIDContainer var5 = new OIDContainer(0);
            EncodedContainer var6 = new EncodedContainer(10551040);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            this.typeId = new byte[var5.dataLen];
            System.arraycopy(var5.data, var5.dataOffset, this.typeId, 0, var5.dataLen);
            int var9 = ASN1Lengths.determineLengthLen(var6.data, var6.dataOffset + 1);
            int var10 = 1 + var9;
            this.value = new byte[var6.dataLen - var10];
            System.arraycopy(var6.data, var6.dataOffset + var10, this.value, 0, var6.dataLen - var10);
         } catch (ASN_Exception var11) {
            throw new NameException("Cannot decode the BER of the OtherName.");
         }
      }
   }

   /** @deprecated */
   public void addValues(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) {
      if (var1 != null && var3 != 0) {
         this.typeId = new byte[var3];
         System.arraycopy(var1, var2, this.typeId, 0, var3);
      }

      if (var4 != null && var6 != 0) {
         this.value = new byte[var6];
         System.arraycopy(var4, var5, this.value, 0, var6);
      }

   }

   /** @deprecated */
   public byte[] getTypeID() {
      return this.typeId;
   }

   /** @deprecated */
   public byte[] getValue() {
      return this.value;
   }

   /** @deprecated */
   public String toString() {
      return new String(this.value);
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws NameException {
      if (var0 == null) {
         throw new NameException("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new NameException("Unable to determine length of the BER");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws NameException {
      this.special = var1;
      return this.derEncodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified array is null.");
      } else {
         try {
            int var4;
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode Other Name.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode Other Name.");
         }
      }
   }

   private int derEncodeInit() throws NameException {
      if (this.value != null && this.typeId != null) {
         try {
            int var1 = ASN1Lengths.getLengthLen(this.value.length);
            byte[] var2 = new byte[this.value.length + 1 + var1];
            var2[0] = -96;
            ASN1Lengths.writeLength(var2, 1, this.value.length);
            System.arraycopy(this.value, 0, var2, var1 + 1, this.value.length);
            SequenceContainer var3 = new SequenceContainer(this.special, true, 0);
            OIDContainer var4 = new OIDContainer(16777216, true, 0, this.typeId, 0, this.typeId.length);
            EncodedContainer var5 = new EncodedContainer(65280, true, 0, var2, 0, var2.length);
            EndContainer var6 = new EndContainer();
            ASN1Container[] var7 = new ASN1Container[]{var3, var4, var5, var6};
            this.asn1Template = new ASN1Template(var7);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var8) {
            throw new NameException(var8);
         }
      } else {
         throw new NameException("Cannot encode OtherName: values are not set.");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof OtherName) {
         OtherName var2 = (OtherName)var1;
         if (!CertJUtils.byteArraysEqual(this.typeId, var2.typeId)) {
            return false;
         } else {
            return CertJUtils.byteArraysEqual(this.value, var2.value);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = Arrays.hashCode(this.typeId);
      if (this.value != null) {
         var1 ^= Arrays.hashCode(this.value);
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      OtherName var1 = new OtherName();
      var1.typeId = this.typeId;
      var1.value = this.value;
      var1.special = this.special;

      try {
         if (this.asn1Template != null) {
            var1.derEncodeInit();
         }

         return var1;
      } catch (NameException var3) {
         throw new CloneNotSupportedException("Cannot get ASN1 Template");
      }
   }
}

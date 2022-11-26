package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.certj.CertJUtils;
import java.util.Arrays;

/** @deprecated */
public class Data extends ContentInfo {
   /** @deprecated */
   protected byte[] data;
   /** @deprecated */
   protected int dataLen;

   /** @deprecated */
   public Data() {
      this.contentType = 1;
      OctetStringContainer var1 = null;

      try {
         var1 = new OctetStringContainer(10551296, true, 0, (byte[])null, 0, 0);
      } catch (ASN_Exception var3) {
      }

      this.contentASN1Def = new ASN1Container[1];
      this.contentASN1Def[0] = var1;
   }

   /** @deprecated */
   public void setContent(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.data = new byte[var3];
            System.arraycopy(var1, var2, this.data, 0, var3);
            this.dataLen = var3;
         } else {
            throw new PKCS7Exception("Invalid Content data");
         }
      } else {
         throw new PKCS7Exception("content is null");
      }
   }

   /** @deprecated */
   public byte[] getData() {
      if (this.data != null) {
         byte[] var1 = new byte[this.dataLen];
         System.arraycopy(this.data, 0, var1, 0, this.dataLen);
         return var1;
      } else {
         return null;
      }
   }

   /** @deprecated */
   protected int getContentDERLen() throws PKCS7Exception {
      this.contentASN1Def[0].data = null;
      this.contentASN1Def[0].dataLen = this.dataLen;
      this.contentASN1Def[0].dataPresent = this.data != null;
      this.contentASN1Template = new ASN1Template(this.contentASN1Def);

      try {
         return this.contentASN1Template.derEncodeInit();
      } catch (ASN_Exception var2) {
         throw new PKCS7Exception(var2);
      }
   }

   /** @deprecated */
   protected int writeContent(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot write Data: output array is null.");
      } else {
         try {
            this.contentASN1Def[0].data = this.data;
            this.contentASN1Def[0].dataOffset = 0;
            this.contentASN1Def[0].dataLen = this.dataLen;
            return this.contentASN1Template.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            throw new PKCS7Exception("Could not DER encode Data: ", var4);
         }
      }
   }

   /** @deprecated */
   protected boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         if (this.maxBufferSize != 0) {
            OctetStringContainer var4 = new OctetStringContainer(10551296, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
            this.contentASN1Def = new ASN1Container[1];
            this.contentASN1Def[0] = var4;
         }

         this.contentASN1Template = new ASN1Template(this.contentASN1Def);
         this.contentASN1Template.berDecodeInit();
         this.numberOfBytesRead = this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
         this.copyNewDataToOutput();
         return true;
      } catch (ASN_Exception var5) {
         throw new PKCS7Exception("Could not decode message: ", var5);
      }
   }

   /** @deprecated */
   protected int contentReadUpdate(byte[] var1, int var2, int var3) throws PKCS7Exception {
      this.numberOfBytesRead = 0;
      int var4 = 0;
      if (var1 == null) {
         return var4;
      } else {
         if (this.contentASN1Template == null) {
            this.contentASN1Template = new ASN1Template(this.contentASN1Def);
            this.contentASN1Template.berDecodeInit();
         }

         try {
            if (this.contentASN1Template.isComplete()) {
               return var4;
            } else {
               this.numberOfBytesRead = this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
               var4 += this.copyNewDataToOutput();
               return var4;
            }
         } catch (ASN_Exception var6) {
            throw new PKCS7Exception("Could not decode message: ", var6);
         }
      }
   }

   private int copyNewDataToOutput() {
      if (this.contentASN1Def[0].data != null && this.contentASN1Def[0].dataLen != 0) {
         if (this.data == null) {
            if (this.maxBufferSize != 0) {
               this.data = new byte[this.maxBufferSize];
            } else {
               this.data = new byte[this.contentASN1Def[0].dataLen];
            }
         }

         int var1 = this.data.length - this.dataLen;
         if (var1 < this.contentASN1Def[0].dataLen) {
            byte[] var2 = new byte[this.dataLen];
            System.arraycopy(this.data, 0, var2, 0, this.dataLen);
            this.clearInternalBuffer(this.data);
            this.data = new byte[this.contentASN1Def[0].dataLen + this.dataLen];
            System.arraycopy(var2, 0, this.data, 0, this.dataLen);
            this.clearInternalBuffer(var2);
         }

         int var3 = this.contentASN1Def[0].dataLen;
         if (var3 != 0) {
            System.arraycopy(this.contentASN1Def[0].data, this.contentASN1Def[0].dataOffset, this.data, this.dataLen, var3);
         }

         this.dataLen += var3;
         return var3;
      } else {
         return 0;
      }
   }

   /** @deprecated */
   public int getUnprocessedDataLen() {
      return this.dataLen;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Data) {
         Data var2 = (Data)var1;
         return this.dataLen == var2.dataLen && CertJUtils.byteArraysEqual(this.data, 0, this.dataLen, var2.data, 0, var2.dataLen);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.data);
      var2 = var1 * var2 + this.dataLen;
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      Data var1 = (Data)super.clone();
      if (this.data != null) {
         var1.data = new byte[this.data.length];
         System.arraycopy(this.data, 0, var1.data, 0, this.dataLen);
         var1.dataLen = this.dataLen;
      }

      return var1;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      super.clearSensitiveData();
      this.clearInternalBuffer(this.data);
   }
}

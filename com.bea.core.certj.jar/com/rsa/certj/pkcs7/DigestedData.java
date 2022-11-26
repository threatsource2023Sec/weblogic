package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import java.util.Arrays;

/** @deprecated */
public class DigestedData extends ContentInfo {
   /** @deprecated */
   protected int version = -1;
   /** @deprecated */
   protected byte[] digestOID;
   private String algorithmName;
   private byte[] digest;

   /** @deprecated */
   public DigestedData(CertJ var1, CertPathCtx var2) {
      this.contentType = 5;
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
   }

   /** @deprecated */
   public void setContentInfo(ContentInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Unable to set content: content is null.");
      } else {
         try {
            this.content = (ContentInfo)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Unable to clone ContentInfo type");
         }
      }
   }

   /** @deprecated */
   public void setVersionNumber(int var1) {
      this.version = var1;
   }

   /** @deprecated */
   public int getVersionNumber() {
      return this.version;
   }

   /** @deprecated */
   public void setDigestAlgorithm(String var1) throws PKCS7Exception {
      this.digestOID = setDigestAlgorithmInternal(this.theCertJ, var1);
      this.algorithmName = var1;
   }

   static byte[] setDigestAlgorithmInternal(CertJ var0, String var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Could not set digest algorithm: algName is null");
      } else {
         try {
            JSAFE_MessageDigest var2 = h.a(var1, "Java", var0);
            return var2.getDERAlgorithmID();
         } catch (JSAFE_Exception var3) {
            throw new PKCS7Exception("Could not set algorithm algorithm: algName is invalid");
         }
      }
   }

   static byte[] setDigestAlgorithmInternal(c var0, String var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Could not set digest algorithm: algName is null");
      } else {
         try {
            JSAFE_MessageDigest var2 = h.a(var1, "Java", var0.b);
            return var2.getDERAlgorithmID();
         } catch (JSAFE_Exception var3) {
            throw new PKCS7Exception("Could not set algorithm algorithm: algName is invalid");
         }
      }
   }

   /** @deprecated */
   public void setDigestAlgorithm(byte[] var1, int var2, int var3) throws PKCS7Exception {
      this.digestOID = setDigestAlgorithmInternal(var1, var2, var3);
   }

   static byte[] setDigestAlgorithmInternal(byte[] var0, int var1, int var2) throws PKCS7Exception {
      if (var0 != null && var2 > 0) {
         if (var1 >= 0 && var1 + var2 <= var0.length) {
            byte[] var3 = new byte[var2];
            System.arraycopy(var0, var1, var3, 0, var2);
            return var3;
         } else {
            throw new PKCS7Exception("Invalid digest algorithm identifier.");
         }
      } else {
         throw new PKCS7Exception("Could not set algorithm OID: oid is null.");
      }
   }

   /** @deprecated */
   public byte[] getDigestAlgorithmOID() {
      return this.digestOID;
   }

   /** @deprecated */
   public String getDigestAlgorithmName() throws PKCS7Exception {
      return getDigestAlgorithmNameInternal(this.theCertJ, this.digestOID, this.algorithmName);
   }

   static String getDigestAlgorithmNameInternal(CertJ var0, byte[] var1, String var2) throws PKCS7Exception {
      if (var1 == null) {
         return null;
      } else if (var2 != null) {
         return var2;
      } else {
         try {
            JSAFE_MessageDigest var3 = h.f(var1, 0, "Java", (CertJ)var0);
            return var3.getAlgorithm();
         } catch (JSAFE_Exception var4) {
            throw new PKCS7Exception("Could not set algorithm OID: ", var4);
         }
      }
   }

   static String getDigestAlgorithmNameInternal(c var0, byte[] var1, String var2) throws PKCS7Exception {
      if (var1 == null) {
         return null;
      } else if (var2 != null) {
         return var2;
      } else {
         try {
            JSAFE_MessageDigest var3 = h.e(var1, 0, "Java", (FIPS140Context)var0.b);
            return var3.getAlgorithm();
         } catch (JSAFE_Exception var4) {
            throw new PKCS7Exception("Could not set algorithm OID: ", var4);
         }
      }
   }

   /** @deprecated */
   protected int getContentDERLen() throws PKCS7Exception {
      return this.contentDEREncodeInit();
   }

   /** @deprecated */
   protected int writeContent(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot write DigestedData: output array is null.");
      } else {
         try {
            if (this.contentASN1Template == null) {
               this.getContentDERLen();
            }

            int var3 = this.contentASN1Template.derEncode(var1, var2);
            this.contentASN1Template = null;
            return var3;
         } catch (ASN_Exception var4) {
            this.contentASN1Template = null;
            throw new PKCS7Exception("Unable to DER encode DigestedData message: ", var4);
         }
      }
   }

   private int contentDEREncodeInit() throws PKCS7Exception {
      if (this.content == null) {
         throw new PKCS7Exception("content is not set.");
      } else {
         if (this.flag == 0) {
            this.digest = this.getDigest();
         }

         if (this.version == -1) {
            this.version = 0;
         }

         if (this.contentEncoding == null) {
            int var1 = this.content.getContentInfoDERLen();
            this.contentEncoding = new byte[var1];
            this.content.writeMessage(this.contentEncoding, 0);
         }

         try {
            EncodedContainer var9 = new EncodedContainer(0, true, 0, this.contentEncoding, 0, this.contentEncoding.length);
            SequenceContainer var2 = new SequenceContainer(10551296, true, 0);
            EndContainer var3 = new EndContainer();
            IntegerContainer var4 = new IntegerContainer(0, true, 0, this.version);
            EncodedContainer var5 = new EncodedContainer(12288, true, 0, this.digestOID, 0, this.digestOID.length);
            int var6 = 0;
            if (this.digest != null) {
               var6 = this.digest.length;
            }

            OctetStringContainer var7 = new OctetStringContainer(0, true, 0, this.digest, 0, var6);
            this.contentASN1Def = new ASN1Container[6];
            this.contentASN1Def[0] = var2;
            this.contentASN1Def[1] = var4;
            this.contentASN1Def[2] = var5;
            this.contentASN1Def[3] = var9;
            this.contentASN1Def[4] = var7;
            this.contentASN1Def[5] = var3;
            this.contentASN1Template = new ASN1Template(this.contentASN1Def);
            return this.contentASN1Template.derEncodeInit();
         } catch (ASN_Exception var8) {
            throw new PKCS7Exception(var8);
         }
      }
   }

   /** @deprecated */
   public byte[] getMessageDigest() {
      if (this.digest == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.digest.length];
         System.arraycopy(this.digest, 0, var1, 0, this.digest.length);
         return var1;
      }
   }

   private byte[] getDigest() throws PKCS7Exception {
      byte[] var1 = null;

      try {
         if (this.contentEncoding == null) {
            int var2 = this.content.getContentInfoDERLen();
            this.contentEncoding = new byte[var2];
            this.content.writeMessage(this.contentEncoding, 0);
         }

         SequenceContainer var10 = new SequenceContainer(0);
         EndContainer var3 = new EndContainer();
         OIDContainer var4 = new OIDContainer(16777216);
         EncodedContainer var5 = new EncodedContainer(10616576);
         ASN1Container[] var6 = new ASN1Container[]{var10, var4, var5, var3};
         ASN1.berDecode(this.contentEncoding, 0, var6);
         if (var5.dataPresent) {
            int var7 = 1;
            var7 += ASN1Lengths.determineLengthLen(var5.data, var5.dataOffset + 1);
            ++var7;
            var7 += ASN1Lengths.determineLengthLen(var5.data, var5.dataOffset + var7);
            var1 = new byte[var5.dataLen - var7];
            System.arraycopy(var5.data, var5.dataOffset + var7, var1, 0, var5.dataLen - var7);
         }
      } catch (ASN_Exception var9) {
         throw new PKCS7Exception("Could not DER encode ContentInfo: ", var9);
      }

      try {
         if (var1 == null) {
            return null;
         } else {
            JSAFE_MessageDigest var11 = h.f(this.digestOID, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
            var11.digestInit();
            var11.digestUpdate(var1, 0, var1.length);
            byte[] var12 = var11.digestFinal();
            var11.clearSensitiveData();
            return var12;
         }
      } catch (JSAFE_Exception var8) {
         throw new PKCS7Exception("Could not digest ContentInfo: ", var8);
      }
   }

   /** @deprecated */
   protected boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         SequenceContainer var4 = new SequenceContainer(10551296);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12288);
         EncodedContainer var8 = new EncodedContainer(12288, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
         OctetStringContainer var9 = new OctetStringContainer(0);
         this.contentASN1Def = new ASN1Container[6];
         this.contentASN1Def[0] = var4;
         this.contentASN1Def[1] = var6;
         this.contentASN1Def[2] = var7;
         this.contentASN1Def[3] = var8;
         this.contentASN1Def[4] = var9;
         this.contentASN1Def[5] = var5;
         this.contentASN1Template = new ASN1Template(this.contentASN1Def);
         this.contentASN1Template.berDecodeInit();
         this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
         if (this.contentASN1Def[0].checkTag()) {
            return false;
         } else {
            this.setDecodedValues();
            this.flag = 1;
            return true;
         }
      } catch (ASN_Exception var10) {
         throw new PKCS7Exception("Could not decode message: ", var10);
      }
   }

   private void setDecodedValues() throws PKCS7Exception {
      try {
         byte[] var1;
         if (this.version == -1) {
            if (!this.contentASN1Def[1].isComplete()) {
               if (this.contentASN1Def[1].data == null) {
                  return;
               }

               this.incompleteContainer = 1;
               this.copyNewData(this.contentASN1Def[1].data, this.contentASN1Def[1].dataOffset, this.contentASN1Def[1].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 1) {
               var1 = this.copyToOutput(this.contentASN1Def[1]);
               IntegerContainer var2 = new IntegerContainer(0, true, 0, var1, 0, var1.length, true);
               this.version = var2.getValueAsInt();
            } else {
               this.version = ((IntegerContainer)this.contentASN1Def[1]).getValueAsInt();
            }
         }

         if (this.digestOID == null) {
            if (!this.contentASN1Def[2].isComplete()) {
               if (this.contentASN1Def[2].data == null) {
                  return;
               }

               this.incompleteContainer = 2;
               this.copyNewData(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 2) {
               this.digestOID = this.copyToOutput(this.contentASN1Def[2]);
            } else {
               this.digestOID = new byte[this.contentASN1Def[2].dataLen];
               System.arraycopy(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.digestOID, 0, this.contentASN1Def[2].dataLen);
            }
         }

         if (this.content == null) {
            if (!this.contentASN1Def[3].isComplete()) {
               if (this.contentASN1Def[3].data == null) {
                  return;
               }

               this.incompleteContainer = 3;
               this.copyNewData(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, this.contentASN1Def[3].dataLen, this.maxBufferSize);
               return;
            }

            if (this.incompleteContainer == 3) {
               var1 = this.copyToOutput(this.contentASN1Def[3]);
               this.decodeContent(var1, 0, var1.length);
            } else {
               this.decodeContent(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, this.contentASN1Def[3].dataLen);
            }
         }

         var1 = this.getDigest();
         if (this.digest == null) {
            if (this.contentASN1Def[4].isComplete()) {
               if (this.contentASN1Def[4].data == null && var1 == null) {
                  return;
               }

               if (this.incompleteContainer == 4) {
                  this.digest = this.copyToOutput(this.contentASN1Def[4]);
               } else if (this.contentASN1Def[4].data != null) {
                  this.digest = new byte[this.contentASN1Def[4].dataLen];
                  System.arraycopy(this.contentASN1Def[4].data, this.contentASN1Def[4].dataOffset, this.digest, 0, this.contentASN1Def[4].dataLen);
               }

               if (!CertJUtils.byteArraysEqual(this.digest, var1)) {
                  throw new PKCS7Exception("Invalid digest.");
               }
            } else if (this.contentASN1Def[4].data != null) {
               this.incompleteContainer = 4;
               this.copyNewData(this.contentASN1Def[4].data, this.contentASN1Def[4].dataOffset, this.contentASN1Def[4].dataLen, 0);
            }
         }

      } catch (Exception var3) {
         throw new PKCS7Exception("Cannot set decoded values.", var3);
      }
   }

   private void decodeContent(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 != 0) {
         try {
            SequenceContainer var4 = new SequenceContainer(0);
            EndContainer var5 = new EndContainer();
            OIDContainer var6 = new OIDContainer(16777216);
            EncodedContainer var7 = new EncodedContainer(10616576, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
            ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
            ASN1.berDecode(var1, var2, var8);
            this.content = ContentInfo.getInstance(var6.data, var6.dataOffset, var6.dataLen, this.theCertJ, this.theCertPathCtx);
            if (this.content.readInit(var1, var2, var3, this.maxBufferSize) && this.content.readFinal()) {
               return;
            }
         } catch (Exception var9) {
            throw new PKCS7Exception("Cannot decode content.", var9);
         }

         throw new PKCS7Exception("Cannot decode content.");
      } else {
         throw new PKCS7Exception("Cannot decode DigestedData: data is null.");
      }
   }

   /** @deprecated */
   protected int contentReadUpdate(byte[] var1, int var2, int var3) throws PKCS7Exception {
      int var4 = 0;
      if (var1 == null) {
         return var4;
      } else if (this.contentASN1Template == null) {
         throw new PKCS7Exception("Call readInit before readUpdate.");
      } else {
         try {
            if (this.contentASN1Template.isComplete()) {
               return var4;
            } else {
               var4 = this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
               this.setDecodedValues();
               return var4;
            }
         } catch (ASN_Exception var6) {
            throw new PKCS7Exception("Could not decode message: ", var6);
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      DigestedData var1 = (DigestedData)super.clone();
      var1.version = this.version;
      if (this.digest != null) {
         var1.digest = (byte[])((byte[])this.digest.clone());
      }

      if (this.digestOID != null) {
         var1.digestOID = (byte[])((byte[])this.digestOID.clone());
      }

      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof DigestedData) {
         DigestedData var2 = (DigestedData)var1;
         if (this.version != var2.version) {
            return false;
         } else if (this.content != null && !this.content.equals(var2.content)) {
            return false;
         } else {
            return CertJUtils.byteArraysEqual(this.digest, var2.digest) && CertJUtils.byteArraysEqual(this.digestOID, var2.digestOID);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.digest);
      var2 = var1 * var2 + Arrays.hashCode(this.digestOID);
      var2 = var1 * var2 + this.version;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.content);
      return var2;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      super.clearSensitiveData();
      this.version = -1;
      this.digestOID = null;
      this.digest = null;
   }
}

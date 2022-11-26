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
import com.rsa.asn1.OIDList;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.util.Arrays;

/** @deprecated */
public class EncryptedData extends ContentInfo {
   /** @deprecated */
   protected int version = -1;
   /** @deprecated */
   protected EncryptedContentInfo info = new EncryptedContentInfo();
   private String encryptionAlgName;
   private JSAFE_SecretKey theKey;
   private byte[] keyData;
   private char[] password;
   private byte[] iv;
   private byte[] salt;

   /** @deprecated */
   public EncryptedData(CertJ var1, CertPathCtx var2) {
      this.contentType = 6;
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
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
   public void setIV(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 > 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         this.iv = new byte[var3];
         System.arraycopy(var1, var2, this.iv, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getIV() {
      if (this.iv == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.iv.length];
         System.arraycopy(this.iv, 0, var1, 0, this.iv.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setSalt(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 > 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         this.salt = new byte[var3];
         System.arraycopy(var1, var2, this.salt, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getSalt() {
      if (this.salt == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.salt.length];
         System.arraycopy(this.salt, 0, var1, 0, this.salt.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(String var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Algorithm name is null");
      } else {
         this.encryptionAlgName = var1;

         try {
            JSAFE_SymmetricCipher var2 = h.c(var1, this.getDeviceOrJava(), this.theCertJ);
            if (var2.getFeedbackMode() != null && !var2.getFeedbackMode().equals("ECB")) {
               if (this.iv == null) {
                  if (this.theCertJ == null) {
                     throw new PKCS7Exception("CertJ object is null; cannot get Random object.");
                  }

                  JSAFE_SecureRandom var3 = this.theCertJ.getRandomObject();
                  var2.generateIV(var3);
               } else {
                  var2.setIV(this.iv, 0, this.iv.length);
               }
            }

            if (this.salt != null) {
               var2.setSalt(this.salt, 0, this.salt.length);
            }

            byte[] var7 = var2.getDERAlgorithmID();
            this.info.b(var7, 0, var7.length);
         } catch (JSAFE_Exception var4) {
            throw new PKCS7Exception("Could not set algorithm OID: ", var4);
         } catch (RandomException var5) {
            throw new PKCS7Exception("Could not set algorithm OID", var5);
         } catch (NoServiceException var6) {
            throw new PKCS7Exception("Could not set algorithm OID", var6);
         }
      }
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.info.b(var1, var2, var3);
         } else {
            throw new PKCS7Exception("Invalid content-encryption algorithm ID");
         }
      } else {
         throw new PKCS7Exception("Could not set algorithm OID: OID is null");
      }
   }

   /** @deprecated */
   public byte[] getEncryptionAlgorithmOID() {
      return this.info == null ? null : this.info.b();
   }

   /** @deprecated */
   public String getEncryptionAlgorithmName() throws PKCS7Exception {
      if (this.encryptionAlgName != null) {
         return this.encryptionAlgName;
      } else if (this.info == null) {
         return null;
      } else {
         byte[] var1 = this.info.b();
         if (var1 == null) {
            return null;
         } else {
            try {
               SequenceContainer var2 = new SequenceContainer(0, true, 0);
               EndContainer var3 = new EndContainer();
               OIDContainer var4 = new OIDContainer(0);
               EncodedContainer var5 = new EncodedContainer(130816);
               ASN1Container[] var6 = new ASN1Container[]{var2, var4, var5, var3};
               ASN1.berDecode(var1, 0, var6);
               return OIDList.getTrans(var4.data, var4.dataOffset, var4.dataLen, 7);
            } catch (ASN_Exception var7) {
               throw new PKCS7Exception("Encryption algorithm is not valid.", var7);
            }
         }
      }
   }

   /** @deprecated */
   public void setSecretKey(JSAFE_SecretKey var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Secret key is null.");
      } else {
         try {
            this.theKey = (JSAFE_SecretKey)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Unable to clone Secret Key");
         }
      }
   }

   /** @deprecated */
   public JSAFE_SecretKey getSecretKey() throws PKCS7Exception {
      if (this.theKey == null) {
         return null;
      } else {
         try {
            return (JSAFE_SecretKey)this.theKey.clone();
         } catch (CloneNotSupportedException var2) {
            throw new PKCS7Exception("Unable to clone Secret Key");
         }
      }
   }

   /** @deprecated */
   public void setSecretKeyData(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         if (this.encryptionAlgName == null) {
            this.encryptionAlgName = this.getEncryptionAlgorithmName();
         }

         if (this.encryptionAlgName == null) {
            this.keyData = new byte[var3];
            System.arraycopy(var1, var2, this.keyData, 0, var3);
         } else {
            try {
               JSAFE_SymmetricCipher var4 = h.c(this.encryptionAlgName, "Java", this.theCertJ);
               String var5 = var4.getEncryptionAlgorithm();
               this.theKey = h.f(var5, this.getDeviceOrJava(), this.theCertJ);
               this.theKey.setSecretKeyData(var1, var2, var3);
            } catch (JSAFE_Exception var6) {
               throw new PKCS7Exception("Invalid Key Data ", var6);
            }
         }

      } else {
         throw new PKCS7Exception("NULL Key Data ");
      }
   }

   /** @deprecated */
   public byte[] getSecretKeyData() {
      return this.theKey == null ? null : this.theKey.getSecretKeyData();
   }

   /** @deprecated */
   public void setPassword(char[] var1, int var2, int var3) {
      if (var1 != null && var3 > 0 && var2 >= 0 && var2 + var3 <= var1.length) {
         this.password = new char[var3];
         System.arraycopy(var1, var2, this.password, 0, var3);
      }

   }

   /** @deprecated */
   public char[] getPassword() throws PKCS7Exception {
      if (this.password != null) {
         char[] var1 = new char[this.password.length];
         System.arraycopy(this.password, 0, var1, 0, this.password.length);
         return var1;
      } else if (this.theKey == null) {
         throw new PKCS7Exception("The SecretKey and Password are not set.");
      } else {
         try {
            return this.theKey.getPassword();
         } catch (JSAFE_Exception var2) {
            throw new PKCS7Exception("The SecretKey is not set with a password.");
         }
      }
   }

   /** @deprecated */
   public void setContentInfo(ContentInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("content is null.");
      } else {
         try {
            this.content = (ContentInfo)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Unable to clone ContentInfo type");
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
         throw new PKCS7Exception("Cannot write EncryptedData: output array is null.");
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
            throw new PKCS7Exception("Unable to DER encode EncryptedData message: ", var4);
         }
      }
   }

   private int contentDEREncodeInit() throws PKCS7Exception {
      if (this.flag == 0) {
         this.setContentData();
      }

      try {
         SequenceContainer var1 = new SequenceContainer(10551296, true, 0);
         EndContainer var2 = new EndContainer();
         IntegerContainer var3 = new IntegerContainer(0, true, 0, this.version);
         int var4 = this.info.a(0);
         byte[] var5 = new byte[var4];
         var4 = this.info.d(var5, 0, 0);
         EncodedContainer var6 = new EncodedContainer(0, true, 0, var5, 0, var4);
         this.contentASN1Def = new ASN1Container[4];
         this.contentASN1Def[0] = var1;
         this.contentASN1Def[1] = var3;
         this.contentASN1Def[2] = var6;
         this.contentASN1Def[3] = var2;
         this.contentASN1Template = new ASN1Template(this.contentASN1Def);
         return this.contentASN1Template.derEncodeInit();
      } catch (ASN_Exception var7) {
         throw new PKCS7Exception(var7);
      }
   }

   private void setContentData() throws PKCS7Exception {
      if (this.version == -1) {
         this.version = 0;
      }

      if (this.content == null) {
         throw new PKCS7Exception("There is no content to encrypt.");
      } else {
         int var1 = this.content.getContentType();
         byte[] var2 = new byte[9];
         System.arraycopy(P7OID, 0, var2, 0, 8);
         var2[8] = (byte)var1;
         this.info.a(var2, 0, 9);
         byte[] var3 = null;

         try {
            int var4 = this.content.getContentInfoDERLen();
            byte[] var5 = new byte[var4];
            this.content.writeMessage(var5, 0);
            SequenceContainer var6 = new SequenceContainer(0);
            EndContainer var7 = new EndContainer();
            OIDContainer var8 = new OIDContainer(16777216);
            EncodedContainer var9 = new EncodedContainer(10616576);
            ASN1Container[] var10 = new ASN1Container[]{var6, var8, var9, var7};
            ASN1.berDecode(var5, 0, var10);
            if (var9.dataPresent) {
               int var11 = 1;
               var11 += ASN1Lengths.determineLengthLen(var9.data, var9.dataOffset + 1);
               ++var11;
               var11 += ASN1Lengths.determineLengthLen(var9.data, var9.dataOffset + var11);
               var3 = new byte[var9.dataLen - var11];
               System.arraycopy(var9.data, var9.dataOffset + var11, var3, 0, var9.dataLen - var11);
            }
         } catch (ASN_Exception var15) {
            throw new PKCS7Exception("Could not DER encode ContentInfo: ", var15);
         }

         try {
            if (var3 != null) {
               byte[] var16 = this.info.b();
               if (this.theCertJ == null) {
                  throw new PKCS7Exception("CertJ object is null; cannot get Random object.");
               } else {
                  JSAFE_SecureRandom var17 = this.theCertJ.getRandomObject();
                  String var18 = this.getDeviceOrJava();
                  JSAFE_SymmetricCipher var19 = h.c(var16, 0, var18, (CertJ)this.theCertJ);
                  int var20 = var19.getOutputBufferSize(var3.length);
                  byte[] var21 = new byte[var20];
                  if (this.theKey == null) {
                     if (this.keyData != null) {
                        String var22 = var19.getEncryptionAlgorithm();
                        this.theKey = h.f(var22, var18, this.theCertJ);
                        this.theKey.setSecretKeyData(this.keyData, 0, this.keyData.length);
                     } else {
                        if (this.password == null) {
                           throw new PKCS7Exception("Secret key is not set.");
                        }

                        this.theKey = var19.getBlankKey();
                        this.theKey.setPassword(this.password, 0, this.password.length);
                     }
                  }

                  var19.encryptInit(this.theKey, var17);
                  var20 = var19.encryptUpdate(var3, 0, var3.length, var21, 0);
                  var20 += var19.encryptFinal(var21, var20);
                  this.info.c(var21, 0, var20);
                  var19.clearSensitiveData();
               }
            }
         } catch (JSAFE_Exception var12) {
            throw new PKCS7Exception("Could not encrypt content: ", var12);
         } catch (RandomException var13) {
            throw new PKCS7Exception("Could not encrypt ContentInfo", var13);
         } catch (NoServiceException var14) {
            throw new PKCS7Exception("Could not encrypt ContentInfo", var14);
         }
      }
   }

   /** @deprecated */
   protected boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         SequenceContainer var4 = new SequenceContainer(10551296);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12288, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
         this.contentASN1Def = new ASN1Container[4];
         this.contentASN1Def[0] = var4;
         this.contentASN1Def[1] = var6;
         this.contentASN1Def[2] = var7;
         this.contentASN1Def[3] = var5;
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
      } catch (ASN_Exception var8) {
         throw new PKCS7Exception("Could not decode message: ", var8);
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

         if (this.contentASN1Def[2].isComplete()) {
            if (this.incompleteContainer == 2) {
               var1 = this.copyToOutput(this.contentASN1Def[2]);
               this.decodeEncryptedContent(var1, 0, var1.length);
            } else {
               this.decodeEncryptedContent(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen);
            }
         } else if (this.contentASN1Def[2].data != null) {
            this.incompleteContainer = 2;
            this.copyNewData(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen, this.maxBufferSize);
         }

      } catch (Exception var3) {
         throw new PKCS7Exception("Cannot set decoded values.", var3);
      }
   }

   private void decodeEncryptedContent(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 != 0) {
         try {
            this.info = new EncryptedContentInfo(var1, var2, 0, this.maxBufferSize);
            byte[] var4 = this.info.b();
            byte[] var5 = this.info.a();
            this.content = ContentInfo.getInstance(var5, 0, 9, this.theCertJ, this.theCertPathCtx);
            if (this.theCertJ == null) {
               throw new PKCS7Exception("CertJ object is null; cannot get Random object.");
            }

            JSAFE_SecureRandom var6 = this.theCertJ.getRandomObject();
            byte[] var7 = this.info.c();
            if (var7 == null) {
               return;
            }

            String var8 = this.getDeviceOrJava();
            JSAFE_SymmetricCipher var9 = h.c(var4, 0, var8, (CertJ)this.theCertJ);
            int var10 = var9.getOutputBufferSize(var7.length);
            byte[] var11 = new byte[var10];
            if (this.theKey == null) {
               if (this.keyData != null) {
                  String var12 = var9.getEncryptionAlgorithm();
                  this.theKey = h.f(var12, var8, this.theCertJ);
                  this.theKey.setSecretKeyData(this.keyData, 0, this.keyData.length);
               } else {
                  if (this.password == null) {
                     throw new PKCS7Exception("Secret key is not set.");
                  }

                  this.theKey = var9.getBlankKey();
                  this.theKey.setPassword(this.password, 0, this.password.length);
               }
            }

            var9.decryptInit(this.theKey, var6);
            var10 = var9.decryptUpdate(var7, 0, var7.length, var11, 0);
            var10 += var9.decryptFinal(var11, var10);
            var9.clearSensitiveData();
            int var25 = var10 + 1 + ASN1Lengths.getLengthLen(var10);
            byte[] var13 = new byte[var25];
            if (var5[8] == 1) {
               var13[0] = 4;
            } else {
               var13[0] = 48;
            }

            var25 = 1 + ASN1Lengths.writeLength(var13, 1, var10);
            System.arraycopy(var11, 0, var13, var25, var10);
            int var14 = var13.length + 1 + ASN1Lengths.getLengthLen(var13.length);
            byte[] var15 = new byte[var14];
            var15[0] = -96;
            var14 = 1 + ASN1Lengths.writeLength(var15, 1, var13.length);
            System.arraycopy(var13, 0, var15, var14, var13.length);
            SequenceContainer var16 = new SequenceContainer(0, true, 0);
            EndContainer var17 = new EndContainer();
            OIDContainer var18 = new OIDContainer(16777216, true, 0, var5, 0, var5.length);
            EncodedContainer var19 = new EncodedContainer(10616576, true, 0, var15, 0, var15.length);
            ASN1Container[] var20 = new ASN1Container[]{var16, var18, var19, var17};
            ASN1Template var21 = new ASN1Template(var20);
            int var22 = var21.derEncodeInit();
            byte[] var23 = new byte[var22];
            var21.derEncode(var23, 0);
            if (this.content.readInit(var23, 0, var22, this.maxBufferSize) && this.content.readFinal()) {
               return;
            }
         } catch (Exception var24) {
            throw new PKCS7Exception("Cannot decode content.", var24);
         }

         throw new PKCS7Exception("Cannot decode content.");
      } else {
         throw new PKCS7Exception("Cannot decode content: data is null.");
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
      EncryptedData var1 = (EncryptedData)super.clone();
      if (this.theKey != null) {
         var1.theKey = (JSAFE_SecretKey)this.theKey.clone();
      }

      var1.version = this.version;
      if (this.info != null) {
         var1.info = (EncryptedContentInfo)this.info.clone();
      }

      var1.encryptionAlgName = this.encryptionAlgName;
      if (this.password != null) {
         var1.password = new char[this.password.length];
         System.arraycopy(this.password, 0, var1.password, 0, this.password.length);
      }

      if (this.iv != null) {
         var1.iv = new byte[this.iv.length];
         System.arraycopy(this.iv, 0, var1.iv, 0, this.iv.length);
      }

      if (this.salt != null) {
         var1.salt = new byte[this.salt.length];
         System.arraycopy(this.salt, 0, var1.salt, 0, this.salt.length);
      }

      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof EncryptedData) {
         EncryptedData var2 = (EncryptedData)var1;
         if (this.version != var2.version) {
            return false;
         } else if (this.content != null && !this.content.equals(var2.content)) {
            return false;
         } else if (this.iv != null && var2.iv != null && !CertJUtils.byteArraysEqual(this.iv, var2.iv)) {
            return false;
         } else if (this.salt != null && var2.salt != null && !CertJUtils.byteArraysEqual(this.salt, var2.salt)) {
            return false;
         } else {
            if (this.info != null) {
               if (!this.info.equals(var2.info)) {
                  return false;
               }
            } else if (var2.info != null) {
               return false;
            }

            if (this.password != null) {
               if (var2.password == null) {
                  return false;
               }

               if (this.password.length != var2.password.length) {
                  return false;
               }

               for(int var3 = 0; var3 < this.password.length; ++var3) {
                  if (this.password[var3] != var2.password[var3]) {
                     return false;
                  }
               }
            } else if (var2.password != null) {
               return false;
            }

            if (this.theKey != null) {
               if (var2.theKey == null) {
                  return false;
               }

               if (!this.theKey.getAlgorithm().equals(var2.theKey.getAlgorithm())) {
                  return false;
               }

               byte[] var5 = this.theKey.getSecretKeyData();
               byte[] var4 = var2.theKey.getSecretKeyData();
               if (!CertJUtils.byteArraysEqual(var5, var4)) {
                  return false;
               }
            } else if (var2.theKey != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.info);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.content);
      var2 = var1 * var2 + Arrays.hashCode(this.iv);
      var2 = var1 * var2 + Arrays.hashCode(this.password);
      var2 = var1 * var2 + Arrays.hashCode(this.salt);
      var2 = var1 * var2 + this.version;
      if (this.theKey != null) {
         byte[] var3 = this.theKey.getSecretKeyData();
         var2 = var1 * var2 + Arrays.hashCode(var3);
         var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.theKey.getAlgorithm());
      }

      return var2;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      super.clearSensitiveData();
      if (this.theKey != null) {
         this.theKey.clearSensitiveData();
      }

      this.version = -1;
      this.info = new EncryptedContentInfo();
   }
}

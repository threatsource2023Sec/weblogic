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
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_AsymmetricCipher;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.util.Vector;

/** @deprecated */
public class EnvelopedData extends ContentInfo {
   /** @deprecated */
   protected int version = -1;
   /** @deprecated */
   protected Vector recipients = new Vector();
   /** @deprecated */
   protected EncryptedContentInfo info = new EncryptedContentInfo();
   private JSAFE_SecretKey theKey;
   private int theKeyLen;
   private String algorithmName;

   /** @deprecated */
   public EnvelopedData(CertJ var1, CertPathCtx var2) {
      this.contentType = 3;
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
   public void setEncryptionAlgorithm(String var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Algorithm name is null.");
      } else {
         this.algorithmName = var1;

         try {
            if (this.theCertJ == null) {
               throw new PKCS7Exception("CertJ object is NULL; cannot get Random object.");
            } else {
               JSAFE_SymmetricCipher var3 = h.c(var1, this.getDeviceOrJava(), this.theCertJ);
               if (var3.getFeedbackMode() != null && !var3.getFeedbackMode().equals("ECB")) {
                  JSAFE_SecureRandom var4 = this.theCertJ.getRandomObject();
                  var3.generateIV(var4);
               }

               byte[] var8 = var3.getDERAlgorithmID();
               if (var8 != null) {
                  this.info.b(var8, 0, var8.length);
               }

               this.theKeyLen = var2;
            }
         } catch (JSAFE_Exception var5) {
            throw new PKCS7Exception("Could not get algorithm OID: ", var5);
         } catch (RandomException var6) {
            throw new PKCS7Exception("Could not get algorithm OID", var6);
         } catch (NoServiceException var7) {
            throw new PKCS7Exception("Could not get algorithm OID", var7);
         }
      }
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(byte[] var1, int var2, int var3, int var4) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.info.b(var1, var2, var3);
            this.theKeyLen = var4;
         } else {
            throw new PKCS7Exception("invalid data");
         }
      } else {
         throw new PKCS7Exception("Could not set algorithm OID: OID is null");
      }
   }

   /** @deprecated */
   public String getEncryptionAlgorithmName() throws PKCS7Exception {
      if (this.algorithmName != null) {
         return this.algorithmName;
      } else if (this.info == null) {
         return null;
      } else {
         byte[] var1 = this.info.b();
         if (var1 == null) {
            throw new PKCS7Exception("Encryption algorithm is not set.");
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
   public byte[] getEncryptionAlgorithmOID() {
      return this.info == null ? null : this.info.b();
   }

   /** @deprecated */
   public void setContentInfo(ContentInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Null content");
      } else {
         try {
            this.content = (ContentInfo)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Unable to clone ContentInfo type");
         }
      }
   }

   /** @deprecated */
   public void addRecipientInfo(RecipientInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Null RecipientInfo");
      } else {
         try {
            this.recipients.addElement((RecipientInfo)var1.clone());
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Cannot add this Recipient Information.");
         }
      }
   }

   /** @deprecated */
   public Vector getRecipientInfos() {
      return new Vector(this.recipients);
   }

   /** @deprecated */
   protected int getContentDERLen() throws PKCS7Exception {
      return this.contentDEREncodeInit();
   }

   /** @deprecated */
   protected int writeContent(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot write EnvelopedData: output array is null.");
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
            throw new PKCS7Exception("Unable to DER encode EnvelopedData message: ", var4);
         }
      }
   }

   private int contentDEREncodeInit() throws PKCS7Exception {
      try {
         if (this.flag == 0) {
            this.setContentData();
         }

         if (this.recipients.isEmpty()) {
            throw new PKCS7Exception("RecipientInfos are not set.");
         } else {
            OfContainer var2 = new OfContainer(0, 12544, new EncodedContainer(12288));
            Vector var3 = new Vector();
            var3.addElement(var2);

            int var6;
            byte[] var7;
            for(int var4 = 0; var4 < this.recipients.size(); ++var4) {
               try {
                  RecipientInfo var5 = (RecipientInfo)this.recipients.elementAt(var4);
                  var6 = var5.getDERLen(0);
                  var7 = new byte[var6];
                  var6 = var5.getDEREncoding(var7, 0, 0);
                  EncodedContainer var1 = new EncodedContainer(0, true, 0, var7, 0, var6);
                  var2.addContainer(var1);
               } catch (ASN_Exception var15) {
                  throw new PKCS7Exception("Unable to encode RecipientInfos: ", var15);
               }
            }

            ASN1Container[] var17 = new ASN1Container[var3.size()];
            var3.copyInto(var17);
            ASN1Template var18 = new ASN1Template(var17);
            var6 = var18.derEncodeInit();
            var7 = new byte[var6];
            var6 = var18.derEncode(var7, 0);
            EncodedContainer var8 = new EncodedContainer(0, true, 0, var7, 0, var6);
            int var9 = this.info.a(0);
            byte[] var10 = new byte[var9];
            var9 = this.info.d(var10, 0, 0);
            EncodedContainer var11 = new EncodedContainer(0, true, 0, var10, 0, var9);
            SequenceContainer var12 = new SequenceContainer(10551296, true, 0);
            EndContainer var13 = new EndContainer();
            IntegerContainer var14 = new IntegerContainer(0, true, 0, this.version);
            this.contentASN1Def = new ASN1Container[5];
            this.contentASN1Def[0] = var12;
            this.contentASN1Def[1] = var14;
            this.contentASN1Def[2] = var8;
            this.contentASN1Def[3] = var11;
            this.contentASN1Def[4] = var13;
            this.contentASN1Template = new ASN1Template(this.contentASN1Def);
            return this.contentASN1Template.derEncodeInit();
         }
      } catch (ASN_Exception var16) {
         throw new PKCS7Exception("Could not DER encode EnvelopedData: ", var16);
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
            if (this.contentEncoding == null) {
               int var4 = this.content.getContentInfoDERLen();
               this.contentEncoding = new byte[var4];
               this.content.writeMessage(this.contentEncoding, 0);
            }

            SequenceContainer var24 = new SequenceContainer(0);
            EndContainer var5 = new EndContainer();
            OIDContainer var6 = new OIDContainer(16777216);
            EncodedContainer var7 = new EncodedContainer(10616576);
            ASN1Container[] var8 = new ASN1Container[]{var24, var6, var7, var5};
            ASN1.berDecode(this.contentEncoding, 0, var8);
            if (var7.dataPresent) {
               int var9 = 1;
               var9 += ASN1Lengths.determineLengthLen(var7.data, var7.dataOffset + 1);
               ++var9;
               var9 += ASN1Lengths.determineLengthLen(var7.data, var7.dataOffset + var9);
               var3 = new byte[var7.dataLen - var9];
               System.arraycopy(var7.data, var7.dataOffset + var9, var3, 0, var7.dataLen - var9);
            }
         } catch (ASN_Exception var23) {
            throw new PKCS7Exception("Could not DER encode ContentInfo: ", var23);
         }

         int var34;
         try {
            byte[] var25 = this.info.b();
            if (var25 == null) {
               throw new PKCS7Exception("Encryption Algorithm is not set.");
            }

            if (this.theCertJ == null) {
               throw new PKCS7Exception("CertJ object is NULL; cannot get Random object.");
            }

            JSAFE_SecureRandom var27 = this.theCertJ.getRandomObject();
            JSAFE_SymmetricCipher var29 = h.c(var25, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
            String var31 = var29.getEncryptionAlgorithm();
            this.theKey = h.f(var31, this.getDeviceOrJava(), this.theCertJ);
            if (this.theKeyLen != 0) {
               int[] var33 = new int[]{this.theKeyLen};
               this.theKey.generateInit(var33, var27);
            } else {
               this.theKey.generateInit((int[])null, var27);
            }

            this.theKey.generate();
            if (var3 != null) {
               var34 = var29.getOutputBufferSize(var3.length);
               byte[] var35 = new byte[var34];
               var29.encryptInit(this.theKey);
               var34 = var29.encryptUpdate(var3, 0, var3.length, var35, 0);
               var34 += var29.encryptFinal(var35, var34);
               this.info.c(var35, 0, var34);
            }

            var29.clearSensitiveData();
         } catch (JSAFE_Exception var20) {
            throw new PKCS7Exception("Could not encrypt content: ", var20);
         } catch (RandomException var21) {
            throw new PKCS7Exception("Could not encrypt content", var21);
         } catch (NoServiceException var22) {
            throw new PKCS7Exception("Could not encrypt content", var22);
         }

         try {
            if (this.theCertPathCtx == null) {
               throw new PKCS7Exception("CertPathCtx object is null, cannot get Database.");
            } else {
               DatabaseService var26 = this.theCertPathCtx.getDatabase();
               Vector var28 = new Vector();
               if (this.theCertJ == null) {
                  throw new PKCS7Exception("CertJ object is NULL; cannot get Random object.");
               } else {
                  for(int var30 = 0; var30 < this.recipients.size(); ++var30) {
                     RecipientInfo var32 = (RecipientInfo)this.recipients.elementAt(var30);
                     var34 = var26.selectCertificateByIssuerAndSerialNumber(var32.getIssuerName(), var32.getSerialNumber(), var28);
                     if (var34 == 0) {
                        throw new PKCS7Exception("Cannot find this Recipient cert.");
                     }

                     X509Certificate var36 = (X509Certificate)var28.elementAt(0);
                     JSAFE_PublicKey var10 = var36.getSubjectPublicKey(this.getDeviceOrJava());
                     JSAFE_SecureRandom var11 = this.theCertJ.getRandomObject();
                     byte[] var12 = var32.getEncryptionAlgorithmOID();
                     JSAFE_AsymmetricCipher var13 = h.e(var12, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
                     var13.encryptInit(var10, var11, this.theCertJ.getPKCS11Sessions());
                     byte[] var14 = var13.wrapSecretKey(this.theKey, false);
                     var32.setEncryptedKey(var14, 0, var14.length);
                     var10.clearSensitiveData();
                     var13.clearSensitiveData();
                     var28.removeAllElements();
                  }

               }
            }
         } catch (JSAFE_Exception var15) {
            throw new PKCS7Exception("Could not encrypt ContentInfo", var15);
         } catch (RandomException var16) {
            throw new PKCS7Exception("Could not encrypt ContentInfo", var16);
         } catch (NoServiceException var17) {
            throw new PKCS7Exception("Could not encrypt ContentInfo", var17);
         } catch (DatabaseException var18) {
            throw new PKCS7Exception("Cannot get Cert from DB.", var18);
         } catch (CertificateException var19) {
            throw new PKCS7Exception("Cannot get IssuerName and SerialNumber from cert.", var19);
         }
      }
   }

   /** @deprecated */
   protected boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         SequenceContainer var4 = new SequenceContainer(10551296);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12544);
         EncodedContainer var8 = new EncodedContainer(12288, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
         this.contentASN1Def = new ASN1Container[5];
         this.contentASN1Def[0] = var4;
         this.contentASN1Def[1] = var6;
         this.contentASN1Def[2] = var7;
         this.contentASN1Def[3] = var8;
         this.contentASN1Def[4] = var5;
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
      } catch (ASN_Exception var9) {
         throw new PKCS7Exception("Could not decode message: ", var9);
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

         if (this.recipients.isEmpty()) {
            if (!this.contentASN1Def[2].isComplete()) {
               if (this.contentASN1Def[2].data == null) {
                  return;
               }

               this.incompleteContainer = 2;
               this.copyNewData(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 2) {
               var1 = this.copyToOutput(this.contentASN1Def[2]);
               this.decodeRecipientInfos(var1, 0, var1.length);
            } else {
               this.decodeRecipientInfos(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen);
            }
         }

         if (this.contentASN1Def[3].isComplete()) {
            if (this.incompleteContainer == 3) {
               var1 = this.copyToOutput(this.contentASN1Def[3]);
               this.info = new EncryptedContentInfo(var1, 0, 0, this.maxBufferSize);
            } else {
               this.info = new EncryptedContentInfo(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, 0, this.maxBufferSize);
            }

            this.decodeEncryptedKey();
            this.decodeEncryptedContent();
         } else if (this.contentASN1Def[3].data != null) {
            this.incompleteContainer = 3;
            this.copyNewData(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, this.contentASN1Def[3].dataLen, this.maxBufferSize);
         }
      } catch (Exception var3) {
         throw new PKCS7Exception("Cannot set decoded values.", var3);
      }
   }

   private void decodeRecipientInfos(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 != 0) {
         try {
            OfContainer var4 = new OfContainer(0, 12544, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               RecipientInfo var9 = new RecipientInfo(CertJInternalHelper.context(this.theCertJ), var8.data, var8.dataOffset, 0);
               this.addRecipientInfo(var9);
            }

         } catch (Exception var10) {
            throw new PKCS7Exception("Cannot decode recipientInfo.", var10);
         }
      } else {
         throw new PKCS7Exception("Cannot decode RecipientInfo: data is null.");
      }
   }

   private void decodeEncryptedContent() throws PKCS7Exception {
      try {
         byte[] var1 = this.info.a();
         this.content = ContentInfo.getInstance(var1, 0, 9, this.theCertJ, this.theCertPathCtx);
         byte[] var2 = this.info.b();
         if (var2 == null) {
            throw new PKCS7Exception("Encryption Algorithm is not set.");
         }

         byte[] var3 = this.info.c();
         if (var3 == null) {
            return;
         }

         JSAFE_SymmetricCipher var4 = h.c(var2, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
         int var5 = var4.getOutputBufferSize(var3.length);
         byte[] var6 = new byte[var5];
         var4.decryptInit(this.theKey);
         var5 = var4.decryptUpdate(var3, 0, var3.length, var6, 0);
         var5 += var4.decryptFinal(var6, var5);
         var4.clearSensitiveData();
         this.theKey.clearSensitiveData();
         int var7 = var5 + 1 + ASN1Lengths.getLengthLen(var5);
         byte[] var8 = new byte[var7];
         if (var1[8] == 1) {
            var8[0] = 4;
         } else {
            var8[0] = 48;
         }

         var7 = 1 + ASN1Lengths.writeLength(var8, 1, var5);
         System.arraycopy(var6, 0, var8, var7, var5);
         int var9 = var8.length + 1 + ASN1Lengths.getLengthLen(var8.length);
         byte[] var10 = new byte[var9];
         var10[0] = -96;
         var9 = 1 + ASN1Lengths.writeLength(var10, 1, var8.length);
         System.arraycopy(var8, 0, var10, var9, var8.length);
         SequenceContainer var11 = new SequenceContainer(0, true, 0);
         EndContainer var12 = new EndContainer();
         OIDContainer var13 = new OIDContainer(16777216, true, 0, var1, 0, var1.length);
         EncodedContainer var14 = new EncodedContainer(10616576, true, 0, var10, 0, var10.length);
         ASN1Container[] var15 = new ASN1Container[]{var11, var13, var14, var12};
         ASN1Template var16 = new ASN1Template(var15);
         int var17 = var16.derEncodeInit();
         byte[] var18 = new byte[var17];
         var16.derEncode(var18, 0);
         if (this.content.readInit(var18, 0, var17, 0, this.maxBufferSize) && this.content.readFinal()) {
            return;
         }
      } catch (Exception var19) {
         throw new PKCS7Exception("Cannot decode content.", var19);
      }

      throw new PKCS7Exception("Cannot decode content.");
   }

   private void decodeEncryptedKey() throws PKCS7Exception {
      if (this.theCertPathCtx == null) {
         throw new PKCS7Exception("CertPathCtx object is null, cannot get Database.");
      } else if (this.theCertJ == null) {
         throw new PKCS7Exception("CertJ object is NULL.");
      } else {
         DatabaseService var1 = this.theCertPathCtx.getDatabase();
         Vector var3 = new Vector();
         JSAFE_PrivateKey var4 = null;

         try {
            int var2;
            for(var2 = 0; var2 < this.recipients.size(); ++var2) {
               RecipientInfo var5 = (RecipientInfo)this.recipients.elementAt(var2);
               int var6 = var1.selectCertificateByIssuerAndSerialNumber(var5.getIssuerName(), var5.getSerialNumber(), var3);
               if (var6 != 0) {
                  for(int var7 = 0; var7 < var6; ++var7) {
                     X509Certificate var8 = (X509Certificate)var3.elementAt(var7);
                     var4 = var1.selectPrivateKeyByCertificate(var8);
                     if (var4 != null) {
                        break;
                     }
                  }

                  if (var4 != null) {
                     byte[] var18 = var5.getEncryptedKey();
                     if (var18 == null) {
                        throw new PKCS7Exception("Recipient's encrypted Secret key is not set.");
                     }

                     byte[] var19 = this.info.b();
                     if (var19 == null) {
                        throw new PKCS7Exception("Encryption Algorithm is not set.");
                     }

                     JSAFE_SymmetricCipher var9 = h.c(var19, 0, "Java", (CertJ)this.theCertJ);
                     String var10 = var9.getEncryptionAlgorithm();
                     byte[] var11 = var5.getEncryptionAlgorithmOID();
                     if (var11 == null) {
                        throw new PKCS7Exception("Recipient's Encryption Algorithm is not set.");
                     }

                     JSAFE_AsymmetricCipher var12 = h.e(var11, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
                     var12.decryptInit(var4, this.theCertJ.getPKCS11Sessions());
                     Object var13 = null;
                     byte[] var20 = new byte[var12.getOutputBufferSize(var18.length)];
                     int var14 = var12.decryptUpdate(var18, 0, var18.length, var20, 0);
                     var14 += var12.decryptFinal(var20, var14);
                     this.theKey = JSAFE_SecretKey.getInstance(var10, this.getDeviceOrJava());
                     this.theKey.setSecretKeyData(var20, 0, var14);
                     var12.clearSensitiveData();
                     var4.clearSensitiveData();
                     var3.removeAllElements();
                     break;
                  }

                  var3.removeAllElements();
               }
            }

            if (var2 == this.recipients.size()) {
               throw new PKCS7Exception("Private Key is not set");
            }
         } catch (NoServiceException var15) {
            throw new PKCS7Exception("Cannot get Cert from DB.", var15);
         } catch (DatabaseException var16) {
            throw new PKCS7Exception("Cannot get Cert from DB.", var16);
         } catch (JSAFE_Exception var17) {
            throw new PKCS7Exception("Could not decode encrypted key", var17);
         }
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
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof EnvelopedData) {
         EnvelopedData var2 = (EnvelopedData)var1;
         if (var2.version != this.version) {
            return false;
         } else if (this.content != null && !this.content.equals(var2.content)) {
            return false;
         } else {
            int var3 = this.recipients.size();
            int var4 = var2.recipients.size();
            if (var3 != var4) {
               return false;
            } else {
               for(int var5 = 0; var5 < var3; ++var5) {
                  if (this.recipients.elementAt(var5) != null) {
                     int var6;
                     for(var6 = 0; var6 < var4 && !((RecipientInfo)this.recipients.elementAt(var5)).equals(var2.recipients.elementAt(var6)); ++var6) {
                     }

                     if (var6 == var4) {
                        return false;
                     }
                  }
               }

               if (this.info != null) {
                  if (!this.info.equals(var2.info)) {
                     return false;
                  }
               } else if (var2.info != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.content);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.info);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.recipients);
      var2 = var1 * var2 + this.version;
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      EnvelopedData var1 = (EnvelopedData)super.clone();
      var1.version = this.version;
      var1.recipients = new Vector(this.recipients);
      var1.info = (EncryptedContentInfo)this.info.clone();
      return var1;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      super.clearSensitiveData();
      this.recipients.clear();
      this.version = -1;
      this.info = new EncryptedContentInfo();
   }
}

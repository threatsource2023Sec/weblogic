package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.pkcs7.EnvelopedData;
import com.rsa.certj.pkcs7.PKCS7Exception;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;

/** @deprecated */
public class EncryptedKey implements Serializable, Cloneable {
   /** @deprecated */
   public static final int ENCRYPTED_VALUE = 0;
   /** @deprecated */
   public static final int ENVELOPED_DATA = 1;
   private CertPathCtx theCertPathCtx;
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;
   private int type = -1;
   private EncryptedValue encryptValue;
   private EnvelopedData envelopData;
   private int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public EncryptedKey(CertJ var1, CertPathCtx var2, JSAFE_PublicKey var3, JSAFE_PrivateKey var4) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified CertJ value is NULL.");
      } else {
         this.theCertJ = var1;
         this.theCertPathCtx = var2;
         if (var3 != null) {
            this.pubKey = var3;
         }

         if (var4 != null) {
            this.privKey = var4;
         }

      }
   }

   /** @deprecated */
   public void decodeEncryptedKey(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Encoding EncryptedKey is null.");
      } else {
         this.special = var3;

         try {
            ChoiceContainer var4 = new ChoiceContainer(var3);
            EncodedContainer var5 = new EncodedContainer(12288);
            EncodedContainer var6 = new EncodedContainer(8400896);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var5.dataPresent) {
               this.encryptValue = new EncryptedValue(this.theCertJ, this.pubKey, this.privKey);
               this.encryptValue.decodeEncryptedValue(var5.data, var5.dataOffset, 0);
               this.type = 0;
            } else if (var6.dataPresent) {
               this.type = 1;
               this.envelopData = new EnvelopedData(this.theCertJ, this.theCertPathCtx);

               try {
                  boolean var9 = this.envelopData.readInit(var6.data, var6.dataOffset, var6.dataLen, 8400896);
                  if (!var9) {
                     throw new CRMFException("Not enough data in EnvelopedData.");
                  }

                  var9 = this.envelopData.readFinal();
                  if (!var9) {
                     throw new CRMFException("Invalid data in EnvelopedData.");
                  }
               } catch (PKCS7Exception var10) {
                  throw new CRMFException(var10);
               }
            }

         } catch (ASN_Exception var11) {
            throw new CRMFException("Cannot decode the BER of the EncryptedKey.", var11);
         }
      }
   }

   /** @deprecated */
   public void setType(int var1) throws CRMFException {
      if (var1 != 0 && var1 != 1) {
         throw new CRMFException("Invalid EncryptedKey type.");
      } else {
         this.type = var1;
      }
   }

   /** @deprecated */
   public int getType() {
      return this.type;
   }

   /** @deprecated */
   public void setEncryptedValue(EncryptedValue var1) throws CRMFException {
      if (this.type == 1) {
         throw new CRMFException("Wrong type: this EncryptedKey object is of ENVELOPED_DATA type");
      } else if (var1 == null) {
         throw new CRMFException("specified EncryptedValue object is null.");
      } else {
         try {
            this.encryptValue = (EncryptedValue)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Encrypted Value.", var3);
         }
      }
   }

   /** @deprecated */
   public EncryptedValue getEncryptedValue() throws CRMFException {
      if (this.type == 1) {
         throw new CRMFException("Wrong type: this EncryptedKey object is of ENVELOPED_DATA type");
      } else if (this.encryptValue == null) {
         return null;
      } else {
         try {
            return (EncryptedValue)this.encryptValue.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Invalid Encrypted Value.", var2);
         }
      }
   }

   /** @deprecated */
   public void setEnvelopedData(EnvelopedData var1) throws CRMFException {
      if (this.type == 0) {
         throw new CRMFException("Wrong type: this EncryptedKey object is of ENCRYPTED_VALUE type");
      } else if (var1 == null) {
         throw new CRMFException("Specified EnvelopedData object is NULL.");
      } else {
         try {
            this.envelopData = (EnvelopedData)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Enveloped Data.", var3);
         }
      }
   }

   /** @deprecated */
   public EnvelopedData getEnvelopedData() throws CRMFException {
      if (this.type == 0) {
         throw new CRMFException("Wrong type: this EncryptedKey object is of ENCRYPTED_VALUE type");
      } else if (this.envelopData == null) {
         return null;
      } else {
         try {
            return (EnvelopedData)this.envelopData.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Invalid Enveloped Data.", var2);
         }
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("EncryptedKey Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      this.special = var1;
      return this.encodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified array is null in EncryptedKey.");
      } else {
         this.special = var3;

         try {
            if (this.asn1Template == null) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode EncryptedKey.", var6);
         }
      }
   }

   private int encodeInit() throws CRMFException {
      try {
         boolean var3 = false;
         boolean var4 = false;
         byte[] var1;
         int var2;
         if (this.encryptValue != null) {
            var2 = this.encryptValue.getDERLen(0);
            var1 = new byte[var2];
            var2 = this.encryptValue.getDEREncoding(var1, 0, 0);
            var3 = true;
         } else {
            if (this.envelopData == null) {
               throw new CRMFException("EncryptedKey Data is not set.");
            }

            var2 = this.envelopData.getContentInfoDERLen(8400896);
            var1 = new byte[var2];
            var2 = this.envelopData.writeMessage(var1, 0, 8388608);
            var4 = true;
         }

         ChoiceContainer var5 = new ChoiceContainer(this.special, 0);
         EndContainer var6 = new EndContainer();
         EncodedContainer var7 = new EncodedContainer(8400896, var4, 0, var1, 0, var2);
         EncodedContainer var8 = new EncodedContainer(12288, var3, 0, var1, 0, var2);
         ASN1Container[] var9 = new ASN1Container[]{var5, var8, var7, var6};
         this.asn1Template = new ASN1Template(var9);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var10) {
         throw new CRMFException(var10);
      } catch (PKCS7Exception var11) {
         throw new CRMFException(var11);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         EncryptedKey var1 = new EncryptedKey(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
         if (this.envelopData != null) {
            var1.envelopData = (EnvelopedData)this.envelopData.clone();
         }

         if (this.encryptValue != null) {
            var1.encryptValue = (EncryptedValue)this.encryptValue.clone();
         }

         var1.type = this.type;
         var1.special = this.special;
         if (this.asn1Template != null) {
            var1.encodeInit();
         }

         return var1;
      } catch (CRMFException var2) {
         throw new CloneNotSupportedException(var2.getMessage());
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof EncryptedKey) {
         EncryptedKey var2 = (EncryptedKey)var1;
         if (this.type != var2.type) {
            return false;
         } else {
            if (this.envelopData != null) {
               if (!this.envelopData.equals(var2.envelopData)) {
                  return false;
               }
            } else if (var2.envelopData != null) {
               return false;
            }

            if (this.encryptValue != null) {
               if (!this.encryptValue.equals(var2.encryptValue)) {
                  return false;
               }
            } else if (var2.encryptValue != null) {
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
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.encryptValue);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.envelopData);
      var2 = var1 * var2 + this.type;
      return var2;
   }
}

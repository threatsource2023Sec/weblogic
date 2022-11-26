package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.AlgorithmID;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_AsymmetricCipher;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class EncryptedValue implements Serializable, Cloneable {
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;
   private byte[] intendedAlgId;
   private byte[] symmAlgId;
   private byte[] keyAlgId;
   private byte[] hintValue;
   private byte[] encryptedKeyValue;
   private byte[] symmKeyValue;
   private JSAFE_SecretKey theKey;
   private JSAFE_PrivateKey thePrivateKey;
   private ASN1Template asn1Template;
   private int special;
   private byte[] theDecryptedValue;

   /** @deprecated */
   public EncryptedValue(CertJ var1, JSAFE_PublicKey var2, JSAFE_PrivateKey var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("The specified CertJ value is NULL in EncryptedValue.");
      } else {
         if (var2 != null) {
            this.pubKey = var2;
         }

         if (var3 != null) {
            this.privKey = var3;
         }

         this.theCertJ = var1;
      }
   }

   /** @deprecated */
   public void decodeEncryptedValue(byte[] var1, int var2, int var3) throws CRMFException {
      this.special = var3;
      if (var1 == null) {
         throw new CRMFException("EncryptedValue Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            EncodedContainer var6 = new EncodedContainer(8466432);
            EncodedContainer var7 = new EncodedContainer(8466433);
            BitStringContainer var8 = new BitStringContainer(8454146);
            EncodedContainer var9 = new EncodedContainer(8466435);
            OctetStringContainer var10 = new OctetStringContainer(8466436);
            BitStringContainer var11 = new BitStringContainer(0);
            ASN1Container[] var12 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var5};
            ASN1.berDecode(var1, var2, var12);
            if (var6.dataPresent && var6.data != null && var6.dataLen != 0) {
               this.intendedAlgId = new byte[var6.dataLen];
               this.intendedAlgId[0] = 48;
               System.arraycopy(var6.data, var6.dataOffset + 1, this.intendedAlgId, 1, var6.dataLen - 1);
            }

            if (var7.dataPresent && var7.data != null && var7.dataLen != 0) {
               this.symmAlgId = new byte[var7.dataLen];
               this.symmAlgId[0] = 48;
               System.arraycopy(var7.data, var7.dataOffset + 1, this.symmAlgId, 1, var7.dataLen - 1);
            }

            if (var9.dataPresent && var9.data != null && var9.dataLen != 0) {
               this.keyAlgId = new byte[var9.dataLen];
               this.keyAlgId[0] = 48;
               System.arraycopy(var9.data, var9.dataOffset + 1, this.keyAlgId, 1, var9.dataLen - 1);
            }

            if (var11.data != null && var11.dataLen != 0) {
               this.encryptedKeyValue = new byte[var11.dataLen];
               System.arraycopy(var11.data, var11.dataOffset, this.encryptedKeyValue, 0, var11.dataLen);
               if (var8.data != null && var8.dataLen != 0) {
                  this.symmKeyValue = new byte[var8.dataLen];
                  System.arraycopy(var8.data, var8.dataOffset, this.symmKeyValue, 0, var8.dataLen);
               }

               if (var10.data != null && var10.dataLen != 0) {
                  this.hintValue = new byte[var10.dataLen];
                  System.arraycopy(var10.data, var10.dataOffset, this.hintValue, 0, var10.dataLen);
               }

               if (this.symmAlgId != null && this.keyAlgId != null && this.encryptedKeyValue != null && this.symmKeyValue != null) {
                  this.decryptPrivateKey();
               }

            } else {
               throw new CRMFException("Encrypted Private Key value is missing.");
            }
         } catch (ASN_Exception var13) {
            throw new CRMFException("Cannot decode the BER of the Encrypted Value.");
         }
      }
   }

   /** @deprecated */
   public void decryptPrivateKey() throws CRMFException {
      try {
         if (this.privKey == null) {
            throw new CRMFException("Private Key is not set in EncryptedValue");
         } else if (this.symmAlgId != null && this.symmKeyValue != null && this.encryptedKeyValue != null) {
            String var1 = AlgorithmID.berDecodeAlgID(this.symmAlgId, 0, 7, (EncodedContainer)null);
            if (var1 == null) {
               throw new CRMFException("Invalid Symmetric Algorithm OID.");
            } else {
               int var2 = var1.indexOf("/");
               String var3 = var1.substring(0, var2);
               String var4 = this.theCertJ.getDevice();
               JSAFE_AsymmetricCipher var5 = h.e(this.keyAlgId, 0, var4, (CertJ)this.theCertJ);
               var5.decryptInit(this.privKey, this.theCertJ.getPKCS11Sessions());
               this.theKey = var5.unwrapSecretKey(this.symmKeyValue, 0, this.symmKeyValue.length, false, var3);
               var5.clearSensitiveData();
               JSAFE_SymmetricCipher var6 = h.c(this.symmAlgId, 0, var4, (CertJ)this.theCertJ);
               JSAFE_SecureRandom var7 = this.theCertJ.getRandomObject();
               var6.decryptInit(this.theKey, var7);

               try {
                  this.thePrivateKey = var6.unwrapPrivateKey(this.encryptedKeyValue, 0, this.encryptedKeyValue.length, false);
               } catch (JSAFE_Exception var21) {
                  try {
                     var6.decryptReInit();
                     int var9 = var6.getOutputBufferSize(this.encryptedKeyValue.length);
                     this.theDecryptedValue = new byte[var9];
                     int var10 = var6.decryptUpdate(this.encryptedKeyValue, 0, this.encryptedKeyValue.length, this.theDecryptedValue, 0);
                     var6.decryptFinal(this.theDecryptedValue, var10);
                  } catch (JSAFE_Exception var19) {
                     throw new CRMFException("Could not decode encrypted value", var19);
                  } finally {
                     var6.clearSensitiveData();
                  }
               }

            }
         } else {
            throw new CRMFException("Values are not set.");
         }
      } catch (NoServiceException var22) {
         throw new CRMFException("Cannot get random object from CertJ.", var22);
      } catch (RandomException var23) {
         throw new CRMFException("Cannot get random object from CertJ.", var23);
      } catch (JSAFE_Exception var24) {
         throw new CRMFException("Could not decode encrypted key", var24);
      } catch (ASN_Exception var25) {
         throw new CRMFException(var25);
      }
   }

   /** @deprecated */
   public void setPrivateKey(JSAFE_PrivateKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("The private key is NULL.");
      } else {
         try {
            this.thePrivateKey = (JSAFE_PrivateKey)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Private Key.", var3);
         }
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey getPrivateKey() throws CRMFException {
      if (this.thePrivateKey == null) {
         return null;
      } else {
         try {
            return (JSAFE_PrivateKey)this.thePrivateKey.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Invalid Private Key.", var2);
         }
      }
   }

   /** @deprecated */
   public byte[] getDecryptedValue() {
      if (this.theDecryptedValue == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.theDecryptedValue.length];
         System.arraycopy(this.theDecryptedValue, 0, var1, 0, this.theDecryptedValue.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setSymmetricKey(JSAFE_SecretKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("The secret key is NULL.");
      } else {
         try {
            this.theKey = (JSAFE_SecretKey)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Symmetric Key.", var3);
         }
      }
   }

   /** @deprecated */
   public JSAFE_SecretKey getSecretKey() throws CRMFException {
      if (this.theKey == null) {
         return null;
      } else {
         try {
            return (JSAFE_SecretKey)this.theKey.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Invalid Symmetric Key.", var2);
         }
      }
   }

   /** @deprecated */
   public void setEncryptedSecretKey(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.symmKeyValue = new byte[var3];
         System.arraycopy(var1, var2, this.symmKeyValue, 0, var3);
      } else {
         throw new CRMFException("The specified EncryptedSecretKey values are null.");
      }
   }

   /** @deprecated */
   public byte[] getEncryptedSecretKey() {
      if (this.symmKeyValue == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.symmKeyValue.length];
         System.arraycopy(this.symmKeyValue, 0, var1, 0, this.symmKeyValue.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setEncryptedPrivateKey(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.encryptedKeyValue = new byte[var3];
         System.arraycopy(var1, var2, this.encryptedKeyValue, 0, var3);
      } else {
         throw new CRMFException("The specified EncryptedPrivateKey values are null.");
      }
   }

   /** @deprecated */
   public byte[] getEncryptedPrivateKey() {
      if (this.encryptedKeyValue == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.encryptedKeyValue.length];
         System.arraycopy(this.encryptedKeyValue, 0, var1, 0, this.encryptedKeyValue.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setValueHint(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.hintValue = new byte[var3];
         System.arraycopy(var1, var2, this.hintValue, 0, var3);
      } else {
         throw new CRMFException("Value hint is null.");
      }
   }

   /** @deprecated */
   public byte[] getValueHint() {
      if (this.hintValue == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.hintValue.length];
         System.arraycopy(this.hintValue, 0, var1, 0, this.hintValue.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setKeyEncryptionAlgorithm(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.keyAlgId = new byte[var3];
         System.arraycopy(var1, var2, this.keyAlgId, 0, var3);
      } else {
         throw new CRMFException("The specified KeyEncryptionAlgorithm OID is null.");
      }
   }

   /** @deprecated */
   public byte[] getKeyEncryptionAlgorithm() {
      if (this.keyAlgId == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.keyAlgId.length];
         System.arraycopy(this.keyAlgId, 0, var1, 0, this.keyAlgId.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setValueEncryptionAlgorithm(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.symmAlgId = new byte[var3];
         System.arraycopy(var1, var2, this.symmAlgId, 0, var3);
      } else {
         throw new CRMFException("The specified ValueEncryptionAlgorithm OID is null.");
      }
   }

   /** @deprecated */
   public byte[] getValueEncryptionAlgorithm() {
      if (this.symmAlgId == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.symmAlgId.length];
         System.arraycopy(this.symmAlgId, 0, var1, 0, this.symmAlgId.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setIntendedAlgorithm(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.intendedAlgId = new byte[var3];
         System.arraycopy(var1, var2, this.intendedAlgId, 0, var3);
      } else {
         throw new CRMFException("The specified IntendedAlgorithm OID is null.");
      }
   }

   /** @deprecated */
   public byte[] getIntendedAlgorithm() {
      if (this.intendedAlgId == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.intendedAlgId.length];
         System.arraycopy(this.intendedAlgId, 0, var1, 0, this.intendedAlgId.length);
         return var1;
      }
   }

   /** @deprecated */
   public byte[] encryptPrivateKey() throws CRMFException {
      if (this.theKey != null && this.thePrivateKey != null && this.symmAlgId != null) {
         try {
            JSAFE_SymmetricCipher var1 = h.c(this.symmAlgId, 0, this.theCertJ.getDevice(), (CertJ)this.theCertJ);
            var1.encryptInit(this.theKey);
            byte[] var2 = var1.wrapPrivateKey(this.thePrivateKey, false);
            var1.clearSensitiveData();
            return var2;
         } catch (JSAFE_Exception var3) {
            throw new CRMFException("Cannot encrypt the PrivateKey. ", var3);
         }
      } else {
         return null;
      }
   }

   /** @deprecated */
   public byte[] encryptSecretKey() throws CRMFException {
      if (this.theKey != null && this.keyAlgId != null && this.pubKey != null) {
         try {
            JSAFE_SecureRandom var1 = this.theCertJ.getRandomObject();
            JSAFE_AsymmetricCipher var2 = h.e(this.keyAlgId, 0, this.theCertJ.getDevice(), (CertJ)this.theCertJ);
            var2.encryptInit(this.pubKey, var1, this.theCertJ.getPKCS11Sessions());
            byte[] var3 = var2.wrapSecretKey(this.theKey, false);
            var2.clearSensitiveData();
            return var3;
         } catch (NoServiceException var4) {
            throw new CRMFException("Cannot get random object from CertJ.", var4);
         } catch (JSAFE_Exception var5) {
            throw new CRMFException("Could not encrypt the secret key. ", var5);
         } catch (RandomException var6) {
            throw new CRMFException("Could not encrypt the secret key. ", var6);
         }
      } else {
         return null;
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("EncryptedValue Encoding is null.");
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
         throw new CRMFException("Specified array is null in EncryptedValue.");
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
            throw new CRMFException("Unable to encode EncryptedValue.", var6);
         }
      }
   }

   private int encodeInit() throws CRMFException {
      try {
         if (this.encryptedKeyValue == null) {
            this.encryptedKeyValue = this.encryptPrivateKey();
         }

         if (this.symmKeyValue == null) {
            this.symmKeyValue = this.encryptSecretKey();
         }

         boolean var1 = false;
         int var2 = 0;
         byte[] var3 = null;
         if (this.intendedAlgId != null) {
            var1 = true;
            var2 = this.intendedAlgId.length;
            var3 = new byte[var2];
            var3[0] = -96;
            System.arraycopy(this.intendedAlgId, 1, var3, 1, var2 - 1);
         }

         EncodedContainer var4 = new EncodedContainer(8466432, var1, 0, var3, 0, var2);
         var1 = false;
         var2 = 0;
         if (this.hintValue != null) {
            var1 = true;
            var2 = this.hintValue.length;
         }

         OctetStringContainer var5 = new OctetStringContainer(8466436, var1, 0, this.hintValue, 0, var2);
         var1 = false;
         var2 = 0;
         if (this.symmAlgId != null) {
            var1 = true;
            var2 = this.symmAlgId.length;
            var3 = new byte[var2];
            var3[0] = -95;
            System.arraycopy(this.symmAlgId, 1, var3, 1, var2 - 1);
         }

         EncodedContainer var6 = new EncodedContainer(8466433, var1, 0, var3, 0, var2);
         var1 = false;
         var2 = 0;
         if (this.keyAlgId != null) {
            var1 = true;
            var2 = this.keyAlgId.length;
            var3 = new byte[var2];
            var3[0] = -93;
            System.arraycopy(this.keyAlgId, 1, var3, 1, var2 - 1);
         }

         EncodedContainer var7 = new EncodedContainer(8466435, var1, 0, var3, 0, var2);
         var1 = false;
         var2 = 0;
         SequenceContainer var8 = new SequenceContainer(this.special, true, 0);
         EndContainer var9 = new EndContainer();
         if (this.symmKeyValue != null) {
            var1 = true;
            var2 = this.symmKeyValue.length;
         }

         BitStringContainer var10 = new BitStringContainer(8454146, var1, 0, this.symmKeyValue, 0, var2);
         if (this.encryptedKeyValue == null) {
            throw new CRMFException("The encrypted private key value is missing.");
         } else {
            BitStringContainer var11 = new BitStringContainer(0, true, 0, this.encryptedKeyValue, 0, this.encryptedKeyValue.length);
            ASN1Container[] var12 = new ASN1Container[]{var8, var4, var6, var10, var7, var5, var11, var9};
            this.asn1Template = new ASN1Template(var12);
            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var13) {
         throw new CRMFException(var13);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         EncryptedValue var1 = new EncryptedValue(this.theCertJ, this.pubKey, this.privKey);
         if (this.intendedAlgId != null) {
            var1.intendedAlgId = new byte[this.intendedAlgId.length];
            System.arraycopy(this.intendedAlgId, 0, var1.intendedAlgId, 0, this.intendedAlgId.length);
         }

         if (this.symmAlgId != null) {
            var1.symmAlgId = new byte[this.symmAlgId.length];
            System.arraycopy(this.symmAlgId, 0, var1.symmAlgId, 0, this.symmAlgId.length);
         }

         if (this.keyAlgId != null) {
            var1.keyAlgId = new byte[this.keyAlgId.length];
            System.arraycopy(this.keyAlgId, 0, var1.keyAlgId, 0, this.keyAlgId.length);
         }

         if (this.hintValue != null) {
            var1.hintValue = new byte[this.hintValue.length];
            System.arraycopy(this.hintValue, 0, var1.hintValue, 0, this.hintValue.length);
         }

         if (this.encryptedKeyValue != null) {
            var1.encryptedKeyValue = new byte[this.encryptedKeyValue.length];
            System.arraycopy(this.encryptedKeyValue, 0, var1.encryptedKeyValue, 0, this.encryptedKeyValue.length);
         }

         if (this.symmKeyValue != null) {
            var1.symmKeyValue = new byte[this.symmKeyValue.length];
            System.arraycopy(this.symmKeyValue, 0, var1.symmKeyValue, 0, this.symmKeyValue.length);
         }

         if (this.theKey != null) {
            var1.theKey = (JSAFE_SecretKey)this.theKey.clone();
         }

         if (this.thePrivateKey != null) {
            var1.thePrivateKey = (JSAFE_PrivateKey)this.thePrivateKey.clone();
         }

         if (this.asn1Template != null) {
            var1.encodeInit();
         }

         var1.special = this.special;
         return var1;
      } catch (CRMFException var2) {
         throw new CloneNotSupportedException(var2.getMessage());
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof EncryptedValue) {
         EncryptedValue var2 = (EncryptedValue)var1;
         if (!CertJUtils.byteArraysEqual(this.intendedAlgId, var2.intendedAlgId)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.symmAlgId, var2.symmAlgId)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.keyAlgId, var2.keyAlgId)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.hintValue, var2.hintValue)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.encryptedKeyValue, var2.encryptedKeyValue)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.symmKeyValue, var2.symmKeyValue)) {
            return false;
         } else {
            if (this.theKey != null) {
               if (var2.theKey == null) {
                  return false;
               }

               byte[][] var3 = this.theKey.getKeyData();
               byte[][] var4 = var2.theKey.getKeyData();
               if (!CertJUtils.byteArraysEqual(var3[0], var4[0])) {
                  return false;
               }
            } else if (var2.theKey != null) {
               return false;
            }

            if (this.thePrivateKey != null) {
               if (!this.thePrivateKey.equals(var2.thePrivateKey)) {
                  return false;
               }
            } else if (var2.thePrivateKey != null) {
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
      var2 = var1 * var2 + Arrays.hashCode(this.encryptedKeyValue);
      var2 = var1 * var2 + Arrays.hashCode(this.hintValue);
      var2 = var1 * var2 + Arrays.hashCode(this.intendedAlgId);
      var2 = var1 * var2 + Arrays.hashCode(this.keyAlgId);
      var2 = var1 * var2 + Arrays.hashCode(this.symmAlgId);
      var2 = var1 * var2 + Arrays.hashCode(this.symmKeyValue);
      if (this.theKey != null) {
         byte[] var3 = this.theKey.getSecretKeyData();
         var2 = var1 * var2 + Arrays.hashCode(var3);
      }

      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.thePrivateKey);
      return var2;
   }
}

package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;

/** @deprecated */
public class POPOPrivKey implements Serializable, Cloneable {
   /** @deprecated */
   public static final int THIS_MESSAGE = 0;
   /** @deprecated */
   public static final int SUBSEQUENT_MESSAGE = 1;
   /** @deprecated */
   public static final int ENCRYPTED_CERT = 0;
   /** @deprecated */
   public static final int CHALLENGE_RESPONSE = 1;
   private ASN1Template asn1Template;
   private EncryptedValue encValue;
   private int subsequentMessage = -1;
   private int special;
   private int type = -1;
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;

   /** @deprecated */
   public void setType(int var1) throws CRMFException {
      if (var1 != 0 && var1 != 1) {
         throw new CRMFException("Invalid type.");
      } else {
         this.type = var1;
      }
   }

   /** @deprecated */
   public int getType() {
      return this.type;
   }

   /** @deprecated */
   public void setEnvironment(CertJ var1, JSAFE_PublicKey var2, JSAFE_PrivateKey var3) {
      if (var1 != null) {
         this.theCertJ = var1;
      }

      if (var2 != null) {
         this.pubKey = var2;
      }

      if (var3 != null) {
         this.privKey = var3;
      }

   }

   /** @deprecated */
   public void setEncryptedValue(EncryptedValue var1) throws CRMFException {
      if (this.type == 1) {
         throw new CRMFException("Wrong type: this object is of SUBSEQUENT_MESSAGE type");
      } else if (var1 == null) {
         throw new CRMFException("specified EncryptedValue is null");
      } else {
         try {
            this.encValue = (EncryptedValue)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Encrypted Value.", var3);
         }
      }
   }

   /** @deprecated */
   public EncryptedValue getEncryptedValue() throws CRMFException {
      if (this.type == 1) {
         throw new CRMFException("Wrong type: this object is of SUBSEQUENT_MESSAGE type");
      } else if (this.encValue == null) {
         return null;
      } else {
         try {
            return (EncryptedValue)this.encValue.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Invalid Encrypted Value.", var2);
         }
      }
   }

   /** @deprecated */
   public void setSubsequentMessage(int var1) throws CRMFException {
      if (var1 != 0 && var1 != 1) {
         throw new CRMFException("Invalid value for Subsequent Message.");
      } else {
         this.subsequentMessage = var1;
      }
   }

   /** @deprecated */
   public int getSubsequentMessage() {
      return this.subsequentMessage;
   }

   /** @deprecated */
   public void decodePOPOPrivKey(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("POPOPrivKey Encoding is null.");
      } else {
         this.special = var3;

         try {
            ChoiceContainer var4 = new ChoiceContainer(var3);
            BitStringContainer var5 = new BitStringContainer(8388608);
            IntegerContainer var6 = new IntegerContainer(8388609);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var5.dataPresent) {
               this.encValue = new EncryptedValue(this.theCertJ, this.pubKey, this.privKey);
               this.encValue.decodeEncryptedValue(var5.data, var5.dataOffset, 0);
               this.type = 0;
            } else if (var6.dataPresent) {
               this.subsequentMessage = var6.getValueAsInt();
               if (this.subsequentMessage != 0 && this.subsequentMessage != 1) {
                  throw new CRMFException("Invalid value of Subsequent Message.");
               }

               this.type = 1;
            }

         } catch (ASN_Exception var9) {
            throw new CRMFException("Invalid POPOPrivKey encoding: ", var9);
         }
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("POPOPrivKey Encoding is null.");
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
         throw new CRMFException("Specified POPOPrivKey array is null.");
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
            throw new CRMFException("Unable to encode POPOPrivKey.", var6);
         }
      }
   }

   private int encodeInit() throws CRMFException {
      try {
         byte[] var1 = null;
         int var2 = 0;
         boolean var3 = false;
         boolean var4 = false;
         if (this.encValue != null) {
            var2 = this.encValue.getDERLen(0);
            var1 = new byte[var2];
            var2 = this.encValue.getDEREncoding(var1, 0, 8388608);
            var3 = true;
         } else {
            if (this.subsequentMessage == -1) {
               throw new CRMFException("POPOPrivKey Data is not set.");
            }

            var4 = true;
         }

         ChoiceContainer var5 = new ChoiceContainer(this.special, 0);
         BitStringContainer var6 = new BitStringContainer(8388608, var3, 0, var1, 0, var2);
         IntegerContainer var7 = new IntegerContainer(8388609, var4, 0, this.subsequentMessage);
         EndContainer var8 = new EndContainer();
         ASN1Container[] var9 = new ASN1Container[]{var5, var6, var7, var8};
         this.asn1Template = new ASN1Template(var9);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var10) {
         throw new CRMFException(var10);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         POPOPrivKey var1 = new POPOPrivKey();
         var1.type = this.type;
         var1.subsequentMessage = this.subsequentMessage;
         if (this.encValue != null) {
            var1.encValue = (EncryptedValue)this.encValue.clone();
         }

         if (this.theCertJ != null) {
            var1.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
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
      if (var1 != null && var1 instanceof POPOPrivKey) {
         POPOPrivKey var2 = (POPOPrivKey)var1;
         if (this.type != var2.type) {
            return false;
         } else {
            if (this.encValue != null) {
               if (!this.encValue.equals(var2.encValue)) {
                  return false;
               }
            } else if (var2.encValue != null) {
               return false;
            }

            return var2.subsequentMessage == this.subsequentMessage;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.encValue);
      var2 = var1 * var2 + this.subsequentMessage;
      var2 = var1 * var2 + this.type;
      return var2;
   }
}

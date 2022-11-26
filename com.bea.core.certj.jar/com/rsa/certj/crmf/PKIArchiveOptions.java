package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BooleanContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import java.util.Arrays;

/** @deprecated */
public class PKIArchiveOptions extends Control {
   private EncryptedKey privEncKey;
   private byte[] parameters;
   private boolean archiveKey;
   ASN1Template asn1TemplateValue;
   private int special = 0;

   /** @deprecated */
   public PKIArchiveOptions() {
      this.controlTypeFlag = 3;
      this.theOID = new byte[OID_LIST[3].length];
      System.arraycopy(OID_LIST[3], 0, this.theOID, 0, this.theOID.length);
      this.controlTypeString = "PKIArchiveOptions";
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("PKIArchiveOptions Encoding is null.");
      } else {
         try {
            ChoiceContainer var3 = new ChoiceContainer(0);
            EndContainer var4 = new EndContainer();
            EncodedContainer var5 = new EncodedContainer(10551040);
            OctetStringContainer var6 = new OctetStringContainer(8388609);
            BooleanContainer var7 = new BooleanContainer(8388610);
            ASN1Container[] var8 = new ASN1Container[]{var3, var5, var6, var7, var4};
            ASN1.berDecode(var1, var2, var8);
            if (var5.dataPresent) {
               this.privEncKey = new EncryptedKey(theCertJ, theCertPathCtx, this.pubKey, this.privKey);
               this.privEncKey.decodeEncryptedKey(var5.data, var5.dataOffset, 10485760);
            } else if (var6.dataPresent) {
               this.parameters = new byte[var6.dataLen];
               System.arraycopy(var6.data, var6.dataOffset, this.parameters, 0, var6.dataLen);
            } else if (var7.dataPresent) {
               this.archiveKey = var7.value;
            }

         } catch (ASN_Exception var9) {
            throw new CRMFException("Could not BER decode PKIArchiveOptions.", var9);
         }
      }
   }

   /** @deprecated */
   public void setEncryptedKey(EncryptedKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified EncryptedKey is null.");
      } else {
         try {
            this.privEncKey = (EncryptedKey)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException(var3);
         }
      }
   }

   /** @deprecated */
   public EncryptedKey getEncryptedKey() {
      if (this.privEncKey == null) {
         return null;
      } else {
         try {
            return (EncryptedKey)this.privEncKey.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setParameters(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 > 0 && var2 >= 0) {
         this.parameters = new byte[var3];
         System.arraycopy(var1, var2, this.parameters, 0, var3);
      } else {
         throw new CRMFException("Specified parameters are null.");
      }
   }

   /** @deprecated */
   public byte[] getParameters() {
      if (this.parameters == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.parameters.length];
         System.arraycopy(this.parameters, 0, var1, 0, this.parameters.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setArchivePrivateKey(boolean var1) {
      this.archiveKey = var1;
   }

   /** @deprecated */
   public boolean getArchivePrivateKey() {
      return this.archiveKey;
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      this.asn1TemplateValue = null;

      try {
         boolean var1 = false;
         boolean var2 = false;
         boolean var3 = false;
         byte[] var4 = null;
         int var5 = 0;
         int var6 = 0;
         if (this.privEncKey != null) {
            var5 = this.privEncKey.getDERLen(10485760);
            var4 = new byte[var5];
            var5 = this.privEncKey.getDEREncoding(var4, 0, 10485760);
            var1 = true;
         } else if (this.parameters != null) {
            var2 = true;
            var6 = this.parameters.length;
         } else {
            var3 = true;
         }

         ChoiceContainer var7 = new ChoiceContainer(0, 0);
         EndContainer var8 = new EndContainer();
         EncodedContainer var9 = new EncodedContainer(10485760, var1, 0, var4, 0, var5);
         OctetStringContainer var10 = new OctetStringContainer(8388609, var2, 0, this.parameters, 0, var6);
         BooleanContainer var11 = new BooleanContainer(8388610, var3, 0, this.archiveKey);
         ASN1Container[] var12 = new ASN1Container[]{var7, var9, var10, var11, var8};
         this.asn1TemplateValue = new ASN1Template(var12);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var13) {
         throw new CRMFException("Cannot encode PKIArchiveOptions control. ", var13);
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in array is null in PKIArchiveOptions control.");
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode PKIArchiveOptions control.");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var4) {
            throw new CRMFException("Cannot encode PKIArchiveOptions control.", var4);
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      PKIArchiveOptions var1 = new PKIArchiveOptions();

      try {
         if (Control.theCertJ != null) {
            var1.setEnvironment(Control.theCertJ, Control.theCertPathCtx, this.pubKey, this.privKey);
         }
      } catch (CRMFException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }

      if (this.privEncKey != null) {
         var1.privEncKey = (EncryptedKey)this.privEncKey.clone();
      }

      if (this.parameters != null) {
         var1.parameters = new byte[this.parameters.length];
         System.arraycopy(this.parameters, 0, var1.parameters, 0, this.parameters.length);
      }

      var1.archiveKey = this.archiveKey;
      var1.special = this.special;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PKIArchiveOptions) {
         PKIArchiveOptions var2 = (PKIArchiveOptions)var1;
         if (var2.archiveKey != this.archiveKey) {
            return false;
         } else {
            if (this.privEncKey != null) {
               if (!this.privEncKey.equals(var2.privEncKey)) {
                  return false;
               }
            } else if (var2.privEncKey != null) {
               return false;
            }

            return CertJUtils.byteArraysEqual(this.parameters, var2.parameters);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + (this.archiveKey ? 1231 : 1237);
      var2 = var1 * var2 + Arrays.hashCode(this.parameters);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.privEncKey);
      return var2;
   }
}

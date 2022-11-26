package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.extensions.GeneralName;
import java.util.Arrays;

/** @deprecated */
public class OldCertID extends Control {
   private GeneralName issuerName;
   private byte[] serialNumber;
   ASN1Template asn1TemplateValue;
   private int special = 0;

   /** @deprecated */
   public OldCertID() {
      this.controlTypeFlag = 4;
      this.theOID = new byte[OID_LIST[4].length];
      System.arraycopy(OID_LIST[4], 0, this.theOID, 0, this.theOID.length);
      this.controlTypeString = "OldCertID";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("OldCertID Encoding is null.");
      } else {
         try {
            SequenceContainer var3 = new SequenceContainer(this.special);
            EndContainer var4 = new EndContainer();
            EncodedContainer var5 = new EncodedContainer(65280);
            IntegerContainer var6 = new IntegerContainer(0);
            ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
            ASN1.berDecode(var1, var2, var7);
            this.serialNumber = new byte[var6.dataLen];
            System.arraycopy(var6.data, var6.dataOffset, this.serialNumber, 0, var6.dataLen);
            this.issuerName = new GeneralName(var5.data, var5.dataOffset, 0);
         } catch (Exception var8) {
            throw new CRMFException("Cannot decode OldCertID control.", var8);
         }
      }
   }

   /** @deprecated */
   public void setCertIssuerName(GeneralName var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Issuer Name in OldCertID control cannot be null.");
      } else {
         try {
            this.issuerName = (GeneralName)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Invalid Issuer Name.", var3);
         }
      }
   }

   /** @deprecated */
   public void setSerialNumber(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 > 0 && var2 >= 0) {
         this.serialNumber = new byte[var3];
         System.arraycopy(var1, var2, this.serialNumber, 0, var3);
      } else {
         throw new CRMFException("Passed in SerialNumber value is null in OldCertID control.");
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      return this.serialNumber == null ? null : (byte[])((byte[])this.serialNumber.clone());
   }

   /** @deprecated */
   public GeneralName getCertIssuerName() {
      if (this.issuerName == null) {
         return null;
      } else {
         try {
            return (GeneralName)this.issuerName.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in array is null in OldCertID control.");
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode OldCertID control.");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            throw new CRMFException("Cannot encode OldCertID control.", var5);
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      this.asn1TemplateValue = null;
      SequenceContainer var1 = new SequenceContainer(this.special, true, 0);
      EndContainer var2 = new EndContainer();
      if (this.issuerName == null) {
         throw new CRMFException("Issuer Name is not set in OldCertID control.");
      } else {
         byte[] var4;
         int var5;
         try {
            var5 = this.issuerName.getDERLen(0);
            var4 = new byte[var5];
            var5 = this.issuerName.getDEREncoding(var4, 0, 0);
         } catch (NameException var9) {
            throw new CRMFException("Cannot encode IssuerName. ", var9);
         }

         try {
            EncodedContainer var3 = new EncodedContainer(12288, true, 0, var4, 0, var5);
            if (this.serialNumber == null) {
               throw new CRMFException("Serial Number is not set in OldCertID control.");
            } else {
               IntegerContainer var6 = null;
               if ((this.serialNumber[0] & 128) >> 7 == 0) {
                  var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
               } else {
                  var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
               }

               ASN1Container[] var7 = new ASN1Container[]{var1, var3, var6, var2};
               this.asn1TemplateValue = new ASN1Template(var7);
               return this.asn1TemplateValue.derEncodeInit();
            }
         } catch (ASN_Exception var8) {
            throw new CRMFException(var8);
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      OldCertID var1 = new OldCertID();
      if (this.issuerName != null) {
         var1.issuerName = (GeneralName)this.issuerName.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = (byte[])((byte[])this.serialNumber.clone());
      }

      var1.special = this.special;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof OldCertID) {
         OldCertID var2 = (OldCertID)var1;
         if (this.issuerName != null) {
            if (!this.issuerName.equals(var2.issuerName)) {
               return false;
            }
         } else if (var2.issuerName != null) {
            return false;
         }

         return CertJUtils.byteArraysEqual(this.serialNumber, var2.serialNumber);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.issuerName);
      var2 = var1 * var2 + Arrays.hashCode(this.serialNumber);
      return var2;
   }
}

package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CRLExtension;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;

/** @deprecated */
public class AuthorityKeyID extends X509V3Extension implements CRLExtension, CertExtension {
   ASN1Template asn1TemplateValue;
   private byte[] keyID;
   private static final int KEY_SPECIAL = 8454144;
   private GeneralNames authorityCertIssuer;
   private static final int ISSUER_SPECIAL = 8454145;
   private byte[] serialNumber;
   private static final int NUMBER_SPECIAL = 8454146;

   /** @deprecated */
   public AuthorityKeyID() {
      this.extensionTypeFlag = 35;
      this.criticality = false;
      this.authorityCertIssuer = new GeneralNames();
      this.setStandardOID(35);
      this.extensionTypeString = "AuthorityKeyID";
   }

   /** @deprecated */
   public AuthorityKeyID(GeneralNames var1, byte[] var2, int var3, int var4, byte[] var5, int var6, int var7, boolean var8) {
      this.extensionTypeFlag = 35;
      this.criticality = var8;
      this.setStandardOID(35);
      this.extensionTypeString = "AuthorityKeyID";
      if (var5 != null && var7 != 0) {
         this.keyID = new byte[var7];
         System.arraycopy(var5, var6, this.keyID, 0, var7);
      }

      if (var1 != null) {
         this.authorityCertIssuer = var1;
      }

      if (var2 != null && var4 != 0) {
         this.serialNumber = new byte[var4];
         System.arraycopy(var2, var3, this.serialNumber, 0, var4);
      }

   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         OctetStringContainer var3;
         IntegerContainer var4;
         EncodedContainer var5;
         try {
            SequenceContainer var6 = new SequenceContainer(0);
            EndContainer var7 = new EndContainer();
            var3 = new OctetStringContainer(8454144);
            var4 = new IntegerContainer(8454146);
            var5 = new EncodedContainer(8466433);
            ASN1Container[] var8 = new ASN1Container[]{var6, var3, var5, var4, var7};
            ASN1.berDecode(var1, var2, var8);
         } catch (ASN_Exception var10) {
            throw new CertificateException("Could not decode AuthorityKeyID extension.");
         }

         try {
            if (var3.dataPresent && var3.dataLen != 0 && var3.data != null) {
               this.keyID = new byte[var3.dataLen];
               System.arraycopy(var3.data, var3.dataOffset, this.keyID, 0, var3.dataLen);
            }

            if (var5.dataPresent && var4.dataPresent) {
               this.authorityCertIssuer = new GeneralNames(var5.data, var5.dataOffset, 8454145);
               this.serialNumber = new byte[var4.dataLen];
               System.arraycopy(var4.data, var4.dataOffset, this.serialNumber, 0, var4.dataLen);
            }

         } catch (NameException var9) {
            throw new CertificateException("Could not decode AuthorityKeyID extension!!!.");
         }
      }
   }

   /** @deprecated */
   public void setKeyID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.keyID = new byte[var3];
         System.arraycopy(var1, var2, this.keyID, 0, var3);
      }

   }

   /** @deprecated */
   public void setAuthorityCertIssuer(GeneralNames var1) {
      if (var1 != null) {
         this.authorityCertIssuer = var1;
      }

   }

   /** @deprecated */
   public void setSerialNumber(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.serialNumber = new byte[var3];
         System.arraycopy(var1, var2, this.serialNumber, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getKeyID() {
      if (this.keyID == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.keyID.length];
         System.arraycopy(this.keyID, 0, var1, 0, this.keyID.length);
         return var1;
      }
   }

   /** @deprecated */
   public GeneralNames getAuthorityCertIssuer() {
      return this.authorityCertIssuer;
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      if (this.serialNumber == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.serialNumber.length];
         System.arraycopy(this.serialNumber, 0, var1, 0, this.serialNumber.length);
         return var1;
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         SequenceContainer var1 = new SequenceContainer(0, true, 0);
         EndContainer var2 = new EndContainer();
         OctetStringContainer var3 = null;
         IntegerContainer var4 = null;
         EncodedContainer var5 = null;
         byte var6 = 0;
         if (this.keyID != null) {
            var3 = new OctetStringContainer(8454144, true, 0, this.keyID, 0, this.keyID.length);
            var6 = 1;
         }

         if (this.authorityCertIssuer != null && this.serialNumber != null) {
            var4 = new IntegerContainer(8454146, true, 0, this.serialNumber, 0, this.serialNumber.length, true);

            try {
               int var7 = this.authorityCertIssuer.getDERLen(8454145);
               byte[] var8 = new byte[var7];
               byte var9 = 0;
               int var10 = this.authorityCertIssuer.getDEREncoding(var8, var9, 8454145);
               var5 = new EncodedContainer(8466433, true, 0, var8, var9, var10);
            } catch (NameException var11) {
               return 0;
            }

            if (var6 == 1) {
               var6 = 3;
            } else {
               var6 = 2;
            }
         }

         switch (var6) {
            case 0:
               ASN1Container[] var13 = new ASN1Container[]{var1, var2};
               this.asn1TemplateValue = new ASN1Template(var13);
               break;
            case 1:
               ASN1Container[] var14 = new ASN1Container[]{var1, var3, var2};
               this.asn1TemplateValue = new ASN1Template(var14);
               break;
            case 2:
               ASN1Container[] var15 = new ASN1Container[]{var1, var5, var4, var2};
               this.asn1TemplateValue = new ASN1Template(var15);
               break;
            case 3:
               ASN1Container[] var16 = new ASN1Container[]{var1, var3, var5, var4, var2};
               this.asn1TemplateValue = new ASN1Template(var16);
         }

         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var12) {
         return 0;
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      AuthorityKeyID var1 = new AuthorityKeyID();
      if (this.keyID != null) {
         var1.keyID = (byte[])((byte[])this.keyID.clone());
      }

      if (this.authorityCertIssuer != null) {
         var1.authorityCertIssuer = (GeneralNames)this.authorityCertIssuer.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = (byte[])((byte[])this.serialNumber.clone());
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.keyID = null;
      this.authorityCertIssuer = null;
      this.serialNumber = null;
      this.asn1TemplateValue = null;
   }
}

package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public class BiometricInfo extends X509V3Extension implements CertExtension {
   private Vector bioData = new Vector();
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public BiometricInfo() {
      this.extensionTypeFlag = 124;
      this.criticality = false;
      this.setSpecialOID(BIO_INFO_OID);
      this.extensionTypeString = "Biometric_Info";
   }

   /** @deprecated */
   public BiometricInfo(BiometricData var1, boolean var2) {
      this.extensionTypeFlag = 124;
      this.criticality = var2;
      this.setSpecialOID(BIO_INFO_OID);
      this.extensionTypeString = "Biometric_Info";
      if (var1 != null) {
         this.bioData.addElement(var1);
      }

   }

   /** @deprecated */
   public void addBioData(BiometricData var1) {
      if (var1 != null) {
         this.bioData.addElement(var1);
      }

   }

   /** @deprecated */
   public int getBioDataCount() {
      return this.bioData.size();
   }

   /** @deprecated */
   public BiometricData getBioData(int var1) throws CertificateException {
      if (this.bioData.size() > var1 && var1 >= 0) {
         return (BiometricData)this.bioData.elementAt(var1);
      } else {
         throw new CertificateException("Specified index is invalid.");
      }
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 != null && var2 >= 0) {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               BiometricData var8 = new BiometricData(var7.data, var7.dataOffset, 0);
               this.bioData.addElement(var8);
            }

         } catch (ASN_Exception var9) {
            throw new CertificateException("Cannot decode the BER of this extension.", var9);
         } catch (NameException var10) {
            throw new CertificateException("Cannot decode the BER of this extension.", var10);
         }
      } else {
         throw new CertificateException("Encoding is null.");
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
         var1.addElement(var2);
         Iterator var4 = this.bioData.iterator();

         while(var4.hasNext()) {
            BiometricData var5 = (BiometricData)var4.next();

            try {
               int var6 = var5.getDERLen(0);
               byte[] var7 = new byte[var6];
               var6 = var5.getDEREncoding(var7, 0, 0);
               EncodedContainer var3 = new EncodedContainer(0, true, 0, var7, 0, var6);
               var2.addContainer(var3);
            } catch (ASN_Exception var8) {
               return 0;
            } catch (NameException var9) {
               return 0;
            }
         }

         ASN1Container[] var11 = new ASN1Container[var1.size()];
         var1.copyInto(var11);
         this.asn1TemplateValue = new ASN1Template(var11);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var10) {
         return 0;
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
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
   public boolean equals(Object var1) {
      if (!(var1 instanceof BiometricInfo)) {
         return false;
      } else {
         int var2 = this.getDERLen(0);
         if (var2 == 0) {
            return false;
         } else {
            byte[] var3 = new byte[var2];
            this.getDEREncoding(var3, 0, 0);
            BiometricInfo var4 = (BiometricInfo)var1;
            int var5 = var4.getDERLen(0);
            if (var5 == 0) {
               return false;
            } else {
               byte[] var6 = new byte[var5];
               var4.getDEREncoding(var6, 0, 0);
               return CertJUtils.byteArraysEqual(var3, var6);
            }
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];
         this.getDEREncoding(var2, 0, 0);
         return Arrays.hashCode(var2);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      BiometricInfo var1 = new BiometricInfo();
      Iterator var2 = this.bioData.iterator();

      while(var2.hasNext()) {
         BiometricData var3 = (BiometricData)var2.next();
         var1.bioData.add(var3);
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
      this.bioData = new Vector();
      this.asn1TemplateValue = null;
   }
}

package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.OCSPSingleExtension;
import com.rsa.certj.cert.X500Name;
import java.util.Vector;

/** @deprecated */
public class OCSPServiceLocator extends X509V3Extension implements OCSPSingleExtension {
   private X500Name issuer;
   private AuthorityInfoAccess locator;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public OCSPServiceLocator() {
      this.extensionTypeFlag = 122;
      this.criticality = false;
      this.setSpecialOID(OCSP_SERVICE_LOCATOR_OID);
      this.extensionTypeString = "OCSPServiceLocator";
      this.issuer = null;
      this.locator = null;
   }

   /** @deprecated */
   public OCSPServiceLocator(X500Name var1, AuthorityInfoAccess var2) throws CertificateException {
      if (var1 != null && var2 != null) {
         this.extensionTypeFlag = 122;
         this.criticality = false;
         this.setSpecialOID(OCSP_SERVICE_LOCATOR_OID);
         this.extensionTypeString = "OCSPServiceLocator";

         try {
            this.issuer = (X500Name)var1.clone();
            this.locator = (AuthorityInfoAccess)var2.clone();
         } catch (CloneNotSupportedException var4) {
            throw new CertificateException(var4);
         }
      } else {
         throw new CertificateException("Missing values");
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.issuer == null) {
         return 0;
      } else {
         int var1 = this.issuer.getDERLen(0);
         byte[] var2 = new byte[var1];

         try {
            this.issuer.getDEREncoding(var2, 0, var2.length);
         } catch (NameException var9) {
            return 0;
         }

         try {
            SequenceContainer var3 = new SequenceContainer(0, true, 0);
            EncodedContainer var4 = new EncodedContainer(65280, true, 0, var2, 0, var2.length);
            EncodedContainer var5;
            if (this.locator != null) {
               byte[] var6 = this.getAIADER(this.locator);
               var5 = new EncodedContainer(65536, true, 0, var6, 0, var6.length);
            } else {
               var5 = new EncodedContainer(65536, false, 0, (byte[])null, 0, 0);
            }

            EndContainer var10 = new EndContainer();
            ASN1Container[] var7 = new ASN1Container[]{var3, var4, var5, var10};
            this.asn1TemplateValue = new ASN1Template(var7);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var8) {
            return 0;
         }
      }
   }

   private EncodedContainer encodeDescription(int var1, Vector var2, Vector var3) throws CertificateException {
      SequenceContainer var5 = new SequenceContainer(0, true, 0);
      EndContainer var6 = new EndContainer();
      byte[] var7 = (byte[])((byte[])var2.elementAt(var1));

      try {
         OIDContainer var8 = new OIDContainer(16777216, true, 0, var7, 0, var7.length);
         GeneralName var9 = (GeneralName)var3.elementAt(var1);
         byte[] var10 = new byte[var9.getDERLen(0)];
         int var11 = var9.getDEREncoding(var10, 0, 0);
         EncodedContainer var12 = new EncodedContainer(0, true, 0, var10, 0, var11);
         ASN1Container[] var13 = new ASN1Container[]{var5, var8, var12, var6};
         ASN1Template var14 = new ASN1Template(var13);
         int var15 = var14.derEncodeInit();
         byte[] var16 = new byte[var15];
         var15 = var14.derEncode(var16, 0);
         EncodedContainer var4 = new EncodedContainer(12288, true, 0, var16, 0, var15);
         return var4;
      } catch (ASN_Exception var17) {
         throw new CertificateException(" Can't encode Access Description");
      } catch (NameException var18) {
         throw new CertificateException("Could not encode Access Description ");
      }
   }

   private byte[] getAIADER(AuthorityInfoAccess var1) {
      Vector var2 = new Vector();
      Vector var3 = new Vector();
      int var5 = var1.getAccessDescriptionCount();
      if (var5 == 0) {
         return null;
      } else {
         try {
            for(int var6 = 0; var6 < var5; ++var6) {
               byte[] var7 = var1.getAccessMethod(var6);
               byte[] var8 = new byte[var7.length];
               System.arraycopy(var7, 0, var8, 0, var7.length);
               var3.addElement(var8);
               var2.addElement(var1.getAccessLocation(var6));
            }
         } catch (CertificateException var12) {
            return null;
         }

         try {
            Vector var13 = new Vector();
            OfContainer var14 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
            var13.addElement(var14);

            for(int var15 = 0; var15 < var3.size(); ++var15) {
               EncodedContainer var9 = this.encodeDescription(var15, var3, var2);
               var14.addContainer(var9);
            }

            ASN1Container[] var16 = new ASN1Container[var13.size()];
            var13.copyInto(var16);
            ASN1Template var4 = new ASN1Template(var16);
            int var17 = var4.derEncodeInit();
            byte[] var10 = new byte[var17];
            var4.derEncode(var10, 0);
            return var10;
         } catch (Exception var11) {
            return null;
         }
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
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         GenTimeContainer var3 = new GenTimeContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var6) {
            throw new CertificateException("Could not decode ArchiveCutoff extension.");
         }
      }
   }

   /** @deprecated */
   public X500Name getIssuer() {
      return this.issuer;
   }

   /** @deprecated */
   public AuthorityInfoAccess getLocator() {
      return this.locator;
   }

   /** @deprecated */
   public void setIssuer(X500Name var1) throws CloneNotSupportedException {
      if (var1 == null) {
         throw new CloneNotSupportedException("Missing value");
      } else {
         try {
            this.issuer = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CloneNotSupportedException(var3.getMessage());
         }
      }
   }

   /** @deprecated */
   public void setLocator(AuthorityInfoAccess var1) throws CloneNotSupportedException {
      if (var1 == null) {
         throw new CloneNotSupportedException("Missing value");
      } else {
         try {
            this.locator = (AuthorityInfoAccess)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CloneNotSupportedException(var3.getMessage());
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      OCSPServiceLocator var1 = new OCSPServiceLocator();
      if (this.issuer != null) {
         var1.issuer = (X500Name)this.issuer.clone();
      }

      if (this.locator != null) {
         var1.locator = (AuthorityInfoAccess)this.locator.clone();
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }
}

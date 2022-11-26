package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BooleanContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class BasicConstraints extends X509V3Extension implements CertExtension {
   private static final String EXT_NAME = "BasicConstraints";
   private boolean cA;
   private int pathLenConstraint = -1;
   private boolean setPathLen;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public BasicConstraints() {
      this.extensionTypeString = "BasicConstraints";
      this.extensionTypeFlag = 19;
      this.criticality = false;
      this.setPathLen = false;
      this.setStandardOID(19);
   }

   /** @deprecated */
   public BasicConstraints(boolean var1, int var2, boolean var3) throws CertificateException {
      this.extensionTypeString = "BasicConstraints";
      this.extensionTypeFlag = 19;
      this.criticality = var3;
      this.setPathLen = var1;
      this.setStandardOID(19);
      this.cA = var1;
      if (var1) {
         this.pathLenConstraint = var2;
      } else {
         throw new CertificateException("The pathLenConstraint shall be present only if cA is set to true.");
      }
   }

   /** @deprecated */
   public BasicConstraints(boolean var1) throws CertificateException {
      this.extensionTypeString = "BasicConstraints";
      this.extensionTypeFlag = 19;
      this.criticality = var1;
      this.setStandardOID(19);
      this.cA = true;
      this.setPathLen = false;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         BooleanContainer var5 = new BooleanContainer(131072);
         IntegerContainer var6 = new IntegerContainer(65536);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};

         try {
            ASN1.berDecode(var1, var2, var7);
            if (var5.dataPresent) {
               this.cA = var5.value;
            } else {
               this.cA = false;
            }

            this.setPathLen = false;
            if (var6.dataPresent && this.cA) {
               this.pathLenConstraint = var6.getValueAsInt();
               this.setPathLen = true;
            }

         } catch (ASN_Exception var9) {
            throw new CertificateException("Could not decode BasicConstraints extension.");
         }
      }
   }

   /** @deprecated */
   public void setCA(boolean var1) throws CertificateException {
      this.cA = var1;
   }

   /** @deprecated */
   public boolean getCA() {
      return this.cA;
   }

   /** @deprecated */
   public void setPathLen(int var1) throws CertificateException {
      if (!this.cA) {
         throw new CertificateException("The pathLenConstraint shall be present only if cA is set to true.");
      } else {
         this.pathLenConstraint = var1;
         this.setPathLen = true;
      }
   }

   /** @deprecated */
   public int getPathLen() {
      return this.pathLenConstraint;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      BooleanContainer var3 = new BooleanContainer(131072, this.cA, 0, this.cA);
      ASN1Container[] var4;
      if (this.cA && this.setPathLen) {
         IntegerContainer var5 = new IntegerContainer(65536, true, 0, this.pathLenConstraint);
         var4 = new ASN1Container[]{var1, var3, var5, var2};
      } else {
         var4 = new ASN1Container[]{var1, var3, var2};
      }

      this.asn1TemplateValue = new ASN1Template(var4);

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
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
      BasicConstraints var1 = new BasicConstraints();
      var1.cA = this.cA;
      var1.pathLenConstraint = this.pathLenConstraint;
      var1.setPathLen = this.setPathLen;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.cA = false;
      this.pathLenConstraint = -1;
      this.asn1TemplateValue = null;
   }
}

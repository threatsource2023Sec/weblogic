package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509V3Extensions;

/** @deprecated */
public class V3ExtensionAttribute extends X501Attribute {
   private X509V3Extensions theExtensions;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public V3ExtensionAttribute() {
      super(2, "V3ExtensionAttribute");
   }

   /** @deprecated */
   public V3ExtensionAttribute(X509V3Extensions var1) {
      this();
      this.setV3ExtensionAttribute(var1);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();

         try {
            SetContainer var3 = new SetContainer(0);
            EndContainer var4 = new EndContainer();
            EncodedContainer var5 = new EncodedContainer(12288);
            ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};
            ASN1.berDecode(var1, var2, var6);
            this.theExtensions = new X509V3Extensions(var5.data, var5.dataOffset, 0, 1);
         } catch (ASN_Exception var7) {
            this.reset();
            throw new AttributeException("Could not BER decode V3ExtensionAttribute.");
         } catch (CertificateException var8) {
            this.reset();
            throw new AttributeException("Could not create the attribute object: ", var8);
         }
      }
   }

   /** @deprecated */
   public void setV3ExtensionAttribute(X509V3Extensions var1) {
      try {
         if (var1 != null) {
            this.theExtensions = (X509V3Extensions)var1.clone();
         }
      } catch (CloneNotSupportedException var3) {
         this.theExtensions = null;
      }

   }

   /** @deprecated */
   public X509V3Extensions getV3ExtensionAttribute() {
      if (this.theExtensions == null) {
         return null;
      } else {
         try {
            return (X509V3Extensions)this.theExtensions.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.theExtensions == null) {
         return 0;
      } else {
         int var1 = this.theExtensions.getDERLen(0);

         try {
            EndContainer var2 = new EndContainer();
            SetContainer var3 = new SetContainer(0, true, 0);
            EncodedContainer var4 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var1);
            ASN1Container[] var5 = new ASN1Container[]{var3, var4, var2};
            this.asn1TemplateValue = new ASN1Template(var5);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var6) {
            return 0;
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         int var3;
         try {
            var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }

         var3 += this.theExtensions.getDEREncoding(var1, var2 + var3, 0);
         return var3;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      V3ExtensionAttribute var1 = new V3ExtensionAttribute();

      try {
         if (this.theExtensions != null) {
            var1.theExtensions = (X509V3Extensions)this.theExtensions.clone();
         }
      } catch (CloneNotSupportedException var3) {
         var1.theExtensions = null;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof V3ExtensionAttribute) {
         V3ExtensionAttribute var2 = (V3ExtensionAttribute)var1;
         if (this.theExtensions != null) {
            if (var2.theExtensions == null) {
               return false;
            }

            int var3 = this.theExtensions.getDERLen(0);
            int var4 = var2.theExtensions.getDERLen(0);
            if (var3 != var4) {
               return false;
            }

            byte[] var5 = new byte[var3];
            byte[] var6 = new byte[var4];
            this.theExtensions.getDEREncoding(var5, 0, 0);
            var2.theExtensions.getDEREncoding(var6, 0, 0);
            if (!CertJUtils.byteArraysEqual(var5, var6)) {
               return false;
            }
         } else if (var2.theExtensions != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.theExtensions);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.theExtensions = null;
      this.asn1TemplateValue = null;
   }
}

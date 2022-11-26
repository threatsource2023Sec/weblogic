package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.attributes.X501Attribute;
import java.util.Vector;

/** @deprecated */
public class SubjectDirectoryAttributes extends X509V3Extension implements CertExtension {
   private Vector attributes = new Vector();
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public SubjectDirectoryAttributes() {
      this.extensionTypeFlag = 9;
      this.criticality = false;
      this.setStandardOID(9);
      this.extensionTypeString = "SubjectDirectoryAttributes";
   }

   /** @deprecated */
   public SubjectDirectoryAttributes(X501Attribute var1, boolean var2) {
      this.extensionTypeFlag = 9;
      this.criticality = var2;
      this.setStandardOID(9);
      if (var1 != null) {
         this.attributes.addElement(var1);
      }

      this.extensionTypeString = "SubjectDirectoryAttributes";
   }

   /** @deprecated */
   public void addAttribute(X501Attribute var1) {
      if (var1 != null) {
         this.attributes.addElement(var1);
      }

   }

   /** @deprecated */
   public X501Attribute getAttributes(int var1) throws CertificateException {
      if (var1 < this.attributes.size()) {
         return (X501Attribute)this.attributes.elementAt(var1);
      } else {
         throw new CertificateException("Invalid Index");
      }
   }

   /** @deprecated */
   public int getAttributesCount() {
      return this.attributes.size();
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();
            if (var5 > 0) {
               this.attributes = new Vector();
            }

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               X501Attribute var8 = X501Attribute.getInstance(var7.data, var7.dataOffset, 0);
               this.attributes.addElement(var8);
            }

         } catch (ASN_Exception var9) {
            throw new CertificateException("Could not decode SubjectDirectoryAttributes extension.");
         } catch (AttributeException var10) {
            throw new CertificateException("Could not decode SubjectDirectoryAttributes extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         OfContainer var1 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
         int var2 = 0;
         if (this.attributes != null) {
            var2 = this.attributes.size();
         }

         for(int var3 = 0; var3 < var2; ++var3) {
            X501Attribute var4 = (X501Attribute)this.attributes.elementAt(var3);
            int var5 = var4.getDERLen(0);
            byte[] var6 = new byte[var5];
            var5 = var4.getDEREncoding(var6, 0, 0);
            EncodedContainer var7 = new EncodedContainer(12288, true, 0, var6, 0, var5);
            var1.addContainer(var7);
         }

         ASN1Container[] var9 = new ASN1Container[]{var1};
         this.asn1TemplateValue = new ASN1Template(var9);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (Exception var8) {
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
            return this.asn1TemplateValue.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SubjectDirectoryAttributes var1 = new SubjectDirectoryAttributes();
      var1.attributes = new Vector(this.attributes);
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.attributes.clear();
   }
}

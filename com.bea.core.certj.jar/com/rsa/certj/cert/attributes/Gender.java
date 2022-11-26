package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.cert.AttributeException;

/** @deprecated */
public class Gender extends X501Attribute {
   private String gender;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public Gender() {
      super(20, "gender");
      this.gender = "";
   }

   /** @deprecated */
   public Gender(String var1) throws AttributeException {
      this();
      this.setGender(var1);
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
            PrintStringContainer var5 = new PrintStringContainer(0);
            ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};
            ASN1.berDecode(var1, var2, var6);
            this.gender = var5.getValueAsString();
         } catch (ASN_Exception var7) {
            throw new AttributeException("Could not BER decode gender.", var7);
         }
      }
   }

   /** @deprecated */
   public void setGender(String var1) throws AttributeException {
      if (var1 != null) {
         if (var1.length() != 1 || !var1.equals("M") || var1.equals("m") || var1.equals("F") || var1.equals("f")) {
            throw new AttributeException("Gender has a wrong value; should be one of M, m, F, f.");
         }

         this.reset();
         this.gender = var1;
      }

   }

   /** @deprecated */
   public String getGender() {
      return this.gender;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.gender == null) {
         return 0;
      } else {
         try {
            SetContainer var1 = new SetContainer(0, true, 0);
            PrintStringContainer var2 = new PrintStringContainer(0, true, 0, this.gender, 1, 1);
            EndContainer var3 = new EndContainer();
            ASN1Container[] var4 = new ASN1Container[]{var1, var2, var3};
            this.asn1TemplateValue = new ASN1Template(var4);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var5) {
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
      Gender var1 = new Gender();
      if (this.gender != null) {
         var1.gender = this.gender;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Gender) {
         Gender var2 = (Gender)var1;
         return this.gender.equalsIgnoreCase(var2.gender);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.gender);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.gender = null;
      this.asn1TemplateValue = null;
   }
}

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
public class CountryOfResidence extends X501Attribute {
   private String country;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public CountryOfResidence() {
      super(22, "countryOfResidence");
      this.country = "";
   }

   /** @deprecated */
   public CountryOfResidence(String var1) throws AttributeException {
      this();
      this.setCountry(var1);
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
            this.country = var5.getValueAsString();
         } catch (ASN_Exception var7) {
            throw new AttributeException("Could not BER decode CountryOfResidence.");
         }
      }
   }

   /** @deprecated */
   public void setCountry(String var1) throws AttributeException {
      if (var1 != null) {
         if (var1.length() != 2) {
            throw new AttributeException("Country of Residence should be 2 characters long.");
         }

         this.reset();
         this.country = var1;
      }

   }

   /** @deprecated */
   public String getCountry() {
      return this.country;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.country == null) {
         return 0;
      } else {
         try {
            SetContainer var1 = new SetContainer(0, true, 0);
            PrintStringContainer var2 = new PrintStringContainer(0, true, 0, this.country, 2, 2);
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
      CountryOfResidence var1 = new CountryOfResidence();
      if (this.country != null) {
         var1.country = this.country;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof CountryOfResidence) {
         CountryOfResidence var2 = (CountryOfResidence)var1;
         return this.country.equalsIgnoreCase(var2.country);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.country);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.country = null;
      this.asn1TemplateValue = null;
   }
}

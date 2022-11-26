package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BMPStringContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.cert.AttributeException;

/** @deprecated */
public class FriendlyName extends X501Attribute {
   private String friendlyName;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public FriendlyName() {
      super(3, "FriendlyName");
      this.friendlyName = "";
   }

   /** @deprecated */
   public FriendlyName(String var1) {
      this();
      this.setFriendlyName(var1);
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
            BMPStringContainer var5 = new BMPStringContainer(0);
            ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};
            ASN1.berDecode(var1, var2, var6);
            this.friendlyName = var5.getValueAsString();
         } catch (ASN_Exception var7) {
            throw new AttributeException("Could not BER decode FriendlyName.");
         }
      }
   }

   /** @deprecated */
   public void setFriendlyName(String var1) {
      if (var1 != null) {
         this.reset();
         this.friendlyName = var1;
      }

   }

   /** @deprecated */
   public String getFriendlyName() {
      return this.friendlyName == null ? null : this.friendlyName;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.friendlyName == null) {
         return 0;
      } else {
         try {
            SetContainer var1 = new SetContainer(0, true, 0);
            BMPStringContainer var2 = new BMPStringContainer(0, true, 0, this.friendlyName);
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
      FriendlyName var1 = new FriendlyName();
      if (this.friendlyName != null) {
         var1.friendlyName = this.friendlyName;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof FriendlyName) {
         FriendlyName var2 = (FriendlyName)var1;
         return this.friendlyName.equals(var2.friendlyName);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.friendlyName);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.friendlyName = null;
      this.asn1TemplateValue = null;
   }
}

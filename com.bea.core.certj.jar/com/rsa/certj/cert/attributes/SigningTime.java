package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.asn1.UTCTimeContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.cert.AttributeException;
import java.util.Date;

/** @deprecated */
public class SigningTime extends X501Attribute {
   private Date signingTime;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public SigningTime() {
      super(0, "SigningTime");
   }

   /** @deprecated */
   public SigningTime(Date var1) {
      this();
      this.setSigningTime(var1);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();
         SetContainer var3 = new SetContainer(0);
         EndContainer var4 = new EndContainer();
         UTCTimeContainer var5 = new UTCTimeContainer(0);
         ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};

         try {
            ASN1.berDecode(var1, var2, var6);
         } catch (ASN_Exception var8) {
            throw new AttributeException("Could not BER decode SigningTime.");
         }

         this.signingTime = new Date(var5.theTime.getTime());
      }
   }

   /** @deprecated */
   public void setSigningTime(Date var1) {
      if (var1 != null) {
         this.reset();
         this.signingTime = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getSigningTime() {
      return this.signingTime == null ? null : new Date(this.signingTime.getTime());
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.signingTime == null) {
         return 0;
      } else {
         SetContainer var1 = new SetContainer(0, true, 0);
         UTCTimeContainer var2 = new UTCTimeContainer(0, true, 0, this.signingTime);
         EndContainer var3 = new EndContainer();
         ASN1Container[] var4 = new ASN1Container[]{var1, var2, var3};
         this.asn1TemplateValue = new ASN1Template(var4);

         try {
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
      SigningTime var1 = new SigningTime();
      if (this.signingTime != null) {
         var1.signingTime = new Date(this.signingTime.getTime());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof SigningTime) {
         SigningTime var2 = (SigningTime)var1;
         if (this.signingTime == null) {
            return var2.signingTime == null;
         } else {
            return var2.signingTime == null ? false : this.signingTime.equals(var2.signingTime);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.signingTime);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.signingTime = null;
      this.asn1TemplateValue = null;
   }
}

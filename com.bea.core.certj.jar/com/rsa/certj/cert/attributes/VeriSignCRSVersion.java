package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.cert.AttributeException;

/** @deprecated */
public class VeriSignCRSVersion extends X501Attribute {
   private int version;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignCRSVersion() {
      super(12, "VeriSignCRSVersion");
      this.version = -1;
   }

   /** @deprecated */
   public VeriSignCRSVersion(int var1) {
      this();
      this.setVersion(var1);
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
            IntegerContainer var5 = new IntegerContainer(0);
            ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};
            ASN1.berDecode(var1, var2, var6);
            this.version = var5.getValueAsInt();
         } catch (ASN_Exception var7) {
            throw new AttributeException("Could not BER decode VeriSignCRSVersion.");
         }
      }
   }

   /** @deprecated */
   public void setVersion(int var1) {
      this.reset();
      if (var1 >= 0) {
         this.version = var1;
      }

   }

   /** @deprecated */
   public int getVersion() {
      return this.version;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.version == -1) {
         this.version = 0;
      }

      SetContainer var1 = new SetContainer(0, true, 0);
      IntegerContainer var2 = new IntegerContainer(0, true, 0, this.version);
      EndContainer var3 = new EndContainer();
      ASN1Container[] var4 = new ASN1Container[]{var1, var2, var3};
      this.asn1TemplateValue = new ASN1Template(var4);

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
         return 0;
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
      VeriSignCRSVersion var1 = new VeriSignCRSVersion();
      if (this.version != -1) {
         var1.version = this.version;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof VeriSignCRSVersion) {
         VeriSignCRSVersion var2 = (VeriSignCRSVersion)var1;
         return this.version == var2.version;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + this.version;
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.version = -1;
      this.asn1TemplateValue = null;
   }
}

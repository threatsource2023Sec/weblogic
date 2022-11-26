package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CompatibilityType;
import com.rsa.certj.cert.AttributeException;

/** @deprecated */
abstract class IntegerPrintableStringAttr extends X501Attribute {
   /** @deprecated */
   protected int value = -1;
   private ASN1Template asn1TemplateValue;

   /** @deprecated */
   protected IntegerPrintableStringAttr(int var1, String var2) {
      super(var1, var2);
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
            IntegerContainer var5 = new IntegerContainer(65536);
            PrintStringContainer var6 = new PrintStringContainer(65536);
            ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
            ASN1.berDecode(var1, var2, var7);
            if (var5.dataPresent) {
               this.value = var5.getValueAsInt();
            } else {
               if (!var6.dataPresent) {
                  throw new AttributeException("Unexpected encoding.");
               }

               String var8 = var6.getValueAsString();
               this.value = Integer.parseInt(var8);
            }

         } catch (Exception var9) {
            throw new AttributeException("Could not BER decode attribute.");
         }
      }
   }

   /** @deprecated */
   protected void setValue(int var1) {
      this.reset();
      if (var1 >= 0) {
         this.value = var1;
      }

   }

   /** @deprecated */
   protected int getValue() {
      return this.value;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.value == -1) {
         return 0;
      } else {
         SetContainer var1 = new SetContainer(0, true, 0);
         EndContainer var2 = new EndContainer();
         ASN1Container[] var3 = new ASN1Container[]{var1, null, var2};

         try {
            if (CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_SCEP)) {
               String var4 = Integer.toString(this.value);
               PrintStringContainer var5 = new PrintStringContainer(0, true, 0, var4);
               var3[1] = var5;
            } else {
               IntegerContainer var7 = new IntegerContainer(0, true, 0, this.value);
               var3[1] = var7;
            }

            this.asn1TemplateValue = new ASN1Template(var3);
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
         byte var5;
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            int var4 = var3;
            return var4;
         } catch (ASN_Exception var9) {
            var5 = 0;
         } finally {
            this.asn1Template = null;
         }

         return var5;
      }
   }

   /** @deprecated */
   protected void copyValues(IntegerPrintableStringAttr var1) {
      var1.value = this.value;
      super.copyValues(var1);
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof IntegerPrintableStringAttr) {
         IntegerPrintableStringAttr var2 = (IntegerPrintableStringAttr)var1;
         return var2.value == this.value;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + this.value;
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.value = -1;
      this.asn1TemplateValue = null;
   }
}

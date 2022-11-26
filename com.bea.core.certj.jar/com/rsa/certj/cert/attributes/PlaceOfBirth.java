package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BMPStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.asn1.UniversalStringContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.cert.AttributeException;

/** @deprecated */
public class PlaceOfBirth extends X501Attribute {
   private String placeOfBirth;
   private int stringType;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public PlaceOfBirth() {
      super(19, "placeOfBirth");
   }

   /** @deprecated */
   public PlaceOfBirth(String var1, int var2) throws AttributeException {
      this();
      this.setPlaceOfBirth(var1, var2);
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
            ChoiceContainer var5 = new ChoiceContainer(0);
            PrintStringContainer var6 = new PrintStringContainer(0, 1, -1);
            TeletexStringContainer var7 = new TeletexStringContainer(0, 1, -1);
            UniversalStringContainer var8 = new UniversalStringContainer(0, 1, -1);
            BMPStringContainer var9 = new BMPStringContainer(0, 1, -1);
            UTF8StringContainer var10 = new UTF8StringContainer(0);
            IA5StringContainer var11 = new IA5StringContainer(0, 1, -1);
            ASN1Container[] var12 = new ASN1Container[]{var3, var5, var6, var7, var8, var9, var10, var11, var4, var4};
            ASN1.berDecode(var1, var2, var12);
            if (var6.dataPresent) {
               this.stringType = 4864;
               this.placeOfBirth = var6.getValueAsString();
            } else if (var7.dataPresent) {
               this.stringType = 5120;
               this.placeOfBirth = var7.getValueAsString();
            } else if (var8.dataPresent) {
               this.stringType = 7168;
               this.placeOfBirth = var8.getValueAsString();
            } else if (var9.dataPresent) {
               this.stringType = 7680;
               this.placeOfBirth = var9.getValueAsString();
            } else if (var10.dataPresent) {
               this.stringType = 3072;
               this.placeOfBirth = this.utf8Decode(var10.data, var10.dataOffset, var10.dataLen);
            } else {
               if (!var11.dataPresent) {
                  throw new AttributeException("DirectoryString expected.");
               }

               this.stringType = 5632;
               this.placeOfBirth = var11.getValueAsString();
            }

         } catch (Exception var13) {
            throw new AttributeException("Could not BER decode placeOfBirth.", var13);
         }
      }
   }

   /** @deprecated */
   public void setPlaceOfBirth(String var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("PlaceOfBirth is null.");
      } else {
         this.reset();
         this.placeOfBirth = var1;
         if (var2 != 4864 && var2 != 5120 && var2 != 7168 && var2 != 3072 && var2 != 7680 && var2 != 5632) {
            throw new AttributeException("Invalid String Type.");
         } else {
            this.stringType = var2;
         }
      }
   }

   /** @deprecated */
   public String getPlaceOfBirth() {
      return this.placeOfBirth;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.placeOfBirth == null) {
         return 0;
      } else {
         if (this.stringType == 0) {
            this.stringType = 3072;
         }

         try {
            EndContainer var1 = new EndContainer();
            SetContainer var2 = new SetContainer(0, true, 0);
            ChoiceContainer var3 = new ChoiceContainer(0, 0);
            Object var4;
            switch (this.stringType) {
               case 3072:
                  byte[] var5 = this.utf8Encode(this.placeOfBirth);
                  if (var5.length < 2) {
                     return 0;
                  }

                  var4 = new UTF8StringContainer(0, true, 0, var5, 2, var5.length - 2);
                  break;
               case 4864:
                  var4 = new PrintStringContainer(0, true, 0, this.placeOfBirth);
                  break;
               case 5120:
                  var4 = new TeletexStringContainer(0, true, 0, this.placeOfBirth);
                  break;
               case 5632:
                  var4 = new IA5StringContainer(0, true, 0, this.placeOfBirth);
                  break;
               case 7168:
                  var4 = new UniversalStringContainer(0, true, 0, this.placeOfBirth);
                  break;
               case 7680:
                  var4 = new BMPStringContainer(0, true, 0, this.placeOfBirth);
                  break;
               default:
                  return 0;
            }

            ASN1Container[] var7 = new ASN1Container[]{var2, var3, (ASN1Container)var4, var1, var1};
            this.asn1TemplateValue = new ASN1Template(var7);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (Exception var6) {
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
      PlaceOfBirth var1 = new PlaceOfBirth();
      if (this.placeOfBirth != null) {
         var1.placeOfBirth = this.placeOfBirth;
      }

      var1.stringType = this.stringType;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PlaceOfBirth) {
         PlaceOfBirth var2 = (PlaceOfBirth)var1;
         if (this.placeOfBirth == null) {
            return var2.placeOfBirth == null;
         } else {
            return var2.placeOfBirth == null ? false : this.placeOfBirth.equals(var2.placeOfBirth);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.placeOfBirth);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.placeOfBirth = null;
      this.asn1TemplateValue = null;
   }
}

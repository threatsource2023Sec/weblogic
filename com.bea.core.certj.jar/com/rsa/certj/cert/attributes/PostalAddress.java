package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BMPStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.asn1.UniversalStringContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.cert.AttributeException;
import java.util.Vector;

/** @deprecated */
public class PostalAddress extends X501Attribute {
   private Vector postalAddress;
   private Vector stringType;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public PostalAddress() {
      super(16, "postalAddress");
      this.postalAddress = new Vector();
      this.stringType = new Vector();
   }

   /** @deprecated */
   public PostalAddress(String var1, int var2) throws AttributeException {
      this();
      this.addPostalAddress(var1, var2);
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
            OfContainer var7 = new OfContainer(0, 12288, new EncodedContainer(65280));
            ASN1Container[] var8 = new ASN1Container[]{var7};
            ASN1.berDecode(var5.data, var5.dataOffset, var8);

            for(int var10 = 0; var10 < var7.getContainerCount(); ++var10) {
               EncodedContainer var9 = (EncodedContainer)var7.containerAt(var10);
               ChoiceContainer var11 = new ChoiceContainer(0);
               PrintStringContainer var12 = new PrintStringContainer(0, 1, -1);
               TeletexStringContainer var13 = new TeletexStringContainer(0, 1, -1);
               UniversalStringContainer var14 = new UniversalStringContainer(0, 1, -1);
               BMPStringContainer var15 = new BMPStringContainer(0, 1, -1);
               UTF8StringContainer var16 = new UTF8StringContainer(0);
               IA5StringContainer var17 = new IA5StringContainer(0, 1, -1);
               ASN1Container[] var18 = new ASN1Container[]{var11, var12, var13, var14, var15, var16, var17, var4};
               ASN1.berDecode(var9.data, var9.dataOffset, var18);
               if (var12.dataPresent) {
                  this.stringType.addElement(new Integer(4864));
                  this.postalAddress.addElement(var12.getValueAsString());
               } else if (var13.dataPresent) {
                  this.stringType.addElement(new Integer(5120));
                  this.postalAddress.addElement(var13.getValueAsString());
               } else if (var14.dataPresent) {
                  this.stringType.addElement(new Integer(7168));
                  this.postalAddress.addElement(var14.getValueAsString());
               } else if (var15.dataPresent) {
                  this.stringType.addElement(new Integer(7680));
                  this.postalAddress.addElement(var15.getValueAsString());
               } else if (var16.dataPresent) {
                  this.stringType.addElement(new Integer(3072));
                  this.postalAddress.addElement(this.utf8Decode(var16.data, var16.dataOffset, var16.dataLen));
               } else {
                  if (!var17.dataPresent) {
                     throw new AttributeException("DirectoryString expected.");
                  }

                  this.stringType.addElement(new Integer(5632));
                  this.postalAddress.addElement(var17.getValueAsString());
               }
            }

         } catch (ASN_Exception var19) {
            throw new AttributeException("Could not BER decode postalAddress.", var19);
         }
      }
   }

   /** @deprecated */
   public void addPostalAddress(String var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("PostalAddress is null.");
      } else if (var2 != 4864 && var2 != 5120 && var2 != 7168 && var2 != 3072 && var2 != 7680 && var2 != 5632) {
         throw new AttributeException("Invalid String Type.");
      } else {
         this.postalAddress.addElement(var1);
         this.stringType.addElement(new Integer(var2));
      }
   }

   /** @deprecated */
   public String[] getPostalAddress() {
      String[] var1 = new String[this.postalAddress.size()];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = (String)this.postalAddress.elementAt(var2);
      }

      return var1;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.postalAddress == null) {
         return 0;
      } else {
         try {
            OfContainer var1 = new OfContainer(0, true, 0, 12288, new EncodedContainer(65280));
            EndContainer var2 = new EndContainer();
            SetContainer var3 = new SetContainer(0, true, 0);
            ChoiceContainer var4 = new ChoiceContainer(0);

            for(int var9 = 0; var9 < this.postalAddress.size(); ++var9) {
               int var10 = (Integer)this.stringType.elementAt(var9);
               if (var10 == 0) {
                  var10 = 3072;
               }

               Object var5;
               switch (var10) {
                  case 3072:
                     byte[] var11 = this.utf8Encode((String)this.postalAddress.elementAt(var9));
                     if (var11.length < 2) {
                        return 0;
                     }

                     var5 = new UTF8StringContainer(0, true, 0, var11, 2, var11.length - 2);
                     break;
                  case 4864:
                     var5 = new PrintStringContainer(0, true, 0, (String)this.postalAddress.elementAt(var9));
                     break;
                  case 5120:
                     var5 = new TeletexStringContainer(0, true, 0, (String)this.postalAddress.elementAt(var9));
                     break;
                  case 5632:
                     var5 = new IA5StringContainer(0, true, 0, (String)this.postalAddress.elementAt(var9));
                     break;
                  case 7168:
                     var5 = new UniversalStringContainer(0, true, 0, (String)this.postalAddress.elementAt(var9));
                     break;
                  case 7680:
                     var5 = new BMPStringContainer(0, true, 0, (String)this.postalAddress.elementAt(var9));
                     break;
                  default:
                     return 0;
               }

               ASN1Container[] var15 = new ASN1Container[]{var4, (ASN1Container)var5, var2};
               ASN1Template var6 = new ASN1Template(var15);
               byte[] var7 = new byte[var6.derEncodeInit()];
               int var12 = var6.derEncode(var7, 0);
               EncodedContainer var8 = new EncodedContainer(0, true, 0, var7, 0, var12);
               var1.addContainer(var8);
            }

            ASN1Container[] var14 = new ASN1Container[]{var3, var1, var2};
            this.asn1TemplateValue = new ASN1Template(var14);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (Exception var13) {
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
      PostalAddress var1 = new PostalAddress();

      for(int var2 = 0; var2 < this.postalAddress.size(); ++var2) {
         var1.postalAddress.addElement(this.postalAddress.elementAt(var2));
         var1.stringType.addElement(this.stringType.elementAt(var2));
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PostalAddress) {
         PostalAddress var2 = (PostalAddress)var1;
         if (this.postalAddress.size() != var2.postalAddress.size()) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.postalAddress.size(); ++var3) {
               if (!((String)this.postalAddress.elementAt(var3)).equals(var2.postalAddress.elementAt(var3))) {
                  return false;
               }

               if (!((Integer)this.stringType.elementAt(var3)).equals(var2.stringType.elementAt(var3))) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.postalAddress);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.stringType);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.postalAddress = new Vector();
      this.stringType = new Vector();
      this.asn1TemplateValue = null;
   }
}

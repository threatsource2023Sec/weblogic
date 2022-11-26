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
import com.rsa.certj.cert.AttributeException;
import java.util.Arrays;

/** @deprecated */
public class ChallengePassword extends X501Attribute {
   private char[] thePassword;
   private int stringType;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public ChallengePassword() {
      super(1, "ChallengePassword");
   }

   /** @deprecated */
   public ChallengePassword(char[] var1, int var2, int var3, int var4) throws AttributeException {
      this();
      this.setChallengePassword(var1, var2, var3, var4);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();
         SetContainer var3 = new SetContainer(0);
         EndContainer var4 = new EndContainer();
         ChoiceContainer var5 = new ChoiceContainer(0);

         try {
            PrintStringContainer var6 = new PrintStringContainer(0);
            TeletexStringContainer var7 = new TeletexStringContainer(0);
            UniversalStringContainer var8 = new UniversalStringContainer(0);
            BMPStringContainer var9 = new BMPStringContainer(0);
            UTF8StringContainer var10 = new UTF8StringContainer(0);
            IA5StringContainer var11 = new IA5StringContainer(0);
            ASN1Container[] var12 = new ASN1Container[]{var3, var5, var6, var7, var8, var9, var10, var11, var4, var4};
            ASN1.berDecode(var1, var2, var12);
            if (var6.dataPresent) {
               this.stringType = 4864;
               this.thePassword = var6.getValueAsString().toCharArray();
            } else if (var7.dataPresent) {
               this.stringType = 5120;
               this.thePassword = var7.getValueAsString().toCharArray();
            } else if (var8.dataPresent) {
               this.stringType = 7168;
               this.thePassword = var8.getValueAsString().toCharArray();
            } else if (var9.dataPresent) {
               this.stringType = 7680;
               this.thePassword = var9.getValueAsString().toCharArray();
            } else if (var10.dataPresent) {
               this.stringType = 3072;
               String var13 = this.utf8Decode(var10.data, var10.dataOffset, var10.dataLen);
               this.thePassword = var13.toCharArray();
            } else if (var11.dataPresent) {
               this.stringType = 5632;
               this.thePassword = var11.getValueAsString().toCharArray();
            }

         } catch (ASN_Exception var14) {
            throw new AttributeException("Could not BER decode ChallengePassword.");
         }
      }
   }

   /** @deprecated */
   public void setChallengePassword(char[] var1, int var2, int var3, int var4) throws AttributeException {
      if (var1 != null && var3 != 0) {
         this.thePassword = new char[var3];
         System.arraycopy(var1, var2, this.thePassword, 0, var3);
         if (var4 != 4864 && var4 != 5120 && var4 != 7168 && var4 != 3072 && var4 != 7680 && var4 != 5632) {
            throw new AttributeException("Invalid String Type.");
         } else {
            this.stringType = var4;
         }
      } else {
         throw new AttributeException("Password is null.");
      }
   }

   /** @deprecated */
   public char[] getChallengePassword() {
      if (this.thePassword == null) {
         return null;
      } else {
         char[] var1 = new char[this.thePassword.length];

         for(int var2 = 0; var2 < this.thePassword.length; ++var2) {
            var1[var2] = (char)(this.thePassword[var2] & 255);
         }

         return var1;
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.thePassword == null) {
         return 0;
      } else {
         if (this.stringType == 0) {
            this.stringType = 3072;
         }

         try {
            EndContainer var1 = new EndContainer();
            SetContainer var2 = new SetContainer(0, true, 0);
            ChoiceContainer var3 = new ChoiceContainer(0, 0);
            byte[] var5 = new byte[this.thePassword.length];

            for(int var6 = 0; var6 < this.thePassword.length; ++var6) {
               var5[var6] = (byte)this.thePassword[var6];
            }

            Object var4;
            switch (this.stringType) {
               case 3072:
                  byte[] var8 = this.utf8Encode(new String(var5));
                  if (var8.length < 2) {
                     return 0;
                  }

                  var4 = new UTF8StringContainer(0, true, 0, var8, 2, var8.length - 2);
                  break;
               case 4864:
                  var4 = new PrintStringContainer(0, true, 0, var5, 0, var5.length);
                  break;
               case 5120:
                  var4 = new TeletexStringContainer(0, true, 0, var5, 0, var5.length);
                  break;
               case 5632:
                  var4 = new IA5StringContainer(0, true, 0, var5, 0, var5.length);
                  break;
               case 7168:
                  var4 = new UniversalStringContainer(0, true, 0, var5, 0, var5.length, 1);
                  break;
               case 7680:
                  var4 = new BMPStringContainer(0, true, 0, var5, 0, var5.length, 1);
                  break;
               default:
                  return 0;
            }

            ASN1Container[] var9 = new ASN1Container[]{var2, var3, (ASN1Container)var4, var1, var1};
            this.asn1TemplateValue = new ASN1Template(var9);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (Exception var7) {
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
      ChallengePassword var1 = new ChallengePassword();
      if (this.thePassword != null) {
         var1.thePassword = (char[])((char[])this.thePassword.clone());
      }

      var1.stringType = this.stringType;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ChallengePassword) {
         ChallengePassword var2 = (ChallengePassword)var1;
         if (this.thePassword != null) {
            if (var2.thePassword == null) {
               return false;
            }

            if (this.thePassword.length != var2.thePassword.length) {
               return false;
            }

            for(int var3 = 0; var3 < this.thePassword.length; ++var3) {
               if (this.thePassword[var3] != var2.thePassword[var3]) {
                  return false;
               }
            }
         } else if (var2.thePassword != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;
      if (this.thePassword != null) {
         var1 ^= Arrays.hashCode(this.thePassword);
      }

      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.asn1TemplateValue = null;
      if (this.thePassword != null) {
         for(int var1 = 0; var1 < this.thePassword.length; ++var1) {
            this.thePassword[var1] = 0;
         }

         this.thePassword = null;
      }

   }

   /** @deprecated */
   protected void finalize() {
      this.reset();
   }
}

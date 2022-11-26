package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.NumericStringContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;

/** @deprecated */
public class ORName implements Serializable, Cloneable {
   /** @deprecated */
   public static final int COUNTRY_NAME = 0;
   /** @deprecated */
   public static final int ADMIN_DOMAIN_NAME = 1;
   /** @deprecated */
   public static final int PRIVATE_DOMAIN_NAME = 2;
   /** @deprecated */
   public static final int POSTAL_CODE = 3;
   /** @deprecated */
   public static final int PHYSICAL_DELIVERY_COUNTRY_NAME = 4;
   private int flag;
   private String numeric;
   private String printable;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   private static final int COUNTRYNAME_SPECIAL = 4194305;
   private static final int ADMIN_SPECIAL = 4194306;

   /** @deprecated */
   public ORName(int var1, byte[] var2, int var3, int var4) throws NameException {
      this.special = var4;
      this.flag = var1;
      if (var2 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            ChoiceContainer var5 = null;
            EndContainer var8 = new EndContainer();
            switch (var1) {
               case 0:
                  var5 = new ChoiceContainer(var4 | 4194305);
                  break;
               case 1:
                  var5 = new ChoiceContainer(var4 | 4194306);
                  break;
               case 2:
               case 3:
               case 4:
                  var5 = new ChoiceContainer(var4);
            }

            NumericStringContainer var6;
            PrintStringContainer var7;
            switch (var1) {
               case 0:
               case 4:
                  var6 = new NumericStringContainer(0, 1, 3);
                  var7 = new PrintStringContainer(0, 1, 2);
                  break;
               case 1:
               case 2:
               case 3:
                  var6 = new NumericStringContainer(0, 1, 16);
                  var7 = new PrintStringContainer(0, 1, 16);
                  break;
               default:
                  throw new NameException("Unknown ORName name type!");
            }

            ASN1Container[] var9 = new ASN1Container[]{var5, var6, var7, var8};
            ASN1.berDecode(var2, var3, var9);
            if (var6.dataPresent) {
               this.numeric = new String(var6.data, var6.dataOffset, var6.dataLen);
            }

            if (var7.dataPresent) {
               this.printable = new String(var7.data, var7.dataOffset, var7.dataLen);
            }

         } catch (ASN_Exception var10) {
            throw new NameException("Cannot decode the BER of the ORName.");
         }
      }
   }

   /** @deprecated */
   public ORName(int var1) {
      this.flag = var1;
   }

   /** @deprecated */
   public int getNameType() {
      return this.flag;
   }

   /** @deprecated */
   public void setNumericValue(String var1) {
      if (var1 != null) {
         this.numeric = var1;
      }

   }

   /** @deprecated */
   public void setPrintableValue(String var1) {
      if (var1 != null) {
         this.printable = var1;
      }

   }

   /** @deprecated */
   public String getNumericValue() {
      return this.numeric;
   }

   /** @deprecated */
   public String getPrintableValue() {
      return this.printable;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.numeric != null) {
         var1.append(this.numeric);
      }

      if (this.printable != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.printable);
      }

      return var1.toString();
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws NameException {
      if (var0 == null) {
         throw new NameException("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new NameException("Unable to determine length of the BER");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      this.special = var1;
      return this.derEncodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified array is null.");
      } else {
         try {
            if (this.asn1Template == null || var3 != this.special) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode ORName");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            EndContainer var4 = new EndContainer();
            ChoiceContainer var1;
            switch (this.flag) {
               case 0:
                  var1 = new ChoiceContainer(this.special | 4194305, 0);
                  break;
               case 1:
                  var1 = new ChoiceContainer(this.special | 4194306, 0);
                  break;
               case 2:
               case 3:
               case 4:
                  var1 = new ChoiceContainer(this.special, 0);
                  break;
               default:
                  return 0;
            }

            ASN1Container[] var5;
            if (this.numeric != null) {
               NumericStringContainer var2;
               switch (this.flag) {
                  case 0:
                  case 4:
                     var2 = new NumericStringContainer(0, true, 0, this.numeric, 1, 3);
                     break;
                  case 1:
                  case 2:
                  case 3:
                     var2 = new NumericStringContainer(0, true, 0, this.numeric, 1, 16);
                     break;
                  default:
                     return 0;
               }

               var5 = new ASN1Container[]{var1, var2, var4};
               this.asn1Template = new ASN1Template(var5);
            } else {
               PrintStringContainer var3;
               switch (this.flag) {
                  case 0:
                  case 4:
                     var3 = new PrintStringContainer(0, true, 0, this.printable, 1, 2);
                     break;
                  case 1:
                  case 2:
                  case 3:
                     var3 = new PrintStringContainer(0, true, 0, this.printable, 1, 16);
                     break;
                  default:
                     return 0;
               }

               var5 = new ASN1Container[]{var1, var3, var4};
               this.asn1Template = new ASN1Template(var5);
            }

            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var6) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ORName) {
         ORName var2 = (ORName)var1;
         if (this.flag != var2.flag) {
            return false;
         } else {
            if (this.numeric != null) {
               if (!this.numeric.equals(var2.numeric)) {
                  return false;
               }
            } else if (var2.numeric != null) {
               return false;
            }

            if (this.printable != null) {
               if (!this.printable.equals(var2.printable)) {
                  return false;
               }
            } else if (var2.printable != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.flag;
      if (this.numeric != null) {
         var1 ^= this.numeric.hashCode();
      }

      if (this.printable != null) {
         var1 ^= this.printable.hashCode();
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ORName var1 = new ORName(this.flag);
      if (this.printable != null) {
         var1.printable = this.printable;
      }

      if (this.numeric != null) {
         var1.numeric = this.numeric;
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

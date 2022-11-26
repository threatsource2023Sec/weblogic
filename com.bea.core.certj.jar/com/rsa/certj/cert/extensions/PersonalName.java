package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class PersonalName implements Serializable, Cloneable {
   private String surname;
   private String givenName;
   private String initials;
   private String generation;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   private static final int SURNAME_SPECIAL = 8388608;
   private static final int GIVEN_NAME_SPECIAL = 8454145;
   private static final int INITIALS_SPECIAL = 8454146;
   private static final int GENERATION_SPECIAL = 8454147;

   /** @deprecated */
   public PersonalName(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SetContainer var4 = new SetContainer(var3);
            PrintStringContainer var5 = new PrintStringContainer(8388608, 1, 40);
            PrintStringContainer var6 = new PrintStringContainer(8454145, 1, 16);
            PrintStringContainer var7 = new PrintStringContainer(8454146, 1, 5);
            PrintStringContainer var8 = new PrintStringContainer(8454147, 1, 3);
            EndContainer var9 = new EndContainer();
            ASN1Container[] var10 = new ASN1Container[]{var4, var5, var6, var7, var8, var9};
            ASN1.berDecode(var1, var2, var10);
            if (!var5.dataPresent) {
               throw new NameException("Surname is missing.");
            } else {
               this.surname = var5.getValueAsString();
               if (var6.dataPresent) {
                  this.givenName = var6.getValueAsString();
               }

               if (var7.dataPresent) {
                  this.initials = var7.getValueAsString();
               }

               if (var8.dataPresent) {
                  this.generation = var8.getValueAsString();
               }

            }
         } catch (ASN_Exception var11) {
            throw new NameException("Cannot decode the BER of the Personal name.");
         }
      }
   }

   /** @deprecated */
   public PersonalName() {
   }

   /** @deprecated */
   public void setSurname(String var1) {
      if (var1 != null) {
         this.surname = var1;
      }

   }

   /** @deprecated */
   public void setGivenName(String var1) {
      if (var1 != null) {
         this.givenName = var1;
      }

   }

   /** @deprecated */
   public void setInitials(String var1) {
      if (var1 != null) {
         this.initials = var1;
      }

   }

   /** @deprecated */
   public void setGenerationQualifier(String var1) {
      if (var1 != null) {
         this.generation = var1;
      }

   }

   /** @deprecated */
   public String getSurname() {
      return this.surname;
   }

   /** @deprecated */
   public String getGivenName() {
      return this.givenName;
   }

   /** @deprecated */
   public String getInitials() {
      return this.initials;
   }

   /** @deprecated */
   public String getGenerationQualifier() {
      return this.generation;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.surname != null) {
         var1.append(this.surname);
      }

      if (this.givenName != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.givenName);
      }

      if (this.initials != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.initials);
      }

      if (this.generation != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.generation);
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
               int var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode PersonalName.");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode PersonalName.");
         }
      }
   }

   private int derEncodeInit() {
      try {
         byte var1 = 0;
         SetContainer var2 = new SetContainer(this.special, true, 0);
         EndContainer var3 = new EndContainer();
         if (this.surname == null) {
            return 0;
         } else {
            PrintStringContainer var4 = new PrintStringContainer(8388608, true, 0, this.surname, 1, 40);
            PrintStringContainer var5 = null;
            PrintStringContainer var6 = null;
            PrintStringContainer var7 = null;
            if (this.givenName != null) {
               var7 = new PrintStringContainer(8454145, true, 0, this.givenName, 1, 16);
               var1 = 1;
            }

            if (this.initials != null) {
               var6 = new PrintStringContainer(8454146, true, 0, this.initials, 1, 5);
               if (var1 == 0) {
                  var1 = 2;
               } else {
                  var1 = 3;
               }
            }

            if (this.generation != null) {
               var5 = new PrintStringContainer(8454147, true, 0, this.generation, 1, 3);
               if (var1 == 0) {
                  var1 = 4;
               } else if (var1 == 1) {
                  var1 = 5;
               } else if (var1 == 2) {
                  var1 = 6;
               } else {
                  var1 = 7;
               }
            }

            switch (var1) {
               case 0:
                  ASN1Container[] var8 = new ASN1Container[]{var2, var4, var3};
                  this.asn1Template = new ASN1Template(var8);
                  break;
               case 1:
                  ASN1Container[] var9 = new ASN1Container[]{var2, var4, var7, var3};
                  this.asn1Template = new ASN1Template(var9);
                  break;
               case 2:
                  ASN1Container[] var10 = new ASN1Container[]{var2, var4, var6, var3};
                  this.asn1Template = new ASN1Template(var10);
                  break;
               case 3:
                  ASN1Container[] var11 = new ASN1Container[]{var2, var4, var7, var6, var3};
                  this.asn1Template = new ASN1Template(var11);
                  break;
               case 4:
                  ASN1Container[] var12 = new ASN1Container[]{var2, var4, var5, var3};
                  this.asn1Template = new ASN1Template(var12);
                  break;
               case 5:
                  ASN1Container[] var13 = new ASN1Container[]{var2, var4, var7, var5, var3};
                  this.asn1Template = new ASN1Template(var13);
                  break;
               case 6:
                  ASN1Container[] var14 = new ASN1Container[]{var2, var4, var6, var5, var3};
                  this.asn1Template = new ASN1Template(var14);
                  break;
               case 7:
                  ASN1Container[] var15 = new ASN1Container[]{var2, var4, var7, var6, var5, var3};
                  this.asn1Template = new ASN1Template(var15);
                  break;
               default:
                  return 0;
            }

            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var16) {
         return 0;
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PersonalName) {
         PersonalName var2 = (PersonalName)var1;
         if (this.surname != null) {
            if (!this.surname.equals(var2.surname)) {
               return false;
            }
         } else if (var2.surname != null) {
            return false;
         }

         if (this.givenName != null) {
            if (!this.givenName.equals(var2.givenName)) {
               return false;
            }
         } else if (var2.givenName != null) {
            return false;
         }

         if (this.initials != null) {
            if (!this.initials.equals(var2.initials)) {
               return false;
            }
         } else if (var2.initials != null) {
            return false;
         }

         if (this.generation != null) {
            if (!this.generation.equals(var2.generation)) {
               return false;
            }
         } else if (var2.generation != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];

         try {
            this.getDEREncoding(var2, 0, 0);
         } catch (NameException var4) {
            return 0;
         }

         return Arrays.hashCode(var2);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      PersonalName var1 = new PersonalName();
      if (this.surname != null) {
         var1.surname = this.surname;
      }

      if (this.givenName != null) {
         var1.givenName = this.givenName;
      }

      if (this.initials != null) {
         var1.initials = this.initials;
      }

      if (this.generation != null) {
         var1.generation = this.generation;
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

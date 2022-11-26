package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;

/** @deprecated */
public class PDSParameter implements Serializable, Cloneable {
   private String printable;
   private String teletex;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public PDSParameter(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SetContainer var4 = new SetContainer(var3);
            PrintStringContainer var5 = new PrintStringContainer(65536, 1, 30);
            TeletexStringContainer var6 = new TeletexStringContainer(65536, 1, 30);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var5.dataPresent) {
               this.printable = var5.getValueAsString();
            }

            if (var6.dataPresent) {
               this.teletex = var6.getValueAsString();
            }

         } catch (ASN_Exception var9) {
            throw new NameException("Cannot decode the BER of the RDSParameter.");
         }
      }
   }

   /** @deprecated */
   public PDSParameter() {
   }

   /** @deprecated */
   public void setPrintableString(String var1) {
      if (var1 != null) {
         this.printable = var1;
      }

   }

   /** @deprecated */
   public void setTeletexString(String var1) {
      if (var1 != null) {
         this.teletex = var1;
      }

   }

   /** @deprecated */
   public String getPrintableString() {
      return this.printable;
   }

   /** @deprecated */
   public String getTeletexString() {
      return this.teletex;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.printable != null) {
         var1.append(this.printable);
      }

      if (this.teletex != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.teletex);
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
                  throw new NameException("Unable to encode PDSParameter.");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode PDSParameter.");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            byte var1 = 0;
            SetContainer var2 = new SetContainer(this.special, true, 0);
            EndContainer var3 = new EndContainer();
            TeletexStringContainer var4 = null;
            PrintStringContainer var5 = null;
            if (this.printable != null) {
               var5 = new PrintStringContainer(65536, true, 0, this.printable, 1, 30);
               var1 = 1;
            }

            if (this.teletex != null) {
               var4 = new TeletexStringContainer(65536, true, 0, this.teletex, 1, 30);
               if (var1 == 0) {
                  var1 = 2;
               } else {
                  var1 = 3;
               }
            }

            switch (var1) {
               case 0:
                  ASN1Container[] var6 = new ASN1Container[]{var2, var3};
                  this.asn1Template = new ASN1Template(var6);
                  break;
               case 1:
                  ASN1Container[] var7 = new ASN1Container[]{var2, var5, var3};
                  this.asn1Template = new ASN1Template(var7);
                  break;
               case 2:
                  ASN1Container[] var8 = new ASN1Container[]{var2, var4, var3};
                  this.asn1Template = new ASN1Template(var8);
                  break;
               case 3:
                  ASN1Container[] var9 = new ASN1Container[]{var2, var5, var4, var3};
                  this.asn1Template = new ASN1Template(var9);
                  break;
               default:
                  return 0;
            }

            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var10) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PDSParameter) {
         PDSParameter var2 = (PDSParameter)var1;
         if (this.printable != null) {
            if (!this.printable.equals(var2.printable)) {
               return false;
            }
         } else if (var2.printable != null) {
            return false;
         }

         if (this.teletex != null) {
            if (!this.teletex.equals(var2.teletex)) {
               return false;
            }
         } else if (var2.teletex != null) {
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
      if (this.printable != null) {
         var1 ^= this.printable.hashCode();
      }

      if (this.teletex != null) {
         var1 ^= this.teletex.hashCode();
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      PDSParameter var1 = new PDSParameter();
      if (this.printable != null) {
         var1.printable = this.printable;
      }

      if (this.teletex != null) {
         var1.teletex = this.teletex;
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

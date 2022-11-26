package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class UnformattedPostalAddress implements Serializable, Cloneable {
   private String[] printableAddress = new String[6];
   private int printIndex;
   private String teletex;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public UnformattedPostalAddress(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SetContainer var4 = new SetContainer(var3);
            EncodedContainer var5 = new EncodedContainer(77824);
            TeletexStringContainer var6 = new TeletexStringContainer(65536, 1, 180);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var5.dataPresent) {
               OfContainer var9 = new OfContainer(65536, 12288, new EncodedContainer(4864));
               ASN1Container[] var10 = new ASN1Container[]{var9};
               ASN1.berDecode(var5.data, var5.dataOffset, var10);
               int var11 = var9.getContainerCount();
               if (var11 > 6) {
                  throw new NameException("Too many entries in PrintableAddress: MAX number is 6.");
               }

               for(int var12 = 0; var12 < var11; ++var12) {
                  ASN1Container var13 = var9.containerAt(var12);
                  PrintStringContainer var14 = new PrintStringContainer(0, 1, 30);
                  ASN1Container[] var15 = new ASN1Container[]{var14};
                  ASN1.berDecode(var13.data, var13.dataOffset, var15);
                  this.printableAddress[this.printIndex] = var14.getValueAsString();
                  ++this.printIndex;
               }
            }

            if (var6.dataPresent) {
               this.teletex = var6.getValueAsString();
            }

         } catch (ASN_Exception var16) {
            throw new NameException("Cannot decode the BER of the UnformattedPostalAddress.");
         }
      }
   }

   /** @deprecated */
   public UnformattedPostalAddress() {
   }

   /** @deprecated */
   public void setPrintableAddress(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 30) {
         throw new NameException("Specified value is too long.");
      } else if (this.printIndex < 5) {
         this.printableAddress[this.printIndex] = var1;
         ++this.printIndex;
      } else {
         throw new NameException("Cannot add PrintableAddress: MAX  number is 6.");
      }
   }

   /** @deprecated */
   public void setTeletexString(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 180) {
         throw new NameException("Specified value is too long.");
      } else {
         this.teletex = var1;
      }
   }

   /** @deprecated */
   public String[] getPrintableAddress() {
      return this.printableAddress;
   }

   /** @deprecated */
   public String getTeletexString() {
      return this.teletex;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.printIndex; ++var2) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.printableAddress[var2]);
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
   public int getDERLen(int var1) throws NameException {
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
                  throw new NameException("Unable to encode UnformattedPostalAddress");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode UnformattedPostalAddress");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            SetContainer var1 = new SetContainer(this.special, true, 0);
            EndContainer var4 = new EndContainer();
            TeletexStringContainer var3;
            ASN1Container[] var5;
            if (this.printIndex > 0) {
               EncodedContainer var2 = this.encodePrintAddress();
               if (this.teletex != null) {
                  var3 = new TeletexStringContainer(65536, true, 0, this.teletex, 1, 180);
                  var5 = new ASN1Container[]{var1, var2, var3, var4};
                  this.asn1Template = new ASN1Template(var5);
               } else {
                  var5 = new ASN1Container[]{var1, var2, var4};
                  this.asn1Template = new ASN1Template(var5);
               }
            } else if (this.teletex != null) {
               var3 = new TeletexStringContainer(65536, true, 0, this.teletex, 1, 180);
               var5 = new ASN1Container[]{var1, var3, var4};
               this.asn1Template = new ASN1Template(var5);
            } else {
               var5 = new ASN1Container[]{var1, var4};
               this.asn1Template = new ASN1Template(var5);
            }

            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var6) {
            return 0;
         } catch (NameException var7) {
            return 0;
         }
      }
   }

   private EncodedContainer encodePrintAddress() throws NameException {
      Vector var3 = new Vector();

      try {
         OfContainer var4 = new OfContainer(65536, true, 0, 12288, new EncodedContainer(4864));
         var3.addElement(var4);

         for(int var5 = 0; var5 < this.printIndex; ++var5) {
            PrintStringContainer var6 = new PrintStringContainer(0, true, 0, this.printableAddress[var5], 1, 30);
            ASN1Container[] var7 = new ASN1Container[]{var6};
            ASN1Template var8 = new ASN1Template(var7);
            int var9 = var8.derEncodeInit();
            byte[] var10 = new byte[var9];
            var9 = var8.derEncode(var10, 0);
            EncodedContainer var2 = new EncodedContainer(4864, true, 0, var10, 0, var9);
            var4.addContainer(var2);
         }

         ASN1Container[] var12 = new ASN1Container[var3.size()];
         var3.copyInto(var12);
         ASN1Template var13 = new ASN1Template(var12);
         int var14 = var13.derEncodeInit();
         byte[] var15 = new byte[var14];
         var14 = var13.derEncode(var15, 0);
         EncodedContainer var1 = new EncodedContainer(12288, true, 0, var15, 0, var14);
         return var1;
      } catch (ASN_Exception var11) {
         throw new NameException(" Can't encode PrintAddress");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof UnformattedPostalAddress) {
         UnformattedPostalAddress var2 = (UnformattedPostalAddress)var1;
         if (this.printIndex != var2.printIndex) {
            return false;
         } else {
            if (this.teletex != null) {
               if (!this.teletex.equals(var2.teletex)) {
                  return false;
               }
            } else if (var2.teletex != null) {
               return false;
            }

            for(int var3 = 0; var3 < this.printIndex; ++var3) {
               if (!this.printableAddress[var3].equals(var2.printableAddress[var3])) {
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
      int var1 = this.printIndex;
      if (this.teletex != null) {
         var1 ^= this.teletex.hashCode();
      }

      for(int var2 = 0; var2 < this.printIndex; ++var2) {
         var1 ^= 17 * this.printableAddress[var2].hashCode();
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      UnformattedPostalAddress var1 = new UnformattedPostalAddress();
      var1.printIndex = this.printIndex;
      if (this.teletex != null) {
         var1.teletex = this.teletex;
      }

      System.arraycopy(this.printableAddress, 0, var1.printableAddress, 0, this.printIndex);
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

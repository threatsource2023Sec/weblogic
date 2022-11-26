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
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class BuiltInDomainDefinedAttributes implements Serializable, Cloneable {
   private String[][] attrData = new String[4][2];
   int attrIndex;
   private int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public BuiltInDomainDefinedAttributes() {
   }

   /** @deprecated */
   public BuiltInDomainDefinedAttributes(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();
            if (var6 > 4) {
               throw new NameException("Too many BuiltInDomainDefinedAttributes: MAX number is 4.");
            } else {
               for(int var7 = 0; var7 < var6; ++var7) {
                  ASN1Container var8 = var4.containerAt(var7);
                  SequenceContainer var9 = new SequenceContainer(0);
                  EndContainer var10 = new EndContainer();
                  PrintStringContainer var11 = new PrintStringContainer(0, 1, 8);
                  PrintStringContainer var12 = new PrintStringContainer(0, 1, 128);
                  ASN1Container[] var13 = new ASN1Container[]{var9, var11, var12, var10};
                  ASN1.berDecode(var8.data, var8.dataOffset, var13);
                  if (var11.data != null && var11.dataLen != 0 && var12.data != null && var12.dataLen != 0) {
                     this.attrData[this.attrIndex][0] = var11.getValueAsString();
                     this.attrData[this.attrIndex][1] = var12.getValueAsString();
                     ++this.attrIndex;
                  }
               }

            }
         } catch (Exception var14) {
            throw new NameException("Cannot decode the BER of BuiltInDomainDefinedAttributes.");
         }
      }
   }

   /** @deprecated */
   public void addAttribute(String var1, String var2) throws NameException {
      if (var1 != null && var2 != null) {
         if (var1.length() <= 8 && var2.length() <= 128) {
            if (this.attrIndex < 3) {
               this.attrData[this.attrIndex][0] = var1;
               this.attrData[this.attrIndex][1] = var2;
               ++this.attrIndex;
            } else {
               throw new NameException("Cannot add Attribute: MAX attribute number is 4.");
            }
         } else {
            throw new NameException("Specified values are too long.");
         }
      } else {
         throw new NameException("Specified values are null.");
      }
   }

   /** @deprecated */
   public String[] getAttribute(int var1) throws NameException {
      if (var1 > 3) {
         throw new NameException("Specified index is invalid.");
      } else {
         String[] var2 = new String[]{this.attrData[var1][0], this.attrData[var1][1]};
         return var2;
      }
   }

   /** @deprecated */
   public int getAttributeCount() {
      return this.attrIndex;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.attrIndex; ++var2) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.attrData[var2][0]);
         var1.append(",");
         var1.append(this.attrData[var2][1]);
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
         throw new NameException("Encoding is null.");
      } else {
         try {
            if (this.asn1Template == null || var3 != this.special) {
               int var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode BuiltInDomainDefinedAttributes");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode BuiltInDomainDefinedAttributes");
         }
      }
   }

   private int derEncodeInit() throws NameException {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.attrIndex; ++var3) {
               try {
                  EncodedContainer var4 = this.encodeAttribute(var3);
                  var2.addContainer(var4);
               } catch (ASN_Exception var5) {
                  throw new NameException("Can't encode BuiltInDomainDefinedAttributes");
               }
            }

            ASN1Container[] var7 = new ASN1Container[var1.size()];
            var1.copyInto(var7);
            this.asn1Template = new ASN1Template(var7);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var6) {
            throw new NameException(var6);
         }
      }
   }

   private EncodedContainer encodeAttribute(int var1) throws NameException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();

      try {
         PrintStringContainer var5 = new PrintStringContainer(0, true, 0, this.attrData[var1][0], 1, 8);
         PrintStringContainer var6 = new PrintStringContainer(0, true, 0, this.attrData[var1][1], 1, 128);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
         ASN1Template var8 = new ASN1Template(var7);
         int var9 = var8.derEncodeInit();
         byte[] var10 = new byte[var9];
         var9 = var8.derEncode(var10, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var10, 0, var9);
         return var2;
      } catch (ASN_Exception var11) {
         throw new NameException(" Can't encode BuiltInDomainDefinedAttribute");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BuiltInDomainDefinedAttributes) {
         BuiltInDomainDefinedAttributes var2 = (BuiltInDomainDefinedAttributes)var1;
         if (this.attrIndex != var2.attrIndex) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.attrIndex; ++var3) {
               if (!this.attrData[var3][0].equals(var2.attrData[var3][0])) {
                  return false;
               }

               if (!this.attrData[var3][1].equals(var2.attrData[var3][1])) {
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
      int var1 = 0;

      for(int var2 = 0; var2 < this.attrIndex; ++var2) {
         var1 ^= this.attrData[var2][0].hashCode();
         var1 ^= 17 * this.attrData[var2][1].hashCode();
         var1 *= 33;
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      BuiltInDomainDefinedAttributes var1 = (BuiltInDomainDefinedAttributes)super.clone();

      for(int var2 = 0; var2 < this.attrIndex; ++var2) {
         var1.attrData[var2][0] = this.attrData[var2][0];
         var1.attrData[var2][1] = this.attrData[var2][1];
      }

      try {
         if (this.asn1Template != null) {
            var1.derEncodeInit();
         }

         return var1;
      } catch (NameException var3) {
         throw new CloneNotSupportedException("Cannot get ASN1 Template");
      }
   }
}

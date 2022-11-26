package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.cert.attributes.X501Attribute;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class X501Attributes implements Serializable, Cloneable {
   private Vector theAttributes;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public X501Attributes() {
   }

   /** @deprecated */
   public X501Attributes(byte[] var1, int var2, int var3) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.setAttributesBER(var1, var2, var3);
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws AttributeException {
      if (var0 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new AttributeException("Could not read the BER encoding.");
         }
      }
   }

   private void setAttributesBER(byte[] var1, int var2, int var3) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();

         try {
            OfContainer var4 = new OfContainer(var3, 12544, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();
            if (var6 > 0) {
               this.theAttributes = new Vector();
            }

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               X501Attribute var9 = X501Attribute.getInstance(var8.data, var8.dataOffset, 0);
               this.theAttributes.addElement(var9);
            }

         } catch (ASN_Exception var10) {
            throw new AttributeException("Could not read the BER of the Attributes.");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      return this.encodeInit(var1);
   }

   private int encodeInit(int var1) {
      this.special = var1;

      try {
         OfContainer var2 = new OfContainer(var1, true, 0, 12544, new EncodedContainer(12288));
         int var3 = 0;
         if (this.theAttributes != null) {
            var3 = this.theAttributes.size();
         }

         if (var3 == 0) {
            return 2;
         } else {
            for(int var4 = 0; var4 < var3; ++var4) {
               X501Attribute var5 = (X501Attribute)this.theAttributes.elementAt(var4);
               int var6 = var5.getDERLen(0);
               byte[] var7 = new byte[var6];
               var6 = var5.getDEREncoding(var7, 0, 0);
               EncodedContainer var8 = new EncodedContainer(12288, true, 0, var7, 0, var6);
               var2.addContainer(var8);
            }

            ASN1Container[] var11 = new ASN1Container[]{var2};
            this.asn1Template = new ASN1Template(var11);
            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var9) {
         this.asn1Template = null;
         return 0;
      } catch (AttributeException var10) {
         this.asn1Template = null;
         return 0;
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Specified array is null.");
      } else if ((this.asn1Template == null || var3 != this.special) && this.encodeInit(var3) == 0) {
         throw new AttributeException("Cannot compute the DER of the Attributes");
      } else if (this.theAttributes == null) {
         var1[var2] = -96;
         var1[var2 + 1] = 0;
         return 2;
      } else {
         try {
            return this.asn1Template.derEncode(var1, var2);
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public int addAttribute(X501Attribute var1) {
      if (var1 == null) {
         return -1;
      } else {
         this.reset();
         if (this.theAttributes == null) {
            this.theAttributes = new Vector();
            this.theAttributes.addElement(var1);
            return 0;
         } else {
            X501Attribute var2 = this.getAttributeByOID(var1.getOID());
            if (var2 != null) {
               this.theAttributes.removeElement(var2);
            }

            this.theAttributes.addElement(var1);
            return this.theAttributes.indexOf(var1);
         }
      }
   }

   /** @deprecated */
   public int getAttributeCount() {
      return this.theAttributes != null ? this.theAttributes.size() : 0;
   }

   /** @deprecated */
   public X501Attribute getAttributeByIndex(int var1) {
      try {
         return this.theAttributes != null ? (X501Attribute)this.theAttributes.elementAt(var1) : null;
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   /** @deprecated */
   public X501Attribute getAttributeByType(int var1) {
      if (this.theAttributes == null) {
         return null;
      } else {
         int var2 = this.getAttributeCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            X501Attribute var4 = this.getAttributeByIndex(var3);
            if (var4.getAttributeType() == var1) {
               return var4;
            }
         }

         return null;
      }
   }

   /** @deprecated */
   public X501Attribute getAttributeByOID(byte[] var1) {
      if (this.theAttributes == null) {
         return null;
      } else {
         int var2 = this.getAttributeCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            X501Attribute var4 = this.getAttributeByIndex(var3);
            if (var4.compareOID(var1)) {
               return var4;
            }
         }

         return null;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      X501Attributes var1 = new X501Attributes();
      if (this.theAttributes != null) {
         var1.theAttributes = new Vector();
         int var2 = this.theAttributes.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            X501Attribute var4 = (X501Attribute)this.getAttributeByIndex(var3).clone();
            var1.addAttribute(var4);
         }
      }

      if (this.asn1Template != null) {
         var1.getDERLen(this.special);
      }

      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof X501Attributes) {
         X501Attributes var2 = (X501Attributes)var1;
         int var3 = this.theAttributes.size();
         int var4 = var2.theAttributes.size();
         if (var3 != var4) {
            return false;
         } else {
            try {
               int var5 = this.getDERLen(0);
               int var6 = var2.getDERLen(0);
               if (var5 != var6) {
                  return false;
               } else {
                  byte[] var7 = new byte[var5];
                  byte[] var8 = new byte[var6];
                  var5 = this.getDEREncoding(var7, 0, 0);
                  var6 = var2.getDEREncoding(var8, 0, 0);
                  if (var5 != var6) {
                     return false;
                  } else {
                     for(int var9 = 0; var9 < var5; ++var9) {
                        if (var7[var9] != var8[var9]) {
                           return false;
                        }
                     }

                     return true;
                  }
               }
            } catch (AttributeException var10) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      byte[] var2 = new byte[var1];

      try {
         this.getDEREncoding(var2, 0, 0);
      } catch (AttributeException var4) {
         return 0;
      }

      return Arrays.hashCode(var2);
   }

   /** @deprecated */
   protected void reset() {
      this.asn1Template = null;
   }
}

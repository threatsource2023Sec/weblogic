package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class ExtensionAttributes implements Serializable, Cloneable {
   private Vector attributes = new Vector();
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public ExtensionAttributes(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12544, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               this.attributes.addElement(new ExtensionAttribute(var8.data, var8.dataOffset, 0));
            }

         } catch (ASN_Exception var9) {
            throw new NameException("Cannot decode the BER of ExtensionAttributes.");
         }
      }
   }

   /** @deprecated */
   public ExtensionAttributes() {
   }

   /** @deprecated */
   public void addAttribute(ExtensionAttribute var1) {
      if (var1 != null) {
         this.attributes.addElement(var1);
      }

   }

   /** @deprecated */
   public ExtensionAttribute getAttribute(int var1) throws NameException {
      if (var1 >= this.attributes.size()) {
         throw new NameException(" Invalid index ");
      } else {
         return (ExtensionAttribute)this.attributes.elementAt(var1);
      }
   }

   /** @deprecated */
   public int getAttributeCount() {
      return this.attributes.size();
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.attributes.size() - 1;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.append(((ExtensionAttribute)this.attributes.elementAt(var3)).toString());
         var1.append(",");
      }

      var1.append(((ExtensionAttribute)this.attributes.elementAt(var2)).toString());
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
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode ExtensionAttributes");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(this.special, true, 0, 12544, new EncodedContainer(12288));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.attributes.size(); ++var3) {
               int var4 = ((ExtensionAttribute)this.attributes.elementAt(var3)).getDERLen(0);
               byte[] var5 = new byte[var4];
               int var6 = ((ExtensionAttribute)this.attributes.elementAt(var3)).getDEREncoding(var5, 0, 0);
               EncodedContainer var7 = new EncodedContainer(0, true, 0, var5, 0, var6);
               var2.addContainer(var7);
            }

            ASN1Container[] var10 = new ASN1Container[var1.size()];
            var1.copyInto(var10);
            this.asn1Template = new ASN1Template(var10);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var8) {
            return 0;
         } catch (NameException var9) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ExtensionAttributes) {
         ExtensionAttributes var2 = (ExtensionAttributes)var1;
         if (this.attributes.size() != var2.attributes.size()) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.attributes.size(); ++var3) {
               if (!((ExtensionAttribute)this.attributes.elementAt(var3)).equals(var2.attributes.elementAt(var3))) {
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

      for(int var2 = 0; var2 < this.attributes.size(); ++var2) {
         var1 ^= ((ExtensionAttribute)this.attributes.elementAt(var2)).hashCode();
         var1 *= 17;
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ExtensionAttributes var1 = new ExtensionAttributes();

      for(int var2 = 0; var2 < this.attributes.size(); ++var2) {
         var1.attributes.addElement(this.attributes.elementAt(var2));
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

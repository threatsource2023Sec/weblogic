package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;

/** @deprecated */
public class ORAddress implements Serializable, Cloneable {
   private BuiltInStandardAttributes standardAttr;
   private BuiltInDomainDefinedAttributes domainAttr;
   private ExtensionAttributes extensionAttr;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public ORAddress(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            EncodedContainer var6 = new EncodedContainer(12288);
            EncodedContainer var7 = new EncodedContainer(77824);
            EncodedContainer var8 = new EncodedContainer(78080);
            ASN1Container[] var9 = new ASN1Container[]{var4, var6, var7, var8, var5};
            ASN1.berDecode(var1, var2, var9);
            this.standardAttr = new BuiltInStandardAttributes(var6.data, var6.dataOffset, 0);
            if (var7.dataPresent) {
               this.domainAttr = new BuiltInDomainDefinedAttributes(var7.data, var7.dataOffset, 0);
            }

            if (var8.dataPresent) {
               this.extensionAttr = new ExtensionAttributes(var8.data, var8.dataOffset, 0);
            }

         } catch (ASN_Exception var10) {
            throw new NameException("Cannot decode the BER of the ORAddress.", var10);
         }
      }
   }

   /** @deprecated */
   public ORAddress() {
   }

   /** @deprecated */
   public void setBuiltInStandardAttributes(BuiltInStandardAttributes var1) {
      if (var1 != null) {
         this.standardAttr = var1;
      }

   }

   /** @deprecated */
   public void setBuiltInDomainDefinedAttributes(BuiltInDomainDefinedAttributes var1) {
      if (var1 != null) {
         this.domainAttr = var1;
      }

   }

   /** @deprecated */
   public void setExtensionAttributes(ExtensionAttributes var1) {
      if (var1 != null) {
         this.extensionAttr = var1;
      }

   }

   /** @deprecated */
   public BuiltInStandardAttributes getBuiltInStandardAttributes() {
      return this.standardAttr;
   }

   /** @deprecated */
   public BuiltInDomainDefinedAttributes getBuiltInDomainDefinedAttributes() {
      return this.domainAttr;
   }

   /** @deprecated */
   public ExtensionAttributes getExtensionAttributes() {
      return this.extensionAttr;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.standardAttr != null) {
         var1.append(this.standardAttr.toString());
      }

      if (this.domainAttr != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.domainAttr.toString());
      }

      if (this.extensionAttr != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(this.extensionAttr.toString());
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
            throw new NameException("Unable to determine length of the BER", var3);
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
            int var4;
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Unable to encode ORAddress");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode ORAddress", var5);
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            SequenceContainer var1 = new SequenceContainer(this.special, true, 0);
            EndContainer var2 = new EndContainer();
            EncodedContainer var3 = null;
            EncodedContainer var4 = null;
            byte var5 = 0;
            if (this.standardAttr == null) {
               return 0;
            } else {
               byte[] var6 = new byte[this.standardAttr.getDERLen(0)];
               int var7 = this.standardAttr.getDEREncoding(var6, 0, 0);
               EncodedContainer var8 = new EncodedContainer(12288, true, 0, var6, 0, var7);
               byte[] var9;
               int var10;
               if (this.domainAttr != null) {
                  var9 = new byte[this.domainAttr.getDERLen(65536)];
                  var10 = this.domainAttr.getDEREncoding(var9, 0, 65536);
                  var3 = new EncodedContainer(77824, true, 0, var9, 0, var10);
                  var5 = 1;
               }

               if (this.extensionAttr != null) {
                  var9 = new byte[this.extensionAttr.getDERLen(65536)];
                  var10 = this.extensionAttr.getDEREncoding(var9, 0, 65536);
                  var4 = new EncodedContainer(78080, true, 0, var9, 0, var10);
                  if (var5 == 1) {
                     var5 = 2;
                  } else {
                     var5 = 3;
                  }
               }

               switch (var5) {
                  case 0:
                     ASN1Container[] var15 = new ASN1Container[]{var1, var8, var2};
                     this.asn1Template = new ASN1Template(var15);
                     break;
                  case 1:
                     ASN1Container[] var16 = new ASN1Container[]{var1, var8, var3, var2};
                     this.asn1Template = new ASN1Template(var16);
                     break;
                  case 2:
                     ASN1Container[] var11 = new ASN1Container[]{var1, var8, var3, var4, var2};
                     this.asn1Template = new ASN1Template(var11);
                     break;
                  case 3:
                     ASN1Container[] var12 = new ASN1Container[]{var1, var8, var4, var2};
                     this.asn1Template = new ASN1Template(var12);
               }

               return this.asn1Template.derEncodeInit();
            }
         } catch (NameException var13) {
            return 0;
         } catch (ASN_Exception var14) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ORAddress) {
         ORAddress var2 = (ORAddress)var1;
         if (this.standardAttr != null) {
            if (!this.standardAttr.equals(var2.standardAttr)) {
               return false;
            }
         } else if (var2.standardAttr != null) {
            return false;
         }

         if (this.domainAttr != null) {
            if (!this.domainAttr.equals(var2.domainAttr)) {
               return false;
            }
         } else if (var2.domainAttr != null) {
            return false;
         }

         if (this.extensionAttr != null) {
            if (!this.extensionAttr.equals(var2.extensionAttr)) {
               return false;
            }
         } else if (var2.extensionAttr != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return this.toString().hashCode();
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ORAddress var1 = new ORAddress();
      if (this.standardAttr != null) {
         var1.standardAttr = (BuiltInStandardAttributes)this.standardAttr.clone();
      }

      if (this.domainAttr != null) {
         var1.domainAttr = (BuiltInDomainDefinedAttributes)this.domainAttr.clone();
      }

      if (this.extensionAttr != null) {
         var1.extensionAttr = (ExtensionAttributes)this.extensionAttr.clone();
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

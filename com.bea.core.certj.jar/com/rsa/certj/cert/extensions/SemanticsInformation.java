package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class SemanticsInformation implements Serializable, Cloneable {
   private byte[] id;
   private GeneralNames name;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public SemanticsInformation(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var2 >= 0) {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            OIDContainer var5 = new OIDContainer(16842752);
            EncodedContainer var6 = new EncodedContainer(77824);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var5.data == null && var6.data == null) {
               throw new NameException("At least one field in SemanticsInformation shall be present.");
            } else {
               if (var5.data != null) {
                  this.id = new byte[var5.dataLen];
                  System.arraycopy(var5.data, var5.dataOffset, this.id, 0, var5.dataLen);
               }

               if (var6.data != null) {
                  this.name = new GeneralNames(var6.data, var6.dataOffset, 0);
               }

            }
         } catch (ASN_Exception var9) {
            throw new NameException("Invalid encoding of SemanticsInformation. ", var9);
         }
      } else {
         throw new NameException("Encoding is null.");
      }
   }

   /** @deprecated */
   public SemanticsInformation() {
   }

   /** @deprecated */
   public void setName(GeneralNames var1) {
      this.name = var1;
   }

   /** @deprecated */
   public GeneralNames getName() {
      return this.name;
   }

   /** @deprecated */
   public void setID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0 && var2 >= 0 && var1.length >= var3 + var2) {
         this.id = new byte[var3];
         System.arraycopy(var1, var2, this.id, 0, var3);
      } else {
         this.id = null;
      }

   }

   /** @deprecated */
   public byte[] getID() {
      if (this.id == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.id.length];
         System.arraycopy(this.id, 0, var1, 0, this.id.length);
         return var1;
      }
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
            throw new NameException("Unable to determine length of the BER. ", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws NameException {
      if (this.name == null && this.id == null) {
         throw new NameException("At least one value should be present.");
      } else {
         this.special = var1;
         return this.derEncodeInit();
      }
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
                  throw new NameException("Cannot encode SemanticsInformation.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode SemanticsInformation.", var5);
         }
      }
   }

   private int derEncodeInit() throws NameException {
      try {
         SequenceContainer var1 = new SequenceContainer(this.special);
         EndContainer var4 = new EndContainer();
         OIDContainer var2;
         if (this.id != null) {
            var2 = new OIDContainer(16842752, true, 0, this.id, 0, this.id.length);
         } else {
            var2 = new OIDContainer(16842752, false, 0, (byte[])null, 0, 0);
         }

         EncodedContainer var3;
         if (this.name != null) {
            int var5 = this.name.getDERLen(0);
            byte[] var6 = new byte[var5];
            this.name.getDEREncoding(var6, 0, 0);
            var3 = new EncodedContainer(77824, true, 0, var6, 0, var6.length);
         } else {
            var3 = new EncodedContainer(77824, true, 0, (byte[])null, 0, 0);
         }

         ASN1Container[] var8 = new ASN1Container[]{var1, var2, var3, var4};
         this.asn1Template = new ASN1Template(var8);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var7) {
         throw new NameException("cannot create the DER encoding. ", var7);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof SemanticsInformation) {
         SemanticsInformation var2 = (SemanticsInformation)var1;
         if (this.name != null) {
            if (!this.name.equals(var2.name)) {
               return false;
            }
         } else if (var2.name != null) {
            return false;
         }

         if (this.id != null) {
            if (!CertJUtils.byteArraysEqual(this.id, var2.id)) {
               return false;
            }
         } else if (var2.id != null) {
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
      if (this.name != null) {
         var1 ^= this.name.hashCode();
      }

      if (this.id != null) {
         var1 ^= 17 * Arrays.hashCode(this.id);
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SemanticsInformation var1 = new SemanticsInformation();
      if (this.name != null) {
         var1.name = (GeneralNames)this.name.clone();
      }

      if (this.id != null) {
         var1.id = new byte[this.id.length];
         System.arraycopy(this.id, 0, var1.id, 0, this.id.length);
      }

      var1.special = this.special;

      try {
         if (this.asn1Template != null) {
            var1.derEncodeInit();
         }

         return var1;
      } catch (NameException var3) {
         throw new CloneNotSupportedException("Cannot clone SemanticsInformation object. " + var3.getMessage());
      }
   }
}

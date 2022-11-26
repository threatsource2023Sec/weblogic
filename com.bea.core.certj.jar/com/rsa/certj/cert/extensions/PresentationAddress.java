package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class PresentationAddress implements Serializable, Cloneable {
   private byte[] pSelector;
   private int pOffset;
   private int pLength;
   private byte[] sSelector;
   private int sOffset;
   private int sLength;
   private byte[] tSelector;
   private int tOffset;
   private int tLength;
   private Vector nAddresses = new Vector();
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   private static final int P_SPECIAL = 10551296;
   private static final int S_SPECIAL = 10551297;
   private static final int T_SPECIAL = 10551298;
   private static final int N_SPECIAL = 10485763;

   /** @deprecated */
   public PresentationAddress(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("NameBER is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            OctetStringContainer var6 = new OctetStringContainer(10551296);
            OctetStringContainer var7 = new OctetStringContainer(10551297);
            OctetStringContainer var8 = new OctetStringContainer(10551298);
            EncodedContainer var9 = new EncodedContainer(10498307);
            ASN1Container[] var10 = new ASN1Container[]{var4, var6, var7, var8, var9, var5};
            ASN1.berDecode(var1, var2, var10);
            OfContainer var11 = new OfContainer(10485763, 12544, new OctetStringContainer(0));
            ASN1Container[] var12 = new ASN1Container[]{var11};
            ASN1.berDecode(var9.data, var9.dataOffset, var12);
            int var13 = var11.getContainerCount();

            for(int var14 = 0; var14 < var13; ++var14) {
               ASN1Container var15 = var11.containerAt(var14);
               byte[] var16 = new byte[var15.dataLen];
               System.arraycopy(var15.data, var15.dataOffset, var16, 0, var15.dataLen);
               this.nAddresses.addElement(var16);
            }

            if (var6.dataPresent) {
               this.pSelector = new byte[var6.dataLen];
               System.arraycopy(var6.data, var6.dataOffset, this.pSelector, 0, var6.dataLen);
               this.pOffset = 0;
               this.pLength = var6.dataLen;
            }

            if (var7.dataPresent) {
               this.sSelector = new byte[var7.dataLen];
               System.arraycopy(var7.data, var7.dataOffset, this.sSelector, 0, var7.dataLen);
               this.sOffset = 0;
               this.sLength = var7.dataLen;
            }

            if (var8.dataPresent) {
               this.tSelector = new byte[var8.dataLen];
               System.arraycopy(var8.data, var8.dataOffset, this.tSelector, 0, var8.dataLen);
               this.tOffset = 0;
               this.tLength = var8.dataLen;
            }

         } catch (ASN_Exception var17) {
            throw new NameException("Cannot decode the BER of the PresentationAddress.");
         }
      }
   }

   /** @deprecated */
   public PresentationAddress() {
   }

   /** @deprecated */
   public void setPSelector(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var3 != 0) {
         this.pSelector = new byte[var3];
         System.arraycopy(var1, var2, this.pSelector, 0, var3);
         this.pOffset = 0;
         this.pLength = var3;
      } else {
         throw new NameException("Data is null.");
      }
   }

   /** @deprecated */
   public void setSSelector(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var3 != 0) {
         this.sSelector = new byte[var3];
         System.arraycopy(var1, var2, this.sSelector, 0, var3);
         this.sOffset = 0;
         this.sLength = var3;
      } else {
         throw new NameException("Data is null.");
      }
   }

   /** @deprecated */
   public void setTSelector(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var3 != 0) {
         this.tSelector = new byte[var3];
         System.arraycopy(var1, var2, this.tSelector, 0, var3);
         this.tOffset = 0;
         this.tLength = var3;
      } else {
         throw new NameException("Data is null.");
      }
   }

   /** @deprecated */
   public void setNAddress(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var3 != 0) {
         byte[] var4 = new byte[var3];
         System.arraycopy(var1, var2, var4, 0, var3);
         this.nAddresses.addElement(var4);
      } else {
         throw new NameException("Data is null.");
      }
   }

   /** @deprecated */
   public byte[] getPSelector() {
      if (this.pSelector == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.pLength];
         System.arraycopy(this.pSelector, this.pOffset, var1, 0, this.pLength);
         return var1;
      }
   }

   /** @deprecated */
   public byte[] getSSelector() {
      if (this.sSelector == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.sLength];
         System.arraycopy(this.sSelector, this.sOffset, var1, 0, this.sLength);
         return var1;
      }
   }

   /** @deprecated */
   public byte[] getTSelector() {
      if (this.tSelector == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.tLength];
         System.arraycopy(this.tSelector, this.tOffset, var1, 0, this.tLength);
         return var1;
      }
   }

   /** @deprecated */
   public byte[][] getNAddresses() {
      int var1 = this.nAddresses.size();
      byte[][] var2 = new byte[var1][];

      for(int var3 = 0; var3 < var1; ++var3) {
         byte[] var4 = (byte[])((byte[])this.nAddresses.elementAt(var3));
         var2[var3] = new byte[var4.length];
         System.arraycopy(var4, 0, var2[var3], 0, var4.length);
      }

      return var2;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.pSelector != null) {
         var1.append(new String(this.pSelector, this.pOffset, this.pLength));
      }

      if (this.sSelector != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(new String(this.sSelector, this.sOffset, this.sLength));
      }

      if (this.tSelector != null) {
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(new String(this.tSelector, this.tOffset, this.tLength));
      }

      for(int var2 = 0; var2 < this.nAddresses.size(); ++var2) {
         byte[] var3 = (byte[])((byte[])this.nAddresses.elementAt(var2));
         if (var1.length() != 0) {
            var1.append(",");
         }

         var1.append(new String(var3, 0, var3.length));
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
                  throw new NameException("Unable to encode PresentationAddress");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode PresentationAddress");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(10485763, true, 0, 12544, new OctetStringContainer(0));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.nAddresses.size(); ++var3) {
               byte[] var4 = (byte[])((byte[])this.nAddresses.elementAt(var3));
               OctetStringContainer var5 = new OctetStringContainer(0, true, 0, var4, 0, var4.length);
               var2.addContainer(var5);
            }

            ASN1Container[] var15 = new ASN1Container[var1.size()];
            var1.copyInto(var15);
            ASN1Template var16 = new ASN1Template(var15);
            int var17 = var16.derEncodeInit();
            byte[] var6 = new byte[var17];
            var17 = var16.derEncode(var6, 0);
            EncodedContainer var7 = new EncodedContainer(10498307, true, 0, var6, 0, var17);
            SequenceContainer var8 = new SequenceContainer(this.special, true, 0);
            EndContainer var9 = new EndContainer();
            OctetStringContainer var10;
            if (this.pSelector != null) {
               var10 = new OctetStringContainer(10551296, true, 0, this.pSelector, this.pOffset, this.pLength);
            } else {
               var10 = new OctetStringContainer(10551296, false, 0, this.pSelector, this.pOffset, this.pLength);
            }

            OctetStringContainer var11;
            if (this.sSelector != null) {
               var11 = new OctetStringContainer(10551297, true, 0, this.sSelector, this.sOffset, this.sLength);
            } else {
               var11 = new OctetStringContainer(10551297, false, 0, this.sSelector, this.sOffset, this.sLength);
            }

            OctetStringContainer var12;
            if (this.tSelector != null) {
               var12 = new OctetStringContainer(10551298, true, 0, this.tSelector, this.tOffset, this.tLength);
            } else {
               var12 = new OctetStringContainer(10551298, false, 0, this.tSelector, this.tOffset, this.tLength);
            }

            ASN1Container[] var13 = new ASN1Container[]{var8, var10, var11, var12, var7, var9};
            this.asn1Template = new ASN1Template(var13);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var14) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PresentationAddress) {
         PresentationAddress var2 = (PresentationAddress)var1;
         int var3;
         if (this.pSelector != null) {
            if (this.pLength != var2.pLength || this.pOffset != var2.pOffset) {
               return false;
            }

            for(var3 = 0; var3 < this.pLength; ++var3) {
               if (this.pSelector[var3] != var2.pSelector[var3]) {
                  return false;
               }
            }
         } else if (var2.pSelector != null) {
            return false;
         }

         if (this.sSelector != null) {
            if (this.sLength != var2.sLength || this.sOffset != var2.sOffset) {
               return false;
            }

            for(var3 = 0; var3 < this.sLength; ++var3) {
               if (this.sSelector[var3] != var2.sSelector[var3]) {
                  return false;
               }
            }
         } else if (var2.sSelector != null) {
            return false;
         }

         if (this.tSelector != null) {
            if (this.tLength != var2.tLength || this.tOffset != var2.tOffset) {
               return false;
            }

            for(var3 = 0; var3 < this.tLength; ++var3) {
               if (this.tSelector[var3] != var2.tSelector[var3]) {
                  return false;
               }
            }
         } else if (var2.tSelector != null) {
            return false;
         }

         if (this.nAddresses.size() != var2.nAddresses.size()) {
            return false;
         } else {
            for(var3 = 0; var3 < this.nAddresses.size(); ++var3) {
               byte[] var4 = (byte[])((byte[])this.nAddresses.elementAt(var3));
               byte[] var5 = (byte[])((byte[])var2.nAddresses.elementAt(var3));
               if (!CertJUtils.byteArraysEqual(var4, var5)) {
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
      PresentationAddress var1 = new PresentationAddress();
      if (this.pSelector != null) {
         var1.pOffset = this.pOffset;
         var1.pLength = this.pLength;
         var1.pSelector = new byte[this.pLength];
         System.arraycopy(this.pSelector, this.pOffset, var1.pSelector, var1.pOffset, this.pLength);
      }

      if (this.sSelector != null) {
         var1.sOffset = this.sOffset;
         var1.sLength = this.sLength;
         var1.sSelector = new byte[this.sLength];
         System.arraycopy(this.sSelector, this.sOffset, var1.sSelector, var1.sOffset, this.sLength);
      }

      if (this.tSelector != null) {
         var1.tOffset = this.tOffset;
         var1.tLength = this.tLength;
         var1.tSelector = new byte[this.tLength];
         System.arraycopy(this.tSelector, this.tOffset, var1.tSelector, var1.tOffset, this.tLength);
      }

      var1.nAddresses = new Vector(this.nAddresses);
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

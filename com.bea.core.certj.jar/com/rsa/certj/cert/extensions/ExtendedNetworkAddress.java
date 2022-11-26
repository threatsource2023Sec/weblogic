package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.NumericStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;

/** @deprecated */
public class ExtendedNetworkAddress implements Serializable, Cloneable {
   private String number;
   private String subAddress;
   private PresentationAddress psapAddress;
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   private static final int NUMBER_SPECIAL = 8388608;
   private static final int SUBADDRESS_SPECIAL = 8454145;
   private static final int PSAPADDRESS_SPECIAL = 8388608;

   /** @deprecated */
   public ExtendedNetworkAddress(byte[] var1, int var2, int var3) throws NameException {
      this.special = var3;
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            ChoiceContainer var4 = new ChoiceContainer(var3);
            SequenceContainer var5 = new SequenceContainer(0);
            NumericStringContainer var6 = new NumericStringContainer(8388608, 1, 15);
            NumericStringContainer var7 = new NumericStringContainer(8454145, 1, 40);
            EncodedContainer var8 = new EncodedContainer(8400896);
            EndContainer var9 = new EndContainer();
            ASN1Container[] var10 = new ASN1Container[]{var4, var5, var6, var7, var9, var8, var9};
            ASN1.berDecode(var1, var2, var10);
            if (var8.dataPresent) {
               byte[] var11 = new byte[var8.dataLen];
               System.arraycopy(var8.data, var8.dataOffset, var11, 0, var8.dataLen);
               this.psapAddress = new PresentationAddress(var11, 0, 8388608);
            } else {
               this.number = new String(var6.data, var6.dataOffset, var6.dataLen);
               if (var7.dataPresent) {
                  this.subAddress = new String(var7.data, var7.dataOffset, var7.dataLen);
               }
            }

         } catch (ASN_Exception var12) {
            throw new NameException("Cannot decode the BER of the ExtendedNetworkAddress.");
         }
      }
   }

   /** @deprecated */
   public ExtendedNetworkAddress() {
   }

   /** @deprecated */
   public void setNumber(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 15) {
         throw new NameException("Specified value are too long.");
      } else {
         this.number = var1;
      }
   }

   /** @deprecated */
   public void setSubAddress(String var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else if (var1.length() > 40) {
         throw new NameException("Specified value are too long.");
      } else {
         this.subAddress = var1;
      }
   }

   /** @deprecated */
   public void setPsapAddress(PresentationAddress var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified value is null.");
      } else {
         this.psapAddress = var1;
      }
   }

   /** @deprecated */
   public String getNumber() {
      return this.number;
   }

   /** @deprecated */
   public String getSubAddress() {
      return this.subAddress;
   }

   /** @deprecated */
   public PresentationAddress getPsapAddress() {
      return this.psapAddress;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.psapAddress != null) {
         var1.append(this.psapAddress.toString());
      } else {
         if (this.number != null) {
            var1.append(this.number);
         }

         if (this.subAddress != null) {
            if (var1.length() != 0) {
               var1.append(",");
            }

            var1.append(this.subAddress);
         }
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
            throw new NameException("Unable to encode ExtendedNetworkAddress");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            ChoiceContainer var1 = new ChoiceContainer(this.special, 0);
            EndContainer var2 = new EndContainer();
            SequenceContainer var3 = new SequenceContainer(0, true, 0);
            if (this.psapAddress != null) {
               int var4 = this.psapAddress.getDERLen(8388608);
               byte[] var5 = new byte[var4];
               var4 = this.psapAddress.getDEREncoding(var5, 0, 8388608);
               EncodedContainer var6 = new EncodedContainer(12288, true, 0, var5, 0, var4);
               ASN1Container[] var7 = new ASN1Container[]{var1, var6, var2};
               this.asn1Template = new ASN1Template(var7);
            } else {
               NumericStringContainer var10 = new NumericStringContainer(8388608, true, 0, this.number, 1, 15);
               if (this.subAddress != null) {
                  NumericStringContainer var11 = new NumericStringContainer(8454145, true, 0, this.subAddress, 1, 40);
                  ASN1Container[] var13 = new ASN1Container[]{var1, var3, var10, var11, var2, var2};
                  this.asn1Template = new ASN1Template(var13);
               } else {
                  ASN1Container[] var12 = new ASN1Container[]{var1, var3, var10, var2, var2};
                  this.asn1Template = new ASN1Template(var12);
               }
            }

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
      if (var1 != null && var1 instanceof ExtendedNetworkAddress) {
         ExtendedNetworkAddress var2 = (ExtendedNetworkAddress)var1;
         if (this.number != null) {
            if (!this.number.equals(var2.number)) {
               return false;
            }
         } else if (var2.number != null) {
            return false;
         }

         if (this.subAddress != null) {
            if (!this.subAddress.equals(var2.subAddress)) {
               return false;
            }
         } else if (var2.subAddress != null) {
            return false;
         }

         if (this.psapAddress != null) {
            if (!this.psapAddress.equals(var2.psapAddress)) {
               return false;
            }
         } else if (var2.psapAddress != null) {
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
      if (this.number != null) {
         var1 ^= this.number.hashCode();
      }

      if (this.subAddress != null) {
         var1 ^= 17 * this.subAddress.hashCode();
      }

      if (this.psapAddress != null) {
         var1 ^= 33 * this.psapAddress.hashCode();
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ExtendedNetworkAddress var1 = new ExtendedNetworkAddress();
      if (this.psapAddress != null) {
         var1.psapAddress = (PresentationAddress)this.psapAddress.clone();
      } else {
         if (this.number != null) {
            var1.number = this.number;
         }

         if (this.subAddress != null) {
            var1.subAddress = this.subAddress;
         }
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

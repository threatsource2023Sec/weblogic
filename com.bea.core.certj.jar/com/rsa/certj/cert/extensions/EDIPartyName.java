package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BMPStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.TeletexStringContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.asn1.UniversalStringContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;

/** @deprecated */
public class EDIPartyName implements Serializable, Cloneable {
   private static final int NAMEASSIGNER_SPECIAL = 10551296;
   private static final int PARTYNAME_SPECIAL = 10485761;
   private String nameAssigner;
   private int assignerType;
   private String partyName;
   private int partyType;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public EDIPartyName(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EndContainer var5 = new EndContainer();
            ChoiceContainer var6 = new ChoiceContainer(10551296);
            TeletexStringContainer var7 = new TeletexStringContainer(0);
            PrintStringContainer var8 = new PrintStringContainer(0);
            UniversalStringContainer var9 = new UniversalStringContainer(0);
            UTF8StringContainer var10 = new UTF8StringContainer(0);
            BMPStringContainer var11 = new BMPStringContainer(0);
            ChoiceContainer var12 = new ChoiceContainer(10485761);
            TeletexStringContainer var13 = new TeletexStringContainer(0);
            PrintStringContainer var14 = new PrintStringContainer(0);
            UniversalStringContainer var15 = new UniversalStringContainer(0);
            UTF8StringContainer var16 = new UTF8StringContainer(0);
            BMPStringContainer var17 = new BMPStringContainer(0);
            ASN1Container[] var18 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var5, var12, var13, var14, var15, var16, var17, var5, var5};
            ASN1.berDecode(var1, var2, var18);
            if (var6.dataPresent) {
               if (var7.dataPresent) {
                  this.nameAssigner = var7.getValueAsString();
                  this.assignerType = 5120;
               } else if (var8.dataPresent) {
                  this.nameAssigner = var8.getValueAsString();
                  this.assignerType = 4864;
               } else if (var9.dataPresent) {
                  this.nameAssigner = var9.getValueAsString();
                  this.assignerType = 7168;
               } else if (var10.dataPresent) {
                  this.nameAssigner = new String(var10.data, var10.dataOffset, var10.dataLen);
                  this.assignerType = 3072;
               } else if (var11.dataPresent) {
                  this.nameAssigner = var11.getValueAsString();
                  this.assignerType = 7680;
               }
            }

            if (var13.dataPresent) {
               this.partyName = var13.getValueAsString();
               this.partyType = 5120;
            } else if (var14.dataPresent) {
               this.partyName = var14.getValueAsString();
               this.partyType = 4864;
            } else if (var15.dataPresent) {
               this.partyName = var15.getValueAsString();
               this.partyType = 7168;
            } else if (var16.dataPresent) {
               this.partyName = new String(var16.data, var16.dataOffset, var16.dataLen);
               this.partyType = 3072;
            } else {
               if (!var17.dataPresent) {
                  throw new NameException("Party Name field must be set!");
               }

               this.partyName = var17.getValueAsString();
               this.partyType = 7680;
            }

         } catch (ASN_Exception var19) {
            throw new NameException("Cannot decode the BER of the name.");
         }
      }
   }

   /** @deprecated */
   public EDIPartyName() {
   }

   /** @deprecated */
   public void addNameAssigner(String var1, int var2) {
      if (var1 != null) {
         this.nameAssigner = var1;
         if (var2 == 0) {
            this.assignerType = 7168;
         } else {
            this.assignerType = var2;
         }
      }

   }

   /** @deprecated */
   public void addPartyName(String var1, int var2) {
      if (var1 != null) {
         this.partyName = var1;
         if (var2 == 0) {
            this.partyType = 7168;
         } else {
            this.partyType = var2;
         }
      }

   }

   /** @deprecated */
   public String getNameAssigner() {
      return this.nameAssigner;
   }

   /** @deprecated */
   public String getPartyName() {
      return this.partyName;
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.nameAssigner != null) {
         var1.append(this.nameAssigner);
         var1.append(", ");
      }

      var1.append(this.partyName);
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
                  throw new NameException("Unable to encode EDI Party Name.");
               }
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new NameException("Unable to encode EDI Party Name.");
         }
      }
   }

   private int derEncodeInit() throws NameException {
      SequenceContainer var1 = new SequenceContainer(this.special, true, 0);
      ChoiceContainer var2 = new ChoiceContainer(10551296, 0);
      ChoiceContainer var3 = new ChoiceContainer(10485761, 0);
      EndContainer var4 = new EndContainer();
      Object var5 = null;
      short var7 = 32767;
      if (this.partyName == null) {
         throw new NameException("PartyName is not set.");
      } else {
         Object var6;
         try {
            byte[] var8;
            switch (this.assignerType) {
               case 3072:
                  if (this.nameAssigner != null) {
                     var8 = this.nameAssigner.getBytes();
                     if (var8.length > var7) {
                        throw new NameException("Illegal name length");
                     }

                     var5 = new UTF8StringContainer(0, true, 0, var8, 0, var8.length);
                  } else {
                     var5 = new UTF8StringContainer(0, false, 0, (byte[])null, 0, 0);
                  }
                  break;
               case 4864:
                  var5 = new PrintStringContainer(0, true, 0, this.nameAssigner, 1, var7);
                  break;
               case 5120:
                  var5 = new TeletexStringContainer(0, true, 0, this.nameAssigner, 1, var7);
                  break;
               case 7168:
                  var5 = new UniversalStringContainer(0, true, 0, this.nameAssigner, 1, var7);
                  break;
               case 7680:
                  var5 = new BMPStringContainer(0, true, 0, this.nameAssigner, 1, var7);
            }

            switch (this.partyType) {
               case 3072:
                  var8 = this.partyName.getBytes();
                  if (var8.length > var7) {
                     throw new NameException("Illegal name length");
                  }

                  var6 = new UTF8StringContainer(0, true, 0, var8, 0, var8.length);
                  break;
               case 4864:
                  var6 = new PrintStringContainer(0, true, 0, this.partyName, 1, var7);
                  break;
               case 5120:
                  var6 = new TeletexStringContainer(0, true, 0, this.partyName, 1, var7);
                  break;
               case 7168:
                  var6 = new UniversalStringContainer(0, true, 0, this.partyName, 1, var7);
                  break;
               case 7680:
                  var6 = new BMPStringContainer(0, true, 0, this.partyName, 1, var7);
                  break;
               default:
                  throw new NameException("Illegal empty partyName value");
            }
         } catch (ASN_Exception var10) {
            throw new NameException(var10);
         }

         ASN1Container[] var11;
         if (var5 == null) {
            var11 = new ASN1Container[]{var1, var3, (ASN1Container)var6, var4, var4};
            this.asn1Template = new ASN1Template(var11);
         } else {
            var11 = new ASN1Container[]{var1, var2, (ASN1Container)var5, var4, var3, (ASN1Container)var6, var4, var4};
            this.asn1Template = new ASN1Template(var11);
         }

         try {
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var9) {
            throw new NameException(var9);
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof EDIPartyName)) {
         return false;
      } else {
         EDIPartyName var2 = (EDIPartyName)var1;
         if (this.nameAssigner != null) {
            if (!this.nameAssigner.equals(var2.nameAssigner)) {
               return false;
            }
         } else if (var2.nameAssigner != null) {
            return false;
         }

         if (this.partyName != null) {
            if (!this.partyName.equals(var2.partyName)) {
               return false;
            }
         } else if (var2.partyName != null) {
            return false;
         }

         return true;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return (this.nameAssigner + this.partyName).hashCode();
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      EDIPartyName var1 = new EDIPartyName();
      var1.assignerType = this.assignerType;
      var1.partyType = this.partyType;
      var1.nameAssigner = this.nameAssigner;
      var1.partyName = this.partyName;
      var1.special = this.special;

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

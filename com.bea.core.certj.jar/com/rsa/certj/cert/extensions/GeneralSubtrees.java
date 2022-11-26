package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Vector;

/** @deprecated */
public class GeneralSubtrees implements Serializable, Cloneable {
   private static final int MINIMUM_SPECIAL = 8519680;
   private static final int MAXIMUM_SPECIAL = 8454145;
   private Vector[] subtree = createVectorArray(3);
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public GeneralSubtrees() {
   }

   /** @deprecated */
   public GeneralSubtrees(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               SequenceContainer var9 = new SequenceContainer(0);
               EndContainer var10 = new EndContainer();
               EncodedContainer var11 = new EncodedContainer(65280);
               IntegerContainer var12 = new IntegerContainer(8519680);
               IntegerContainer var13 = new IntegerContainer(8454145);
               ASN1Container[] var14 = new ASN1Container[]{var9, var11, var12, var13, var10};
               ASN1.berDecode(var8.data, var8.dataOffset, var14);
               this.subtree[0].addElement(new GeneralName(var11.data, var11.dataOffset, 0));
               int var15;
               if (var12.dataPresent) {
                  var15 = this.bytesToInt(var12.data, var12.dataOffset, var12.dataLen);
                  this.subtree[1].addElement(new Integer(var15));
               } else {
                  this.subtree[1].addElement(new Integer(0));
               }

               if (var13.dataPresent) {
                  var15 = this.bytesToInt(var13.data, var13.dataOffset, var13.dataLen);
                  this.subtree[2].addElement(new Integer(var15));
               } else {
                  this.subtree[2].addElement(new Integer(-1));
               }
            }

         } catch (Exception var16) {
            throw new NameException("Cannot decode the BER of GeneralSubtrees.");
         }
      }
   }

   private int bytesToInt(byte[] var1, int var2, int var3) throws NameException {
      if (var3 != 0 && var1 != null) {
         if (var3 > 4) {
            throw new NameException("Could not decode General Subtrees.");
         } else {
            int var4 = 0;

            for(int var5 = var2; var5 < var3 + var2; ++var5) {
               var4 <<= 8;
               var4 |= var1[var5] & 255;
            }

            return var4;
         }
      } else {
         return 0;
      }
   }

   /** @deprecated */
   public void addSubtree(GeneralName var1, int var2, int var3) {
      if (var1 != null) {
         this.subtree[0].addElement(var1);
         if (var2 < 0) {
            var2 = 0;
         }

         this.subtree[1].addElement(new Integer(var2));
         if (var3 < 0) {
            var3 = -1;
         }

         this.subtree[2].addElement(new Integer(var3));
      }

   }

   /** @deprecated */
   public GeneralName getBase(int var1) throws NameException {
      if (this.subtree[0].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         return (GeneralName)this.subtree[0].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getMinimum(int var1) throws NameException {
      if (this.subtree[1].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         Integer var2 = (Integer)this.subtree[1].elementAt(var1);
         return var2;
      }
   }

   /** @deprecated */
   public int getMaximum(int var1) throws NameException {
      if (this.subtree[2].size() <= var1) {
         throw new NameException("Specified index is invalid.");
      } else {
         Integer var2 = (Integer)this.subtree[2].elementAt(var1);
         return var2;
      }
   }

   private static Vector[] createVectorArray(int var0) {
      Vector[] var1 = new Vector[var0];

      for(int var2 = 0; var2 < var0; ++var2) {
         var1[var2] = new Vector();
      }

      return var1;
   }

   /** @deprecated */
   public int getSubtreeCount() {
      return this.subtree[0].size();
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.subtree[0].isEmpty()) {
         return "";
      } else {
         for(int var2 = 0; var2 < this.subtree[0].size(); ++var2) {
            String var3 = this.subtree[0].elementAt(var2).toString();
            var1.append(var3);
            var1.append("   min = ");
            var1.append(this.subtree[1].elementAt(var2));
            var1.append(", max = ");
            var1.append(this.subtree[2].elementAt(var2));
            var1.append(" \n");
         }

         return var1.toString();
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
            throw new NameException("Unable to encode GeneralTrees.");
         }
      }
   }

   private int derEncodeInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
         var1.addElement(var2);

         for(int var3 = 0; var3 < this.subtree[0].size(); ++var3) {
            try {
               EncodedContainer var4 = this.encodeSubtree(var3);
               var2.addContainer(var4);
            } catch (ASN_Exception var5) {
               return 0;
            } catch (NameException var6) {
               return 0;
            }
         }

         ASN1Container[] var8 = new ASN1Container[var1.size()];
         var1.copyInto(var8);
         this.asn1Template = new ASN1Template(var8);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var7) {
         return 0;
      }
   }

   private EncodedContainer encodeSubtree(int var1) throws NameException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      int var8 = ((GeneralName)this.subtree[0].elementAt(var1)).getDERLen(0);
      byte[] var9 = new byte[var8];
      byte var10 = 0;
      int var11 = ((GeneralName)this.subtree[0].elementAt(var1)).getDEREncoding(var9, var10, 0);

      try {
         EncodedContainer var5 = new EncodedContainer(65280, true, 0, var9, var10, var11);
         int var12 = (Integer)this.subtree[1].elementAt(var1);
         IntegerContainer var6;
         if (var12 == 0) {
            var6 = new IntegerContainer(8519680, false, 0, 0);
         } else {
            var6 = new IntegerContainer(8519680, true, 0, var12);
         }

         int var13 = (Integer)this.subtree[2].elementAt(var1);
         ASN1Template var7;
         if (var13 == -1) {
            ASN1Container[] var14 = new ASN1Container[]{var3, var5, var6, var4};
            var7 = new ASN1Template(var14);
         } else {
            IntegerContainer var17 = new IntegerContainer(8454145, true, 0, var13);
            ASN1Container[] var15 = new ASN1Container[]{var3, var5, var6, var17, var4};
            var7 = new ASN1Template(var15);
         }

         int var18 = var7.derEncodeInit();
         byte[] var19 = new byte[var18];
         var18 = var7.derEncode(var19, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var19, 0, var18);
         return var2;
      } catch (ASN_Exception var16) {
         throw new NameException(" Can't encode GeneralSubtrees");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GeneralSubtrees) {
         GeneralSubtrees var2 = (GeneralSubtrees)var1;
         int var3 = this.subtree[0].size();
         if (var3 != var2.subtree[0].size()) {
            return false;
         } else {
            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
               GeneralName var5 = (GeneralName)this.subtree[0].elementAt(var4);
               GeneralName var6 = (GeneralName)var2.subtree[0].elementAt(var4);
               if (!var5.equals(var6)) {
                  return false;
               }
            }

            var3 = this.subtree[1].size();
            if (var3 != var2.subtree[1].size()) {
               return false;
            } else {
               int var7;
               int var8;
               for(var4 = 0; var4 < var3; ++var4) {
                  var7 = (Integer)this.subtree[1].elementAt(var4);
                  var8 = (Integer)var2.subtree[1].elementAt(var4);
                  if (var7 != var8) {
                     return false;
                  }
               }

               var3 = this.subtree[2].size();
               if (var3 != var2.subtree[2].size()) {
                  return false;
               } else {
                  for(var4 = 0; var4 < var3; ++var4) {
                     var7 = (Integer)this.subtree[2].elementAt(var4);
                     var8 = (Integer)var2.subtree[2].elementAt(var4);
                     if (var7 != var8) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;

      int var2;
      for(var2 = 0; var2 < this.subtree[0].size(); ++var2) {
         GeneralName var3 = (GeneralName)this.subtree[0].elementAt(var2);
         var1 ^= var3.hashCode();
      }

      int var4;
      for(var2 = 0; var2 < this.subtree[1].size(); ++var2) {
         var4 = (Integer)this.subtree[1].elementAt(var2);
         var1 ^= var4 * 17;
      }

      for(var2 = 0; var2 < this.subtree[2].size(); ++var2) {
         var4 = (Integer)this.subtree[2].elementAt(var2);
         var1 ^= var4 * 33;
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      GeneralSubtrees var1 = new GeneralSubtrees();

      for(int var2 = 0; var2 < this.subtree.length; ++var2) {
         for(int var3 = 0; var3 < this.subtree[var2].size(); ++var3) {
            var1.subtree[var2].addElement(this.subtree[var2].elementAt(var3));
         }
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

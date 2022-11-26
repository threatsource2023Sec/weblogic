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
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public class GeneralNames implements Serializable, Cloneable {
   private Vector nameVector = new Vector();
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public GeneralNames(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(65280));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               GeneralName var9 = new GeneralName(var8.data, var8.dataOffset, 0);
               this.nameVector.addElement(var9);
            }

         } catch (ASN_Exception var10) {
            throw new NameException("Cannot decode the BER of the name.");
         } catch (NameException var11) {
            throw new NameException("Cannot decode the BER of the name.");
         }
      }
   }

   /** @deprecated */
   public GeneralNames() {
   }

   /** @deprecated */
   public void addGeneralName(GeneralName var1) {
      if (var1 != null) {
         this.nameVector.addElement(var1);
      }

   }

   /** @deprecated */
   public Vector getGeneralNames() {
      return this.nameVector;
   }

   /** @deprecated */
   public GeneralName getGeneralName(int var1) throws NameException {
      if (var1 < this.nameVector.size()) {
         return (GeneralName)this.nameVector.elementAt(var1);
      } else {
         throw new NameException("Invalid index.");
      }
   }

   /** @deprecated */
   public void removeGeneralName(int var1) throws NameException {
      if (var1 < this.nameVector.size()) {
         this.nameVector.removeElementAt(var1);
      } else {
         throw new NameException("Invalid Index.");
      }
   }

   /** @deprecated */
   public void addGeneralName(GeneralName var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified name is null.");
      } else if (var2 < this.nameVector.size()) {
         this.nameVector.setElementAt(var1, var2);
      } else {
         throw new NameException("Invalid index.");
      }
   }

   /** @deprecated */
   public String toString() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = this.nameVector.iterator();

      while(var2.hasNext()) {
         GeneralName var3 = (GeneralName)var2.next();
         String var4 = var3.toString();
         var1.append(var4);
         var1.append(",");
      }

      if (var1.length() > 0) {
         var1.setLength(var1.length() - 1);
      }

      return var1.toString();
   }

   /** @deprecated */
   public int getNameCount() {
      return this.nameVector.size();
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
            int var4;
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Cannot encode GeneralNames.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode GeneralNames.");
         }
      }
   }

   private int derEncodeInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(65280));
         var1.addElement(var2);
         Iterator var4 = this.nameVector.iterator();

         while(var4.hasNext()) {
            GeneralName var5 = (GeneralName)var4.next();

            try {
               int var6 = var5.getDERLen(0);
               byte[] var7 = new byte[var6];
               var6 = var5.getDEREncoding(var7, 0, 0);
               EncodedContainer var3 = new EncodedContainer(0, true, 0, var7, 0, var6);
               var2.addContainer(var3);
            } catch (ASN_Exception var8) {
               return 0;
            } catch (NameException var9) {
               return 0;
            }
         }

         ASN1Container[] var11 = new ASN1Container[var1.size()];
         var1.copyInto(var11);
         this.asn1Template = new ASN1Template(var11);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var10) {
         return 0;
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GeneralNames) {
         GeneralNames var2 = (GeneralNames)var1;
         int var3 = this.nameVector.size();
         int var4 = var2.nameVector.size();
         if (var3 != var4) {
            return false;
         } else {
            for(int var5 = 0; var5 < var3; ++var5) {
               if (!((GeneralName)this.nameVector.elementAt(var5)).equals(var2.nameVector.elementAt(var5))) {
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

      for(Iterator var2 = this.nameVector.iterator(); var2.hasNext(); var1 ^= ((GeneralName)var2.next()).hashCode()) {
      }

      return var1;
   }

   /** @deprecated */
   public boolean contains(GeneralName var1) {
      if (var1 == null) {
         return false;
      } else {
         try {
            for(int var2 = 0; var2 < this.getNameCount(); ++var2) {
               if (var1.equals(this.getGeneralName(var2))) {
                  return true;
               }
            }

            return false;
         } catch (NameException var3) {
            return false;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      GeneralNames var1 = new GeneralNames();
      var1.nameVector = new Vector(this.nameVector);
      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}

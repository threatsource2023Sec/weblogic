package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/** @deprecated */
public class X500Name implements Serializable, Cloneable {
   private Vector rdnList = new Vector();
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public X500Name(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12544));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               RDN var9 = new RDN(var8.data, var8.dataOffset, 0);
               this.rdnList.addElement(var9);
            }

         } catch (ASN_Exception var10) {
            throw new NameException("Cannot decode the BER of the name.", var10);
         }
      }
   }

   /** @deprecated */
   public X500Name() {
   }

   /** @deprecated */
   public X500Name(String var1) throws NameException {
      this.createX500Name(var1, 0);
   }

   /** @deprecated */
   public X500Name(String var1, int var2) throws NameException {
      this.createX500Name(var1, var2);
   }

   private void createX500Name(String var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("The string is null.");
      } else if (var2 != 0 && var2 != 3072 && var2 != 4864 && var2 != 5120 && var2 != 5632 && var2 != 7168 && var2 != 7680) {
         throw new NameException("The ASN.1 string encoding type is invalid.");
      } else {
         StringTokenizer var3 = new StringTokenizer(var1, "\\,;", true);
         int var4 = var3.countTokens();
         StringBuffer var5 = new StringBuffer();
         ArrayList var6 = new ArrayList();

         String var7;
         int var8;
         for(var8 = 0; var8 < var4; ++var8) {
            var7 = var3.nextToken();
            if (var7.equals("\\")) {
               var5.append(var7);
               var5.append(var3.nextToken());
               ++var8;
            } else if (!var7.equals(",") && !var7.equals(";")) {
               var5.append(var7);
            } else {
               String var9 = var3.nextToken();
               ++var8;
               if (var9.indexOf("=") == -1) {
                  var5.append(",");
                  var5.append(var9);
               } else {
                  var9 = var9.trim();
                  var6.add(var5.toString());
                  var5 = new StringBuffer(var9);
               }
            }
         }

         if (var5.length() != 0) {
            var6.add(var5.toString());
         }

         for(var8 = var6.size() - 1; var8 >= 0; --var8) {
            var7 = (String)var6.get(var8);
            this.rdnList.addElement(new RDN(var7, var2));
         }

      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      X500Name var1 = new X500Name();

      for(int var2 = 0; var2 < this.rdnList.size(); ++var2) {
         RDN var3 = (RDN)this.rdnList.elementAt(var2);
         var1.rdnList.addElement((RDN)var3.clone());
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }

   private String toString(boolean var1, boolean var2) {
      StringBuffer var3 = new StringBuffer();
      if (this.rdnList.isEmpty()) {
         return "";
      } else {
         int var4;
         RDN var5;
         RDN var6;
         if (var1) {
            for(var4 = this.rdnList.size() - 1; var4 >= 1; --var4) {
               var5 = (RDN)this.rdnList.elementAt(var4);
               var3.append(var5.toString(var2));
               var3.append(",");
            }

            var6 = (RDN)this.rdnList.elementAt(0);
            var3.append(var6.toString(var2));
         } else {
            for(var4 = 0; var4 < this.rdnList.size() - 1; ++var4) {
               var5 = (RDN)this.rdnList.elementAt(var4);
               var3.append(var5.toString(var2));
               var3.append(",");
            }

            var6 = (RDN)this.rdnList.elementAt(this.rdnList.size() - 1);
            var3.append(var6.toString(var2));
         }

         return var3.toString();
      }
   }

   /** @deprecated */
   public String toString(boolean var1) {
      return this.toString(var1, false);
   }

   /** @deprecated */
   public String toString() {
      return this.toString(true, true);
   }

   /** @deprecated */
   public String toStringRFC2253() {
      return this.toString(true, false);
   }

   /** @deprecated */
   public int getRDNCount() {
      return this.rdnList.size();
   }

   /** @deprecated */
   public int getAttributeCount() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.rdnList.size(); ++var2) {
         var1 += ((RDN)this.rdnList.elementAt(var2)).getAttributeCount();
      }

      return var1;
   }

   /** @deprecated */
   public RDN getRDN(int var1) throws NameException {
      int var2 = this.rdnList.size();
      if (var1 >= 0 && var2 > var1) {
         return (RDN)this.rdnList.elementAt(var1);
      } else {
         throw new NameException("Invalid Index.");
      }
   }

   /** @deprecated */
   public void removeRDN(int var1) throws NameException {
      if (var1 < this.rdnList.size()) {
         this.rdnList.removeElementAt(var1);
      } else {
         throw new NameException("Invalid index.");
      }
   }

   /** @deprecated */
   public void addRDN(RDN var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified RDN is null.");
      } else if (var2 < this.rdnList.size()) {
         this.rdnList.setElementAt(var1, var2);
      } else {
         throw new NameException("Invalid index.");
      }
   }

   /** @deprecated */
   public AttributeValueAssertion getAttribute(int var1) {
      int var2 = this.rdnList.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         RDN var4 = (RDN)this.rdnList.elementAt(var3);
         AttributeValueAssertion var5 = var4.getAttribute(var1);
         if (var5 != null) {
            return var5;
         }
      }

      return null;
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
            if (this.asn1Template == null || var3 != this.special) {
               this.getDERLen(var3);
               if (this.asn1Template == null) {
                  throw new NameException("Unable to encode X500Name.");
               }
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode X500Name.", var5);
         }
      }
   }

   private int derEncodeInit() {
      try {
         int var2 = this.rdnList.size();
         OfContainer var1;
         if (var2 == 0) {
            var1 = new OfContainer(this.special, false, 0, 12288, new EncodedContainer(12544));
         } else {
            var1 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12544));

            for(int var3 = 0; var3 < var2; ++var3) {
               RDN var4 = (RDN)this.rdnList.elementAt(var3);
               byte[] var5 = new byte[var4.getDERLen(0)];
               int var6 = var4.getDEREncoding(var5, 0, 0);
               EncodedContainer var7 = new EncodedContainer(12544, true, 0, var5, 0, var6);
               var1.addContainer(var7);
            }
         }

         ASN1Container[] var10 = new ASN1Container[]{var1};
         this.asn1Template = new ASN1Template(var10);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var8) {
         return 0;
      } catch (NameException var9) {
         return 0;
      }
   }

   /** @deprecated */
   public void addRDN(RDN var1) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified RDN is null.");
      } else {
         this.rdnList.addElement(var1);
      }
   }

   /** @deprecated */
   public void addRDN(byte[] var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         RDN var3 = new RDN(var1, var2, 0);
         this.rdnList.addElement(var3);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof X500Name) {
         X500Name var2 = (X500Name)var1;
         int var3 = this.getRDNCount();
         int var4 = var2.getRDNCount();
         if (var3 != var4) {
            return false;
         } else {
            for(int var5 = 0; var5 < var3; ++var5) {
               if (!((RDN)this.rdnList.elementAt(var5)).equals(var2.rdnList.elementAt(var5))) {
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

      for(int var2 = 0; var2 < this.getRDNCount(); ++var2) {
         var1 ^= ((RDN)this.rdnList.elementAt(var2)).hashCode();
         var1 *= 17;
      }

      return var1;
   }

   /** @deprecated */
   public boolean contains(X500Name var1) {
      if (var1 == null) {
         return false;
      } else {
         int var2 = this.getRDNCount();
         int var3 = var1.getRDNCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = 0;

            RDN var6;
            for(var6 = (RDN)var1.rdnList.elementAt(var4); var5 < var2 && !var6.equals(this.rdnList.elementAt(var5)); ++var5) {
            }

            if (var5 >= var2) {
               return false;
            }

            if (!var6.equals(this.rdnList.elementAt(var5))) {
               return false;
            }
         }

         return true;
      }
   }
}

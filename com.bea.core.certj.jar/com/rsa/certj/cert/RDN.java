package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/** @deprecated */
public class RDN implements Serializable, Cloneable {
   private Vector avaList = new Vector();
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;
   private static final String INVALID_INDEX = "Invalid Index.";
   private static final char[] SPECIAL_CHARACTERS = new char[]{',', '=', '+', '<', '>', '#', ';', '"', '\\', ' '};

   /** @deprecated */
   public RDN(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Encoding is null.");
      } else {
         this.special = var3;

         try {
            OfContainer var4 = new OfContainer(var3, 12544, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               SequenceContainer var9 = new SequenceContainer(0);
               OIDContainer var10 = new OIDContainer(16777216);
               EncodedContainer var11 = new EncodedContainer(65280);
               EndContainer var12 = new EndContainer();
               ASN1Container[] var13 = new ASN1Container[]{var9, var10, var11, var12};
               ASN1.berDecode(var8.data, var8.dataOffset, var13);
               int var14 = AttributeValueAssertion.findOID(var10.data, var10.dataOffset, var10.dataLen);
               byte[] var15 = null;
               if (var14 == -1 && var10.data != null && var10.dataLen > 0) {
                  var15 = new byte[var10.dataLen];
                  System.arraycopy(var10.data, var10.dataOffset, var15, 0, var10.dataLen);
               }

               AttributeValueAssertion var16 = new AttributeValueAssertion(var14, var15, var11.data, var11.dataOffset, var11.dataLen);
               this.addNameAVA(var16);
            }

         } catch (ASN_Exception var17) {
            throw new NameException("Cannot decode the BER of the RDN.");
         }
      }
   }

   /** @deprecated */
   public RDN(String var1, int var2) throws NameException {
      ByteBuffer var3 = ByteBuffer.allocate(4);
      if (var1 == null) {
         throw new NameException("The string is null.");
      } else if (var2 != 0 && var2 != 3072 && var2 != 4864 && var2 != 5120 && var2 != 5632 && var2 != 7168 && var2 != 7680) {
         throw new NameException("The ASN.1 string encoding type is invalid.");
      } else {
         StringTokenizer var4 = new StringTokenizer(var1, "\\+", true);
         int var5 = var4.countTokens();
         ArrayList var6 = new ArrayList();
         StringBuilder var7 = new StringBuilder();

         int var9;
         for(var9 = 0; var9 < var5; ++var9) {
            String var8 = var4.nextToken();
            if (var8.equals("\\")) {
               StringBuilder var10 = new StringBuilder(var4.nextToken());
               if (!checkSpecialChar(var10.charAt(0))) {
                  this.processUtf8Byte(var10, var3);
               }

               var7.append(var10.toString());
               ++var9;
            } else {
               var7.append(this.finalizeUtf8Sequence(var3));
               if (!var8.equals("+")) {
                  var7.append(var8);
               } else {
                  var6.add(var7.toString());
                  var7 = new StringBuilder();
               }
            }
         }

         var7.append(this.finalizeUtf8Sequence(var3));
         if (var7.length() != 0) {
            var6.add(var7.toString());
         }

         for(var9 = 0; var9 < var6.size(); ++var9) {
            String var12 = (String)var6.get(var9);
            AttributeValueAssertion var11 = new AttributeValueAssertion(var12, var2);
            this.addNameAVA(var11);
         }

      }
   }

   private void processUtf8Byte(StringBuilder var1, ByteBuffer var2) throws NameException {
      var2.put(getHexByte(var1));
      if (var1.length() > 0) {
         var1.insert(0, this.finalizeUtf8Sequence(var2));
      }
   }

   private String finalizeUtf8Sequence(ByteBuffer var1) {
      if (var1.position() == 0) {
         return "";
      } else {
         String var2 = null;

         try {
            var2 = new String(var1.array(), 0, var1.position(), "UTF-8");
         } catch (UnsupportedEncodingException var4) {
         }

         var1.clear();
         return var2;
      }
   }

   private static boolean checkSpecialChar(char var0) {
      int var1;
      for(var1 = 0; var1 < SPECIAL_CHARACTERS.length && var0 != SPECIAL_CHARACTERS[var1]; ++var1) {
      }

      return var1 != SPECIAL_CHARACTERS.length;
   }

   private static byte getHexByte(StringBuilder var0) throws NameException {
      if (var0.length() < 2) {
         throw new NameException("Invalid UTF-8 escape sequence: Two characters per hex byte required");
      } else {
         try {
            int var1 = Integer.parseInt(var0.substring(0, 2), 16);
            var0.delete(0, 2);
            return (byte)(var1 & 255);
         } catch (NumberFormatException var2) {
            throw new NameException("Invalid UTF-8 escape sequence: Escaped hex byte invalid");
         }
      }
   }

   /** @deprecated */
   public RDN() {
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      RDN var1 = new RDN();

      for(int var2 = 0; var2 < this.avaList.size(); ++var2) {
         AttributeValueAssertion var3 = (AttributeValueAssertion)this.avaList.elementAt(var2);
         var1.avaList.addElement((AttributeValueAssertion)var3.clone());
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }

   /** @deprecated */
   public String toString() {
      return this.toString(true);
   }

   /** @deprecated */
   public String toString(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      if (this.avaList.isEmpty()) {
         return "";
      } else {
         for(int var3 = 0; var3 < this.avaList.size(); ++var3) {
            AttributeValueAssertion var4 = (AttributeValueAssertion)this.avaList.elementAt(var3);
            String var5 = var4.toString(var1);
            var2.append(var5);
            if (var3 < this.avaList.size() - 1) {
               var2.append("+");
            }
         }

         return var2.toString();
      }
   }

   /** @deprecated */
   public int getAttributeCount() {
      return this.avaList.size();
   }

   /** @deprecated */
   public AttributeValueAssertion getAttributeByIndex(int var1) throws NameException {
      int var2 = this.avaList.size();
      if (var1 >= 0 && var2 > var1) {
         return (AttributeValueAssertion)this.avaList.elementAt(var1);
      } else {
         throw new NameException("Invalid Index.");
      }
   }

   /** @deprecated */
   public void removeAVA(int var1) throws NameException {
      if (var1 < this.avaList.size()) {
         this.avaList.removeElementAt(var1);
      } else {
         throw new NameException("Invalid Index.");
      }
   }

   /** @deprecated */
   public void setAVA(AttributeValueAssertion var1, int var2) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified AVA is null.");
      } else if (var2 < this.avaList.size()) {
         this.avaList.setElementAt(var1, var2);
      } else {
         throw new NameException("Invalid Index.");
      }
   }

   /** @deprecated */
   public AttributeValueAssertion getAttribute(int var1) {
      int var2 = this.avaList.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         AttributeValueAssertion var4 = (AttributeValueAssertion)this.avaList.elementAt(var3);
         if (var4.getAttributeType() == var1) {
            return var4;
         }
      }

      return null;
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws NameException {
      if (var0 == null) {
         throw new NameException("Specified array is null.");
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
               if (this.asn1Template == null) {
                  throw new NameException("Unable to encode RDN.");
               }
            }

            return this.asn1Template.derEncode(var1, var2);
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode RDN.");
         }
      }
   }

   private int derEncodeInit() {
      try {
         int var1 = this.avaList.size();
         OfContainer var2 = new OfContainer(this.special, true, 0, 12544, new EncodedContainer(12288));

         for(int var3 = 0; var3 < var1; ++var3) {
            AttributeValueAssertion var4 = (AttributeValueAssertion)this.avaList.elementAt(var3);
            int var5 = var4.getDERLen();
            byte[] var6 = new byte[var5];
            var5 = var4.getDEREncoding(var6, 0);
            EncodedContainer var7 = new EncodedContainer(12288, true, 0, var6, 0, var5);
            var2.addContainer(var7);
         }

         ASN1Container[] var10 = new ASN1Container[]{var2};
         this.asn1Template = new ASN1Template(var10);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var8) {
         return 0;
      } catch (NameException var9) {
         return 0;
      }
   }

   /** @deprecated */
   public void addNameAVA(AttributeValueAssertion var1) {
      if (var1 != null) {
         this.avaList.addElement(var1);
      }

   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof RDN) {
         RDN var2 = (RDN)var1;
         if (this.avaList.size() != var2.avaList.size()) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.avaList.size(); ++var3) {
               boolean var4 = this.findAVA((AttributeValueAssertion)this.avaList.elementAt(var3), var2.avaList);
               if (!var4) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean findAVA(AttributeValueAssertion var1, Vector var2) {
      if (var1 != null && var2 != null) {
         for(int var3 = 0; var3 < var2.size(); ++var3) {
            if (var1.equals(var2.elementAt(var3))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.avaList.size(); ++var2) {
         var1 ^= ((AttributeValueAssertion)this.avaList.elementAt(var2)).hashCode();
         var1 *= 33;
      }

      return var1;
   }
}

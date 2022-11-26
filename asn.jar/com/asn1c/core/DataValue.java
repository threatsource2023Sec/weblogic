package com.asn1c.core;

import java.io.PrintWriter;

public class DataValue implements ASN1Object, Comparable {
   protected int selector;
   public static final int NOTATION_SELECTED = 0;
   public static final int ENCODEDBITSTRING_SELECTED = 1;
   public static final int ENCODEDOCTETSTRING_SELECTED = 2;
   protected ASN1Object dataValue;

   public DataValue(int var1, Open var2) {
      if (var1 != 0) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.dataValue = var2;
      }
   }

   public DataValue(int var1, BitString var2) {
      if (var1 != 1) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.dataValue = var2;
      }
   }

   public DataValue(int var1, OctetString var2) {
      if (var1 != 2) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.dataValue = var2;
      }
   }

   public DataValue(DataValue var1) {
      this.selector = var1.selector;
      switch (this.selector) {
         case 0:
            this.dataValue = new Open((Open)var1.dataValue);
            break;
         case 1:
            this.dataValue = new BitString((BitString)var1.dataValue);
            break;
         case 2:
            this.dataValue = new OctetString((OctetString)var1.dataValue);
      }

   }

   public DataValue() {
      this.selector = -1;
      this.dataValue = null;
   }

   public void setValue(int var1, ASN1Object var2) {
      this.selector = var1;
      this.dataValue = var2;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      switch (this.selector) {
         case 0:
            var1.println(var2 + var3 + "notation:");
            this.dataValue.print(var1, var2 + "    ", "", var4, var5);
            break;
         case 1:
            var1.println(var2 + var3 + "encoded-bit-string:");
            this.dataValue.print(var1, var2 + "    ", "", var4, var5);
            break;
         case 2:
            var1.println(var2 + var3 + "encoded-octet-string:");
            this.dataValue.print(var1, var2 + "    ", "", var4, var5);
            break;
         default:
            var1.println(var2 + var3 + "INVALID" + var4);
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      switch (this.selector) {
         case 0:
            var1.append("notation: ");
            var1.append(this.dataValue);
            break;
         case 1:
            var1.append("encoded-bit-string: ");
            var1.append(this.dataValue);
            break;
         case 2:
            var1.append("encoded-octet-string: ");
            var1.append(this.dataValue);
            break;
         default:
            var1.append("INVALID");
      }

      return var1.toString();
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public int getSelector() {
      return this.selector;
   }

   public void setSelector(int var1) {
      if (this.selector != var1) {
         this.selector = var1;
         this.dataValue = null;
      }
   }

   public Open getNotation() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return (Open)this.dataValue;
      }
   }

   public void setNotation(Open var1) {
      this.setSelector(0);
      this.dataValue = var1;
   }

   public BitString getEncodedBitString() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return (BitString)this.dataValue;
      }
   }

   public void setEncodedBitString(BitString var1) {
      this.setSelector(1);
      this.dataValue = var1;
   }

   public OctetString getEncodedOctetString() {
      if (this.selector != 2) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.dataValue;
      }
   }

   public void setEncodedOctetString(OctetString var1) {
      this.setSelector(2);
      this.dataValue = var1;
   }

   public int compareTo(Object var1) {
      DataValue var2 = (DataValue)var1;
      if (this.selector != var2.selector) {
         return this.selector - var2.selector;
      } else {
         switch (this.selector) {
            case 0:
               return ((Open)this.dataValue).compareTo((Open)var2.dataValue);
            case 1:
               return ((BitString)this.dataValue).compareTo((BitString)var2.dataValue);
            case 2:
               return ((OctetString)this.dataValue).compareTo((OctetString)var2.dataValue);
            default:
               return 0;
         }
      }
   }
}

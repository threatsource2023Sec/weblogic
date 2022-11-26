package com.octetstring.vde.syntax;

import com.octetstring.vde.util.Base64;
import java.io.Serializable;

public class BinarySyntax extends Syntax implements Serializable, Comparable {
   byte[] value;
   int hashCode = 0;

   public BinarySyntax() {
   }

   public BinarySyntax(byte[] value) {
      this.setValue(value);
   }

   public BinarySyntax(byte[] value, int hashCode) {
      this.value = value;
      this.hashCode = hashCode;
   }

   public byte[] getValue() {
      return this.value;
   }

   public void setValue(byte[] value) {
      this.value = value;
      this.hashCode = 0;
      int byteNo = false;
      byte aBitNo = false;

      for(int byteNo = 0; byteNo < value.length; ++byteNo) {
         this.hashCode = this.hashCode + value[byteNo] * 31 ^ value.length - byteNo + 1;
      }

   }

   public void setValue(byte[] value, int hashCode) {
      this.value = value;
      this.hashCode = hashCode;
   }

   public int compareTo(BinarySyntax bin) {
      int equal = 0;
      byte[] byteArray1 = this.getValue();
      byte[] byteArray2 = bin.getValue();
      int ba2size = byteArray2.length;

      for(int byteNo = 0; byteNo < byteArray1.length; ++byteNo) {
         if (byteNo >= ba2size) {
            equal = 1;
            break;
         }

         equal = byteArray1[byteNo] - byteArray2[byteNo];
         if (equal != 0) {
            break;
         }
      }

      if (equal == 0 && ba2size > byteArray2.length) {
         equal = -1;
      }

      return equal;
   }

   public int compareTo(Syntax val) {
      return this.compareTo((BinarySyntax)val);
   }

   public int compareTo(Object obj) {
      return this.compareTo((BinarySyntax)obj);
   }

   public boolean endsWith(Syntax val) {
      return this.toString().endsWith(val.toString());
   }

   public boolean equals(BinarySyntax bin) {
      if (this.hashCode != bin.hashCode()) {
         return false;
      } else {
         return this.compareTo(bin) == 0;
      }
   }

   public boolean equals(Object obj) {
      if (this.hashCode != obj.hashCode()) {
         return false;
      } else {
         return this.compareTo((BinarySyntax)obj) == 0;
      }
   }

   public byte[] getBytes() {
      return this.value;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int indexOf(BinarySyntax indexString) {
      return this.toString().indexOf(indexString.toString());
   }

   public int indexOf(Syntax val) {
      return this.indexOf((BinarySyntax)val);
   }

   public int length() {
      return this.getValue().length;
   }

   public String normalize() {
      return this.toString();
   }

   public Syntax reverse() {
      byte[] orig = this.getValue();
      byte[] reverse = new byte[orig.length];

      for(int byteNo = 0; byteNo < orig.length; ++byteNo) {
         reverse[byteNo] = orig[orig.length - byteNo - 1];
      }

      return new BinarySyntax(reverse);
   }

   public boolean startsWith(Syntax val) {
      return this.toString().startsWith(val.toString());
   }

   public String toString() {
      return Base64.encode(this.getValue());
   }

   public String returnAttributeValue(byte[] decryptedValue) {
      return Base64.encode(decryptedValue);
   }
}

package com.octetstring.vde.syntax;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class IA5String extends Syntax implements Serializable, Comparable {
   byte[] value;
   int hashCode = 0;

   public IA5String() {
   }

   public IA5String(byte[] value) {
      this.setValue(value);
   }

   public IA5String(byte[] value, int hashCode) {
      this.value = value;
      this.hashCode = hashCode;
   }

   public IA5String(String value) {
      this.setValue(value);
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

   public void setValue(String value) {
      try {
         this.setValue(value.getBytes("UTF8"));
      } catch (UnsupportedEncodingException var3) {
      }

   }

   public int compareTo(IA5String ds) {
      int equal = 0;
      byte[] stringOne = this.getValue();
      byte[] stringTwo = ds.getValue();
      int stwoSize = stringTwo.length;

      for(int byteNo = 0; byteNo < stringOne.length; ++byteNo) {
         if (byteNo >= stwoSize) {
            equal = 1;
            break;
         }

         equal = stringOne[byteNo] - stringTwo[byteNo];
         if (equal != 0) {
            break;
         }
      }

      if (equal == 0 && stwoSize > stringOne.length) {
         equal = -1;
      }

      return equal;
   }

   public int compareTo(Syntax val) {
      return this.compareTo((IA5String)val);
   }

   public int compareTo(Object obj) {
      return this.compareTo((IA5String)obj);
   }

   public boolean endsWith(IA5String endsString) {
      int equal = 0;
      byte[] stringOne = this.getValue();
      byte[] stringTwo = endsString.getValue();
      int soneSize = stringOne.length;
      int stwoSize = stringTwo.length;
      if (stwoSize > soneSize) {
         return false;
      } else {
         int startOne = soneSize - stwoSize;

         for(int byteNo = stwoSize - 1; byteNo >= 0; --byteNo) {
            equal = stringOne[startOne + byteNo] - stringTwo[byteNo];
            if (equal != 0) {
               break;
            }
         }

         return equal == 0;
      }
   }

   public boolean endsWith(Syntax val) {
      return this.endsWith((IA5String)val);
   }

   public boolean equals(IA5String ds) {
      if (this.hashCode != ds.hashCode()) {
         return false;
      } else {
         return this.compareTo(ds) == 0;
      }
   }

   public boolean equals(Object obj) {
      if (this.hashCode != obj.hashCode()) {
         return false;
      } else {
         return this.compareTo((IA5String)obj) == 0;
      }
   }

   public byte[] getBytes() {
      return this.value;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int indexOf(IA5String indexString) {
      return this.toString().indexOf(indexString.toString());
   }

   public int indexOf(Syntax val) {
      return this.indexOf((IA5String)val);
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

      return new IA5String(reverse);
   }

   public boolean startsWith(IA5String startString) {
      int equal = 0;
      byte[] stringOne = this.getValue();
      byte[] stringTwo = startString.getValue();
      int soneSize = stringOne.length;
      int stwoSize = stringTwo.length;
      if (stwoSize > soneSize) {
         return false;
      } else {
         for(int byteNo = 0; byteNo < stwoSize; ++byteNo) {
            equal = stringOne[byteNo] - stringTwo[byteNo];
            if (equal != 0) {
               break;
            }
         }

         return equal == 0;
      }
   }

   public boolean startsWith(Syntax val) {
      return this.startsWith((IA5String)val);
   }

   public String toString() {
      try {
         return new String(this.getValue(), "UTF8");
      } catch (UnsupportedEncodingException var2) {
         return new String(this.getValue());
      }
   }
}

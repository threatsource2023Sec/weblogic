package com.bea.common.security.xacml.attr;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class HexBinaryAttribute extends AttributeValue {
   private byte[] value;

   public HexBinaryAttribute(byte[] value) {
      this.value = value;
   }

   public HexBinaryAttribute(String value) throws InvalidAttributeException {
      int len = value.length();
      if (len % 2 != 0) {
         throw new InvalidAttributeException(ApiLogger.getHexBinaryBadLength());
      } else {
         int byteCount = len / 2;
         byte[] bytes = new byte[byteCount];
         int charIndex = 0;

         for(int byteIndex = 0; byteIndex < byteCount; ++byteIndex) {
            int hiNibble = this.hexToBinNibble(value.charAt(charIndex++));
            int loNibble = this.hexToBinNibble(value.charAt(charIndex++));
            bytes[byteIndex] = (byte)(hiNibble * 16 + loNibble);
         }

         this.value = bytes;
      }
   }

   public Type getType() {
      return Type.HEX_BINARY;
   }

   private int hexToBinNibble(char c) throws InvalidAttributeException {
      if (c >= '0' && c <= '9') {
         return c - 48;
      } else if (c >= 'a' && c <= 'f') {
         return c - 97 + 10;
      } else if (c >= 'A' && c <= 'F') {
         return c - 65 + 10;
      } else {
         throw new InvalidAttributeException(ApiLogger.getInvalidHexDigit());
      }
   }

   public ByteArrayHolder getValue() {
      return new ByteArrayHolder(this.value) {
         public String toString() {
            byte[] v = this.getData();
            StringBuffer sb = new StringBuffer();

            for(int byteIndex = 0; byteIndex < v.length; ++byteIndex) {
               byte b = v[byteIndex];
               sb.append(HexBinaryAttribute.this.binToHexNibble(b >> 4 & 15));
               sb.append(HexBinaryAttribute.this.binToHexNibble(b & 15));
            }

            return sb.toString();
         }
      };
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      for(int byteIndex = 0; byteIndex < this.value.length; ++byteIndex) {
         byte b = this.value[byteIndex];
         sb.append(this.binToHexNibble(b >> 4 & 15));
         sb.append(this.binToHexNibble(b & 15));
      }

      return sb.toString();
   }

   private char binToHexNibble(int nibble) {
      char result = false;
      return nibble < 10 ? (char)(nibble + 48) : (char)(nibble - 10 + 65);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof HexBinaryAttribute)) {
         return false;
      } else {
         HexBinaryAttribute other = (HexBinaryAttribute)o;
         return Arrays.equals(this.value, other.value);
      }
   }

   public int internalHashCode() {
      return com.bea.common.security.jdkutils.WeaverUtil.Arrays.hashCode(this.value);
   }

   public int compareTo(HexBinaryAttribute other) {
      int l = this.value.length;
      int lo = other.value.length;
      int i = 0;

      int j;
      for(j = 0; i < l; ++j) {
         if (j >= lo) {
            return Math.abs(this.value[i]);
         }

         if (this.value[i] != other.value[j]) {
            return this.value[i] - other.value[j];
         }

         ++i;
      }

      if (j >= lo) {
         return 0;
      } else {
         return Math.abs(other.value[j]) * -1;
      }
   }

   public boolean add(HexBinaryAttribute o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return new Iterator() {
         boolean nextNotCalled = true;

         public boolean hasNext() {
            return this.nextNotCalled;
         }

         public HexBinaryAttribute next() {
            this.nextNotCalled = false;
            return HexBinaryAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

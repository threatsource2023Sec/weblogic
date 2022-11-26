package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.attr.encoders.BASE64Decoder;
import com.bea.common.security.xacml.attr.encoders.BASE64Encoder;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Base64BinaryAttribute extends AttributeValue {
   private static BASE64Encoder b64Encoder = new BASE64Encoder();
   private static BASE64Decoder b64Decoder = new BASE64Decoder();
   private byte[] value;

   public Base64BinaryAttribute(byte[] value) {
      this.value = value;
   }

   public Type getType() {
      return Type.BASE64_BINARY;
   }

   public Base64BinaryAttribute(String value) throws InvalidAttributeException {
      this(decode(value));
   }

   private static byte[] decode(String value) throws InvalidAttributeException {
      try {
         return b64Decoder.decodeBuffer(value);
      } catch (IOException var2) {
         throw new InvalidAttributeException(var2);
      }
   }

   public ByteArrayHolder getValue() {
      return new ByteArrayHolder(this.value) {
         public String toString() {
            return Base64BinaryAttribute.b64Encoder.encodeBuffer(this.getData());
         }
      };
   }

   public void encodeValue(PrintStream ps) {
      ps.print(b64Encoder.encodeBuffer(this.value));
   }

   public String toString() {
      return b64Encoder.encodeBuffer(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Base64BinaryAttribute)) {
         return false;
      } else {
         Base64BinaryAttribute other = (Base64BinaryAttribute)o;
         return Arrays.equals(this.value, other.value);
      }
   }

   public int internalHashCode() {
      return com.bea.common.security.jdkutils.WeaverUtil.Arrays.hashCode(this.value);
   }

   public int compareTo(Base64BinaryAttribute other) {
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

   public boolean add(Base64BinaryAttribute o) {
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

         public Base64BinaryAttribute next() {
            this.nextNotCalled = false;
            return Base64BinaryAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

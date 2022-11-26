package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class IntegerAttribute extends AttributeValue {
   private int value;

   public IntegerAttribute(int value) {
      this.value = value;
   }

   public Type getType() {
      return Type.INTEGER;
   }

   public IntegerAttribute(Integer value) {
      this(value);
   }

   public IntegerAttribute(String value) throws InvalidAttributeException {
      try {
         this.value = Integer.parseInt(value);
      } catch (NumberFormatException var3) {
         throw new InvalidAttributeException(var3);
      }
   }

   public int getIntValue() {
      return this.value;
   }

   public Integer getValue() {
      return new Integer(this.value);
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getIntValue());
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public int compareTo(IntegerAttribute other) {
      return this.value - other.value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof IntegerAttribute)) {
         return false;
      } else {
         IntegerAttribute other = (IntegerAttribute)o;
         return this.value == other.value;
      }
   }

   public int internalHashCode() {
      return this.value;
   }

   public boolean add(IntegerAttribute o) {
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

         public IntegerAttribute next() {
            this.nextNotCalled = false;
            return IntegerAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

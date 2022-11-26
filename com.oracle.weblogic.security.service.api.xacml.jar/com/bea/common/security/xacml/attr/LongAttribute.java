package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class LongAttribute extends AttributeValue {
   private long value;

   public LongAttribute(long value) {
      this.value = value;
   }

   public Type getType() {
      return Type.LONG;
   }

   public LongAttribute(Long value) {
      this(value);
   }

   public LongAttribute(String value) throws InvalidAttributeException {
      try {
         this.value = Long.parseLong(value);
      } catch (NumberFormatException var3) {
         throw new InvalidAttributeException(var3);
      }
   }

   public long getLongValue() {
      return this.value;
   }

   public Long getValue() {
      return new Long(this.value);
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getLongValue());
   }

   public int compareTo(LongAttribute other) {
      if (this.value == other.value) {
         return 0;
      } else {
         return this.value < other.value ? -1 : 1;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof LongAttribute)) {
         return false;
      } else {
         LongAttribute other = (LongAttribute)o;
         return this.value == other.value;
      }
   }

   public int internalHashCode() {
      return (int)this.value;
   }

   public boolean add(LongAttribute o) {
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

         public LongAttribute next() {
            this.nextNotCalled = false;
            return LongAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

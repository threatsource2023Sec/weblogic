package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class BooleanAttribute extends AttributeValue {
   public static final BooleanAttribute TRUE = new BooleanAttribute(true);
   public static final BooleanAttribute FALSE = new BooleanAttribute(false);
   private boolean value;

   public BooleanAttribute(boolean value) {
      this.value = value;
   }

   public Type getType() {
      return Type.BOOLEAN;
   }

   public BooleanAttribute(Boolean value) {
      this(value);
   }

   public BooleanAttribute(String value) throws InvalidAttributeException {
      if (!"true".equals(value) && !"1".equals(value)) {
         if (!"false".equals(value) && !"0".equals(value)) {
            throw new InvalidAttributeException("Illegal literal value for boolean data-type");
         }

         this.value = false;
      } else {
         this.value = true;
      }

   }

   public boolean getBooleanValue() {
      return this.value;
   }

   public Boolean getValue() {
      return this.value ? Boolean.TRUE : Boolean.FALSE;
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getBooleanValue() ? "true" : "false");
   }

   public String toString() {
      return this.getBooleanValue() ? "true" : "false";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof BooleanAttribute)) {
         return false;
      } else {
         BooleanAttribute other = (BooleanAttribute)o;
         return this.value == other.value;
      }
   }

   public int internalHashCode() {
      return this.getValue().hashCode();
   }

   public int compareTo(BooleanAttribute other) {
      if (this.value == other.value) {
         return 0;
      } else {
         return this.value ? 1 : -1;
      }
   }

   public boolean add(BooleanAttribute o) {
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

         public BooleanAttribute next() {
            this.nextNotCalled = false;
            return BooleanAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

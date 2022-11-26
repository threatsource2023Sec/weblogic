package com.bea.common.security.xacml.attr;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class DecimalAttribute extends AttributeValue {
   private Double value;

   public DecimalAttribute(Double value) {
      this.value = value;
   }

   public Type getType() {
      return Type.DECIMAL;
   }

   public DecimalAttribute(String value) throws InvalidAttributeException {
      if (value.length() <= 0) {
         throw new InvalidAttributeException(ApiLogger.getDecimalIsTooShort());
      } else {
         int idx = 0;
         boolean isNegative = value.charAt(idx) == '-';
         if (isNegative || value.charAt(idx) == '+') {
            ++idx;
         }

         while(idx < value.length() && Character.isDigit(value.charAt(idx))) {
            ++idx;
         }

         ++idx;

         while(idx < value.length() && Character.isDigit(value.charAt(idx))) {
            ++idx;
         }

         if (idx < value.length()) {
            throw new InvalidAttributeException(ApiLogger.getDecimalIncorrectCharacters());
         } else {
            this.value = new Double(value);
         }
      }
   }

   public String toString() {
      return this.value.toString();
   }

   public Double getValue() {
      return this.value;
   }

   public int compareTo(DecimalAttribute other) {
      return this.value.compareTo(other.value);
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DecimalAttribute)) {
         return false;
      } else {
         DecimalAttribute other = (DecimalAttribute)o;
         return this.value == other.value || this.value != null && this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(DecimalAttribute o) {
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

         public DecimalAttribute next() {
            this.nextNotCalled = false;
            return DecimalAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

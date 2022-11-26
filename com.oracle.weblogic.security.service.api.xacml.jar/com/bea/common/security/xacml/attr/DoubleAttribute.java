package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

public class DoubleAttribute extends AttributeValue {
   private static final Double NAN = new Double(Double.NaN);
   private Double value;

   public DoubleAttribute(double value) {
      if (Double.isNaN(value)) {
         this.value = NAN;
      }

      this.value = new Double(value);
   }

   public Type getType() {
      return Type.DOUBLE;
   }

   public DoubleAttribute(Double value) {
      if (Double.isNaN(value)) {
         this.value = NAN;
      }

      this.value = value;
   }

   public DoubleAttribute(String value) throws InvalidAttributeException {
      try {
         if ("INF".equals(value)) {
            this.value = new Double(Double.POSITIVE_INFINITY);
         } else if ("-INF".equals(value)) {
            this.value = new Double(Double.NEGATIVE_INFINITY);
         } else if ("NaN".equals(value)) {
            this.value = NAN;
         } else {
            this.value = new Double(Double.parseDouble(value));
         }

      } catch (NumberFormatException var3) {
         throw new InvalidAttributeException(var3);
      }
   }

   public double getDoubleValue() {
      return this.value;
   }

   public Double getValue() {
      return this.value;
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public int compareTo(DoubleAttribute other) {
      return this.value.compareTo(other.value);
   }

   public String toString() {
      if (Double.POSITIVE_INFINITY == this.value) {
         return "INF";
      } else if (Double.NEGATIVE_INFINITY == this.value) {
         return "-INF";
      } else if (Double.isNaN(this.value)) {
         return "NaN";
      } else {
         DecimalFormat df = new DecimalFormat("0.0#################E0");
         return df.format(this.value);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DoubleAttribute)) {
         return false;
      } else {
         DoubleAttribute other = (DoubleAttribute)o;
         return this.value == other.value || this.value != null && this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.intValue();
   }

   public boolean add(DoubleAttribute o) {
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

         public DoubleAttribute next() {
            this.nextNotCalled = false;
            return DoubleAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}

package org.python.icu.util;

public class Measure {
   private final Number number;
   private final MeasureUnit unit;

   public Measure(Number number, MeasureUnit unit) {
      if (number != null && unit != null) {
         this.number = number;
         this.unit = unit;
      } else {
         throw new NullPointerException("Number and MeasureUnit must not be null");
      }
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof Measure)) {
         return false;
      } else {
         Measure m = (Measure)obj;
         return this.unit.equals(m.unit) && numbersEqual(this.number, m.number);
      }
   }

   private static boolean numbersEqual(Number a, Number b) {
      if (a.equals(b)) {
         return true;
      } else {
         return a.doubleValue() == b.doubleValue();
      }
   }

   public int hashCode() {
      return 31 * Double.valueOf(this.number.doubleValue()).hashCode() + this.unit.hashCode();
   }

   public String toString() {
      return this.number.toString() + ' ' + this.unit.toString();
   }

   public Number getNumber() {
      return this.number;
   }

   public MeasureUnit getUnit() {
      return this.unit;
   }
}

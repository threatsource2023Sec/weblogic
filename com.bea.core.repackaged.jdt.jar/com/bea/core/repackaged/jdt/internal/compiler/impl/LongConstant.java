package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class LongConstant extends Constant {
   private static final LongConstant ZERO = new LongConstant(0L);
   private static final LongConstant MIN_VALUE = new LongConstant(Long.MIN_VALUE);
   private long value;

   public static Constant fromValue(long value) {
      if (value == 0L) {
         return ZERO;
      } else {
         return value == Long.MIN_VALUE ? MIN_VALUE : new LongConstant(value);
      }
   }

   private LongConstant(long value) {
      this.value = value;
   }

   public byte byteValue() {
      return (byte)((int)this.value);
   }

   public char charValue() {
      return (char)((int)this.value);
   }

   public double doubleValue() {
      return (double)this.value;
   }

   public float floatValue() {
      return (float)this.value;
   }

   public int intValue() {
      return (int)this.value;
   }

   public long longValue() {
      return this.value;
   }

   public short shortValue() {
      return (short)((int)this.value);
   }

   public String stringValue() {
      return String.valueOf(this.value);
   }

   public String toString() {
      return "(long)" + this.value;
   }

   public int typeID() {
      return 7;
   }

   public int hashCode() {
      return (int)(this.value ^ this.value >>> 32);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         LongConstant other = (LongConstant)obj;
         return this.value == other.value;
      }
   }
}

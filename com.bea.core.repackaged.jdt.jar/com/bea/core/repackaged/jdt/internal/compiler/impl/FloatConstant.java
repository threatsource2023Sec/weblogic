package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class FloatConstant extends Constant {
   float value;

   public static Constant fromValue(float value) {
      return new FloatConstant(value);
   }

   private FloatConstant(float value) {
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
      return this.value;
   }

   public int intValue() {
      return (int)this.value;
   }

   public long longValue() {
      return (long)this.value;
   }

   public short shortValue() {
      return (short)((int)this.value);
   }

   public String stringValue() {
      return String.valueOf(this.value);
   }

   public String toString() {
      return "(float)" + this.value;
   }

   public int typeID() {
      return 9;
   }

   public int hashCode() {
      return Float.floatToIntBits(this.value);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         FloatConstant other = (FloatConstant)obj;
         return Float.floatToIntBits(this.value) == Float.floatToIntBits(other.value);
      }
   }
}

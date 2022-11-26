package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class ShortConstant extends Constant {
   private short value;

   public static Constant fromValue(short value) {
      return new ShortConstant(value);
   }

   private ShortConstant(short value) {
      this.value = value;
   }

   public byte byteValue() {
      return (byte)this.value;
   }

   public char charValue() {
      return (char)this.value;
   }

   public double doubleValue() {
      return (double)this.value;
   }

   public float floatValue() {
      return (float)this.value;
   }

   public int intValue() {
      return this.value;
   }

   public long longValue() {
      return (long)this.value;
   }

   public short shortValue() {
      return this.value;
   }

   public String stringValue() {
      return String.valueOf(this.value);
   }

   public String toString() {
      return "(short)" + this.value;
   }

   public int typeID() {
      return 4;
   }

   public int hashCode() {
      return this.value;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ShortConstant other = (ShortConstant)obj;
         return this.value == other.value;
      }
   }
}

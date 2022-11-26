package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class ByteConstant extends Constant {
   private byte value;

   public static Constant fromValue(byte value) {
      return new ByteConstant(value);
   }

   private ByteConstant(byte value) {
      this.value = value;
   }

   public byte byteValue() {
      return this.value;
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
      return "(byte)" + this.value;
   }

   public int typeID() {
      return 3;
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
         ByteConstant other = (ByteConstant)obj;
         return this.value == other.value;
      }
   }
}

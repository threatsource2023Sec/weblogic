package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class CharConstant extends Constant {
   private char value;

   public static Constant fromValue(char value) {
      return new CharConstant(value);
   }

   private CharConstant(char value) {
      this.value = value;
   }

   public byte byteValue() {
      return (byte)this.value;
   }

   public char charValue() {
      return this.value;
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
      return (short)this.value;
   }

   public String stringValue() {
      return String.valueOf(this.value);
   }

   public String toString() {
      return "(char)" + this.value;
   }

   public int typeID() {
      return 2;
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
         CharConstant other = (CharConstant)obj;
         return this.value == other.value;
      }
   }
}

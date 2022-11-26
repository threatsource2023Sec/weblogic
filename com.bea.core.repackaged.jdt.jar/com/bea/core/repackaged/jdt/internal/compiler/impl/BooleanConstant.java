package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class BooleanConstant extends Constant {
   private boolean value;
   private static final BooleanConstant TRUE = new BooleanConstant(true);
   private static final BooleanConstant FALSE = new BooleanConstant(false);

   public static Constant fromValue(boolean value) {
      return value ? TRUE : FALSE;
   }

   private BooleanConstant(boolean value) {
      this.value = value;
   }

   public boolean booleanValue() {
      return this.value;
   }

   public String stringValue() {
      return String.valueOf(this.value);
   }

   public String toString() {
      return "(boolean)" + this.value;
   }

   public int typeID() {
      return 5;
   }

   public int hashCode() {
      return this.value ? 1231 : 1237;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return this.getClass() != obj.getClass() ? false : false;
      }
   }
}

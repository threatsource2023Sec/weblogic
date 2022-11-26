package com.kenai.jffi;

import java.util.Arrays;

public final class Union extends Aggregate {
   private final Type[] fields;

   public static Union newUnion(Type... fields) {
      return new Union(fields);
   }

   public Union(Type... fields) {
      super(Foreign.getInstance(), Foreign.getInstance().newStruct(Type.nativeHandles(fields), true));
      this.fields = (Type[])fields.clone();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         if (!super.equals(o)) {
            return false;
         } else {
            Union union = (Union)o;
            return Arrays.equals(this.fields, union.fields);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.fields != null ? Arrays.hashCode(this.fields) : 0);
      return result;
   }
}

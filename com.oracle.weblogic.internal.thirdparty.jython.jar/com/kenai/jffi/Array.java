package com.kenai.jffi;

public final class Array extends Aggregate {
   private final Type elementType;
   private final int length;

   public static Array newArray(Type elementType, int length) {
      return new Array(elementType, length);
   }

   public Array(Type elementType, int length) {
      super(Foreign.getInstance(), Foreign.getInstance().newArray(elementType.handle(), length));
      this.elementType = elementType;
      this.length = length;
   }

   public final Type getElementType() {
      return this.elementType;
   }

   public final int length() {
      return this.length;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         if (!super.equals(o)) {
            return false;
         } else {
            Array array = (Array)o;
            if (this.length != array.length) {
               return false;
            } else {
               if (this.elementType != null) {
                  if (!this.elementType.equals(array.elementType)) {
                     return false;
                  }
               } else if (array.elementType != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.elementType != null ? this.elementType.hashCode() : 0);
      result = 31 * result + this.length;
      return result;
   }
}

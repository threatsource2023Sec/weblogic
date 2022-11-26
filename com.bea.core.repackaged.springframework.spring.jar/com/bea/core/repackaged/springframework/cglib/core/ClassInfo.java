package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Type;

public abstract class ClassInfo {
   protected ClassInfo() {
   }

   public abstract Type getType();

   public abstract Type getSuperType();

   public abstract Type[] getInterfaces();

   public abstract int getModifiers();

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return !(o instanceof ClassInfo) ? false : this.getType().equals(((ClassInfo)o).getType());
      }
   }

   public int hashCode() {
      return this.getType().hashCode();
   }

   public String toString() {
      return this.getType().getClassName();
   }
}

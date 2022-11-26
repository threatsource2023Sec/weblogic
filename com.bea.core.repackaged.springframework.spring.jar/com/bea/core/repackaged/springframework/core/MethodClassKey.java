package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;

public final class MethodClassKey implements Comparable {
   private final Method method;
   @Nullable
   private final Class targetClass;

   public MethodClassKey(Method method, @Nullable Class targetClass) {
      this.method = method;
      this.targetClass = targetClass;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MethodClassKey)) {
         return false;
      } else {
         MethodClassKey otherKey = (MethodClassKey)other;
         return this.method.equals(otherKey.method) && ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass);
      }
   }

   public int hashCode() {
      return this.method.hashCode() + (this.targetClass != null ? this.targetClass.hashCode() * 29 : 0);
   }

   public String toString() {
      return this.method + (this.targetClass != null ? " on " + this.targetClass : "");
   }

   public int compareTo(MethodClassKey other) {
      int result = this.method.getName().compareTo(other.method.getName());
      if (result == 0) {
         result = this.method.toString().compareTo(other.method.toString());
         if (result == 0 && this.targetClass != null && other.targetClass != null) {
            result = this.targetClass.getName().compareTo(other.targetClass.getName());
         }
      }

      return result;
   }
}

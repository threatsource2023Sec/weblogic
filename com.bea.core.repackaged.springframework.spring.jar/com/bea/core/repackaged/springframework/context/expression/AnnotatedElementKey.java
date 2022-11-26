package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.AnnotatedElement;

public final class AnnotatedElementKey implements Comparable {
   private final AnnotatedElement element;
   @Nullable
   private final Class targetClass;

   public AnnotatedElementKey(AnnotatedElement element, @Nullable Class targetClass) {
      Assert.notNull(element, (String)"AnnotatedElement must not be null");
      this.element = element;
      this.targetClass = targetClass;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotatedElementKey)) {
         return false;
      } else {
         AnnotatedElementKey otherKey = (AnnotatedElementKey)other;
         return this.element.equals(otherKey.element) && ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass);
      }
   }

   public int hashCode() {
      return this.element.hashCode() + (this.targetClass != null ? this.targetClass.hashCode() * 29 : 0);
   }

   public String toString() {
      return this.element + (this.targetClass != null ? " on " + this.targetClass : "");
   }

   public int compareTo(AnnotatedElementKey other) {
      int result = this.element.toString().compareTo(other.element.toString());
      if (result == 0 && this.targetClass != null) {
         if (other.targetClass == null) {
            return 1;
         }

         result = this.targetClass.getName().compareTo(other.targetClass.getName());
      }

      return result;
   }
}

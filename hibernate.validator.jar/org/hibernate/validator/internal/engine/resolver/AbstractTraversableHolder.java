package org.hibernate.validator.internal.engine.resolver;

import javax.validation.Path;

abstract class AbstractTraversableHolder {
   private final Object traversableObject;
   private final Path.Node traversableProperty;
   private final int hashCode;

   protected AbstractTraversableHolder(Object traversableObject, Path.Node traversableProperty) {
      this.traversableObject = traversableObject;
      this.traversableProperty = traversableProperty;
      this.hashCode = this.buildHashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && o instanceof AbstractTraversableHolder) {
         AbstractTraversableHolder that = (AbstractTraversableHolder)o;
         if (this.traversableObject != null) {
            if (this.traversableObject == that.traversableObject) {
               return this.traversableProperty.equals(that.traversableProperty);
            }
         } else if (that.traversableObject == null) {
            return this.traversableProperty.equals(that.traversableProperty);
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int buildHashCode() {
      int result = this.traversableObject != null ? System.identityHashCode(this.traversableObject) : 0;
      result = 31 * result + this.traversableProperty.hashCode();
      return result;
   }
}

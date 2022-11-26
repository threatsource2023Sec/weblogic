package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeHelper;

class BeanConstraintLocation implements ConstraintLocation {
   private final Class declaringClass;
   private final Type typeForValidatorResolution;

   BeanConstraintLocation(Class declaringClass) {
      this.declaringClass = declaringClass;
      this.typeForValidatorResolution = (Type)(declaringClass.getTypeParameters().length == 0 ? declaringClass : TypeHelper.parameterizedType(declaringClass, declaringClass.getTypeParameters()));
   }

   public Class getDeclaringClass() {
      return this.declaringClass;
   }

   public Member getMember() {
      return null;
   }

   public Type getTypeForValidatorResolution() {
      return this.typeForValidatorResolution;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      path.addBeanNode();
   }

   public Object getValue(Object parent) {
      return parent;
   }

   public String toString() {
      return "BeanConstraintLocation [declaringClass=" + this.declaringClass + ", typeForValidatorResolution=" + this.typeForValidatorResolution + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.declaringClass == null ? 0 : this.declaringClass.hashCode());
      result = 31 * result + (this.typeForValidatorResolution == null ? 0 : this.typeForValidatorResolution.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         BeanConstraintLocation other = (BeanConstraintLocation)obj;
         if (this.declaringClass == null) {
            if (other.declaringClass != null) {
               return false;
            }
         } else if (!this.declaringClass.equals(other.declaringClass)) {
            return false;
         }

         if (this.typeForValidatorResolution == null) {
            if (other.typeForValidatorResolution != null) {
               return false;
            }
         } else if (!this.typeForValidatorResolution.equals(other.typeForValidatorResolution)) {
            return false;
         }

         return true;
      }
   }
}

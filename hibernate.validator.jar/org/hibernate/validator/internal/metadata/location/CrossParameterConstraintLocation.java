package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

class CrossParameterConstraintLocation implements ConstraintLocation {
   private final Executable executable;

   CrossParameterConstraintLocation(Executable executable) {
      this.executable = executable;
   }

   public Class getDeclaringClass() {
      return this.executable.getDeclaringClass();
   }

   public Member getMember() {
      return this.executable;
   }

   public Type getTypeForValidatorResolution() {
      return Object[].class;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      path.addCrossParameterNode();
   }

   public Object getValue(Object parent) {
      return parent;
   }

   public String toString() {
      return "CrossParameterConstraintLocation [executable=" + this.executable + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.executable == null ? 0 : this.executable.hashCode());
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
         CrossParameterConstraintLocation other = (CrossParameterConstraintLocation)obj;
         if (this.executable == null) {
            if (other.executable != null) {
               return false;
            }
         } else if (!this.executable.equals(other.executable)) {
            return false;
         }

         return true;
      }
   }
}

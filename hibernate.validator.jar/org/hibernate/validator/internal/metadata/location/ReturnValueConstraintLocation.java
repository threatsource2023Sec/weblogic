package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;

class ReturnValueConstraintLocation implements ConstraintLocation {
   private final Executable executable;
   private final Type typeForValidatorResolution;

   ReturnValueConstraintLocation(Executable executable) {
      this.executable = executable;
      this.typeForValidatorResolution = ReflectionHelper.boxedType(ReflectionHelper.typeOf(executable));
   }

   public Class getDeclaringClass() {
      return this.executable.getDeclaringClass();
   }

   public Member getMember() {
      return this.executable;
   }

   public Type getTypeForValidatorResolution() {
      return this.typeForValidatorResolution;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      path.addReturnValueNode();
   }

   public Object getValue(Object parent) {
      return parent;
   }

   public String toString() {
      return "ReturnValueConstraintLocation [executable=" + this.executable + "]";
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
         ReturnValueConstraintLocation other = (ReturnValueConstraintLocation)obj;
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

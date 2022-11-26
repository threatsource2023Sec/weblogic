package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;

public class ParameterConstraintLocation implements ConstraintLocation {
   private final Executable executable;
   private final int index;
   private final Type typeForValidatorResolution;

   ParameterConstraintLocation(Executable executable, int index) {
      this.executable = executable;
      this.index = index;
      this.typeForValidatorResolution = ReflectionHelper.boxedType(ReflectionHelper.typeOf(executable, index));
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

   public int getIndex() {
      return this.index;
   }

   public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
      String name = (String)parameterNameProvider.getParameterNames(this.executable).get(this.index);
      path.addParameterNode(name, this.index);
   }

   public Object getValue(Object parent) {
      return ((Object[])((Object[])parent))[this.index];
   }

   public String toString() {
      return "ParameterConstraintLocation [executable=" + this.executable + ", index=" + this.index + ", typeForValidatorResolution=" + this.typeForValidatorResolution + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.executable == null ? 0 : this.executable.hashCode());
      result = 31 * result + this.index;
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
         ParameterConstraintLocation other = (ParameterConstraintLocation)obj;
         if (this.executable == null) {
            if (other.executable != null) {
               return false;
            }
         } else if (!this.executable.equals(other.executable)) {
            return false;
         }

         return this.index == other.index;
      }
   }
}

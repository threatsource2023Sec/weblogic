package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.Arrays;
import java.util.Objects;

public class AptBinaryLocalVariableBinding extends LocalVariableBinding {
   AnnotationBinding[] annotationBindings;
   public MethodBinding methodBinding;

   public AptBinaryLocalVariableBinding(char[] name, TypeBinding type, int modifiers, AnnotationBinding[] annotationBindings, MethodBinding methodBinding) {
      super(name, type, modifiers, true);
      this.annotationBindings = annotationBindings == null ? Binding.NO_ANNOTATIONS : annotationBindings;
      this.methodBinding = methodBinding;
   }

   public AnnotationBinding[] getAnnotations() {
      return this.annotationBindings;
   }

   public int hashCode() {
      int result = 17;
      int c = CharOperation.hashCode(this.name);
      result = 31 * result + c;
      c = this.type.hashCode();
      result = 31 * result + c;
      c = this.modifiers;
      result = 31 * result + c;
      c = Arrays.hashCode(this.annotationBindings);
      result = 31 * result + c;
      c = this.methodBinding.hashCode();
      result = 31 * result + c;
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
         AptBinaryLocalVariableBinding other = (AptBinaryLocalVariableBinding)obj;
         return CharOperation.equals(this.name, other.name) && Objects.equals(this.type, other.type) && this.modifiers == other.modifiers && Arrays.equals(this.annotationBindings, other.annotationBindings) && Objects.equals(this.methodBinding, other.methodBinding);
      }
   }
}

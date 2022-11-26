package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.ArrayList;
import java.util.List;

public class MissingTypeBinding extends BinaryTypeBinding {
   public MissingTypeBinding(PackageBinding packageBinding, char[][] compoundName, LookupEnvironment environment) {
      this.compoundName = compoundName;
      this.computeId();
      this.tagBits |= 131264L;
      this.environment = environment;
      this.fPackage = packageBinding;
      this.fileName = CharOperation.concatWith(compoundName, '/');
      this.sourceName = compoundName[compoundName.length - 1];
      this.modifiers = 1;
      this.superclass = null;
      this.superInterfaces = Binding.NO_SUPERINTERFACES;
      this.typeVariables = Binding.NO_TYPE_VARIABLES;
      this.memberTypes = Binding.NO_MEMBER_TYPES;
      this.fields = Binding.NO_FIELDS;
      this.methods = Binding.NO_METHODS;
   }

   public TypeBinding clone(TypeBinding outerType) {
      return this;
   }

   public List collectMissingTypes(List missingTypes) {
      if (missingTypes == null) {
         missingTypes = new ArrayList(5);
      } else if (((List)missingTypes).contains(this)) {
         return (List)missingTypes;
      }

      ((List)missingTypes).add(this);
      return (List)missingTypes;
   }

   public int problemId() {
      return 1;
   }

   void setMissingSuperclass(ReferenceBinding missingSuperclass) {
      this.superclass = missingSuperclass;
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
   }

   public String toString() {
      return "[MISSING:" + new String(CharOperation.concatWith(this.compoundName, '.')) + "]";
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class AptSourceLocalVariableBinding extends LocalVariableBinding {
   public MethodBinding methodBinding;
   private LocalVariableBinding local;

   public AptSourceLocalVariableBinding(LocalVariableBinding localVariableBinding, MethodBinding methodBinding) {
      super(localVariableBinding.name, localVariableBinding.type, localVariableBinding.modifiers, true);
      this.constant = localVariableBinding.constant;
      this.declaration = localVariableBinding.declaration;
      this.declaringScope = localVariableBinding.declaringScope;
      this.id = localVariableBinding.id;
      this.resolvedPosition = localVariableBinding.resolvedPosition;
      this.tagBits = localVariableBinding.tagBits;
      this.useFlag = localVariableBinding.useFlag;
      this.initializationCount = localVariableBinding.initializationCount;
      this.initializationPCs = localVariableBinding.initializationPCs;
      this.methodBinding = methodBinding;
      this.local = localVariableBinding;
   }

   public AnnotationBinding[] getAnnotations() {
      return this.local.getAnnotations();
   }
}

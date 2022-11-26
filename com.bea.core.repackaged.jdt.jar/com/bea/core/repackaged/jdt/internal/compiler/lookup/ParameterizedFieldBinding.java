package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;

public class ParameterizedFieldBinding extends FieldBinding {
   public FieldBinding originalField;

   public ParameterizedFieldBinding(ParameterizedTypeBinding parameterizedDeclaringClass, FieldBinding originalField) {
      super(originalField.name, (TypeBinding)((originalField.modifiers & 16384) != 0 ? parameterizedDeclaringClass : ((originalField.modifiers & 8) != 0 ? originalField.type : Scope.substitute(parameterizedDeclaringClass, (TypeBinding)originalField.type))), originalField.modifiers, parameterizedDeclaringClass, (Constant)null);
      this.originalField = originalField;
      this.tagBits = originalField.tagBits;
      this.id = originalField.id;
   }

   public Constant constant() {
      return this.originalField.constant();
   }

   public FieldBinding original() {
      return this.originalField.original();
   }

   public void setConstant(Constant constant) {
      this.originalField.setConstant(constant);
   }
}

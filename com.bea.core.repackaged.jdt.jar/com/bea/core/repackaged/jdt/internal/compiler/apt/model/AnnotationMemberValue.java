package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;

public class AnnotationMemberValue extends AnnotationValueImpl {
   private final MethodBinding _methodBinding;

   public AnnotationMemberValue(BaseProcessingEnvImpl env, Object value, MethodBinding methodBinding) {
      super(env, value, methodBinding.returnType);
      this._methodBinding = methodBinding;
   }

   public MethodBinding getMethodBinding() {
      return this._methodBinding;
   }
}

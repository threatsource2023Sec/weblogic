package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;

public abstract class NameReference extends Reference implements InvocationSite {
   public Binding binding;
   public TypeBinding actualReceiverType;

   public NameReference() {
      this.bits |= 7;
   }

   public FieldBinding fieldBinding() {
      return (FieldBinding)this.binding;
   }

   public FieldBinding lastFieldBinding() {
      return (this.bits & 7) == 1 ? this.fieldBinding() : null;
   }

   public InferenceContext18 freshInferenceContext(Scope scope) {
      return null;
   }

   public boolean isSuperAccess() {
      return false;
   }

   public boolean isTypeAccess() {
      return this.binding == null || this.binding instanceof ReferenceBinding;
   }

   public boolean isTypeReference() {
      return this.binding instanceof ReferenceBinding;
   }

   public void setActualReceiverType(ReferenceBinding receiverType) {
      if (receiverType != null) {
         this.actualReceiverType = receiverType;
      }
   }

   public void setDepth(int depth) {
      this.bits &= -8161;
      if (depth > 0) {
         this.bits |= (depth & 255) << 5;
      }

   }

   public void setFieldIndex(int index) {
   }

   public abstract String unboundReferenceErrorName();

   public abstract char[][] getName();

   public void checkEffectiveFinality(VariableBinding localBinding, Scope scope) {
      if ((this.bits & 524288) != 0 && !localBinding.isFinal() && !localBinding.isEffectivelyFinal()) {
         scope.problemReporter().cannotReferToNonEffectivelyFinalOuterLocal(localBinding, this);
         throw new AbortMethod(scope.referenceCompilationUnit().compilationResult, (CategorizedProblem)null);
      }
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExpressionContext;

public interface InvocationSite {
   TypeBinding[] genericTypeArguments();

   boolean isSuperAccess();

   boolean isQualifiedSuper();

   boolean isTypeAccess();

   void setActualReceiverType(ReferenceBinding var1);

   void setDepth(int var1);

   void setFieldIndex(int var1);

   int sourceEnd();

   int sourceStart();

   default int nameSourceStart() {
      return this.sourceStart();
   }

   default int nameSourceEnd() {
      return this.sourceEnd();
   }

   TypeBinding invocationTargetType();

   boolean receiverIsImplicitThis();

   boolean checkingPotentialCompatibility();

   void acceptPotentiallyCompatibleMethods(MethodBinding[] var1);

   InferenceContext18 freshInferenceContext(Scope var1);

   ExpressionContext getExpressionContext();

   public static class EmptyWithAstNode implements InvocationSite {
      ASTNode node;

      public EmptyWithAstNode(ASTNode node) {
         this.node = node;
      }

      public TypeBinding[] genericTypeArguments() {
         return null;
      }

      public boolean isSuperAccess() {
         return false;
      }

      public boolean isTypeAccess() {
         return false;
      }

      public void setActualReceiverType(ReferenceBinding receiverType) {
      }

      public void setDepth(int depth) {
      }

      public void setFieldIndex(int depth) {
      }

      public int sourceEnd() {
         return this.node.sourceEnd;
      }

      public int sourceStart() {
         return this.node.sourceStart;
      }

      public TypeBinding invocationTargetType() {
         return null;
      }

      public boolean receiverIsImplicitThis() {
         return false;
      }

      public InferenceContext18 freshInferenceContext(Scope scope) {
         return null;
      }

      public ExpressionContext getExpressionContext() {
         return ExpressionContext.VANILLA_CONTEXT;
      }

      public boolean isQualifiedSuper() {
         return false;
      }

      public boolean checkingPotentialCompatibility() {
         return false;
      }

      public void acceptPotentiallyCompatibleMethods(MethodBinding[] methods) {
      }
   }
}

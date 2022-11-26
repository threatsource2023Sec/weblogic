package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class SuperReference extends ThisReference {
   public SuperReference(int sourceStart, int sourceEnd) {
      super(sourceStart, sourceEnd);
   }

   public static ExplicitConstructorCall implicitSuperConstructorCall() {
      return new ExplicitConstructorCall(1);
   }

   public boolean isImplicitThis() {
      return false;
   }

   public boolean isSuper() {
      return true;
   }

   public boolean isUnqualifiedSuper() {
      return true;
   }

   public boolean isThis() {
      return false;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return output.append("super");
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      ReferenceBinding enclosingReceiverType = scope.enclosingReceiverType();
      if (!this.checkAccess(scope, enclosingReceiverType)) {
         return null;
      } else if (enclosingReceiverType.id == 1) {
         scope.problemReporter().cannotUseSuperInJavaLangObject(this);
         return null;
      } else {
         return this.resolvedType = enclosingReceiverType.superclass();
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      visitor.visit(this, blockScope);
      visitor.endVisit(this, blockScope);
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocReturnStatement extends ReturnStatement {
   public JavadocReturnStatement(int s, int e) {
      super((Expression)null, s, e);
      this.bits |= 294912;
   }

   public void resolve(BlockScope scope) {
      MethodScope methodScope = scope.methodScope();
      MethodBinding methodBinding = null;
      TypeBinding methodType = methodScope.referenceContext instanceof AbstractMethodDeclaration ? ((methodBinding = ((AbstractMethodDeclaration)methodScope.referenceContext).binding) == null ? null : methodBinding.returnType) : TypeBinding.VOID;
      if (methodType != null && methodType != TypeBinding.VOID) {
         if ((this.bits & 262144) != 0) {
            scope.problemReporter().javadocEmptyReturnTag(this.sourceStart, this.sourceEnd, scope.getDeclarationModifiers());
         }
      } else {
         scope.problemReporter().javadocUnexpectedTag(this.sourceStart, this.sourceEnd);
      }

   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output).append("return");
      if ((this.bits & 262144) == 0) {
         output.append(' ').append(" <not empty>");
      }

      return output;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}

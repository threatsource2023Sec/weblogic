package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;

public class PrefixExpression extends CompoundAssignment {
   public PrefixExpression(Expression lhs, Expression expression, int operator, int pos) {
      super(lhs, expression, operator, lhs.sourceEnd);
      this.sourceStart = pos;
      this.sourceEnd = lhs.sourceEnd;
   }

   public boolean checkCastCompatibility() {
      return false;
   }

   public String operatorToString() {
      switch (this.operator) {
         case 13:
            return "--";
         case 14:
            return "++";
         default:
            return "unknown operator";
      }
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      output.append(this.operatorToString()).append(' ');
      return this.lhs.printExpression(0, output);
   }

   public boolean restrainUsageToNumericTypes() {
      return true;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.lhs.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}

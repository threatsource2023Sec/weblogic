package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.kernel.exps.AbstractExpressionVisitor;
import org.apache.openjpa.kernel.exps.Expression;

class HasContainsExpressionVisitor extends AbstractExpressionVisitor {
   private boolean _found = false;

   public static boolean hasContains(Expression exp) {
      if (exp == null) {
         return false;
      } else {
         HasContainsExpressionVisitor v = new HasContainsExpressionVisitor();
         exp.acceptVisit(v);
         return v._found;
      }
   }

   public void enter(Expression exp) {
      if (!this._found) {
         this._found = exp instanceof ContainsExpression || exp instanceof BindVariableAndExpression;
      }

   }
}

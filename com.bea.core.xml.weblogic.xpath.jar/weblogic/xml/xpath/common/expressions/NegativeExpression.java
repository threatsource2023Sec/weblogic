package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public final class NegativeExpression extends NumberExpression {
   private Expression mExpr;

   public NegativeExpression(Expression expr) {
      if (expr == null) {
         throw new IllegalArgumentException("null expr");
      } else {
         this.mExpr = expr;
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mExpr);
      this.mExpr.getSubExpressions(out);
   }

   public final double evaluateAsNumber(Context ctx) {
      return -this.mExpr.evaluateAsNumber(ctx);
   }
}

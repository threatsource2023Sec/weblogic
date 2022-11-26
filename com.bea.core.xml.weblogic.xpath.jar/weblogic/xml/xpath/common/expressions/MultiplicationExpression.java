package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public final class MultiplicationExpression extends NumberExpression {
   private Expression mLeft;
   private Expression mRight;

   public MultiplicationExpression(Expression left, Expression right) {
      if (left == null) {
         throw new IllegalArgumentException("left is null");
      } else if (right == null) {
         throw new IllegalArgumentException("right is null");
      } else {
         this.mLeft = left;
         this.mRight = right;
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mLeft);
      out.add(this.mRight);
      this.mLeft.getSubExpressions(out);
      this.mRight.getSubExpressions(out);
   }

   public final double evaluateAsNumber(Context ctx) {
      return this.mLeft.evaluateAsNumber(ctx) * this.mRight.evaluateAsNumber(ctx);
   }
}

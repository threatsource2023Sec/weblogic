package weblogic.xml.xpath.common.expressions;

import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public final class GreaterThanOrEqualExpression extends BinaryExpression {
   private final int mEvaluationType = super.getEvaluationType();

   public GreaterThanOrEqualExpression(Expression left, Expression right) {
      super(left, right);
   }

   public final boolean evaluateAsBoolean(Context ctx) {
      return this.mLeft.evaluateAsNumber(ctx) >= this.mRight.evaluateAsNumber(ctx);
   }
}

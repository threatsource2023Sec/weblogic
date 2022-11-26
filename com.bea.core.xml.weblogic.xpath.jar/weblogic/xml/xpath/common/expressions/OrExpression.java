package weblogic.xml.xpath.common.expressions;

import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public final class OrExpression extends BinaryExpression {
   public OrExpression(Expression left, Expression right) {
      super(left, right);
   }

   public boolean evaluateAsBoolean(Context ctx) {
      return this.mLeft.evaluateAsBoolean(ctx) || this.mRight.evaluateAsBoolean(ctx);
   }
}

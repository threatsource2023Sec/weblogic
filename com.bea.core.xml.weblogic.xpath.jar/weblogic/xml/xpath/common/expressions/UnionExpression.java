package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;

public final class UnionExpression extends NodesetExpression {
   private Expression mLeft;
   private Expression mRight;

   public UnionExpression(Expression left, Expression right, Interrogator i) {
      super(i);
      if (left == null) {
         throw new IllegalArgumentException("null left");
      } else if (right == null) {
         throw new IllegalArgumentException("null right");
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

   public List evaluateAsNodeset(Context ctx) {
      List out = this.mLeft.evaluateAsNodeset(ctx);
      out.addAll(this.mRight.evaluateAsNodeset(ctx));
      return out;
   }

   public String toString() {
      return this.mLeft + " | " + this.mRight;
   }
}

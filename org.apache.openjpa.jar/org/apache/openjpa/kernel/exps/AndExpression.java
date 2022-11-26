package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

class AndExpression extends Exp {
   private final Exp _exp1;
   private final Exp _exp2;

   public AndExpression(Exp exp1, Exp exp2) {
      this._exp1 = exp1;
      this._exp2 = exp2;
   }

   public Exp getExpression1() {
      return this._exp1;
   }

   public Exp getExpression2() {
      return this._exp2;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._exp1.evaluate(candidate, orig, ctx, params) && this._exp2.evaluate(candidate, orig, ctx, params);
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      return this._exp1.evaluate(candidates, ctx, params) && this._exp2.evaluate(candidates, ctx, params);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._exp1.acceptVisit(visitor);
      this._exp2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

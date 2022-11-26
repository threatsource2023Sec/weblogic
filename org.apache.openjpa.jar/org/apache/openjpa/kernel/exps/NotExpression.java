package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

class NotExpression extends Exp {
   private final Exp _exp;

   public NotExpression(Exp exp) {
      this._exp = exp;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return !this._exp.evaluate(candidate, orig, ctx, params);
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      return !this._exp.evaluate(candidates, ctx, params);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._exp.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

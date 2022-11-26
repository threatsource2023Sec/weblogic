package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

class ValExpression extends Exp {
   private final Val _val;

   public ValExpression(Val val) {
      this._val = val;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object o = this._val.eval(candidate, orig, ctx, params);
      return o != null && (Boolean)o;
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      Collection c = this._val.eval((Collection)candidates, (Object)null, ctx, params);
      Object o = c != null && !c.isEmpty() ? c.iterator().next() : null;
      return o != null && (Boolean)o;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

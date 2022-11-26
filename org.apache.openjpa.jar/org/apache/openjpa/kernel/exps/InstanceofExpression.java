package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.StoreContext;

class InstanceofExpression extends Exp {
   private final Val _val;
   private final Class _cls;

   public InstanceofExpression(Val val, Class cls) {
      this._val = val;
      this._cls = Filters.wrap(cls);
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._cls.isInstance(this._val.eval(candidate, orig, ctx, params));
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      Collection c = this._val.eval((Collection)candidates, (Object)null, ctx, params);
      Object o = c != null && !c.isEmpty() ? c.iterator().next() : null;
      return this._cls.isInstance(o);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

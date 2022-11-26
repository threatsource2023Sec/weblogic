package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.StoreContext;

abstract class CompareExpression extends Exp {
   private final Val _val1;
   private final Val _val2;

   public CompareExpression(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object o1 = this._val1.eval(candidate, orig, ctx, params);
      Object o2 = this._val2.eval(candidate, orig, ctx, params);
      if (o1 != null && o2 != null) {
         Class c = Filters.promote(o1.getClass(), o2.getClass());
         o1 = Filters.convert(o1, c);
         o2 = Filters.convert(o2, c);
      }

      return this.compare(o1, o2);
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      Collection c1 = this._val1.eval((Collection)candidates, (Object)null, ctx, params);
      Collection c2 = this._val2.eval((Collection)candidates, (Object)null, ctx, params);
      Object o1 = c1 != null && !c1.isEmpty() ? c1.iterator().next() : null;
      Object o2 = c2 != null && !c2.isEmpty() ? c2.iterator().next() : null;
      if (o1 != null && o2 != null) {
         Class c = Filters.promote(o1.getClass(), o2.getClass());
         o1 = Filters.convert(o1, c);
         o2 = Filters.convert(o2, c);
      }

      return this.compare(o1, o2);
   }

   protected abstract boolean compare(Object var1, Object var2);

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

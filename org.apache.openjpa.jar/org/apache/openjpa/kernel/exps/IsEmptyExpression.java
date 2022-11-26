package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.kernel.StoreContext;

class IsEmptyExpression extends Exp {
   private final Val _val;

   public IsEmptyExpression(Val val) {
      this._val = val;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object obj = this._val.eval(candidate, orig, ctx, params);
      if (obj == null) {
         return true;
      } else if (obj instanceof Collection) {
         return ((Collection)obj).isEmpty();
      } else {
         return obj instanceof Map ? ((Map)obj).isEmpty() : false;
      }
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      Collection c = this._val.eval((Collection)candidates, (Object)null, ctx, params);
      if (c != null && !c.isEmpty()) {
         Object obj = c.iterator().next();
         if (obj == null) {
            return true;
         } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
         } else {
            return obj instanceof Map ? ((Map)obj).isEmpty() : false;
         }
      } else {
         return false;
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

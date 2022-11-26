package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Collections;
import org.apache.openjpa.kernel.StoreContext;

class ContainsExpression extends Exp {
   private final Val _val1;
   private final Val _val2;

   public ContainsExpression(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object obj = this._val1.eval(candidate, orig, ctx, params);
      Collection coll = this.getCollection(obj);
      return coll != null && !coll.isEmpty() && coll.contains(this._val2.eval(candidate, orig, ctx, params));
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      Collection coll = this._val1.eval((Collection)candidates, (Object)null, ctx, params);
      if (coll != null && !coll.isEmpty()) {
         coll = this.getCollection(coll.iterator().next());
         if (coll != null && !coll.isEmpty()) {
            Collection coll2 = this._val2.eval((Collection)candidates, (Object)null, ctx, params);
            return coll2 != null && !coll2.isEmpty() ? coll.contains(coll2.iterator().next()) : false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected Collection getCollection(Object obj) {
      return (Collection)(obj instanceof Collection ? (Collection)obj : Collections.singleton(obj));
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

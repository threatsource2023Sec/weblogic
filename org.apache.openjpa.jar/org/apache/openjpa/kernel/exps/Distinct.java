package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.apache.openjpa.kernel.StoreContext;

class Distinct extends Val {
   private final Val _val;

   public Distinct(Val val) {
      this._val = val;
   }

   public Class getType() {
      return Collection.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      if (candidate == null) {
         candidate = Collections.EMPTY_LIST;
      }

      Collection arg = candidate instanceof Collection ? (Collection)candidate : Collections.singleton(candidate);
      return this.eval((Collection)arg, orig, ctx, params).iterator().next();
   }

   protected Collection eval(Collection candidates, Object orig, StoreContext ctx, Object[] params) {
      Collection args = this._val.eval(candidates, orig, ctx, params);
      return new HashSet(args);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

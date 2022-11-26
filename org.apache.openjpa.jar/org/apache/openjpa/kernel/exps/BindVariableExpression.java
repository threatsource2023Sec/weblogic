package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

class BindVariableExpression extends Exp {
   private final BoundVariable _var;
   private final Val _val;

   public BindVariableExpression(BoundVariable var, Val val) {
      this._var = var;
      this._val = val;
   }

   public BoundVariable getVariable() {
      return this._var;
   }

   public Collection getVariableValues(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object values = this._val.eval(candidate, orig, ctx, params);
      return this.getCollection(values);
   }

   protected Collection getCollection(Object values) {
      return (Collection)values;
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Collection vals = this.getVariableValues(candidate, orig, ctx, params);
      return vals != null && !vals.isEmpty();
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      if (candidates != null && !candidates.isEmpty()) {
         Object obj = candidates.iterator().next();
         return this.eval(obj, obj, ctx, params);
      } else {
         return false;
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._var.acceptVisit(visitor);
      this._val.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

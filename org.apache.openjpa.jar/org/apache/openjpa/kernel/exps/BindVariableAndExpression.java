package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.StoreContext;

class BindVariableAndExpression extends AndExpression {
   public BindVariableAndExpression(BindVariableExpression var, Exp exp) {
      super(var, exp);
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      BindVariableExpression bind = (BindVariableExpression)this.getExpression1();
      Collection vals = bind.getVariableValues(candidate, orig, ctx, params);
      if (vals != null && !vals.isEmpty()) {
         BoundVariable var = bind.getVariable();
         Iterator itr = vals.iterator();

         do {
            if (!itr.hasNext()) {
               return false;
            }
         } while(!var.setValue(itr.next()) || !this.getExpression2().evaluate(candidate, orig, ctx, params));

         return true;
      } else {
         return false;
      }
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      if (candidates != null && !candidates.isEmpty()) {
         Object obj = candidates.iterator().next();
         return this.eval(obj, obj, ctx, params);
      } else {
         return false;
      }
   }
}

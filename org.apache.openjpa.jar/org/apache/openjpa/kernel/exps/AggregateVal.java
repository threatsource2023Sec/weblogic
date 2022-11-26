package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Collections;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

abstract class AggregateVal extends Val {
   private static final Localizer _loc = Localizer.forPackage(AggregateVal.class);
   private final Val _val;

   public AggregateVal(Val val) {
      this._val = val;
   }

   public boolean isAggregate() {
      return true;
   }

   public Class getType() {
      return this.getType(this._val.getType());
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      if (candidate == null) {
         candidate = Collections.EMPTY_LIST;
      }

      if (candidate instanceof Collection) {
         return this.eval((Collection)candidate, orig, ctx, params).iterator().next();
      } else {
         throw new UserException(_loc.get("agg-in-filter"));
      }
   }

   protected Collection eval(Collection candidates, Object orig, StoreContext ctx, Object[] params) {
      Collection args = this._val.eval(candidates, orig, ctx, params);
      return Collections.singleton(this.operate(args, this._val.getType()));
   }

   protected abstract Class getType(Class var1);

   protected abstract Object operate(Collection var1, Class var2);

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

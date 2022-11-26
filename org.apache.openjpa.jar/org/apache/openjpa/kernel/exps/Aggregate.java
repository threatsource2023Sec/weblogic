package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Collections;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

class Aggregate extends Val {
   private static final Localizer _loc = Localizer.forPackage(Aggregate.class);
   private final AggregateListener _listener;
   private final Val _arg;

   public Aggregate(AggregateListener listener, Val arg) {
      this._listener = listener;
      this._arg = arg;
   }

   public boolean isAggregate() {
      return true;
   }

   public Class getType() {
      return this._listener.getType(this.getArgTypes());
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
      Collection args = null;
      if (this._arg != null) {
         args = this._arg.eval(candidates, orig, ctx, params);
      }

      Object agg = this._listener.evaluate(args, this.getArgTypes(), candidates, ctx);
      return Collections.singleton(agg);
   }

   private Class[] getArgTypes() {
      if (this._arg == null) {
         return null;
      } else {
         return this._arg instanceof Args ? ((Args)this._arg).getTypes() : new Class[]{this._arg.getType()};
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      if (this._arg != null) {
         this._arg.acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}

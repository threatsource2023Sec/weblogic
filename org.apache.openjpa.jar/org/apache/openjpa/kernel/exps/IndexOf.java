package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;
import serp.util.Numbers;

class IndexOf extends Val {
   private final Val _val;
   private final Val _args;

   public IndexOf(Val val, Val args) {
      this._val = val;
      this._args = args;
   }

   public Class getType() {
      return Integer.TYPE;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object str = this._val.eval(candidate, orig, ctx, params);
      Object arg = this._args.eval(candidate, orig, ctx, params);
      int idx;
      if (arg instanceof Object[]) {
         Object[] args = (Object[])((Object[])arg);
         idx = str.toString().indexOf(args[0].toString(), ((Number)args[1]).intValue());
      } else {
         idx = str.toString().indexOf(arg.toString());
      }

      return Numbers.valueOf(idx);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      this._args.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Substring extends Val {
   private final Val _val;
   private final Val _args;

   public Substring(Val val, Val args) {
      this._val = val;
      this._args = args;
   }

   public Class getType() {
      return String.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object str = this._val.eval(candidate, orig, ctx, params);
      Object arg = this._args.eval(candidate, orig, ctx, params);
      if (arg instanceof Object[]) {
         Object[] args = (Object[])((Object[])arg);
         int start = ((Number)args[0]).intValue();
         int end = ((Number)args[1]).intValue();
         String string = str == null ? "" : str.toString();
         return string.substring(start, Math.min(end, string.length()));
      } else {
         return str.toString().substring(((Number)arg).intValue());
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      this._args.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

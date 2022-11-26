package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Concat extends Val {
   private final Val _val;
   private final Val _args;

   public Concat(Val val, Val args) {
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
      StringBuffer cat = new StringBuffer(str.toString());
      Object arg = this._args.eval(candidate, orig, ctx, params);
      if (arg instanceof Object[]) {
         for(int i = 0; i < ((Object[])((Object[])arg)).length; ++i) {
            cat.append(((Object[])((Object[])arg))[i].toString());
         }
      } else {
         cat.append(arg.toString());
      }

      return cat.toString();
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      this._args.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

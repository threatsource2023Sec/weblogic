package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class ToUpperCase extends Val {
   private final Val _val;

   public ToUpperCase(Val val) {
      this._val = val;
   }

   public Class getType() {
      return String.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._val.eval(candidate, orig, ctx, params).toString().toUpperCase();
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

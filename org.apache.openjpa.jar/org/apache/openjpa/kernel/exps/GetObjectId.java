package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class GetObjectId extends Val {
   private final Val _val;

   public GetObjectId(Val val) {
      this._val = val;
   }

   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return ctx.getObjectId(this._val.eval(candidate, orig, ctx, params));
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

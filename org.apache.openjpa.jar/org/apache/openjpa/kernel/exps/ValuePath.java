package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class ValuePath extends CandidatePath {
   private final Val _val;

   public ValuePath(Val val) {
      this._val = val;
   }

   public Class getCandidateType() {
      return this._val.getType();
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return super.eval(this._val.eval(candidate, orig, ctx, params), orig, ctx, params);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

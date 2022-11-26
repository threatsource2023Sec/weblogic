package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

abstract class UnaryMathVal extends Val {
   private final Val _val;

   public UnaryMathVal(Val val) {
      this._val = val;
   }

   public Class getType() {
      return this.getType(this._val.getType());
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object o1 = this._val.eval(candidate, orig, ctx, params);
      return this.operate(o1, this._val.getType());
   }

   protected abstract Class getType(Class var1);

   protected abstract Object operate(Object var1, Class var2);

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

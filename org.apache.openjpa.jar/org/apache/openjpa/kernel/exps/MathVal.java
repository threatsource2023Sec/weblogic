package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.StoreContext;

abstract class MathVal extends Val {
   private final Val _val1;
   private final Val _val2;

   public MathVal(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
   }

   public Class getType() {
      Class c1 = this._val1.getType();
      Class c2 = this._val2.getType();
      return Filters.promote(c1, c2);
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object o1 = this._val1.eval(candidate, orig, ctx, params);
      Object o2 = this._val2.eval(candidate, orig, ctx, params);
      return this.operate(o1, this._val1.getType(), o2, this._val2.getType());
   }

   protected abstract Object operate(Object var1, Class var2, Object var3, Class var4);

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

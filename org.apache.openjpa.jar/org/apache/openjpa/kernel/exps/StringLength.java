package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;
import serp.util.Numbers;

class StringLength extends Val {
   private final Val _val;
   private Class _cast = null;

   public StringLength(Val val) {
      this._val = val;
   }

   public Class getType() {
      return this._cast != null ? this._cast : Integer.TYPE;
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object eval = this._val.eval(candidate, orig, ctx, params);
      return eval == null ? Numbers.valueOf(0) : Numbers.valueOf(eval.toString().length());
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

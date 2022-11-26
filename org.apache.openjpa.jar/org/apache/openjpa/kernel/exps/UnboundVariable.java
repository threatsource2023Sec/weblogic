package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class UnboundVariable extends Val {
   private Class _type = null;
   private Object _val = null;

   public UnboundVariable(Class type) {
      this._type = type;
   }

   public boolean isVariable() {
      return true;
   }

   public Class getType() {
      return this._type;
   }

   public void setImplicitType(Class type) {
      this._type = type;
   }

   public void setValue(Object value) {
      this._val = value;
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._val;
   }
}

package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class BoundVariable extends Val {
   private Class _type = null;
   private Object _val = null;

   public BoundVariable(Class type) {
      this._type = type;
   }

   public boolean setValue(Object value) {
      if (value != null && !this._type.isAssignableFrom(value.getClass())) {
         return false;
      } else {
         this._val = value;
         return true;
      }
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

   public void castTo(Class type) {
      if (!this._type.isAssignableFrom(type) && !type.isAssignableFrom(this._type)) {
         throw new ClassCastException(this._type.getName());
      } else {
         this._type = type;
      }
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._val;
   }
}

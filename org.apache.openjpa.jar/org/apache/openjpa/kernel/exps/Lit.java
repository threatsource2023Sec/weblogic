package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.StoreContext;

class Lit extends Val implements Literal {
   private Object _val;
   private final int _ptype;

   public Lit(Object val, int ptype) {
      this._val = val;
      this._ptype = ptype;
   }

   public Object getValue() {
      return this._val;
   }

   public void setValue(Object val) {
      this._val = val;
   }

   public int getParseType() {
      return this._ptype;
   }

   public Object getValue(Object[] parameters) {
      return this._val;
   }

   public Class getType() {
      return this._val == null ? Object.class : this._val.getClass();
   }

   public void setImplicitType(Class type) {
      this._val = Filters.convert(this._val, type);
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this._val;
   }
}

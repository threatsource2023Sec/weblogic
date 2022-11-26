package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Param extends Val implements Parameter {
   private String _name = null;
   private Class _type = null;
   private int _index = -1;

   public Param(String name, Class type) {
      this._name = name;
      this._type = type;
   }

   public String getParameterName() {
      return this._name;
   }

   public Class getType() {
      return this._type;
   }

   public void setImplicitType(Class type) {
      this._type = type;
   }

   public void setIndex(int index) {
      this._index = index;
   }

   public Object getValue(Object[] params) {
      return params[this._index];
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return this.getValue(params);
   }
}

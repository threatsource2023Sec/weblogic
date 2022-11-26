package org.apache.openjpa.kernel.exps;

import java.util.Map;
import org.apache.openjpa.kernel.StoreContext;

class GetMapValue extends Val {
   private final Val _map;
   private final Val _arg;

   public GetMapValue(Val map, Val arg) {
      this._map = map;
      this._arg = arg;
   }

   public boolean isVariable() {
      return false;
   }

   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return ((Map)this._map.eval(candidate, orig, ctx, params)).get(this._arg.eval(candidate, orig, ctx, params));
   }
}

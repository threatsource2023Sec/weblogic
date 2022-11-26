package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Null extends Val {
   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return null;
   }
}

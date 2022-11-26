package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class This extends Val {
   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return orig;
   }

   public Class getType() {
      return this.getMetaData().getDescribedType();
   }
}

package org.apache.openjpa.kernel.exps;

import java.util.Date;
import org.apache.openjpa.kernel.StoreContext;

class CurrentDate extends Val {
   public Class getType() {
      return Date.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return new Date();
   }
}

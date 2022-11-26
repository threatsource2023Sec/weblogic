package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Collection;
import java.util.Map;

class InKeyExpression extends InExpression {
   public InKeyExpression(Val val, Const constant) {
      super(val, constant);
   }

   protected Collection getCollection(ExpContext ctx, ExpState state) {
      Map map = (Map)this.getConstant().getValue(ctx, state);
      return map == null ? null : map.keySet();
   }
}

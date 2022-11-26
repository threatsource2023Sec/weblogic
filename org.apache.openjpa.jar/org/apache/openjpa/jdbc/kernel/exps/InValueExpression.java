package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Collection;
import java.util.Map;

class InValueExpression extends InExpression {
   public InValueExpression(Val val, Const constant) {
      super(val, constant);
   }

   protected Collection getCollection(ExpContext ctx, ExpState state) {
      Map map = (Map)this.getConstant().getValue(ctx, state);
      return map == null ? null : map.values();
   }
}

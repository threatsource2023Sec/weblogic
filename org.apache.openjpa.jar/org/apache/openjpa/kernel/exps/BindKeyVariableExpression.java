package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Map;

class BindKeyVariableExpression extends BindVariableExpression {
   public BindKeyVariableExpression(BoundVariable var, Val val) {
      super(var, val);
   }

   protected Collection getCollection(Object values) {
      Map map = (Map)values;
      return map == null ? null : map.keySet();
   }
}

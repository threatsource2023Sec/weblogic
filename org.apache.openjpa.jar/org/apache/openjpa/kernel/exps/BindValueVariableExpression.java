package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Map;

class BindValueVariableExpression extends BindVariableExpression {
   public BindValueVariableExpression(BoundVariable var, Val val) {
      super(var, val);
   }

   protected Collection getCollection(Object values) {
      Map map = (Map)values;
      return map == null ? null : map.values();
   }
}

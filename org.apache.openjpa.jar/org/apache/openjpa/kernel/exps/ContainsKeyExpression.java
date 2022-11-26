package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Map;

class ContainsKeyExpression extends ContainsExpression {
   public ContainsKeyExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   protected Collection getCollection(Object obj) {
      return obj == null ? null : ((Map)obj).keySet();
   }
}

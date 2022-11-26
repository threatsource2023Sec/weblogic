package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.Select;

class ContainsKeyExpression extends ContainsExpression {
   public ContainsKeyExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      Val val1 = this.getValue1();
      if (val1 instanceof PCPath) {
         ((PCPath)val1).getKey();
      }

      return super.initialize(sel, ctx, contains);
   }
}

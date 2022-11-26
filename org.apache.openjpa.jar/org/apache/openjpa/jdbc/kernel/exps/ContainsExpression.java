package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.Select;
import serp.util.Numbers;

class ContainsExpression extends EqualExpression {
   public ContainsExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      Val val1 = this.getValue1();
      if (contains != null && val1 instanceof PCPath) {
         PCPath sql = (PCPath)val1;
         String path = sql.getPath();
         Integer count = (Integer)contains.get(path);
         if (count == null) {
            count = Numbers.valueOf(0);
         } else {
            count = Numbers.valueOf(count + 1);
         }

         contains.put(path, count);
         sql.setContainsId(count.toString());
      }

      return super.initialize(sel, ctx, contains);
   }

   protected boolean isDirectComparison() {
      return false;
   }
}

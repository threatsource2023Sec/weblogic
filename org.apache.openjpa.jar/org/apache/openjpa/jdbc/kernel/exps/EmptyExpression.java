package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class EmptyExpression implements Exp {
   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return ExpState.NULL;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      visitor.exit((Expression)this);
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;

abstract class AbstractVal implements Val {
   protected static final String TRUE = "1 = 1";
   protected static final String FALSE = "1 <> 1";

   public boolean isVariable() {
      return false;
   }

   public boolean isAggregate() {
      return false;
   }

   public boolean isXPath() {
      return false;
   }

   public Object toDataStoreValue(Select sel, ExpContext ctx, ExpState state, Object val) {
      return val;
   }

   public void appendIsEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      sql.append("1 <> 1");
   }

   public void appendIsNotEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      sql.append("1 = 1");
   }

   public void appendIsNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      this.appendTo(sel, ctx, state, sql, 0);
      sql.append(" IS ").appendValue((Object)null);
   }

   public void appendIsNotNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      this.appendTo(sel, ctx, state, sql, 0);
      sql.append(" IS NOT ").appendValue((Object)null);
   }

   public void appendSize(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      sql.append("1");
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      visitor.exit((Value)this);
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

class Null extends Const {
   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   public Object getValue(Object[] params) {
      return null;
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      sql.appendValue((Object)null);
   }
}

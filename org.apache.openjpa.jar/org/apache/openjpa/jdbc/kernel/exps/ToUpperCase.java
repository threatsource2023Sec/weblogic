package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

class ToUpperCase extends StringFunction {
   public ToUpperCase(Val val) {
      super(val);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf, int index) {
      DBDictionary dict = ctx.store.getDBDictionary();
      String func = dict.toUpperCaseFunction;
      dict.assertSupport(func != null, "ToUpperCaseFunction");
      func = dict.getCastFunction(this.getValue(), func);
      int idx = func.indexOf("{0}");
      buf.append(func.substring(0, idx));
      this.getValue().appendTo(sel, ctx, state, buf, index);
      buf.append(func.substring(idx + 3));
   }
}

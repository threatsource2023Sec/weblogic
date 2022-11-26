package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

class StringLength extends StringFunction {
   private Class _cast = null;

   public StringLength(Val val) {
      super(val);
   }

   public Class getType() {
      return this._cast != null ? this._cast : Integer.TYPE;
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf, int index) {
      DBDictionary dict = ctx.store.getDBDictionary();
      String func = dict.stringLengthFunction;
      dict.assertSupport(func != null, "StringLengthFunction");
      func = dict.getCastFunction(this.getValue(), func);
      int idx = func.indexOf("{0}");
      buf.append(func.substring(0, idx));
      this.getValue().appendTo(sel, ctx, state, buf, index);
      buf.append(func.substring(idx + 3));
   }
}

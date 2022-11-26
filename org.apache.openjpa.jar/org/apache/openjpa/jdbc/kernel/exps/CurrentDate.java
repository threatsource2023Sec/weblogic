package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Date;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.util.InternalException;

class CurrentDate extends Const {
   private final int _type;

   public CurrentDate(int type) {
      this._type = type;
   }

   public Class getType() {
      return Date.class;
   }

   public void setImplicitType(Class type) {
   }

   public Object getValue(Object[] params) {
      return new Date();
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      switch (this._type) {
         case 14:
            sql.append(ctx.store.getDBDictionary().currentDateFunction);
            break;
         case 1010:
            sql.append(ctx.store.getDBDictionary().currentTimeFunction);
            break;
         case 1011:
            sql.append(ctx.store.getDBDictionary().currentTimestampFunction);
            break;
         default:
            throw new InternalException();
      }

   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Constant;
import org.apache.openjpa.meta.ClassMetaData;

abstract class Const extends AbstractVal implements Constant {
   private ClassMetaData _meta = null;

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public Object getSQLValue(Select sel, ExpContext ctx, ExpState state) {
      return this.getValue(ctx, state);
   }

   public boolean isSQLValueNull(Select sel, ExpContext ctx, ExpState state) {
      Object val = this.getSQLValue(sel, ctx, state);
      if (val == null) {
         return true;
      } else if (!(val instanceof Object[])) {
         return false;
      } else {
         Object[] arr = (Object[])((Object[])val);

         for(int i = 0; i < arr.length; ++i) {
            if (arr[i] != null) {
               return false;
            }
         }

         return true;
      }
   }

   public Object getValue(ExpContext ctx, ExpState state) {
      return this.getValue(ctx.params);
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return new ConstExpState();
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      if (other instanceof PCPath) {
         ((ConstExpState)state).cols = ((PCPath)other).getColumns(otherState);
      }

   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   private SQLBuffer newSQLBuffer(Select sel, ExpContext ctx, ExpState state) {
      this.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      SQLBuffer buf = new SQLBuffer(ctx.store.getDBDictionary());
      this.appendTo(sel, ctx, state, buf, 0);
      return buf;
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      sel.groupBy(this.newSQLBuffer(sel, ctx, state));
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      sel.orderBy(this.newSQLBuffer(sel, ctx, state), asc, false);
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return this.getValue(ctx, state);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendIsEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      Object obj = this.getValue(ctx, state);
      if (obj instanceof Collection && ((Collection)obj).isEmpty()) {
         sql.append("1 = 1");
      } else if (obj instanceof Map && ((Map)obj).isEmpty()) {
         sql.append("1 = 1");
      } else {
         sql.append("1 <> 1");
      }

   }

   public void appendIsNotEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      Object obj = this.getValue(ctx, state);
      if (obj instanceof Collection && ((Collection)obj).isEmpty()) {
         sql.append("1 <> 1");
      } else if (obj instanceof Map && ((Map)obj).isEmpty()) {
         sql.append("1 <> 1");
      } else {
         sql.append("1 = 1");
      }

   }

   public void appendSize(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      Object obj = this.getValue(ctx, state);
      if (obj instanceof Collection) {
         sql.appendValue(((Collection)obj).size());
      } else if (obj instanceof Map) {
         sql.appendValue(((Map)obj).size());
      } else {
         sql.append("1");
      }

   }

   public void appendIsNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      if (this.isSQLValueNull(sel, ctx, state)) {
         sql.append("1 = 1");
      } else {
         sql.append("1 <> 1");
      }

   }

   public void appendIsNotNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      if (!this.isSQLValueNull(sel, ctx, state)) {
         sql.append("1 = 1");
      } else {
         sql.append("1 <> 1");
      }

   }

   protected static class ConstExpState extends ExpState {
      public Column[] cols = null;

      public Column getColumn(int index) {
         return this.cols != null && this.cols.length > index ? this.cols[index] : null;
      }
   }
}

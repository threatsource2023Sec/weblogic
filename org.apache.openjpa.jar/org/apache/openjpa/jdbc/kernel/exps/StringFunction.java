package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

abstract class StringFunction extends AbstractVal {
   private final Val _val;
   private ClassMetaData _meta = null;

   public StringFunction(Val val) {
      this._val = val;
   }

   public Val getValue() {
      return this._val;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public Class getType() {
      return String.class;
   }

   public void setImplicitType(Class type) {
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this._val.initialize(sel, ctx, flags);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._val.selectColumns(sel, ctx, state, true);
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      sel.groupBy(this.newSQLBuffer(sel, ctx, state));
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      sel.orderBy(this.newSQLBuffer(sel, ctx, state), asc, false);
   }

   private SQLBuffer newSQLBuffer(Select sel, ExpContext ctx, ExpState state) {
      this.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      SQLBuffer buf = new SQLBuffer(ctx.store.getDBDictionary());
      this.appendTo(sel, ctx, state, buf, 0);
      return buf;
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return Filters.convert(res.getObject(this, 1012, (Object)null), this.getType());
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      this._val.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Substring extends AbstractVal {
   private final Val _val1;
   private final Val _val2;
   private ClassMetaData _meta = null;

   public Substring(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
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
      ExpState s1 = this._val1.initialize(sel, ctx, 0);
      ExpState s2 = this._val2.initialize(sel, ctx, 0);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.selectColumns(sel, ctx, bstate.state1, true);
      this._val2.selectColumns(sel, ctx, bstate.state2, true);
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
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.calculateValue(sel, ctx, bstate.state1, (Val)null, (ExpState)null);
      this._val2.calculateValue(sel, ctx, bstate.state2, (Val)null, (ExpState)null);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      FilterValue str = new FilterValueImpl(sel, ctx, bstate.state1, this._val1);
      FilterValue end = null;
      Object start;
      if (this._val2 instanceof Args) {
         FilterValue[] filts = ((Args)this._val2).newFilterValues(sel, ctx, bstate.state2);
         start = filts[0];
         end = filts[1];
      } else {
         start = new FilterValueImpl(sel, ctx, bstate.state2, this._val2);
      }

      ctx.store.getDBDictionary().substring(sql, str, (FilterValue)start, end);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

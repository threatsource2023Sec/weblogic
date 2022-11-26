package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Concat extends AbstractVal {
   private final Val _val1;
   private final Val _val2;
   private ClassMetaData _meta = null;

   public Concat(Val val1, Val val2) {
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
      this._val1.calculateValue(sel, ctx, bstate.state1, (Val)null, (ExpState)null);
      this._val2.calculateValue(sel, ctx, bstate.state2, (Val)null, (ExpState)null);
      DBDictionary dict = ctx.store.getDBDictionary();
      String func = dict.concatenateFunction;
      dict.assertSupport(func != null, "ConcatenateFunction");
      int part1idx = func.indexOf("{0}");
      int part2idx = func.indexOf("{1}");
      String part1 = func.substring(0, java.lang.Math.min(part1idx, part2idx));
      String part2 = func.substring(java.lang.Math.min(part1idx, part2idx) + 3, java.lang.Math.max(part1idx, part2idx));
      String part3 = func.substring(java.lang.Math.max(part1idx, part2idx) + 3);
      sql.append(part1);
      this._val1.appendTo(sel, ctx, bstate.state1, sql, 0);
      sql.append(part2);
      this._val2.appendTo(sel, ctx, bstate.state2, sql, 0);
      sql.append(part3);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

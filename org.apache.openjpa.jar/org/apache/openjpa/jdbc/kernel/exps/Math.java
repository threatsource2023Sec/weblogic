package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Math extends AbstractVal {
   public static final String ADD = "+";
   public static final String SUBTRACT = "-";
   public static final String MULTIPLY = "*";
   public static final String DIVIDE = "/";
   public static final String MOD = "MOD";
   private final Val _val1;
   private final Val _val2;
   private final String _op;
   private ClassMetaData _meta = null;
   private Class _cast = null;

   public Math(Val val1, Val val2, String op) {
      this._val1 = val1;
      this._val2 = val2;
      this._op = op;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public Class getType() {
      if (this._cast != null) {
         return this._cast;
      } else {
         Class c1 = this._val1.getType();
         Class c2 = this._val2.getType();
         return Filters.promote(c1, c2);
      }
   }

   public void setImplicitType(Class type) {
      this._cast = type;
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
      this._val1.calculateValue(sel, ctx, bstate.state1, this._val2, bstate.state2);
      this._val2.calculateValue(sel, ctx, bstate.state2, this._val1, bstate.state1);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      ctx.store.getDBDictionary().mathFunction(sql, this._op, new FilterValueImpl(sel, ctx, bstate.state1, this._val1), new FilterValueImpl(sel, ctx, bstate.state2, this._val2));
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

abstract class UnaryOp extends AbstractVal {
   private final Val _val;
   private ClassMetaData _meta = null;
   private Class _cast = null;

   public UnaryOp(Val val) {
      this._val = val;
   }

   protected Val getValue() {
      return this._val;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public Class getType() {
      return this._cast != null ? this._cast : this.getType(this._val.getType());
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this.initializeValue(sel, ctx, flags);
   }

   protected ExpState initializeValue(Select sel, ExpContext ctx, int flags) {
      return this._val.initialize(sel, ctx, flags);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
      if (this.isAggregate()) {
         sel.setAggregate(true);
      }

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

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      sql.append(this.getOperator());
      sql.append("(");
      this._val.appendTo(sel, ctx, state, sql, 0);
      sql.addCastForParam(this.getOperator(), this._val);
      sql.append(")");
   }

   protected Class getType(Class c) {
      return c;
   }

   protected abstract String getOperator();

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}

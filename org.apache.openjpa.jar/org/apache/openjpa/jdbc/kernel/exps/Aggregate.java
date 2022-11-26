package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Aggregate extends AbstractVal {
   private final JDBCAggregateListener _listener;
   private final Val _arg;
   private final ClassMapping _candidate;
   private ClassMetaData _meta = null;
   private Class _cast = null;

   public Aggregate(JDBCAggregateListener listener, Val arg, ClassMapping candidate) {
      this._listener = listener;
      this._arg = arg;
      this._candidate = candidate;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isAggregate() {
      return true;
   }

   public Class getType() {
      return this._cast != null ? this._cast : this._listener.getType(this.getArgTypes());
   }

   private Class[] getArgTypes() {
      if (this._arg == null) {
         return null;
      } else {
         return this._arg instanceof Args ? ((Args)this._arg).getTypes() : new Class[]{this._arg.getType()};
      }
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this._arg == null ? ExpState.NULL : this._arg.initialize(sel, ctx, 4);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
      sel.setAggregate(true);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      if (this._arg != null) {
         this._arg.selectColumns(sel, ctx, state, true);
      }

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
      if (this._arg != null) {
         this._arg.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      }

   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      this._listener.appendTo(sql, this.getArgs(sel, ctx, state), this._candidate, ctx.store);
      sel.append(sql, state.joins);
   }

   private FilterValue[] getArgs(Select sel, ExpContext ctx, ExpState state) {
      if (this._arg == null) {
         return null;
      } else {
         return this._arg instanceof Args ? ((Args)this._arg).newFilterValues(sel, ctx, state) : new FilterValue[]{new FilterValueImpl(sel, ctx, state, this._arg)};
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      if (this._arg != null) {
         this._arg.acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.meta.ClassMetaData;

class Extension extends AbstractVal implements Val, Exp {
   private final JDBCFilterListener _listener;
   private final Val _target;
   private final Val _arg;
   private final ClassMapping _candidate;
   private ClassMetaData _meta = null;
   private Class _cast = null;

   public Extension(JDBCFilterListener listener, Val target, Val arg, ClassMapping candidate) {
      this._listener = listener;
      this._target = target;
      this._arg = arg;
      this._candidate = candidate;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isVariable() {
      return false;
   }

   public boolean isAggregate() {
      return false;
   }

   public Class getType() {
      if (this._cast != null) {
         return this._cast;
      } else {
         Class targetClass = this._target == null ? null : this._target.getType();
         return this._listener.getType(targetClass, this.getArgTypes());
      }
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
      ExpState targetState = null;
      ExpState argState = null;
      if (this._target != null) {
         targetState = this._target.initialize(sel, ctx, 4);
      }

      if (this._arg != null) {
         argState = this._arg.initialize(sel, ctx, 4);
      }

      Joins j1 = targetState == null ? null : targetState.joins;
      Joins j2 = argState == null ? null : argState.joins;
      return new ExtensionExpState(sel.and(j1, j2), targetState, argState);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      ExtensionExpState estate = (ExtensionExpState)state;
      if (this._target != null) {
         this._target.selectColumns(sel, ctx, estate.targetState, true);
      }

      if (this._arg != null) {
         this._arg.selectColumns(sel, ctx, estate.argState, true);
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
      ExtensionExpState estate = (ExtensionExpState)state;
      if (this._target != null) {
         this._target.calculateValue(sel, ctx, estate.targetState, (Val)null, (ExpState)null);
      }

      if (this._arg != null) {
         this._arg.calculateValue(sel, ctx, estate.argState, (Val)null, (ExpState)null);
      }

   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      ExtensionExpState estate = (ExtensionExpState)state;
      FilterValue target = this._target == null ? null : new FilterValueImpl(sel, ctx, estate.targetState, this._target);
      this._listener.appendTo(sql, target, this.getArgs(sel, ctx, estate.argState), this._candidate, ctx.store);
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
      visitor.enter((Expression)this);
      if (this._target != null) {
         this._target.acceptVisit(visitor);
      }

      if (this._arg != null) {
         this._arg.acceptVisit(visitor);
      }

      visitor.exit((Expression)this);
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return this.initialize(sel, ctx, 0);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      this.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      this.appendTo(sel, ctx, state, sql, 0);
      sel.append(sql, state.joins);
   }

   private static class ExtensionExpState extends ExpState {
      public final ExpState targetState;
      public final ExpState argState;

      public ExtensionExpState(Joins joins, ExpState targetState, ExpState argState) {
         super(joins);
         this.targetState = targetState;
         this.argState = argState;
      }
   }
}

package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Trim extends AbstractVal {
   private final Val _val;
   private final Val _trimChar;
   private final Boolean _where;
   private ClassMetaData _meta = null;

   public Trim(Val val, Val trimChar, Boolean where) {
      this._val = val;
      this._trimChar = trimChar;
      this._where = where;
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
      ExpState valueState = this._val.initialize(sel, ctx, 0);
      ExpState charState = this._trimChar.initialize(sel, ctx, 0);
      return new TrimExpState(sel.and(valueState.joins, charState.joins), valueState, charState);
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      TrimExpState tstate = (TrimExpState)state;
      this._val.selectColumns(sel, ctx, tstate.valueState, true);
      this._trimChar.selectColumns(sel, ctx, tstate.charState, true);
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
      TrimExpState tstate = (TrimExpState)state;
      this._val.calculateValue(sel, ctx, tstate.valueState, (Val)null, (ExpState)null);
      this._trimChar.calculateValue(sel, ctx, tstate.charState, (Val)null, (ExpState)null);
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      DBDictionary dict = ctx.store.getDBDictionary();
      String func;
      if (this._where == null) {
         func = dict.trimBothFunction;
         dict.assertSupport(func != null, "TrimBothFunction");
      } else if (this._where) {
         func = dict.trimLeadingFunction;
         dict.assertSupport(func != null, "TrimLeadingFunction");
      } else {
         func = dict.trimTrailingFunction;
         dict.assertSupport(func != null, "TrimTrailingFunction");
      }

      func = dict.getCastFunction(this._val, func);
      int fromPart = func.indexOf("{0}");
      int charPart = func.indexOf("{1}");
      if (charPart == -1) {
         charPart = func.length();
      }

      String part1 = func.substring(0, java.lang.Math.min(fromPart, charPart));
      String part2 = func.substring(java.lang.Math.min(fromPart, charPart) + 3, java.lang.Math.max(fromPart, charPart));
      String part3 = null;
      if (charPart != func.length()) {
         part3 = func.substring(java.lang.Math.max(fromPart, charPart) + 3);
      }

      TrimExpState tstate = (TrimExpState)state;
      sql.append(part1);
      if (fromPart < charPart) {
         this._val.appendTo(sel, ctx, tstate.valueState, sql, 0);
      } else {
         this._trimChar.appendTo(sel, ctx, tstate.charState, sql, 0);
      }

      sql.append(part2);
      if (charPart != func.length()) {
         if (fromPart > charPart) {
            this._val.appendTo(sel, ctx, tstate.valueState, sql, 0);
         } else {
            this._trimChar.appendTo(sel, ctx, tstate.charState, sql, 0);
         }

         sql.append(part3);
      } else if (!(this._trimChar instanceof Const) || String.valueOf(((Const)this._trimChar).getValue(ctx, tstate.charState)).trim().length() != 0) {
         dict.assertSupport(false, "TrimNonWhitespaceCharacters");
      }

   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      this._trimChar.acceptVisit(visitor);
      visitor.exit((Value)this);
   }

   private static class TrimExpState extends ExpState {
      public final ExpState valueState;
      public final ExpState charState;

      public TrimExpState(Joins joins, ExpState valueState, ExpState charState) {
         super(joins);
         this.valueState = valueState;
         this.charState = charState;
      }
   }
}

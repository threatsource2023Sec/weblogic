package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

abstract class CompareEqualExpression implements Exp {
   private static final Localizer _loc = Localizer.forPackage(CompareEqualExpression.class);
   private final Val _val1;
   private final Val _val2;

   public CompareEqualExpression(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
   }

   public Val getValue1() {
      return this._val1;
   }

   public Val getValue2() {
      return this._val2;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      boolean direct = this.isDirectComparison();
      int flags1 = 0;
      int flags2 = 0;
      ExpState s1 = null;
      ExpState s2 = null;
      if (this._val1 instanceof Const) {
         s1 = this._val1.initialize(sel, ctx, 0);
         if (direct && ((Const)this._val1).getValue(ctx, s1) == null) {
            flags2 = 2;
         }
      }

      if (this._val2 instanceof Const) {
         s2 = this._val2.initialize(sel, ctx, 0);
         if (direct && ((Const)this._val2).getValue(ctx, s2) == null) {
            flags1 = 2;
         }
      }

      if (s1 == null) {
         s1 = this._val1.initialize(sel, ctx, flags1);
      }

      if (s2 == null) {
         s2 = this._val2.initialize(sel, ctx, flags2);
      }

      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.calculateValue(sel, ctx, bstate.state1, this._val2, bstate.state2);
      this._val2.calculateValue(sel, ctx, bstate.state2, this._val1, bstate.state1);
      if (!Filters.canConvert(this._val1.getType(), this._val2.getType(), false) && !Filters.canConvert(this._val2.getType(), this._val1.getType(), false)) {
         throw new UserException(_loc.get("cant-convert", this._val1.getType(), this._val2.getType()));
      } else {
         boolean val1Null = this._val1 instanceof Const && ((Const)this._val1).isSQLValueNull(sel, ctx, bstate.state1);
         boolean val2Null = this._val2 instanceof Const && ((Const)this._val2).isSQLValueNull(sel, ctx, bstate.state2);
         this.appendTo(sel, ctx, bstate, buf, val1Null, val2Null);
         sel.append(buf, state.joins);
      }
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.selectColumns(sel, ctx, bstate.state1, true);
      this._val2.selectColumns(sel, ctx, bstate.state2, true);
   }

   protected abstract void appendTo(Select var1, ExpContext var2, BinaryOpExpState var3, SQLBuffer var4, boolean var5, boolean var6);

   protected boolean isDirectComparison() {
      return true;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

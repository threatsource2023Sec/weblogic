package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Literal;

public class Lit extends Const implements Literal {
   private Object _val;
   private int _ptype;

   public Lit(Object val, int ptype) {
      this._val = val;
      this._ptype = ptype;
   }

   public Class getType() {
      return this._val == null ? Object.class : this._val.getClass();
   }

   public void setImplicitType(Class type) {
      this._val = Filters.convert(this._val, type);
   }

   public int getParseType() {
      return this._ptype;
   }

   public Object getValue() {
      return this._val;
   }

   public void setValue(Object val) {
      this._val = val;
   }

   public Object getValue(Object[] params) {
      return this.getValue();
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return new LitExpState();
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      super.calculateValue(sel, ctx, state, other, otherState);
      LitExpState lstate = (LitExpState)state;
      if (other != null) {
         lstate.sqlValue = other.toDataStoreValue(sel, ctx, otherState, this._val);
         lstate.otherLength = other.length(sel, ctx, otherState);
      } else {
         lstate.sqlValue = this._val;
      }

   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      LitExpState lstate = (LitExpState)state;
      if (lstate.otherLength > 1) {
         sql.appendValue(((Object[])((Object[])lstate.sqlValue))[index], lstate.getColumn(index));
      } else {
         sql.appendValue(lstate.sqlValue, lstate.getColumn(index));
      }

   }

   private static class LitExpState extends Const.ConstExpState {
      public Object sqlValue;
      public int otherLength;

      private LitExpState() {
      }

      // $FF: synthetic method
      LitExpState(Object x0) {
         this();
      }
   }
}

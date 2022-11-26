package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.util.ImplHelper;

class ConstGetObjectId extends Const {
   private final Const _constant;

   public ConstGetObjectId(Const constant) {
      this._constant = constant;
   }

   public Class getType() {
      return Object.class;
   }

   public void setImplicitType(Class type) {
   }

   public Object getValue(Object[] params) {
      Object o = this._constant.getValue(params);
      return !ImplHelper.isManageable(o) ? null : ImplHelper.toPersistenceCapable(o, this.getMetaData().getRepository().getConfiguration()).pcFetchObjectId();
   }

   public Object getValue(ExpContext ctx, ExpState state) {
      return ctx.store.getContext().getObjectId(this._constant.getValue(ctx, ((ConstGetObjectIdExpState)state).constantState));
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return new ConstGetObjectIdExpState(this._constant.initialize(sel, ctx, 0));
   }

   public Object getSQLValue(Select sel, ExpContext ctx, ExpState state) {
      return ((ConstGetObjectIdExpState)state).sqlValue;
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      super.calculateValue(sel, ctx, state, other, otherState);
      ConstGetObjectIdExpState cstate = (ConstGetObjectIdExpState)state;
      this._constant.calculateValue(sel, ctx, cstate.constantState, (Val)null, (ExpState)null);
      Object oid = ctx.store.getContext().getObjectId(this._constant.getValue(ctx, cstate.constantState));
      if (other != null) {
         cstate.sqlValue = other.toDataStoreValue(sel, ctx, otherState, oid);
         cstate.otherLength = other.length(sel, ctx, otherState);
      } else {
         cstate.sqlValue = oid;
      }

   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      ConstGetObjectIdExpState cstate = (ConstGetObjectIdExpState)state;
      if (cstate.otherLength > 1) {
         sql.appendValue(((Object[])((Object[])cstate.sqlValue))[index], cstate.getColumn(index));
      } else {
         sql.appendValue(cstate.sqlValue, cstate.getColumn(index));
      }

   }

   private static class ConstGetObjectIdExpState extends Const.ConstExpState {
      public final ExpState constantState;
      public Object sqlValue = null;
      public int otherLength = 0;

      public ConstGetObjectIdExpState(ExpState constantState) {
         this.constantState = constantState;
      }
   }
}

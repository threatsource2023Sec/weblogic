package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.meta.XMLMetaData;

class EndsWithExpression implements Exp {
   private final Val _val1;
   private final Val _val2;

   public EndsWithExpression(Val val1, Val val2) {
      this._val1 = val1;
      this._val2 = val2;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState s1 = this._val1.initialize(sel, ctx, 0);
      ExpState s2 = this._val2.initialize(sel, ctx, 0);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.calculateValue(sel, ctx, bstate.state1, this._val2, bstate.state2);
      this._val2.calculateValue(sel, ctx, bstate.state2, this._val1, bstate.state1);
      DBDictionary dict = ctx.store.getDBDictionary();
      String func = dict.stringLengthFunction;
      String pre = null;
      String post = null;
      if (func != null) {
         int idx = func.indexOf("{0}");
         pre = func.substring(0, idx);
         post = func.substring(idx + 3);
      }

      if (this._val1 instanceof Const && ((Const)this._val1).getValue(ctx, bstate.state1) == null) {
         buf.append("1 <> 1");
      } else if (this._val2 instanceof Const) {
         Object o = ((Const)this._val2).getValue(ctx, bstate.state2);
         if (o == null) {
            buf.append("1 <> 1");
         } else {
            Column col = null;
            if (this._val1 instanceof PCPath) {
               Column[] cols = ((PCPath)this._val1).getColumns(bstate.state1);
               if (cols.length == 1) {
                  col = cols[0];
               }
            }

            this._val1.appendTo(sel, ctx, bstate.state1, buf, 0);
            buf.append(" LIKE ");
            buf.appendValue("%" + o.toString(), col);
         }
      } else {
         dict.assertSupport(pre != null, "StringLengthFunction");
         dict.substring(buf, new FilterValueImpl(sel, ctx, bstate.state1, this._val1), new StringLengthDifferenceFilterValue(sel, ctx, bstate, pre, post), (FilterValue)null);
         buf.append(" = ");
         this._val2.appendTo(sel, ctx, bstate.state2, buf, 0);
      }

      sel.append(buf, state.joins);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.selectColumns(sel, ctx, bstate.state1, true);
      this._val2.selectColumns(sel, ctx, bstate.state2, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private class StringLengthDifferenceFilterValue implements FilterValue {
      private final Select _sel;
      private final ExpContext _ctx;
      private final BinaryOpExpState _state;
      private final String _pre;
      private final String _post;

      public StringLengthDifferenceFilterValue(Select sel, ExpContext ctx, BinaryOpExpState state, String pre, String post) {
         this._sel = sel;
         this._ctx = ctx;
         this._state = state;
         this._pre = pre;
         this._post = post;
      }

      public Class getType() {
         return Integer.TYPE;
      }

      public int length() {
         return 1;
      }

      public void appendTo(SQLBuffer buf) {
         this.appendTo(buf, 0);
      }

      public void appendTo(SQLBuffer buf, int index) {
         buf.append(this._pre);
         EndsWithExpression.this._val1.appendTo(this._sel, this._ctx, this._state.state1, buf, index);
         buf.append(this._post).append(" - ").append(this._pre);
         EndsWithExpression.this._val2.appendTo(this._sel, this._ctx, this._state.state2, buf, index);
         buf.append(this._post);
      }

      public String getColumnAlias(Column col) {
         return this._sel.getColumnAlias(col, this._state.joins);
      }

      public String getColumnAlias(String col, Table table) {
         return this._sel.getColumnAlias(col, table, this._state.joins);
      }

      public Object toDataStoreValue(Object val) {
         return val;
      }

      public boolean isConstant() {
         return false;
      }

      public Object getValue() {
         return null;
      }

      public Object getSQLValue() {
         return null;
      }

      public boolean isPath() {
         return false;
      }

      public ClassMapping getClassMapping() {
         return null;
      }

      public FieldMapping getFieldMapping() {
         return null;
      }

      public PCPath getXPath() {
         return null;
      }

      public XMLMetaData getXmlMapping() {
         return null;
      }
   }
}

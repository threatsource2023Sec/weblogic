package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import serp.util.Strings;

class MatchesExpression implements Exp {
   private final Val _val;
   private final Const _const;
   private final String _single;
   private final String _multi;
   private final String _escape;

   public MatchesExpression(Val val, Const con, String single, String multi, String escape) {
      this._val = val;
      this._const = con;
      this._single = single;
      this._multi = multi;
      this._escape = escape;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState s1 = this._val.initialize(sel, ctx, 0);
      ExpState s2 = this._const.initialize(sel, ctx, 0);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val.calculateValue(sel, ctx, bstate.state1, this._const, bstate.state2);
      this._const.calculateValue(sel, ctx, bstate.state2, this._val, bstate.state1);
      Column col = null;
      if (this._val instanceof PCPath) {
         Column[] cols = ((PCPath)this._val).getColumns(bstate.state1);
         if (cols.length == 1) {
            col = cols[0];
         }
      }

      Object o = this._const.getValue(ctx, bstate.state2);
      if (o == null) {
         buf.append("1 <> 1");
      } else {
         boolean ignoreCase = false;
         String str = o.toString();
         int idx = str.indexOf("(?i)");
         if (idx != -1) {
            ignoreCase = true;
            if (idx + 4 < str.length()) {
               str = str.substring(0, idx) + str.substring(idx + 4);
            } else {
               str = str.substring(0, idx);
            }

            str = str.toLowerCase();
         }

         if (ignoreCase) {
            buf.append("LOWER(");
         }

         this._val.appendTo(sel, ctx, bstate.state1, buf, 0);
         if (ignoreCase) {
            buf.append(")");
         }

         str = replaceEscape(str, this._multi, "%", this._escape);
         str = replaceEscape(str, this._single, "_", this._escape);
         buf.append(" LIKE ").appendValue(str, col);
         if (this._escape != null) {
            buf.append(" ESCAPE '").append(this._escape).append("'");
         }
      }

      sel.append(buf, state.joins);
   }

   private static String replaceEscape(String str, String from, String to, String escape) {
      String[] parts = Strings.split(str, from, Integer.MAX_VALUE);
      StringBuffer repbuf = new StringBuffer();

      for(int i = 0; i < parts.length; ++i) {
         if (i > 0) {
            if (!from.equals(to) && parts[i - 1].endsWith(escape)) {
               repbuf.setLength(repbuf.length() - 1);
               repbuf.append(from);
            } else {
               repbuf.append(to);
            }
         }

         repbuf.append(parts[i]);
      }

      return repbuf.toString();
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val.selectColumns(sel, ctx, bstate.state1, true);
      this._const.selectColumns(sel, ctx, bstate.state2, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      this._const.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}

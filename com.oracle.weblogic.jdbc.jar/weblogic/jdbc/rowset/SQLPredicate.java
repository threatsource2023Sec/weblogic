package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.Predicate;
import weblogic.utils.expressions.Expression;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.expressions.ExpressionParser;
import weblogic.utils.expressions.ExpressionParserException;
import weblogic.utils.expressions.Variable;
import weblogic.utils.expressions.VariableBinder;
import weblogic.utils.expressions.Expression.Type;

/** @deprecated */
@Deprecated
public class SQLPredicate implements Predicate, Serializable {
   private Expression expression = null;

   public SQLPredicate(String selector) throws ExpressionParserException {
      this.expression = (new ExpressionParser()).parse(selector, SQLPredicate.SQLVariableBinder.THE_ONE);
   }

   public boolean evaluate(RowSet rs) {
      try {
         return this.expression.evaluate(rs);
      } catch (Throwable var3) {
         throw new RuntimeException(var3.getMessage());
      }
   }

   public boolean evaluate(Object o, String s) throws SQLException {
      throw new SQLException("This is not supported by SQLPredicate.");
   }

   public boolean evaluate(Object o, int i) throws SQLException {
      throw new SQLException("This is not supported by SQLPredicate.");
   }

   private static class SQLVariable implements Variable {
      private final String key;

      private SQLVariable(String key) {
         this.key = key;
      }

      public Object get(Object context) throws ExpressionEvaluationException {
         try {
            return ((RowSet)context).getObject(this.key);
         } catch (SQLException var3) {
            throw new ExpressionEvaluationException("Could not find field: " + this.key, var3);
         }
      }

      public Expression.Type getType() {
         return Type.ANY;
      }

      // $FF: synthetic method
      SQLVariable(String x0, Object x1) {
         this(x0);
      }
   }

   private static class SQLVariableBinder implements VariableBinder {
      private static final SQLVariableBinder THE_ONE = new SQLVariableBinder();

      public Variable getVariable(String name) {
         return new SQLVariable(name);
      }
   }
}

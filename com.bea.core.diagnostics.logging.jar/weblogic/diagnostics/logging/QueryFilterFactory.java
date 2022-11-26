package weblogic.diagnostics.logging;

import com.bea.logging.LogFilterFactory;
import java.util.logging.Filter;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.QueryFactory;

public class QueryFilterFactory extends LogFilterFactory {
   private static final boolean DEBUG = false;

   public Filter createFilter(String filterExpression) {
      try {
         Query q = this.createQuery(filterExpression);
         return new QueryFilter(q);
      } catch (QueryException var3) {
         return null;
      }
   }

   private Query createQuery(String expr) throws QueryException {
      if (expr != null && expr.length() > 0) {
         LogVariablesImpl lv = LogVariablesImpl.getInstance();
         return QueryFactory.createQuery(lv, lv, expr);
      } else {
         return null;
      }
   }
}

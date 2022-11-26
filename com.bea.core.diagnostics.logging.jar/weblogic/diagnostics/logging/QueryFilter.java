package weblogic.diagnostics.logging;

import com.bea.logging.BaseLogEntry;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryExecutionException;

public class QueryFilter implements Filter {
   private static final boolean DEBUG = false;
   private Query query;

   public QueryFilter(Query query) {
      this.query = query;
   }

   public boolean isLoggable(LogRecord record) {
      if (record instanceof BaseLogEntry) {
         BaseLogEntry logEntry = (BaseLogEntry)record;
         if (this.query != null) {
            try {
               boolean result = this.query.executeQuery(LogVariablesImpl.getInstance().getLogVariablesResolver(logEntry));
               return result;
            } catch (QueryExecutionException var4) {
               return true;
            }
         }
      }

      return true;
   }
}

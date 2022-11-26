package weblogic.diagnostics.query;

public class QueryExecutionException extends QueryException {
   public QueryExecutionException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public QueryExecutionException(String msg) {
      super(msg);
   }
}

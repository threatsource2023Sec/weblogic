package weblogic.diagnostics.query;

public class QueryException extends Exception {
   public QueryException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public QueryException(String msg) {
      super(msg);
   }
}

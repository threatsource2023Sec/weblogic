package weblogic.diagnostics.query;

public class UnknownVariableException extends QueryExecutionException {
   public UnknownVariableException(String msg) {
      super(msg);
   }

   public UnknownVariableException(String msg, Throwable exception) {
      super(msg, exception);
   }
}

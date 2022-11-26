package weblogic.diagnostics.debug;

public class DebugScopeInitializationException extends Exception {
   public DebugScopeInitializationException(Throwable cause) {
      super(cause);
   }

   public DebugScopeInitializationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}

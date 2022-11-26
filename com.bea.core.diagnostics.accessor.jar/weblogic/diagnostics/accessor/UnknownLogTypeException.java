package weblogic.diagnostics.accessor;

import weblogic.diagnostics.type.DiagnosticException;

public class UnknownLogTypeException extends DiagnosticException {
   public UnknownLogTypeException(String logType) {
      super(logType);
   }

   public UnknownLogTypeException(String logType, Throwable t) {
      super(logType, t);
   }
}

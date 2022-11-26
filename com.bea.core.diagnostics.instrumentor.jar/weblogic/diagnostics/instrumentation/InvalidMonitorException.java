package weblogic.diagnostics.instrumentation;

public class InvalidMonitorException extends InstrumentationException {
   public InvalidMonitorException() {
   }

   public InvalidMonitorException(String msg) {
      super(msg);
   }
}

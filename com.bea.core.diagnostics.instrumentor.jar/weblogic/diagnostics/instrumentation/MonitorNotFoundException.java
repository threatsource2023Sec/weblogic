package weblogic.diagnostics.instrumentation;

public class MonitorNotFoundException extends InstrumentationException {
   public MonitorNotFoundException() {
   }

   public MonitorNotFoundException(String msg) {
      super(msg);
   }
}

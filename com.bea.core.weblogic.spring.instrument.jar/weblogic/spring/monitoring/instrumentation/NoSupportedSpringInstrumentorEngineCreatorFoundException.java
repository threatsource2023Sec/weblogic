package weblogic.spring.monitoring.instrumentation;

public class NoSupportedSpringInstrumentorEngineCreatorFoundException extends RuntimeException {
   private static final long serialVersionUID = 3479284762631320942L;

   public NoSupportedSpringInstrumentorEngineCreatorFoundException(String springVersion) {
      super("No supported SpringInstrumentorEngineCreator found for Spring version [" + springVersion + "].");
   }
}

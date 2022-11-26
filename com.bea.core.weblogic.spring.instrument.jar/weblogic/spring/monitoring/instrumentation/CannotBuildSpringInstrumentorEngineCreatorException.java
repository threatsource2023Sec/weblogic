package weblogic.spring.monitoring.instrumentation;

public class CannotBuildSpringInstrumentorEngineCreatorException extends RuntimeException {
   private static final long serialVersionUID = 2260709878848617914L;

   public CannotBuildSpringInstrumentorEngineCreatorException(String message, Throwable cause) {
      super("Cannot build SpringInstrumentorEngineCreator instance of class [" + message + "]", cause);
   }
}

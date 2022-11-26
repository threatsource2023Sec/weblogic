package weblogic.diagnostics.harvester;

public class InvalidHarvesterInstanceNameException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public InvalidHarvesterInstanceNameException() {
   }

   public InvalidHarvesterInstanceNameException(String arg0) {
      super(arg0);
   }

   public InvalidHarvesterInstanceNameException(String arg0, Throwable arg1) {
      super(arg0, arg1);
   }

   public InvalidHarvesterInstanceNameException(Throwable arg0) {
      super(arg0);
   }
}

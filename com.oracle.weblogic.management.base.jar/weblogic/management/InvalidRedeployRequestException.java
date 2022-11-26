package weblogic.management;

public class InvalidRedeployRequestException extends ApplicationException {
   public InvalidRedeployRequestException(String error) {
      super(error);
   }

   public InvalidRedeployRequestException(String error, Throwable nested) {
      super(error, nested);
   }
}

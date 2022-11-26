package weblogic.security.spi;

public class InvalidPrincipalException extends SecurityException {
   public InvalidPrincipalException() {
   }

   public InvalidPrincipalException(String s) {
      super(s);
   }
}

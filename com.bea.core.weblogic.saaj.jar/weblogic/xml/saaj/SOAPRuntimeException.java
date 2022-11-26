package weblogic.xml.saaj;

public class SOAPRuntimeException extends RuntimeException {
   SOAPRuntimeException(Exception e) {
      super(e);
   }

   SOAPRuntimeException(String message, Exception e) {
      super(message, e);
   }

   SOAPRuntimeException(String message) {
      super(message);
   }
}

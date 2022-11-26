package weblogic.servlet.utils.fileupload;

public class BoundaryTooLongException extends RuntimeException {
   public BoundaryTooLongException(String message) {
      super(message);
   }
}

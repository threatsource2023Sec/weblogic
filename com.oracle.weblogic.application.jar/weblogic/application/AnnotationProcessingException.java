package weblogic.application;

public class AnnotationProcessingException extends Exception {
   private static final long serialVersionUID = 1L;

   public AnnotationProcessingException(String message) {
      super(message);
   }

   public AnnotationProcessingException(Exception ex) {
      super(ex);
   }

   public AnnotationProcessingException(String message, Exception ex) {
      super(message, ex);
   }
}

package weblogic.application.library;

public class LibraryProcessingException extends Exception {
   public LibraryProcessingException(String s) {
      super(s);
   }

   public LibraryProcessingException(Throwable th) {
      super(th);
   }

   public LibraryProcessingException(String s, Throwable th) {
      super(s, th);
   }
}

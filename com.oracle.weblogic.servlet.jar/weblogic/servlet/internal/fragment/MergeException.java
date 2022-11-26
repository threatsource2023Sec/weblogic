package weblogic.servlet.internal.fragment;

public class MergeException extends Exception {
   private static final long serialVersionUID = -4585735971751832894L;

   public MergeException(String message, Throwable cause) {
      super(message, cause);
   }

   public MergeException(String message) {
      super(message);
   }
}

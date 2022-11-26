package javax.jdo;

public class JDOUserCallbackException extends JDOUserException {
   public JDOUserCallbackException() {
   }

   public JDOUserCallbackException(String msg) {
      super(msg);
   }

   public JDOUserCallbackException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOUserCallbackException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public JDOUserCallbackException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDOUserCallbackException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDOUserCallbackException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

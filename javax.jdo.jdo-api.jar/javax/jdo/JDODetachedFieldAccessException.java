package javax.jdo;

public class JDODetachedFieldAccessException extends JDOUserException {
   public JDODetachedFieldAccessException() {
   }

   public JDODetachedFieldAccessException(String msg) {
      super(msg);
   }

   public JDODetachedFieldAccessException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDODetachedFieldAccessException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDODetachedFieldAccessException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

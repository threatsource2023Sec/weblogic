package javax.jdo;

public class JDONullIdentityException extends JDOUserException {
   public JDONullIdentityException() {
   }

   public JDONullIdentityException(String msg) {
      super(msg);
   }

   public JDONullIdentityException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDONullIdentityException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDONullIdentityException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

package javax.jdo;

public class JDOUnsupportedOptionException extends JDOUserException {
   public JDOUnsupportedOptionException() {
   }

   public JDOUnsupportedOptionException(String msg) {
      super(msg);
   }

   public JDOUnsupportedOptionException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOUnsupportedOptionException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

package javax.jdo;

public class JDOFatalException extends JDOException {
   public JDOFatalException() {
   }

   public JDOFatalException(String msg) {
      super(msg);
   }

   public JDOFatalException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOFatalException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public JDOFatalException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDOFatalException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDOFatalException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

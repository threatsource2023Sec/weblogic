package javax.jdo;

public class JDOFatalInternalException extends JDOFatalException {
   public JDOFatalInternalException() {
   }

   public JDOFatalInternalException(String msg) {
      super(msg);
   }

   public JDOFatalInternalException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOFatalInternalException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public JDOFatalInternalException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDOFatalInternalException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDOFatalInternalException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

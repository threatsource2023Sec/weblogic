package javax.jdo;

public class JDOReadOnlyException extends JDOUserException {
   public JDOReadOnlyException() {
   }

   public JDOReadOnlyException(String msg) {
      super(msg);
   }

   public JDOReadOnlyException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOReadOnlyException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

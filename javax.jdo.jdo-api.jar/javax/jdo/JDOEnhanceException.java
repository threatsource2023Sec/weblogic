package javax.jdo;

public class JDOEnhanceException extends JDOException {
   public JDOEnhanceException() {
   }

   public JDOEnhanceException(String msg) {
      super(msg);
   }

   public JDOEnhanceException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOEnhanceException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

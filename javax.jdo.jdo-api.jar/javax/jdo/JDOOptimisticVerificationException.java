package javax.jdo;

public class JDOOptimisticVerificationException extends JDOFatalDataStoreException {
   public JDOOptimisticVerificationException() {
   }

   public JDOOptimisticVerificationException(String msg) {
      super(msg);
   }

   public JDOOptimisticVerificationException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDOOptimisticVerificationException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOOptimisticVerificationException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDOOptimisticVerificationException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

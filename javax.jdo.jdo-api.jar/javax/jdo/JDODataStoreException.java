package javax.jdo;

public class JDODataStoreException extends JDOCanRetryException {
   public JDODataStoreException() {
   }

   public JDODataStoreException(String msg) {
      super(msg);
   }

   public JDODataStoreException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDODataStoreException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public JDODataStoreException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDODataStoreException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDODataStoreException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

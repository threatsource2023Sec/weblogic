package javax.jdo;

public class JDOObjectNotFoundException extends JDODataStoreException {
   public JDOObjectNotFoundException() {
   }

   public JDOObjectNotFoundException(String msg) {
      super(msg);
   }

   public JDOObjectNotFoundException(String msg, Object failed) {
      super(msg, failed);
   }

   public JDOObjectNotFoundException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public JDOObjectNotFoundException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public JDOObjectNotFoundException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public JDOObjectNotFoundException(String msg, Throwable nested, Object failed) {
      super(msg, nested, failed);
   }
}

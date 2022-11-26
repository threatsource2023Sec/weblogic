package weblogic.security.spi;

public class JDBCConnectionServiceException extends SecuritySpiException {
   public JDBCConnectionServiceException() {
   }

   public JDBCConnectionServiceException(String msg) {
      super(msg);
   }

   public JDBCConnectionServiceException(Throwable nested) {
      super(nested);
   }

   public JDBCConnectionServiceException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

package weblogic.security.service;

public class AuditLogFileException extends SecurityServiceRuntimeException {
   public AuditLogFileException() {
   }

   public AuditLogFileException(String msg) {
      super(msg);
   }

   public AuditLogFileException(Throwable nested) {
      super(nested);
   }

   public AuditLogFileException(String msg, Throwable nested) {
      super(msg, nested);
   }
}

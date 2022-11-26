package weblogic.cluster.migration;

public class MigrationException extends RuntimeException {
   static final long serialVersionUID = -5904150673307531553L;
   private boolean fatal = false;

   public MigrationException() {
   }

   public MigrationException(String message) {
      super(message);
   }

   public MigrationException(String message, boolean f) {
      super(message);
      this.fatal = f;
   }

   public MigrationException(Exception cause) {
      super(cause);
   }

   public MigrationException(Exception cause, boolean f) {
      super(cause);
      this.fatal = f;
   }

   public MigrationException(String message, Exception cause) {
      super(message, cause);
   }

   public MigrationException(String message, Exception cause, boolean f) {
      super(message, cause);
      this.fatal = f;
   }

   public boolean isFatal() {
      return this.fatal;
   }
}

package weblogic.cluster.migration;

public class MigratableGroupNotReadyException extends MigrationException {
   static final long serialVersionUID = -5904150673307531553L;

   public MigratableGroupNotReadyException() {
      super((String)null, (Exception)null, false);
   }

   public MigratableGroupNotReadyException(String message) {
      super(message, (Exception)null, false);
   }

   public MigratableGroupNotReadyException(Exception cause) {
      super((String)null, cause, false);
   }
}

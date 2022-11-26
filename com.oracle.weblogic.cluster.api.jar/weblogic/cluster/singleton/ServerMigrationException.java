package weblogic.cluster.singleton;

public final class ServerMigrationException extends Exception {
   private String message;
   private int status;

   public ServerMigrationException(String message, int status, Throwable t) {
      this(message, t);
      this.status = status;
   }

   public ServerMigrationException(String message, Throwable t) {
      super(message, t);
      this.message = message;
   }

   public int getStatus() {
      return this.status;
   }

   public String toString() {
      return this.message;
   }
}

package weblogic.cluster.replication;

public class MigrationInProgressException extends Exception {
   public MigrationInProgressException(String reason) {
      super(reason);
   }
}

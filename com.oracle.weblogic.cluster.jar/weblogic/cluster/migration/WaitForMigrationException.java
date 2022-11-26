package weblogic.cluster.migration;

import java.rmi.ConnectException;

public final class WaitForMigrationException extends ConnectException {
   private long suggestedWait;

   public WaitForMigrationException(String s) {
      super(s);
   }

   public WaitForMigrationException(String s, Exception root) {
      super(s, root);
   }

   WaitForMigrationException(String s, Exception root, long suggestedWait) {
      super(s, root);
      this.suggestedWait = suggestedWait;
   }

   public long getSuggestedWait() {
      return this.suggestedWait;
   }
}

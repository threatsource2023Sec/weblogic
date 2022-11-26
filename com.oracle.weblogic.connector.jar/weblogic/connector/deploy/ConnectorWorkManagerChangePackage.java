package weblogic.connector.deploy;

import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.exception.RAException;

class ConnectorWorkManagerChangePackage implements ChangePackage {
   private RAInstanceManager raIM = null;
   private ConnectorModuleChangePackage.ChangeType changeType;
   private int maxConcurrentLongRunningRequests;

   protected ConnectorWorkManagerChangePackage(RAInstanceManager raIM, ConnectorModuleChangePackage.ChangeType changeType, int changedMaxConcurrentLongRunningRequests) {
      this.raIM = raIM;
      this.changeType = changeType;
      this.maxConcurrentLongRunningRequests = changedMaxConcurrentLongRunningRequests;
   }

   public void rollback() throws RAException {
   }

   public void prepare() throws RAException {
   }

   public void activate() throws RAException {
      this.raIM.getBootstrapContext().getConnectorWorkManager().getLongRunningWorkManager().setMaxConcurrentRequests(this.maxConcurrentLongRunningRequests);
   }

   public String toString() {
      return this.changeType.toString() + " " + this.maxConcurrentLongRunningRequests;
   }
}

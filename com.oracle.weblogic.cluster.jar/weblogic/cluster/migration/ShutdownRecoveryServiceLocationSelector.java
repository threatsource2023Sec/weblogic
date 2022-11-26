package weblogic.cluster.migration;

import weblogic.cluster.singleton.SingletonServicesStateManager;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public class ShutdownRecoveryServiceLocationSelector extends FailureRecoveryServiceLocationSelector {
   public ShutdownRecoveryServiceLocationSelector(MigratableTargetMBean mt, SingletonServicesStateManager sm) {
      super(mt, sm);
   }

   public ServerMBean chooseServer() {
      return this.chooseServer(true);
   }
}

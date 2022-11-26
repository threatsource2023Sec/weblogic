package weblogic.cluster.singleton;

import weblogic.cluster.migration.MigratableGroup;

public class RestartOperation extends SingletonOperation {
   public void prepare(SingletonServiceInfo serviceInfo, Leasing leasingService) throws SingletonOperationException, LeasingException {
      if (!(serviceInfo.getService() instanceof MigratableGroup)) {
         throw new SingletonOperationException("Only migratable targets can be restarted.");
      }
   }

   public void execute(SingletonServiceInfo serviceInfo) {
      SingletonService ss = serviceInfo.getService();
      ((MigratableGroup)ss).restart();
   }

   public String getName() {
      return "restart";
   }

   public void failed(String name, Leasing leasingService, Throwable t) throws LeasingException {
   }
}

package weblogic.cluster.singleton;

import weblogic.cluster.migration.MigratableGroup;

public class DeactivateOperation extends SingletonOperation {
   public void prepare(SingletonServiceInfo serviceInfo, Leasing leasingService) throws SingletonOperationException, LeasingException {
      if (leasingService != null) {
         String name = serviceInfo.getName();
         SingletonService ss = serviceInfo.getService();
         if (!(ss instanceof MigratableGroup)) {
            try {
               leasingService.release(name);
            } catch (LeasingException var6) {
               if (DEBUG) {
                  p("Could not release lease for singleton " + name + ". Ignoring and continuing with the deactivation, since we do not own the lease.");
               }
            }
         }

      }
   }

   public void execute(SingletonServiceInfo serviceInfo) {
      SingletonService ss = serviceInfo.getService();
      ss.deactivate();
      serviceInfo.setActivated(false);
   }

   public String getName() {
      return "deactivation";
   }

   public void failed(String name, Leasing leasingService, Throwable t) throws LeasingException {
   }
}

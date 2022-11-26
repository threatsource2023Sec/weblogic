package weblogic.cluster.singleton;

import weblogic.cluster.migration.MigratableGroup;

public class ActivateOperation extends SingletonOperation {
   public void prepare(SingletonServiceInfo serviceInfo, Leasing leasingService) throws SingletonOperationException, LeasingException {
      if (leasingService != null) {
         String name = serviceInfo.getName();
         SingletonService ss = serviceInfo.getService();

         try {
            if (!(ss instanceof MigratableGroup) && !leasingService.tryAcquire(name)) {
               if (DEBUG) {
                  p("Could not acquire lease for singleton " + name + ", stopping activation.");
               }

               throw new SingletonOperationException("Could not start service " + name + " because could not claim lease");
            }
         } catch (LeasingException var6) {
            if (DEBUG) {
               p("Could not acquire lease for singleton " + name + ", stopping activation.");
            }

            throw var6;
         }
      }
   }

   public void execute(SingletonServiceInfo serviceInfo) {
      SingletonService ss = serviceInfo.getService();
      ss.activate();
      serviceInfo.setActivated(true);
   }

   public void failed(String name, Leasing leasingService, Throwable t) throws LeasingException {
      if (leasingService != null) {
         leasingService.release(name);
      }

   }

   public String getName() {
      return "activation";
   }
}

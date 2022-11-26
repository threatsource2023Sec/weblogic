package weblogic.cluster.singleton;

public class ConcurrentSingletonController extends SingletonController {
   public ConcurrentSingletonController(SingletonManager singletonManager, Leasing leasingService) {
      super(singletonManager, leasingService);
   }

   public void execute(SingletonOperation op, String name) throws SingletonOperationException, LeasingException {
      SingletonServiceInfo serviceInfo = this.singletonManager.getServiceInfo(name);
      if (serviceInfo == null) {
         throw new SingletonOperationException("No singleton named " + name + " found on this server.");
      } else {
         synchronized(serviceInfo) {
            this.doExecute(op, serviceInfo);
         }
      }
   }

   public void remove(String name) {
      SingletonServiceInfo serviceInfo = this.singletonManager.remove(name);
      if (serviceInfo != null) {
         synchronized(serviceInfo) {
            this.doRemove(serviceInfo);
         }
      }

   }
}

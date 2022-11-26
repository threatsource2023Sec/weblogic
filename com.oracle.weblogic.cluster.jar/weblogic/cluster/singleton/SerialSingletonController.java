package weblogic.cluster.singleton;

public class SerialSingletonController extends SingletonController {
   public SerialSingletonController(SingletonManager singletonManager, Leasing leasingService) {
      super(singletonManager, leasingService);
   }

   public void execute(SingletonOperation op, String name) throws SingletonOperationException, LeasingException {
      synchronized(this) {
         SingletonServiceInfo serviceInfo = this.singletonManager.getServiceInfo(name);
         if (serviceInfo == null) {
            throw new SingletonOperationException("No singleton named " + name + " found on this server.");
         } else {
            this.doExecute(op, serviceInfo);
         }
      }
   }

   public void remove(String name) {
      synchronized(this) {
         SingletonServiceInfo serviceInfo = this.singletonManager.remove(name);
         if (serviceInfo != null) {
            this.doRemove(serviceInfo);
         }

      }
   }
}

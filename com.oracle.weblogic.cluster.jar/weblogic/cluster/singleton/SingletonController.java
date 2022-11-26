package weblogic.cluster.singleton;

import weblogic.work.StackableThreadContext;

public abstract class SingletonController {
   protected static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   public static final SingletonOperation Activate = new ActivateOperation();
   public static final SingletonOperation Deactivate = new DeactivateOperation();
   public static final SingletonOperation Restart = new RestartOperation();
   private final Leasing leasingService;
   protected final SingletonManager singletonManager;

   public SingletonController(SingletonManager singletonManager, Leasing leasingService) {
      this.leasingService = leasingService;
      this.singletonManager = singletonManager;
   }

   public abstract void execute(SingletonOperation var1, String var2) throws SingletonOperationException, LeasingException;

   public abstract void remove(String var1);

   protected void doRemove(SingletonServiceInfo serviceInfo) {
      try {
         this.doExecute(Deactivate, serviceInfo);
      } catch (LeasingException | SingletonOperationException var3) {
      }

      serviceInfo.setRemoved();
   }

   protected void doExecute(SingletonOperation op, SingletonServiceInfo serviceInfo) throws SingletonOperationException, LeasingException {
      String name = serviceInfo.getName();
      if (serviceInfo.isRemoved()) {
         throw new SingletonOperationException("No singleton named " + name + " found on this server.");
      } else {
         op.prepare(serviceInfo, this.leasingService);
         if (DEBUG) {
            SingletonOperation.p(String.format("Performing operation %s on singleton %s: ", op.getName(), name));
         }

         StackableThreadContext context = serviceInfo.getContext();

         try {
            if (context != null) {
               if (DEBUG) {
                  SingletonOperation.p(String.format("Setting up thread context for operation %s for singleton %s", op.getName(), name));
               }

               context.push();
            }

            op.execute(serviceInfo);
         } catch (Throwable var9) {
            if (DEBUG) {
               SingletonOperation.p(String.format("Failed to perform operation %s on singleton %s: %s", op.getName(), name, var9.getMessage()));
            }

            op.failed(name, this.leasingService, var9);
            throw new SingletonOperationException(String.format("Error trying to perform operation %s on singleton %s: %s", op.getName(), name, var9.toString()), var9);
         } finally {
            if (context != null) {
               context.pop();
            }

         }

      }
   }
}

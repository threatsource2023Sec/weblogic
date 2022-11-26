package weblogic.work;

import javax.management.InvalidAttributeValueException;
import weblogic.kernel.ExecuteQueueMBeanStub;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.management.configuration.KernelMBean;

public final class ExecuteQueueFactory extends WorkManagerFactory {
   private static ExecuteQueueFactory SINGLETON;

   private ExecuteQueueFactory() {
   }

   public static synchronized void initialize(KernelMBean kmb) {
      if (SINGLETON == null) {
         SINGLETON = new ExecuteQueueFactory();
         WorkManagerFactory.set(SINGLETON);
         SINGLETON.initializeHere(kmb);
      }
   }

   private void initializeHere(KernelMBean kmb) {
      if (kmb != null) {
         ExecuteQueueMBean[] queueBeans = kmb.getExecuteQueues();
         int i = queueBeans.length;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            ExecuteQueueMBean queueBean = queueBeans[i];
            String name = queueBean.getName();
            if (name.startsWith("wl_bootstrap_")) {
               name = name.substring(13);
            }

            this.create(name, queueBean);
         }
      }

      if (this.DEFAULT == null) {
         ExecuteQueueMBean queueBean = new ExecuteQueueMBeanStub();
         if (kmb != null && kmb.getThreadPoolSize() > 0) {
            try {
               queueBean.setThreadCount(kmb.getThreadPoolSize());
            } catch (Throwable var6) {
            }
         }

         this.create(queueBean.getName(), queueBean);
      }

      KernelDelegator direct;
      if (Kernel.isServer()) {
         direct = new KernelDelegator("weblogic.kernel.Non-Blocking", 3);
         this.byName.put("weblogic.kernel.Non-Blocking", direct);
         this.SYSTEM = new KernelDelegator("weblogic.kernel.System", kmb.getSystemThreadPoolSize());
         this.byName.put("weblogic.kernel.System", this.SYSTEM);
         this.REJECTOR = new KernelDelegator("weblogic.Rejector", 2);
      } else {
         this.SYSTEM = this.DEFAULT;
      }

      direct = new KernelDelegator();
      this.byName.put("direct", direct);
   }

   protected WorkManager create(String name, int priority, int maxThreads, int minThreads) {
      return this.create(name, maxThreads, minThreads);
   }

   private WorkManager create(String name, int maxThreads, int minThreads) {
      int threadCount = Math.max(minThreads, maxThreads);
      return new KernelDelegator(name, threadCount);
   }

   private WorkManager create(String name, ExecuteQueueMBean eqmb) {
      WorkManagerImpl manager = new KernelDelegator(name, eqmb);
      if (!"weblogic.kernel.Default".equalsIgnoreCase(name) && !"default".equalsIgnoreCase(name)) {
         this.byName.put(name, manager);
      } else {
         this.DEFAULT = manager;
      }

      return manager;
   }

   public static WorkManager createExecuteQueue(String name, int noOfThreads) {
      ExecuteQueueMBean queueBean = new ExecuteQueueMBeanStub();

      try {
         queueBean.setThreadCount(noOfThreads);
         queueBean.setThreadsIncrease(0);
         queueBean.setThreadsMaximum(noOfThreads);
      } catch (InvalidAttributeValueException var4) {
         throw new AssertionError("Invalid ExecuteQueueMBean attributes specified for " + name);
      }

      assert SINGLETON != null;

      return SINGLETON.create(name, queueBean);
   }
}

package weblogic.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.management.configuration.KernelDebugMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.protocol.ClientEnvironment;
import weblogic.utils.StackTraceUtils;
import weblogic.work.ExecuteQueueFactory;

public final class Kernel extends KernelStatus {
   private static final String ALLOW_QUEUE_THROTTLE = "weblogic.kernel.allowQueueThrottling";
   private static final int DIRECT = 0;
   private static KernelMBean config;
   private static ExecuteThreadManager[] queues = new ExecuteThreadManager[1];
   private static final HashMap policyNameAlias = new HashMap();
   private static final ArrayList applicationQueueNames = new ArrayList();
   private static int defaultDispatchIndex;
   private static boolean isTracingEnabled = false;
   private static final boolean allowQueueThrottling = initAllowThrottleProp();

   private static final boolean initAllowThrottleProp() {
      return isApplet() ? false : Boolean.getBoolean("weblogic.kernel.allowQueueThrottling");
   }

   public static final boolean isTracingEnabled() {
      return isTracingEnabled;
   }

   public static boolean isInitialized() {
      return config != null;
   }

   public static synchronized void ensureInitialized() {
      initialized();
      if (config == null) {
         try {
            KernelMBean kmb = new KernelMBeanStub();
            initialize(kmb);
         } catch (Throwable var1) {
            throw new InternalError("error initializing kernel caused by: " + StackTraceUtils.throwable2StackTrace(var1));
         }
      }

   }

   public static synchronized void initialize(KernelMBean kmb) {
      initialized();
      if (config != null) {
         throw new AssertionError("Kernel was initialized more than once");
      } else {
         config = kmb;
         setIsConfigured();
         isTracingEnabled = config.getTracingEnabled();
         VersionInfoFactory.initialize(isServer());
         ClientEnvironment.loadEnvironment();
         policyNameAlias.put("weblogic.kernel.Default", "default");
         if (kmb.getUse81StyleExecuteQueues()) {
            if (isServer()) {
               KernelLogger.logInitializingKernelDelegator();
            }

            ExecuteQueueFactory.initialize(kmb);
         }

         defaultDispatchIndex = getDispatchPolicyIndex("weblogic.kernel.Default");
      }
   }

   public static KernelMBean getConfig() {
      if (!isServer() && !isInitialized()) {
         ensureInitialized();
      }

      return config;
   }

   public static KernelDebugMBean getDebug() {
      return config.getKernelDebug();
   }

   public static ExecuteThreadManager getExecuteThreadManager(String policyName) {
      int index = getDispatchPolicyIndex(policyName);
      return queues[index];
   }

   static ExecuteThreadManager[] getExecuteThreadManagers() {
      return queues;
   }

   public static void execute(ExecuteRequest er) {
      execute(er, false);
   }

   public static void execute(ExecuteRequest er, boolean mayThrottle) {
      queues[defaultDispatchIndex].execute(er, mayThrottle);
   }

   public static void execute(ExecuteRequest er, int policyIndex) {
      execute(er, policyIndex, false);
   }

   public static void execute(ExecuteRequest er, int policyIndex, boolean mayThrottle) {
      if (policyIndex == 0) {
         executeLocally(er);
      } else {
         queues[policyIndex].execute(er, mayThrottle);
      }

   }

   private static void executeLocally(ExecuteRequest er) {
      try {
         er.execute((ExecuteThread)null);
      } catch (ThreadDeath var2) {
         throw var2;
      } catch (ExecuteThreadManager.ShutdownError var3) {
         throw var3;
      } catch (Throwable var4) {
         if (!isApplet()) {
            KernelLogger.logExecuteFailed(var4);
         } else {
            var4.printStackTrace();
         }
      }

   }

   public static void execute(ExecuteRequest er, String policyName) {
      execute(er, policyName, false);
   }

   public static void execute(ExecuteRequest er, String policyName, boolean mayThrottle) {
      execute(er, getDispatchPolicyIndex(policyName), mayThrottle);
   }

   public static void executeIfIdle(ExecuteRequest er, int policyIndex) {
      if (policyIndex == 0 || !queues[policyIndex].executeIfIdle(er)) {
         Thread t = Thread.currentThread();
         if (t instanceof ExecuteThread) {
            ((ExecuteThread)t).execute(er);
         } else {
            try {
               er.execute((ExecuteThread)null);
            } catch (Exception var4) {
               throw new InternalError("Error executing the request on a Non-kernel Thread: " + StackTraceUtils.throwable2StackTrace(var4));
            }
         }
      }

   }

   public static void executeIfIdle(ExecuteRequest er, String policyName) {
      executeIfIdle(er, getDispatchPolicyIndex(policyName));
   }

   public static int getExecuteQueueDepth() {
      return queues[defaultDispatchIndex].getExecuteQueueDepth();
   }

   public static int getExecuteQueueDepth(int policyIndex) {
      return policyIndex == 0 ? 0 : queues[policyIndex].getExecuteQueueDepth();
   }

   public static int getDispatchPolicyIndex(String policyName) {
      if ("direct".equalsIgnoreCase(policyName)) {
         return 0;
      } else {
         int max = queues.length;

         for(int i = 1; i < max; ++i) {
            String queueName = queues[i].getName();
            if (queueName.equalsIgnoreCase(policyName) || queueName.equalsIgnoreCase((String)policyNameAlias.get(policyName))) {
               return i;
            }
         }

         ensureInitialized();
         return getDispatchPolicyIndex("weblogic.kernel.Default");
      }
   }

   public static boolean isDispatchPolicy(String policyName) {
      if ("direct".equalsIgnoreCase(policyName)) {
         return true;
      } else {
         int max = queues.length;

         for(int i = 1; i < max; ++i) {
            String queueName = queues[i].getName();
            if (queueName.equalsIgnoreCase(policyName) || queueName.equalsIgnoreCase((String)policyNameAlias.get(policyName))) {
               return true;
            }
         }

         return false;
      }
   }

   public static void addExecuteQueue(String policyName, int threadCount) {
      addInternalExecuteQueue(policyName, threadCount, 0, threadCount);
   }

   public static void addExecuteQueue(String policyName, String alias, int threadCount) {
      policyNameAlias.put(policyName, alias);
      addInternalExecuteQueue(policyName, threadCount, 0, threadCount);
   }

   public static void addExecuteQueue(String policyName, int initialThreadCount, int increment, int maxThreadCount) {
      addInternalExecuteQueue(policyName, initialThreadCount, increment, maxThreadCount);
   }

   private static void addInternalExecuteQueue(String policyName, int initialThreadCount, int increment, int maxThreadCount) {
      ExecuteQueueMBean queueBean = new ExecuteQueueMBeanStub();

      try {
         queueBean.setThreadCount(initialThreadCount);
         queueBean.setThreadsIncrease(increment);
         queueBean.setThreadsMaximum(maxThreadCount);
      } catch (InvalidAttributeValueException var6) {
         throw new AssertionError("Invalid ExecuteQueueMBean attributes specified for " + policyName);
      }

      addExecuteQueue(policyName, queueBean, false);
   }

   private static void addExecuteQueue(String policyName, ExecuteQueueMBean queueBean) {
      addExecuteQueue(policyName, queueBean, false);
   }

   public static void addExecuteQueue(String policyName, ExecuteQueueMBean queueBean, boolean isApplicationQueue) {
      if (isApplicationQueue) {
         if (!applicationQueueNames.contains(policyName)) {
            applicationQueueNames.add(policyName);
         }
      } else {
         applicationQueueNames.remove(policyName);
         applicationQueueNames.remove(policyNameAlias.get(policyName));
      }

      if (!isDispatchPolicy(policyName)) {
         Class var3 = Kernel.class;
         synchronized(Kernel.class) {
            if (!isDispatchPolicy(policyName)) {
               ExecuteThreadManager etm = new ExecuteThreadManager(policyName, queueBean);
               KernelEnvironment.getKernelEnvironment().addExecuteQueueRuntime(etm);
               ExecuteThreadManager[] tmp = new ExecuteThreadManager[queues.length + 1];
               System.arraycopy(queues, 0, tmp, 0, queues.length);
               tmp[queues.length] = etm;
               queues = tmp;
            }
         }
      }
   }

   public static void initializeExecuteQueue(ExecuteQueueMBean queueBean) {
      addExecuteQueue(queueBean.getName(), queueBean);
   }

   public static void shutdown() {
      KernelStatus.shutdown();
      Class var0 = Kernel.class;
      synchronized(Kernel.class) {
         int max = queues.length;

         for(int i = 1; i < max; ++i) {
            queues[i].shutdown();
         }

      }
   }

   public static boolean checkStuckThreads(String policyName, long maxTime) {
      boolean status = false;
      ExecuteThreadManager[] tmpQueues = getExecuteThreadManagers();
      if (policyName != null) {
         int index = getDispatchPolicyIndex(policyName);
         if (maxTime > 0L && index > 0) {
            ExecuteThreadManager etm = tmpQueues[index];
            if (etm != null) {
               ExecuteThread[] threads = etm.getStuckExecuteThreads(maxTime);
               if (threads != null && threads.length == etm.getExecuteThreadCount()) {
                  status = true;
               }
            }
         }
      }

      return status;
   }

   public static int getPendingTasksCount(String policyName) {
      int count = 0;
      ExecuteThreadManager[] tmpQueues = getExecuteThreadManagers();
      if (policyName != null) {
         int index = getDispatchPolicyIndex(policyName);
         if (index > 0) {
            ExecuteThreadManager etm = tmpQueues[index];
            if (etm != null) {
               count = etm.getPendingTasksCount();
            }
         }
      }

      return count;
   }

   public static List getApplicationDispatchPolicies() {
      return applicationQueueNames;
   }

   public static boolean isApplicationDispatchPolicy(int policy) {
      if (policy == 0) {
         return false;
      } else {
         return applicationQueueNames.contains(queues[policy].getName());
      }
   }

   private Kernel() {
   }

   static boolean isQueueThrottleAllowed() {
      return allowQueueThrottling;
   }

   public static void addDummyDefaultQueue(ExecuteThreadManager etm) {
      Class var1 = Kernel.class;
      synchronized(Kernel.class) {
         ExecuteThreadManager[] tmp = new ExecuteThreadManager[queues.length + 1];
         System.arraycopy(queues, 0, tmp, 0, queues.length);
         tmp[queues.length] = etm;
         queues = tmp;
      }
   }

   @Service
   private static class KernelTypeServiceImpl implements KernelTypeService {
      public boolean isServer() {
         return Kernel.isServer();
      }

      public boolean isApplet() {
         return Kernel.isApplet();
      }

      public void ensureInitialized() {
         Kernel.ensureInitialized();
      }
   }
}

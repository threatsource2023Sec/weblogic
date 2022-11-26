package weblogic.kernel;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ExecuteQueueRuntimeMBean;
import weblogic.management.runtime.JTATransaction;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.transaction.ClientTxHelper;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionManager;
import weblogic.utils.AssertionError;

public final class ExecuteQueueRuntime extends RuntimeMBeanDelegate implements ExecuteQueueRuntimeMBean {
   private static final long serialVersionUID = 8609236957899063575L;
   private long lastDepartureSnapshot;
   private long lastDepartureSnapshotTime;
   private long lastWaitTime;
   private final ExecuteThreadManager queue;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static void addExecuteQueueRuntime(ExecuteThreadManager etm) {
      try {
         new ExecuteQueueRuntime(etm.getName(), etm);
      } catch (ManagementException var2) {
         T3SrvrLogger.logErrorCreatingExecuteQueueRuntime(etm.getName(), var2);
      }

   }

   public ExecuteQueueRuntime() throws ManagementException {
      throw new AssertionError("constructor for JMX compliance only");
   }

   private ExecuteQueueRuntime(String name, ExecuteThreadManager etm) throws ManagementException {
      super(name);
      if ("weblogic.kernel.Default".equalsIgnoreCase(name) || "default".equalsIgnoreCase(name)) {
         ManagementService.getRuntimeAccess(kernelID).getServerRuntime().setDefaultExecuteQueueRuntime(this);
      }

      ManagementService.getRuntimeAccess(kernelID).getServerRuntime().addExecuteQueueRuntime(this);
      this.queue = etm;
      this.lastDepartureSnapshot = 0L;
      this.lastDepartureSnapshotTime = this.lastWaitTime = System.currentTimeMillis();
   }

   public weblogic.management.runtime.ExecuteThread[] getExecuteThreads() {
      ExecuteThread[] threads = this.queue.getExecuteThreads();
      int length = threads.length;
      weblogic.management.runtime.ExecuteThread[] returnThreads = new weblogic.management.runtime.ExecuteThread[length];

      for(int i = 0; i < length; ++i) {
         returnThreads[i] = new ExecuteThreadRuntime(threads[i], this);
      }

      return returnThreads;
   }

   public int getExecuteThreadCurrentIdleCount() {
      return this.queue.getIdleThreadCount();
   }

   public int getPendingRequestCurrentCount() {
      return this.queue.getExecuteQueueDepth();
   }

   public long getPendingRequestOldestTime() {
      long departures = (long)this.getServicedRequestTotalCount();
      long deltaDepartures = departures - this.lastDepartureSnapshot;
      long time = System.currentTimeMillis();
      long deltaTime = time - this.lastDepartureSnapshotTime;
      long wait = time;
      if (deltaDepartures != 0L) {
         long rate = deltaTime / deltaDepartures;
         wait = time - rate * (long)this.getPendingRequestCurrentCount();
      } else if (this.getPendingRequestCurrentCount() != 0) {
         wait = this.lastWaitTime;
      }

      this.lastDepartureSnapshot = departures;
      this.lastDepartureSnapshotTime = time;
      this.lastWaitTime = wait;
      return wait;
   }

   public int getServicedRequestTotalCount() {
      return this.queue.getExecuteQueueDepartures();
   }

   public weblogic.management.runtime.ExecuteThread[] getStuckExecuteThreads() {
      weblogic.management.runtime.ExecuteThread[] returnThreads = null;
      long stuckThreadMaxTime = getConfiguredStuckThreadMaxTime(ManagementService.getRuntimeAccess(kernelID).getServer());
      ExecuteThread[] threads = this.queue.getStuckExecuteThreads(stuckThreadMaxTime);
      if (threads != null) {
         int length = threads.length;
         returnThreads = new weblogic.management.runtime.ExecuteThread[length];

         for(int i = 0; i < length; ++i) {
            returnThreads[i] = new ExecuteThreadRuntime(threads[i], this);
         }
      }

      return returnThreads;
   }

   private static final long getConfiguredStuckThreadMaxTime(ServerMBean serverMBean) {
      return serverMBean.getOverloadProtection().getServerFailureTrigger() != null ? (long)serverMBean.getOverloadProtection().getServerFailureTrigger().getMaxStuckThreadTime() * 1000L : (long)serverMBean.getStuckThreadMaxTime() * 1000L;
   }

   public int getExecuteThreadTotalCount() {
      return this.queue.getExecuteThreadCount();
   }

   private static final class ExecuteThreadRuntime implements weblogic.management.runtime.ExecuteThread {
      private static final long serialVersionUID = -716311114026904319L;
      private final String name;
      private WebLogicObjectName queueName;
      private final String currentRequest;
      private final String lastRequest;
      private final int servicedRequests;
      private final JTATransaction transaction;
      private final String user;
      private final long startTime;
      private final boolean isStuck;
      private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      private final transient Thread executeThread;

      private ExecuteThreadRuntime(ExecuteThread thread, ExecuteQueueRuntimeMBean q) {
         this.executeThread = thread;
         Object obj = thread.getCurrentRequest();
         this.currentRequest = obj != null ? obj.toString() : null;
         this.lastRequest = null;
         this.servicedRequests = thread.getExecuteCount();
         TransactionManager tm = ClientTxHelper.getTransactionManager();
         if (tm != null && tm instanceof ServerTransactionManager) {
            this.transaction = (JTATransaction)((ServerTransactionManager)tm).getJTATransactionForThread(thread);
         } else {
            this.transaction = null;
         }

         String username = null;

         try {
            AuthenticatedSubject au = SecurityManager.getCurrentSubject(kernelID, thread);
            if (au != null) {
               username = SubjectUtils.getUsername(au);
            }
         } catch (Exception var8) {
         }

         this.user = username;
         this.name = thread.getName();

         try {
            RuntimeAccess rtAccess = ManagementService.getRuntimeAccess(kernelID);
            this.queueName = new WebLogicObjectName(rtAccess.getServerName(), "ExecuteQueueRuntime", rtAccess.getDomainName(), rtAccess.getServerName());
         } catch (MalformedObjectNameException var7) {
         }

         this.startTime = thread.getTimeStamp();
         this.isStuck = thread.getPrintStuckThreadMessage();
      }

      public String getName() {
         return this.name;
      }

      public WebLogicObjectName getExecuteQueueRuntimeName() {
         return this.queueName;
      }

      public String getWorkManagerName() {
         return null;
      }

      public String getApplicationName() {
         return null;
      }

      public String getModuleName() {
         return null;
      }

      public String getApplicationVersion() {
         return null;
      }

      public String getPartitionName() {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         return cic != null ? cic.getPartitionName() : null;
      }

      public String getCurrentRequest() {
         return this.currentRequest;
      }

      public String getLastRequest() {
         return this.lastRequest;
      }

      public int getServicedRequestTotalCount() {
         return this.servicedRequests;
      }

      public JTATransaction getTransaction() {
         return this.transaction;
      }

      public String getUser() {
         return this.user;
      }

      public boolean isIdle() {
         return this.currentRequest == null;
      }

      public boolean isStuck() {
         return this.isStuck;
      }

      public boolean isHogger() {
         return this.isStuck;
      }

      public boolean isStandby() {
         return false;
      }

      public long getCurrentRequestStartTime() {
         return this.startTime;
      }

      public Thread getExecuteThread() {
         return this.executeThread;
      }

      public String getStuckThreadActionName() {
         return "server-failure-trigger";
      }

      public long getStuckThreadActionMaxStuckThreadTime() {
         return 0L;
      }

      // $FF: synthetic method
      ExecuteThreadRuntime(ExecuteThread x0, ExecuteQueueRuntimeMBean x1, Object x2) {
         this(x0, x1);
      }
   }
}

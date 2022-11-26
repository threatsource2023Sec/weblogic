package weblogic.work;

import java.security.AccessController;
import weblogic.application.ApplicationAccessService;
import weblogic.management.runtime.ExecuteThread;
import weblogic.management.runtime.JTATransaction;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.SanitizedDiagnosticInfo;

public final class ExecuteThreadRuntime implements ExecuteThread {
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long serialVersionUID = -5035431251569572684L;
   private final String name;
   private final String currentWork;
   private final String lastRequest;
   private final int servicedRequests;
   private final JTATransaction transaction;
   private final String user;
   private final long startTime;
   private final transient weblogic.work.ExecuteThread executeThread;
   private String wmName;
   private String applicationName;
   private String moduleName;
   private String applicationVersion;
   private final boolean standby;
   private final boolean hogger;
   private final boolean stuck;
   private final String partitionName;

   public ExecuteThreadRuntime(weblogic.work.ExecuteThread thread) {
      this(thread, false);
   }

   public ExecuteThreadRuntime(weblogic.work.ExecuteThread thread, boolean includeDetails) {
      this.executeThread = thread;
      this.currentWork = this.getCurrentWorkDescription(thread, includeDetails);
      this.lastRequest = null;
      this.servicedRequests = thread.getExecuteCount();
      TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      if (tm != null && tm instanceof ServerTransactionManager) {
         this.transaction = (JTATransaction)((ServerTransactionManager)tm).getJTATransactionForThread(thread);
      } else {
         this.transaction = null;
      }

      String username = null;

      try {
         AuthenticatedSubject au = SecurityManager.getCurrentSubject(kernelID, thread);
         if (au != null) {
            username = RmiSecurityFacade.getUsername(au);
         }
      } catch (Exception var8) {
      }

      this.user = username;
      this.name = thread.getName();
      this.standby = thread.isStandby();
      this.startTime = thread.getTimeStamp();
      this.hogger = thread.isHog();
      this.stuck = thread.isStuck();
      this.partitionName = thread.getPartitionName();

      try {
         if (this.currentWork == null) {
            this.wmName = "";
         } else {
            WorkManager wm = thread.getWorkManager();
            this.wmName = wm.getName();
         }

         ClassLoader threadCL = thread.getContextClassLoader();
         ApplicationAccessService access = (ApplicationAccessService)LocatorUtilities.getService(ApplicationAccessService.class);
         this.applicationName = access.getApplicationName(threadCL);
         this.moduleName = access.getModuleName(threadCL);
         this.applicationVersion = access.getApplicationVersion(threadCL);
      } catch (Exception var7) {
         this.wmName = null;
         this.applicationName = null;
         this.moduleName = null;
         this.applicationVersion = null;
      }

   }

   public String getName() {
      return this.name;
   }

   public String getCurrentRequest() {
      return this.currentWork;
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
      return this.currentWork == null;
   }

   public boolean isStandby() {
      return this.standby;
   }

   public long getCurrentRequestStartTime() {
      return this.startTime;
   }

   public Thread getExecuteThread() {
      return this.executeThread;
   }

   public String getWorkManagerName() {
      return this.wmName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getApplicationVersion() {
      return this.applicationVersion;
   }

   public String getPartitionName() {
      return this.executeThread != null ? this.executeThread.getPartitionName() : this.partitionName;
   }

   public boolean isHogger() {
      return this.executeThread != null ? this.executeThread.isHog() : this.hogger;
   }

   public boolean isStuck() {
      return this.executeThread != null ? this.executeThread.isStuck() : this.stuck;
   }

   public String getStuckThreadActionName() {
      StuckThreadAction stuckThreadAction = this.executeThread.getStuckThreadAction();
      return stuckThreadAction != null ? stuckThreadAction.getName() : null;
   }

   public long getStuckThreadActionMaxStuckThreadTime() {
      StuckThreadAction stuckThreadAction = this.executeThread.getStuckThreadAction();
      return stuckThreadAction != null ? stuckThreadAction.getMaxStuckTime() : 0L;
   }

   private String getCurrentWorkDescription(weblogic.work.ExecuteThread thread, boolean includeDetails) {
      String workDescription = null;
      Object obj = thread.getCurrentWork();
      if (obj != null) {
         if (obj instanceof SanitizedDiagnosticInfo) {
            SanitizedDiagnosticInfo sdi = (SanitizedDiagnosticInfo)obj;
            workDescription = includeDetails ? sdi.getSanitizedDescriptionVerbose() : sdi.getSanitizedDescription();
         } else {
            workDescription = obj.toString();
         }
      }

      return workDescription;
   }
}

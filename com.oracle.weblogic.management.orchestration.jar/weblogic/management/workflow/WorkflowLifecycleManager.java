package weblogic.management.workflow;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.KernelStatus;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.workflow.internal.OrchestrationLogger;
import weblogic.management.workflow.internal.Workflow;
import weblogic.management.workflow.internal.WorkflowBuilderImpl;
import weblogic.management.workflow.internal.WorkflowProgressImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.utils.StringUtils;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@Singleton
public class WorkflowLifecycleManager {
   private static final int MAX_STANDARD_ID = 2147483646;
   private static String WORK_MANAGER_NAME = "WorkflowLifecycleWorkManager";
   private static int WORK_MANAGER_MIN_THREADS = 0;
   private static int WORK_MANAGER_MAX_THREADS = 6;
   private static String ID_FILE_NAME = "idFile.txt";
   private volatile WorkManager wm;
   private final File baseSaveDir;
   private final WorkflowProgressStore workflowProgressStore;
   private int lastId;
   private volatile boolean blocked;
   private static Boolean isServer = false;

   protected WorkflowLifecycleManager(File baseSaveDir) {
      this.workflowProgressStore = new WorkflowProgressStore();
      this.lastId = 0;
      this.blocked = true;
      this.baseSaveDir = baseSaveDir;
      isServer = KernelStatus.isServer();
      boolean isManagedServer = false;
      if (isServer) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         if (!runtimeAccess.isAdminServer()) {
            isManagedServer = true;
         }
      }

      if (!isManagedServer) {
         this.loadWorkflowsFromDiskAndCreateDomainListener();
         this.blocked = false;
      }

   }

   void loadWorkflowsFromDiskAndCreateDomainListener() {
      if (this.baseSaveDir.exists()) {
         this.loadWorkflowsFromDirectory("DOMAIN", this.baseSaveDir);
      } else if (!this.baseSaveDir.mkdirs()) {
         String path = null;

         try {
            path = this.baseSaveDir.getCanonicalPath();
         } catch (IOException var4) {
            path = "" + this.baseSaveDir;
         }

         OrchestrationLogger.logErrorCreatingWorkflowDirectory(path);
      }

      if (isServer) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         DomainMBean domainMBean = runtimeAccess.getDomain();
         this.createDomainMBeanListener(domainMBean);
      }

   }

   private synchronized void createDomainMBeanListener(DomainMBean domainMBean) {
      domainMBean.addBeanUpdateListener(new WorkflowDomainBeanUpdateListener(this));
   }

   private void loadWorkflowsFromDirectory(String partitionName, File baseDir) {
      if (baseDir.exists()) {
         File[] serviceDirs = baseDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
               return pathname.isDirectory();
            }
         });
         List wfDirs = new ArrayList();
         File[] var5 = serviceDirs;
         int var6 = serviceDirs.length;

         File className;
         for(int var7 = 0; var7 < var6; ++var7) {
            className = var5[var7];
            File[] wfds = className.listFiles(new FileFilter() {
               public boolean accept(File f) {
                  return f.isDirectory() && f.getName().matches("wf\\d+");
               }
            });
            wfDirs.addAll(Arrays.asList(wfds));
         }

         Iterator var17 = wfDirs.iterator();

         while(var17.hasNext()) {
            File wfDir = (File)var17.next();

            try {
               WorkflowProgressImpl workflowProgress = null;
               className = null;

               try {
                  String className = WorkflowProgressImpl.getPersistenceClassName(wfDir, "" + wfDir);
                  Object o = Class.forName(className).getConstructor().newInstance();
                  workflowProgress = (WorkflowProgressImpl)o;
               } catch (Exception var14) {
                  OrchestrationLogger.logErrorLoadingProgressObject(className, "" + wfDir, var14.getMessage());
               } finally {
                  if (workflowProgress == null) {
                     workflowProgress = new WorkflowProgressImpl();
                  }

               }

               workflowProgress.initializeFromStore(wfDir);
               this.workflowProgressStore.put(partitionName, workflowProgress.getWorkflowId(), workflowProgress);
            } catch (RuntimeException var16) {
               OrchestrationLogger.logErrorInitializingProgressObject("" + wfDir, var16);
            }
         }
      }

   }

   public WorkflowLifecycleManager() {
      this(new File(DomainDir.getOrchestrationWorkflowDir()));
   }

   void start(boolean blocked) throws ServiceFailureException {
      this.blocked = blocked;
      if (!blocked) {
         Iterator var2 = this.workflowProgressStore.getAllWorkflows().iterator();

         while(var2.hasNext()) {
            WorkflowProgress workflowProgress = (WorkflowProgress)var2.next();
            if (workflowProgress instanceof WorkflowProgressImpl) {
               WorkflowProgressImpl progressImpl = (WorkflowProgressImpl)workflowProgress;
               if (progressImpl.isActive() && !progressImpl.isExecuted()) {
                  progressImpl.resume(this.getWorkManager());
               }
            }
         }
      }

   }

   void setBlocked(boolean blocked) {
      this.blocked = blocked;
   }

   private File constructSaveDir(String id, String serviceName) {
      return this.constructSaveDir(this.baseSaveDir, id, serviceName);
   }

   private File constructSaveDir(File baseDir, String id, String serviceName) {
      if (serviceName != null && !serviceName.trim().isEmpty()) {
         serviceName = serviceName.trim();
         if (serviceName.length() > 64) {
            serviceName = serviceName.length() + serviceName.substring(serviceName.length() - 60);
         }

         StringBuilder b = new StringBuilder();
         char[] var5 = serviceName.toCharArray();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            char c = var5[var7];
            if (Character.isLetterOrDigit(c)) {
               b.append(c);
            } else if (Character.isWhitespace(c)) {
               b.append('_');
            } else {
               b.append('-');
            }
         }

         serviceName = b.toString();
      } else {
         serviceName = "default";
      }

      return new File(new File(baseDir, serviceName), id);
   }

   public WorkflowProgress startWorkflow(String partitionName, WorkflowBuilder builder, String serviceName) throws StateInjectionException {
      WorkflowProgressImpl progressObject = null;
      WorkflowProgress result = this.startWorkflow(partitionName, builder, serviceName, (WorkflowProgressImpl)progressObject);
      return result;
   }

   public WorkflowProgress startWorkflow(WorkflowBuilder builder, String serviceName) throws StateInjectionException {
      WorkflowProgressImpl progressObject = null;
      WorkflowProgress result = this.startWorkflow("DOMAIN", builder, serviceName, (WorkflowProgressImpl)progressObject);
      return result;
   }

   public WorkflowProgress startWorkflow(String partitionName, WorkflowBuilder builder, String serviceName, WorkflowProgressImpl progressObject) throws StateInjectionException {
      Objects.requireNonNull(partitionName);
      File baseDir = null;
      if (partitionName.equals("DOMAIN")) {
         baseDir = this.baseSaveDir;
      } else {
         baseDir = MTUtils.getBaseDirForPartition(partitionName);
      }

      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         WorkflowBuilderImpl bld = (WorkflowBuilderImpl)builder;
         String id = this.reserveFreeId();
         Workflow wf = bld.toWorkflow(id, this.constructSaveDir(baseDir, id, serviceName));
         Set sharedStateNames = wf.listAllUnsatisfiedSharedStateIds();
         if (!sharedStateNames.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            String name;
            for(Iterator var11 = sharedStateNames.iterator(); var11.hasNext(); sb.append(name)) {
               name = (String)var11.next();
               if (sb.length() > 0) {
                  sb.append(", ");
               }
            }

            throw new StateInjectionException("Can not execute workflow. Not all required shared state objects are defined. (" + sb.toString() + ")");
         } else {
            WorkflowProgressImpl result = null;
            if (progressObject != null) {
               result = progressObject;
            } else {
               result = new WorkflowProgressImpl();
            }

            result.initialize(wf, serviceName, bld.getMeta());

            try {
               result.execute(this.getWorkManager());
            } catch (RuntimeException var14) {
               OrchestrationLogger.logErrorStartingWorkflow(id, var14);

               try {
                  result.deleteFiles();
               } catch (IOException var13) {
                  OrchestrationLogger.logFileDeleteFail(id, var13);
               }

               throw var14;
            }

            this.workflowProgressStore.put(partitionName, result.getWorkflowId(), result);
            return result;
         }
      }
   }

   public WorkflowProgress initWorkflow(WorkflowBuilder builder, String serviceName) throws StateInjectionException {
      WorkflowProgressImpl progressObject = null;
      WorkflowProgress result = this.initWorkflow(builder, serviceName, (WorkflowProgressImpl)progressObject);
      return result;
   }

   public WorkflowProgress initWorkflow(WorkflowBuilder builder, String serviceName, WorkflowProgressImpl progressObject) throws StateInjectionException {
      return this.initWorkflow("DOMAIN", builder, serviceName, progressObject);
   }

   public WorkflowProgress initWorkflow(String partitionName, WorkflowBuilder builder, String serviceName, WorkflowProgressImpl progressObject) throws StateInjectionException {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         Objects.requireNonNull(partitionName);
         File baseDir = null;
         if (partitionName.equals("DOMAIN")) {
            baseDir = this.baseSaveDir;
         } else {
            baseDir = MTUtils.getBaseDirForPartition(partitionName);
         }

         WorkflowBuilderImpl bld = (WorkflowBuilderImpl)builder;
         String id = this.reserveFreeId();
         Workflow wf = bld.toWorkflow(id, this.constructSaveDir(baseDir, id, serviceName));
         Set sharedStateNames = wf.listAllUnsatisfiedSharedStateIds();
         if (!sharedStateNames.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            String name;
            for(Iterator var11 = sharedStateNames.iterator(); var11.hasNext(); sb.append(name)) {
               name = (String)var11.next();
               if (sb.length() > 0) {
                  sb.append(", ");
               }
            }

            throw new StateInjectionException("Can not execute workflow. Not all required shared state objects are defined. (" + sb.toString() + ")");
         } else {
            WorkflowProgressImpl result = null;
            if (progressObject != null) {
               result = progressObject;
            } else {
               result = new WorkflowProgressImpl();
            }

            result.initialize(wf, serviceName, bld.getMeta());
            this.workflowProgressStore.put(partitionName, result.getWorkflowId(), result);
            return result;
         }
      }
   }

   public void executeWorkflow(WorkflowProgress progress) {
      if (progress != null && progress.canResume()) {
         ((WorkflowProgressImpl)progress).execute(this.getWorkManager());
      } else {
         throw new IllegalArgumentException("Can not continue provided workflow.");
      }
   }

   public void revertWorkflow(WorkflowProgress progress) {
      if (progress != null && progress.canResume()) {
         ((WorkflowProgressImpl)progress).revert(this.getWorkManager());
      } else {
         throw new IllegalArgumentException("Can not revert provided workflow.");
      }
   }

   public WorkflowProgress getWorkflowProgress(String partitionName, String id) {
      if (partitionName != null && !partitionName.isEmpty() && id != null && !id.isEmpty()) {
         return partitionName.equals("DOMAIN") ? this.workflowProgressStore.get(id) : this.workflowProgressStore.get(partitionName, id);
      } else {
         return null;
      }
   }

   public WorkflowProgress getWorkflowProgress(String id) {
      return id != null && !id.isEmpty() ? this.workflowProgressStore.get(id) : null;
   }

   public List list(String partitionName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         return partitionName != null && !partitionName.isEmpty() ? new ArrayList(this.workflowProgressStore.values(partitionName)) : null;
      }
   }

   public List list() {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         Collection progressCollection = this.workflowProgressStore.values("DOMAIN");
         return new ArrayList(progressCollection);
      }
   }

   public List getActiveWorkflows() {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Iterator var2 = this.workflowProgressStore.values("DOMAIN").iterator();

         while(var2.hasNext()) {
            WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var2.next();
            if (workflowProgress.isActive()) {
               result.add(workflowProgress);
            }
         }

         return result;
      }
   }

   public WorkflowProgress lookupActiveWorkflow(String partitionName, String serviceName, String workflowId) {
      if (partitionName != null && !partitionName.isEmpty() && serviceName != null && !serviceName.isEmpty() && workflowId != null && !workflowId.isEmpty()) {
         if (this.blocked) {
            throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
         } else {
            WorkflowProgress result = null;
            WorkflowProgress progress = null;
            if (partitionName.equals("DOMAIN")) {
               progress = this.workflowProgressStore.get(workflowId);
            } else {
               progress = this.workflowProgressStore.get(partitionName, workflowId);
            }

            if (progress != null && progress.isActive() && StringUtils.strcmp(serviceName, progress.getServiceName())) {
               result = progress;
            }

            return result;
         }
      } else {
         return null;
      }
   }

   public WorkflowProgress lookupActiveWorkflow(String serviceName, String workflowId) {
      return this.lookupActiveWorkflow("DOMAIN", serviceName, workflowId);
   }

   public List getActiveWorkflows(String partitionName, String serviceName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Collection progressCollection = this.workflowProgressStore.values(partitionName);
         if (progressCollection != null) {
            Iterator var5 = progressCollection.iterator();

            while(var5.hasNext()) {
               WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var5.next();
               if (workflowProgress.isActive() && StringUtils.strcmp(serviceName, workflowProgress.getServiceName())) {
                  result.add(workflowProgress);
               }
            }
         }

         return result;
      }
   }

   public List getActiveWorkflows(String serviceName) {
      return this.getActiveWorkflows("DOMAIN", serviceName);
   }

   public List getInactiveWorkflows() {
      return this.getInactiveWorkflowsForPartitionAndOptionalServiceName("DOMAIN", (String)null);
   }

   public List getInactiveWorkflows(String partitionName, String serviceName) {
      return this.getInactiveWorkflowsForPartitionAndOptionalServiceName(partitionName, serviceName);
   }

   public List getInactiveWorkflows(String serviceName) {
      return this.getInactiveWorkflows("DOMAIN", serviceName);
   }

   private List getInactiveWorkflowsForPartitionAndOptionalServiceName(String partitionName, String serviceName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Iterator var4 = this.workflowProgressStore.values(partitionName).iterator();

         while(var4.hasNext()) {
            WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var4.next();
            if (!workflowProgress.isActive()) {
               if (serviceName == null) {
                  result.add(workflowProgress);
               } else if (StringUtils.strcmp(serviceName, workflowProgress.getServiceName())) {
                  result.add(workflowProgress);
               }
            }
         }

         return result;
      }
   }

   public WorkflowProgress lookupInactiveWorkflow(String partitionName, String serviceName, String workflowId) {
      if (partitionName != null && !partitionName.isEmpty() && serviceName != null && !serviceName.isEmpty() && workflowId != null && !workflowId.isEmpty()) {
         if (this.blocked) {
            throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
         } else {
            WorkflowProgress result = null;
            WorkflowProgress progress = null;
            if (partitionName.equals("DOMAIN")) {
               progress = this.workflowProgressStore.get(workflowId);
            } else {
               progress = this.workflowProgressStore.get(partitionName, workflowId);
            }

            if (progress != null && !progress.isActive() && StringUtils.strcmp(serviceName, progress.getServiceName())) {
               result = progress;
            }

            return result;
         }
      } else {
         return null;
      }
   }

   public WorkflowProgress lookupInactiveWorkflow(String serviceName, String workflowId) {
      return this.lookupInactiveWorkflow("DOMAIN", serviceName, workflowId);
   }

   public List getCompleteWorkflows() {
      return this.getCompleteWorkflowsForPartitionWithOptionalServiceName("DOMAIN", (String)null);
   }

   public List getCompleteWorkflows(String partitionName, String serviceName) {
      return this.getCompleteWorkflowsForPartitionWithOptionalServiceName(partitionName, serviceName);
   }

   private List getCompleteWorkflowsForPartitionWithOptionalServiceName(String partitionName, String serviceName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
      } else {
         List result = new ArrayList();
         Collection progressCollection = this.workflowProgressStore.values(partitionName);
         if (progressCollection != null) {
            Iterator var5 = this.workflowProgressStore.values(partitionName).iterator();

            while(var5.hasNext()) {
               WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var5.next();
               if (workflowProgress.isComplete()) {
                  if (serviceName == null) {
                     result.add(workflowProgress);
                  } else if (StringUtils.strcmp(serviceName, workflowProgress.getServiceName())) {
                     result.add(workflowProgress);
                  }
               }
            }
         }

         return result;
      }
   }

   public List getCompleteWorkflows(String serviceName) {
      return this.getCompleteWorkflowsForPartitionWithOptionalServiceName("DOMAIN", serviceName);
   }

   public WorkflowProgress lookupCompleteWorkflow(String partitionName, String serviceName, String workflowId) {
      if (partitionName != null && !partitionName.isEmpty() && serviceName != null && !serviceName.isEmpty() && workflowId != null && !workflowId.isEmpty()) {
         if (this.blocked) {
            throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
         } else {
            WorkflowProgress result = null;
            WorkflowProgress progress = null;
            if (partitionName.equals("DOMAIN")) {
               progress = this.workflowProgressStore.get(workflowId);
            } else {
               progress = this.workflowProgressStore.get(partitionName, workflowId);
            }

            if (progress != null && progress.isComplete() && StringUtils.strcmp(serviceName, progress.getServiceName())) {
               result = progress;
            }

            return result;
         }
      } else {
         return null;
      }
   }

   public WorkflowProgress lookupCompleteWorkflow(String serviceName, String workflowId) {
      return this.lookupCompleteWorkflow("DOMAIN", serviceName, workflowId);
   }

   public List getStoppedWorkflows() {
      return this.getStoppedWorkflowsForPartitionWithOptionalServiceName("DOMAIN", (String)null);
   }

   public List getStoppedWorkflows(String partitionName, String serviceName) {
      return this.getStoppedWorkflowsForPartitionWithOptionalServiceName(partitionName, serviceName);
   }

   private List getStoppedWorkflowsForPartitionWithOptionalServiceName(String partitionName, String serviceName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Collection progressCollection = this.workflowProgressStore.values(partitionName);
         if (progressCollection != null) {
            Iterator var5 = progressCollection.iterator();

            while(var5.hasNext()) {
               WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var5.next();
               if (workflowProgress.canResume()) {
                  if (serviceName == null) {
                     result.add(workflowProgress);
                  } else if (StringUtils.strcmp(serviceName, workflowProgress.getServiceName())) {
                     result.add(workflowProgress);
                  }
               }
            }
         }

         return result;
      }
   }

   public List getStoppedWorkflows(String serviceName) {
      return this.getStoppedWorkflowsForPartitionWithOptionalServiceName("DOMAIN", serviceName);
   }

   public WorkflowProgress lookupStoppedWorkflow(String partitionName, String serviceName, String workflowId) {
      if (partitionName != null && !partitionName.isEmpty() && serviceName != null && !serviceName.isEmpty() && workflowId != null && !workflowId.isEmpty()) {
         if (this.blocked) {
            throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
         } else {
            WorkflowProgress result = null;
            WorkflowProgress progress = null;
            if (partitionName.equals("DOMAIN")) {
               progress = this.workflowProgressStore.get(workflowId);
            } else {
               progress = this.workflowProgressStore.get(partitionName, workflowId);
            }

            if (progress != null && progress.canResume() && StringUtils.strcmp(serviceName, progress.getServiceName())) {
               result = progress;
            }

            return result;
         }
      } else {
         return null;
      }
   }

   public WorkflowProgress lookupStoppedWorkflow(String serviceName, String workflowId) {
      return this.lookupStoppedWorkflow("DOMAIN", serviceName, workflowId);
   }

   public List getAllWorkflows() {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Iterator var2 = this.workflowProgressStore.values("DOMAIN").iterator();

         while(var2.hasNext()) {
            WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var2.next();
            result.add(workflowProgress);
         }

         return result;
      }
   }

   public List getAllDomainAndPartitionWorkflows() {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         return this.workflowProgressStore.getAllWorkflows();
      }
   }

   public List getAllWorkflows(String partitionName, String serviceName) {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         List result = new ArrayList();
         Collection progressCollection = this.workflowProgressStore.values(partitionName);
         if (progressCollection != null) {
            Iterator var5 = progressCollection.iterator();

            while(var5.hasNext()) {
               WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var5.next();
               if (StringUtils.strcmp(serviceName, workflowProgress.getServiceName())) {
                  result.add(workflowProgress);
               }
            }
         }

         return result;
      }
   }

   public List getAllWorkflows(String serviceName) {
      return this.getAllWorkflows("DOMAIN", serviceName);
   }

   public WorkflowProgress lookupAllWorkflow(String partitionName, String serviceName, String workflowId) {
      if (partitionName != null && !partitionName.isEmpty() && serviceName != null && !serviceName.isEmpty() && workflowId != null && !workflowId.isEmpty()) {
         if (this.blocked) {
            throw new RuntimeException("Service is blocked. Used too early or not on admin server.");
         } else {
            WorkflowProgress result = null;
            WorkflowProgress progress = null;
            if (partitionName.equals("DOMAIN")) {
               progress = this.workflowProgressStore.get(workflowId);
            } else {
               progress = this.workflowProgressStore.get(partitionName, workflowId);
            }

            if (progress != null && StringUtils.strcmp(serviceName, progress.getServiceName())) {
               result = progress;
            }

            return result;
         }
      } else {
         return null;
      }
   }

   public WorkflowProgress lookupAllWorkflow(String serviceName, String workflowId) {
      return this.lookupAllWorkflow("DOMAIN", serviceName, workflowId);
   }

   public synchronized void deleteWorkflow(String partitionName, String id) throws NoSuchElementException, IllegalStateException, IOException {
      if (this.blocked) {
         throw new RuntimeException("Service is blocked. Used to early or not on admin server.");
      } else {
         WorkflowProgressImpl wp = this.workflowProgressStore.get(partitionName, id);
         if (wp == null) {
            throw new NoSuchElementException();
         } else if (wp.isExecuted()) {
            throw new IllegalStateException("Running workflow (" + id + ") can't be deleted.");
         } else {
            wp.deleteFiles();
            this.workflowProgressStore.remove(partitionName, id);
         }
      }
   }

   synchronized void deleteWorkflowsForPartition(String partitionName) {
      if (partitionName.equals("DOMAIN")) {
         throw new IllegalArgumentException("All Global Workflows cannot be deleted.");
      } else if (this.blocked) {
         throw new RuntimeException("Service is blocked. Workflow operations are available only on admin server.");
      } else {
         Collection progressList = this.workflowProgressStore.values(partitionName);
         Iterator var3 = progressList.iterator();

         while(var3.hasNext()) {
            WorkflowProgress wp = (WorkflowProgress)var3.next();
            Objects.requireNonNull(wp);
            String id = wp.getWorkflowId();
            Objects.requireNonNull(id);
            this.workflowProgressStore.remove(partitionName, id);
            if (wp.isRunning()) {
               wp.cancel();
            }

            WorkflowProgressImpl wpImpl = null;
            if (wp instanceof WorkflowProgressImpl) {
               wpImpl = (WorkflowProgressImpl)wp;
            }

            Objects.requireNonNull(wpImpl);

            try {
               wpImpl.deleteFiles();
            } catch (IOException var8) {
               OrchestrationLogger.logFailedToDeleteWorkflowOnPartitionDelete(partitionName, var8);
            }
         }

      }
   }

   public synchronized void deleteWorkflow(String id) throws NoSuchElementException, IllegalStateException, IOException {
      this.deleteWorkflow("DOMAIN", id);
   }

   private synchronized String reserveFreeId() {
      int nextId = ++this.lastId;
      File idFile = new File(this.baseSaveDir + File.separator + ID_FILE_NAME);
      if (idFile.exists()) {
         try {
            Scanner scanner = new Scanner(idFile);
            if (scanner.hasNextInt()) {
               nextId = scanner.nextInt();
            }

            scanner.close();
         } catch (FileNotFoundException var6) {
         }
      }

      if (nextId >= 2147483646) {
         nextId = 0;
      }

      StringBuilder var10000;
      Object[] var10002;
      String id;
      for(id = "wf" + String.format("%04d", nextId); this.workflowProgressStore.containsKey(id); id = var10000.append(String.format("%04d", var10002)).toString()) {
         var10000 = (new StringBuilder()).append("wf");
         var10002 = new Object[1];
         ++nextId;
         var10002[0] = nextId;
      }

      this.lastId = nextId++;

      try {
         if (!idFile.exists()) {
            idFile.createNewFile();
         }

         FileWriter writer = new FileWriter(idFile);
         writer.write(Integer.toString(nextId));
         writer.close();
      } catch (IOException var5) {
      }

      return id;
   }

   private WorkManager getWorkManager() {
      if (this.wm == null) {
         synchronized(this) {
            if (this.wm == null) {
               this.wm = WorkManagerFactory.getInstance().findOrCreate(WORK_MANAGER_NAME, WORK_MANAGER_MIN_THREADS, WORK_MANAGER_MAX_THREADS);
            }
         }
      }

      return this.wm;
   }
}

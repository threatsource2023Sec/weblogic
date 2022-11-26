package weblogic.management.workflow.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import weblogic.management.workflow.CommandFailedException;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.WorkflowException;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowStateChangeListener;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManager;

public class WorkflowProgressImpl implements WorkflowProgress {
   private static String META_NAME_SERVICE_NAME = "___$SERVICE$_$NAME$";
   protected Workflow workflow;
   private BasicData basicData;
   private File storeDir;
   private volatile CountDownLatch executeLatch = null;
   protected transient List listeners = Collections.synchronizedList(new ArrayList());

   public WorkflowProgressImpl() {
   }

   public WorkflowProgressImpl(Workflow workflow, String serviceName, Map meta) throws CorruptedStoreException {
      this.workflow = workflow;
      this.basicData = new BasicData(workflow.getId(), workflow.getName(), new HashMap(meta));
      this.basicData.className = this.getClass().getName();
      this.basicData.meta.put(META_NAME_SERVICE_NAME, serviceName);
      this.storeDir = workflow.getRootDirectory();
      if (!this.storeDir.exists() && !this.storeDir.mkdirs()) {
         throw new WorkflowException(OrchestrationLogger.getCanNotCreateDirectoryForWF(this.getWorkflowId(), this.storeDir.getPath()));
      } else {
         try {
            this.storeBaseData();
            workflow.getContext().storeAll();
         } catch (RuntimeException var7) {
            OrchestrationLogger.logErrorInitializingWorkflowProgress(this.getWorkflowId(), this.storeDir.getPath(), var7);

            try {
               this.deleteFiles();
            } catch (IOException var6) {
               OrchestrationLogger.logFileDeleteFail(this.getWorkflowId(), var6);
            }

            throw var7;
         }
      }
   }

   public void initializeFromStore(File storeDir) throws CorruptedStoreException {
      this.storeDir = storeDir;
      String workflowId = "" + storeDir;
      if (this.workflow != null) {
         workflowId = this.workflow.getWorkflowId();
      }

      this.basicData = loadBasicData(storeDir, workflowId);
      if (this.basicData.completeState != null && !this.canResume()) {
         this.workflow = null;
      } else {
         File file = Workflow.getStoreFileForId(storeDir, this.basicData.id);
         this.workflow = (Workflow)Workflow.load(file);
         this.workflow.setPostLoadAttributes((WorkUnit)null, file);
      }

   }

   private static BasicData loadBasicData(File storeDir, String workflowId) throws CorruptedStoreException {
      File[] files = storeDir.listFiles(new FileFilter() {
         public boolean accept(File f) {
            return f.isFile() && f.getName().endsWith("wfp");
         }
      });
      if (files.length != 1) {
         throw new CorruptedStoreException(OrchestrationLogger.getWrongStorageStructure(workflowId, storeDir.getPath()), storeDir.getPath());
      } else {
         try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[0]));
            Throwable var4 = null;

            BasicData var5;
            try {
               var5 = (BasicData)ois.readObject();
            } catch (Throwable var15) {
               var4 = var15;
               throw var15;
            } finally {
               if (ois != null) {
                  if (var4 != null) {
                     try {
                        ois.close();
                     } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                     }
                  } else {
                     ois.close();
                  }
               }

            }

            return var5;
         } catch (ClassNotFoundException | IOException var17) {
            throw new CorruptedStoreException(var17, storeDir.getPath());
         }
      }
   }

   private void storeBaseData() throws CorruptedStoreException {
      try {
         if (!this.storeDir.exists()) {
            this.storeDir.mkdirs();
         }

         File storeFile = new File(this.storeDir, this.basicData.id + "." + "wfp");
         File tmpFile = File.createTempFile(this.basicData.id + "_" + "wfp", "tmp", this.storeDir);
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmpFile));
         Throwable var4 = null;

         try {
            oos.writeObject(this.basicData);
         } catch (Throwable var17) {
            var4 = var17;
            throw var17;
         } finally {
            if (oos != null) {
               if (var4 != null) {
                  try {
                     oos.close();
                  } catch (Throwable var15) {
                     var4.addSuppressed(var15);
                  }
               } else {
                  oos.close();
               }
            }

         }

         try {
            Files.move(tmpFile.toPath(), storeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
         } catch (IOException var16) {
            throw new CorruptedStoreException(OrchestrationLogger.getCanNotMoveTmpToPermanentFile(this.getWorkflowId(), tmpFile.getPath(), storeFile.getPath()), (String)null, var16);
         }
      } catch (CorruptedStoreException var19) {
         throw var19;
      } catch (Exception var20) {
         throw new CorruptedStoreException(var20, this.storeDir.getPath());
      }
   }

   public synchronized void initialize(Workflow workflow, String serviceName, Map meta) throws CorruptedStoreException {
      this.workflow = workflow;
      this.basicData = new BasicData(workflow.getId(), workflow.getName(), new HashMap(meta));
      this.basicData.className = this.getClass().getName();
      this.basicData.meta.put(META_NAME_SERVICE_NAME, serviceName);
      this.storeDir = workflow.getRootDirectory();
      if (!this.storeDir.exists() && !this.storeDir.mkdirs()) {
         throw new WorkflowException(OrchestrationLogger.getCanNotCreateDirectoryForWF(this.getWorkflowId(), this.storeDir.getPath()));
      } else {
         try {
            this.storeBaseData();
            workflow.getContext().storeAll();
         } catch (RuntimeException var7) {
            OrchestrationLogger.logErrorInitializingWorkflowProgress(this.getWorkflowId(), this.storeDir.getPath(), var7);

            try {
               this.deleteFiles();
            } catch (IOException var6) {
               OrchestrationLogger.logFileDeleteFail(this.getWorkflowId(), var6);
            }

            throw var7;
         }
      }
   }

   public String getWorkflowId() {
      return this.basicData.id;
   }

   public String getName() {
      return this.basicData.name;
   }

   public String getServiceName() {
      return (String)this.basicData.meta.get(META_NAME_SERVICE_NAME);
   }

   public WorkflowProgress.State getState() {
      WorkflowProgress.State state = WorkflowProgress.State.INITIALIZED;
      if (this.workflow != null && this.workflow.getState() != WorkflowProgress.State.NONE) {
         state = this.workflow.getState();
      } else if (this.basicData.completeState != null) {
         state = this.basicData.completeState;
      }

      return state;
   }

   public void waitFor() throws InterruptedException {
      if (this.executeLatch != null) {
         this.executeLatch.await();
      }

   }

   private void collectShareStates(WorkUnit wu, String key, Collection result) {
      if (wu != null && key != null) {
         Serializable obj = (Serializable)wu.getShareState().get(key);
         if (obj != null) {
            result.add(obj);
         }

         if (wu instanceof Workflow) {
            Iterator var5 = ((Workflow)wu).getWorkUnits().iterator();

            while(var5.hasNext()) {
               WorkUnit swu = (WorkUnit)var5.next();
               this.collectShareStates(swu, key, result);
            }
         }

      }
   }

   public boolean isRunning() {
      return this.isActive();
   }

   public boolean canResume() {
      boolean result = false;
      if (this.basicData.completeState != null && !this.isExecuted()) {
         result = this.basicData.completeState == WorkflowProgress.State.CANCELED || this.basicData.completeState == WorkflowProgress.State.FAIL || this.basicData.completeState == WorkflowProgress.State.REVERT_FAIL || this.basicData.completeState == WorkflowProgress.State.REVERT_CANCELED;
      } else if (!this.isStarted()) {
         result = true;
      }

      return result;
   }

   public Collection getSharedState(String name) {
      Collection result = new ArrayList();
      this.collectShareStates(this.workflow, name, result);
      return result;
   }

   public Serializable getFirstSharedState(String name) {
      Collection sStates = this.getSharedState(name);
      return sStates.isEmpty() ? null : (Serializable)((List)sStates).get(0);
   }

   public Date getStartTime() {
      return this.basicData.startTime;
   }

   public boolean isStarted() {
      Date startTime = this.getStartTime();
      return startTime != null;
   }

   public Date getEndTime() {
      return this.basicData.completeTime;
   }

   public synchronized boolean isComplete() {
      return !this.isActive() && !this.canResume();
   }

   public synchronized boolean isActive() {
      boolean started = this.isStarted();
      WorkflowProgress.State state = this.basicData.completeState;
      boolean active = started && state == null;
      return active;
   }

   public synchronized boolean isExecuted() {
      return this.executeLatch != null && this.executeLatch.getCount() > 0L;
   }

   private int[] countCommands(Workflow workflow, int[] subCounts) {
      if (workflow == null) {
         if (this.basicData.completedCommandsCount > -1) {
            return new int[]{this.basicData.allCommandsCount, this.basicData.completedCommandsCount};
         }

         workflow = this.workflow;
      }

      if (subCounts == null) {
         subCounts = new int[]{0, 0};
      }

      Iterator var3 = workflow.getWorkUnits().iterator();

      while(var3.hasNext()) {
         WorkUnit workUnit = (WorkUnit)var3.next();
         if (workUnit instanceof Workflow) {
            this.countCommands((Workflow)workUnit, subCounts);
         } else {
            int var10002 = subCounts[0]++;
            if (workUnit.isComplete()) {
               var10002 = subCounts[1]++;
            }
         }
      }

      return subCounts;
   }

   public int getNumTotalCommands() {
      return this.countCommands((Workflow)null, (int[])null)[0];
   }

   public int getNumCompletedCommands() {
      return this.countCommands((Workflow)null, (int[])null)[1];
   }

   public String getProgressString() {
      int[] counts = this.countCommands((Workflow)null, (int[])null);
      String progressString = null;
      CommandFailedException cfe = this.getFailureCause();
      String failureMessage = "";
      switch (this.getState()) {
         case NONE:
         case INITIALIZED:
         case STARTED:
         case RETRY:
            progressString = OrchestrationLogger.getProgressRunning(this.getWorkflowId(), counts[1], counts[0]);
            break;
         case SUCCESS:
            progressString = OrchestrationLogger.getProgressFinished(this.getWorkflowId(), counts[0]);
            break;
         case REVERTING:
            progressString = OrchestrationLogger.getProgressReverting(this.getWorkflowId());
            break;
         case FAIL:
            cfe = this.getFailureCause();
            if (cfe != null) {
               failureMessage = cfe.getMessage();
            }

            progressString = OrchestrationLogger.getProgressFail(this.getWorkflowId(), failureMessage);
            break;
         case REVERTED:
            cfe = this.getFailureCause();
            if (cfe != null) {
               failureMessage = cfe.getMessage();
            }

            progressString = OrchestrationLogger.getProgressReverted(this.getWorkflowId(), failureMessage);
            break;
         case REVERT_FAIL:
            cfe = this.getFailureCause();
            if (cfe != null) {
               failureMessage = cfe.getMessage();
            }

            progressString = OrchestrationLogger.getProgressRevertFailed(this.getWorkflowId(), failureMessage);
            break;
         case CANCELED:
         case REVERT_CANCELED:
            progressString = OrchestrationLogger.getProgressCanceled(this.getWorkflowId());
            break;
         default:
            progressString = counts[1] + " / " + counts[0];
      }

      progressString = stripMsgcatId(progressString);
      return progressString;
   }

   private synchronized void execute(WorkManager workManager, final ProgressInfo.Operation operation) {
      if (this.basicData.completeState != null && !this.canResume()) {
         throw new WorkflowException(OrchestrationLogger.getWfAlreadyCompleted(this.getWorkflowId()));
      } else if (this.basicData.completeState == null && operation == ProgressInfo.Operation.REVERT) {
         throw new WorkflowException(OrchestrationLogger.getWfWasNeverExecuted(this.getWorkflowId()));
      } else if (this.executeLatch != null && this.executeLatch.getCount() > 0L) {
         throw new WorkflowException(OrchestrationLogger.getWfAlreadyExecuted(this.getWorkflowId()));
      } else {
         Date now = new Date();
         if (this.basicData.startTime == null) {
            this.basicData.startTime = now;
         }

         this.basicData.completeTime = null;
         this.basicData.completeState = null;
         this.basicData.completedCommandsCount = -1;
         this.basicData.globalStatusMessages.add(new StatusHistoryMessage.GlobalStatusHistoryMessage(now, this.workflow.getNextFromSequence(), operation));
         this.storeBaseData();
         this.executeLatch = new CountDownLatch(1);
         workManager.schedule(new ContextWrap(new Runnable() {
            public void run() {
               boolean var17 = false;

               try {
                  var17 = true;
                  FailureDecider.Decision decision;
                  switch (operation) {
                     case REVERT:
                        decision = WorkflowProgressImpl.this.workflow.revert();
                        break;
                     case RESUME:
                        decision = WorkflowProgressImpl.this.workflow.resume();
                        break;
                     default:
                        decision = WorkflowProgressImpl.this.workflow.execute();
                  }

                  if (decision == FailureDecider.Decision.REVERT) {
                     OrchestrationLogger.logRevertStarts(WorkflowProgressImpl.this.workflow.getWorkflowId());
                     WorkflowProgressImpl.this.basicData.globalStatusMessages.add(new StatusHistoryMessage.GlobalStatusHistoryMessage(new Date(), WorkflowProgressImpl.this.workflow.getNextFromSequence(), ProgressInfo.Operation.REVERT));
                     WorkflowProgressImpl.this.storeBaseData();
                     WorkflowProgressImpl.this.workflow.getContext().storeAll();
                     WorkflowProgressImpl.this.workflow.revert();
                     var17 = false;
                  } else {
                     var17 = false;
                  }
               } finally {
                  if (var17) {
                     try {
                        WorkflowProgress.State workflowState = WorkflowProgressImpl.this.workflow.getState();
                        if (workflowState == WorkflowProgress.State.CANCELED || workflowState == WorkflowProgress.State.REVERT_CANCELED) {
                           WorkflowProgressImpl.this.workflow.setCancel(false);
                        }

                        WorkflowProgressImpl.this.basicData.completeTime = new Date();
                        WorkflowProgressImpl.this.basicData.completeState = WorkflowProgressImpl.this.workflow.getState();
                        int[] counts = WorkflowProgressImpl.this.countCommands(WorkflowProgressImpl.this.workflow, (int[])null);
                        WorkflowProgressImpl.this.basicData.allCommandsCount = counts[0];
                        WorkflowProgressImpl.this.basicData.completedCommandsCount = counts[1];
                        WorkflowProgressImpl.this.basicData.globalStatusMessages.add(new StatusHistoryMessage.GlobalStatusHistoryMessage(WorkflowProgressImpl.this.basicData.completeTime, WorkflowProgressImpl.this.workflow.getNextFromSequence(), WorkflowProgressImpl.this.basicData.completeState));
                        WorkflowProgressImpl.this.basicData.statusHistory = WorkflowProgressImpl.this.getStatusHistory();
                        WorkflowProgressImpl.this.basicData.errors = WorkflowProgressImpl.this.getErrors();
                        WorkflowProgressImpl.this.storeBaseData();
                        WorkflowProgressImpl.this.workflow.getContext().storeAll();
                     } finally {
                        WorkflowProgressImpl.this.executeLatch.countDown();
                     }
                  }
               }

               try {
                  WorkflowProgress.State workflowStatex = WorkflowProgressImpl.this.workflow.getState();
                  if (workflowStatex == WorkflowProgress.State.CANCELED || workflowStatex == WorkflowProgress.State.REVERT_CANCELED) {
                     WorkflowProgressImpl.this.workflow.setCancel(false);
                  }

                  WorkflowProgressImpl.this.basicData.completeTime = new Date();
                  WorkflowProgressImpl.this.basicData.completeState = WorkflowProgressImpl.this.workflow.getState();
                  int[] countsx = WorkflowProgressImpl.this.countCommands(WorkflowProgressImpl.this.workflow, (int[])null);
                  WorkflowProgressImpl.this.basicData.allCommandsCount = countsx[0];
                  WorkflowProgressImpl.this.basicData.completedCommandsCount = countsx[1];
                  WorkflowProgressImpl.this.basicData.globalStatusMessages.add(new StatusHistoryMessage.GlobalStatusHistoryMessage(WorkflowProgressImpl.this.basicData.completeTime, WorkflowProgressImpl.this.workflow.getNextFromSequence(), WorkflowProgressImpl.this.basicData.completeState));
                  WorkflowProgressImpl.this.basicData.statusHistory = WorkflowProgressImpl.this.getStatusHistory();
                  WorkflowProgressImpl.this.basicData.errors = WorkflowProgressImpl.this.getErrors();
                  WorkflowProgressImpl.this.storeBaseData();
                  WorkflowProgressImpl.this.workflow.getContext().storeAll();
               } finally {
                  WorkflowProgressImpl.this.executeLatch.countDown();
               }

            }
         }));
      }
   }

   public void execute(WorkManager workManager) {
      this.execute(workManager, ProgressInfo.Operation.EXECUTE);
   }

   public void resume(WorkManager workManager) {
      this.execute(workManager, ProgressInfo.Operation.RESUME);
   }

   public void revert(WorkManager workManager) {
      this.execute(workManager, ProgressInfo.Operation.REVERT);
   }

   public void deleteFiles() throws IOException {
      if (this.storeDir.exists()) {
         recursiveDelete(this.storeDir);
         if (this.basicData.completeState != WorkflowProgress.State.DELETED) {
            WorkflowProgress.State origState = this.basicData.completeState;
            this.basicData.completeState = WorkflowProgress.State.DELETED;
            Iterator var2 = this.listeners.iterator();

            while(var2.hasNext()) {
               WorkflowStateChangeListener listener = (WorkflowStateChangeListener)var2.next();
               listener.workflowStateChanged(origState, WorkflowProgress.State.DELETED, this.getWorkflowId(), this.getWorkflowId());
            }
         }

      }
   }

   private static void recursiveDelete(File f) throws IOException {
      if (f.isDirectory()) {
         File[] var1 = f.listFiles();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            File c = var1[var3];
            recursiveDelete(c);
         }
      }

      if (!f.delete()) {
         throw new IOException(OrchestrationLogger.getCanNotDeleteFile(f.getPath()));
      }
   }

   public void registerListener(WorkflowStateChangeListener listener, boolean recursive) {
      if (listener != null && this.workflow != null) {
         this.workflow.registerListener(listener, recursive);
         this.listeners.add(listener);
      }

   }

   public void cancel() throws WorkflowException, IllegalStateException {
      if (!this.isExecuted()) {
         throw new WorkflowException(OrchestrationLogger.getCanNotCancel(this.getWorkflowId()));
      } else {
         this.workflow.setCancel(true);
      }
   }

   public CommandFailedException getFailureCause() {
      return this.workflow == null ? null : this.workflow.getFailureCause();
   }

   public String getStatusHistory() {
      if (this.workflow == null) {
         return this.basicData.statusHistory;
      } else {
         List messages = new ArrayList(this.workflow.getStatusHistory());
         messages.addAll(this.basicData.globalStatusMessages);
         Collections.sort(messages);
         StringBuilder result = new StringBuilder();
         Iterator var3 = messages.iterator();

         while(var3.hasNext()) {
            StatusHistoryMessage message = (StatusHistoryMessage)var3.next();
            result.append(message.getMessage()).append('\n');
         }

         return result.toString();
      }
   }

   public List getErrors() {
      return this.workflow == null ? this.basicData.errors : this.workflow.getAllErrors();
   }

   public String getNextExecuteStep() {
      if (this.workflow == null) {
         return null;
      } else {
         Workflow wf = this.workflow;

         while(true) {
            WorkUnit unit = wf.getNextStep();
            if (unit == null) {
               return null;
            }

            if (!(unit instanceof Workflow)) {
               return unit.getId() + " (" + unit.getCommandSimpleName() + ")";
            }

            wf = (Workflow)unit;
         }
      }
   }

   public String getNextRevertStep() {
      if (this.workflow == null) {
         return null;
      } else {
         Workflow wf = this.workflow;

         while(true) {
            WorkUnit unit = wf.getNextRevertStep();
            if (unit == null) {
               return null;
            }

            if (!(unit instanceof Workflow)) {
               return unit.getId() + " (" + unit.getCommandSimpleName() + ")";
            }

            wf = (Workflow)unit;
         }
      }
   }

   public String getMeta(String key) {
      return (String)this.basicData.meta.get(key);
   }

   public Map getMeta() {
      return Collections.unmodifiableMap(this.basicData.meta);
   }

   public String getWorkflowType() {
      return (String)this.basicData.meta.get("TYPE");
   }

   public String getWorkflowTarget() {
      return (String)this.basicData.meta.get("TARGETS");
   }

   public synchronized void setWorkflowType(String workflowType) {
      this.basicData.meta.put("TYPE", workflowType);

      try {
         this.storeBaseData();
      } catch (CorruptedStoreException var3) {
         OrchestrationLogger.logSetWorkflowTypeFail(this.getWorkflowId(), workflowType, var3);
      }

   }

   public synchronized void setWorkflowTargetType(String workflowTargetType) {
      this.basicData.meta.put("TARGET_TYPE", workflowTargetType);

      try {
         this.storeBaseData();
      } catch (CorruptedStoreException var3) {
         OrchestrationLogger.logSetWorkflowTargetTypeFail(this.getWorkflowId(), workflowTargetType, var3);
      }

   }

   public synchronized void setWorkflowTarget(String workflowTarget) {
      this.basicData.meta.put("TARGETS", workflowTarget);

      try {
         this.storeBaseData();
      } catch (CorruptedStoreException var3) {
         OrchestrationLogger.logSetWorkflowTarget(this.getWorkflowId(), workflowTarget, var3);
      }

   }

   private static String stripMsgcatId(String message) {
      if (message != null && !message.isEmpty()) {
         String cleanMessage = null;
         int index = message.indexOf(93);
         if (index > 0) {
            ++index;
            cleanMessage = message.substring(index);
         } else {
            cleanMessage = message;
         }

         return cleanMessage;
      } else {
         return message;
      }
   }

   public String toString() {
      String result = null;
      if (this.basicData != null) {
         result = "WorkflowProgressImpl[" + this.basicData.id + "-" + this.basicData.name + "]";
      } else {
         result = super.toString();
      }

      return result;
   }

   public Workflow getWorkflow() {
      return this.workflow;
   }

   public static String getPersistenceClassName(File storeDir, String workflowId) throws CorruptedStoreException {
      String className = null;
      BasicData basicData = loadBasicData(storeDir, workflowId);
      if (basicData != null) {
         className = basicData.className;
      }

      return className;
   }

   static class BasicData implements Serializable {
      private static final long serialVersionUID = 1L;
      private static final String FILE_EXTENSION = "wfp";
      private volatile Date startTime;
      private volatile Date completeTime;
      private volatile WorkflowProgress.State completeState = null;
      private volatile int completedCommandsCount = -1;
      private volatile int allCommandsCount = -1;
      private final String id;
      private final String name;
      private final Map meta;
      private final List globalStatusMessages = new ArrayList();
      private String statusHistory;
      private List errors;
      public String className = null;

      BasicData(String id, String name, Map meta) {
         this.meta = meta;
         this.id = id;
         this.name = name;
      }
   }
}

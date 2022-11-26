package weblogic.management.workflow.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import weblogic.management.workflow.CommandFailedException;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.UnsupportedCommandOperationException;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowStateChangeListener;
import weblogic.management.workflow.command.CommandCancelInterface;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.CommandResumeInterface;
import weblogic.management.workflow.command.CommandRetryInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class WorkUnit implements ProgressInfo, Serializable {
   private static final long serialVersionUID = 1L;
   protected final CommandProvider commandProvider;
   private transient boolean commandInjected = false;
   private boolean commandInitialized = false;
   private transient WorkUnit parentWorkUnit;
   private final String id;
   private final UUID validationId;
   private final FailureDecider failureDecider;
   private transient File storeFile;
   private transient WorkflowContextImpl context = new WorkflowContextImpl(this);
   private final Map sharedState = new HashMap();
   private transient Map workUnitsToStore = new IdentityHashMap();
   private volatile WorkflowProgress.State state;
   protected List operationResults;
   protected transient List listeners;
   private transient volatile boolean cancel;

   WorkUnit(String id, CommandProvider commandProvider, FailureDecider failureDecider, WorkUnit parentWorkUnit, File storeDirectory) {
      this.state = WorkflowProgress.State.NONE;
      this.operationResults = new ArrayList();
      this.listeners = Collections.synchronizedList(new ArrayList());
      this.cancel = false;
      this.parentWorkUnit = parentWorkUnit;
      this.storeFile = getStoreFileForId(storeDirectory, id);
      this.id = id;
      this.commandProvider = commandProvider;
      this.validationId = UUID.randomUUID();
      this.failureDecider = failureDecider;
   }

   public void setPostLoadAttributes(WorkUnit parentWorkUnit, File storeFile) {
      this.parentWorkUnit = parentWorkUnit;
      this.storeFile = storeFile;
   }

   final synchronized void injectIntoCommand() {
      if (!this.commandInjected) {
         (new StateInjectionSupport(this.getContext())).inject(this.commandProvider.getCommand());
         this.commandInjected = true;
      }
   }

   final synchronized void initializeCommand() {
      this.injectIntoCommand();
      if (!this.commandInitialized) {
         this.commandProvider.getCommand().initialize(this.context);
         this.commandInitialized = true;
         this.setState(WorkflowProgress.State.INITIALIZED);
      }
   }

   public synchronized FailureDecider.Decision execute() {
      OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
      if (this.getState() == WorkflowProgress.State.REVERT_FAIL) {
         return FailureDecider.Decision.PROCEED;
      } else {
         this.initializeCommand();
         this.setState(WorkflowProgress.State.STARTED);
         long startTime = System.currentTimeMillis();

         try {
            if (this.commandProvider.getCommand().execute()) {
               this.setState(WorkflowProgress.State.SUCCESS);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, true, new Date(startTime), new Date(), (Exception)null));
               return FailureDecider.Decision.PROCEED;
            } else {
               this.setState(WorkflowProgress.State.FAIL);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, false, new Date(startTime), new Date(), (Exception)null));
               FailureDecider.Decision decision = this.decideFailure();
               if (decision == FailureDecider.Decision.REVERT || decision == FailureDecider.Decision.FAIL) {
                  this.setState(WorkflowProgress.State.CAUSE_FAILURE);
               }

               OrchestrationLogger.logWorkunitFailNoException(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString());
               return decision;
            }
         } catch (Exception var6) {
            this.setState(WorkflowProgress.State.FAIL);
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, false, new Date(startTime), new Date(), var6));
            FailureDecider.Decision decision = this.decideFailure();
            if (decision == FailureDecider.Decision.REVERT || decision == FailureDecider.Decision.FAIL) {
               this.setState(WorkflowProgress.State.CAUSE_FAILURE);
            }

            if (var6 instanceof CommandFailedNoTraceException) {
               OrchestrationLogger.logWorkunitFailNoTrace(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var6.getMessage());
            } else {
               OrchestrationLogger.logWorkunitFail(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var6);
            }

            return decision;
         }
      }
   }

   public FailureDecider.Decision resume() throws UnsupportedCommandOperationException {
      if (this.getState() == WorkflowProgress.State.REVERT_FAIL) {
         return FailureDecider.Decision.PROCEED;
      } else {
         OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
         this.initializeCommand();
         this.setState(WorkflowProgress.State.STARTED);
         long startTime = System.currentTimeMillis();

         try {
            CommandInterface command = this.commandProvider.getCommand();
            boolean result;
            if (command instanceof CommandResumeInterface) {
               result = ((CommandResumeInterface)command).resume();
            } else {
               result = command.execute();
            }

            if (result) {
               this.setState(WorkflowProgress.State.SUCCESS);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RESUME, true, new Date(startTime), new Date(), (Exception)null));
               return FailureDecider.Decision.PROCEED;
            } else {
               this.setState(WorkflowProgress.State.FAIL);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RESUME, false, new Date(startTime), new Date(), (Exception)null));
               FailureDecider.Decision decision = this.decideFailure();
               if (decision == FailureDecider.Decision.REVERT || decision == FailureDecider.Decision.FAIL) {
                  this.setState(WorkflowProgress.State.CAUSE_FAILURE);
               }

               OrchestrationLogger.logWorkunitFailNoException(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationResume(), decision == null ? "null" : decision.toLocalizedString());
               return decision;
            }
         } catch (Exception var7) {
            this.setState(WorkflowProgress.State.FAIL);
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RESUME, false, new Date(startTime), new Date(), var7));
            FailureDecider.Decision decision = this.decideFailure();
            if (decision == FailureDecider.Decision.REVERT || decision == FailureDecider.Decision.FAIL) {
               this.setState(WorkflowProgress.State.CAUSE_FAILURE);
            }

            if (var7 instanceof CommandFailedNoTraceException) {
               OrchestrationLogger.logWorkunitFailNoTrace(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7.getMessage());
            } else {
               OrchestrationLogger.logWorkunitFail(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7);
            }

            return decision;
         }
      }
   }

   public FailureDecider.Decision revert() {
      CommandInterface command = this.commandProvider.getCommand();
      if (this.getState() == WorkflowProgress.State.CAUSE_FAILURE) {
         return FailureDecider.Decision.PROCEED;
      } else if (!(command instanceof CommandRevertInterface)) {
         return FailureDecider.Decision.PROCEED;
      } else {
         OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
         this.initializeCommand();
         this.setState(WorkflowProgress.State.REVERTING);
         long startTime = System.currentTimeMillis();

         try {
            if (((CommandRevertInterface)command).revert()) {
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.REVERT, true, new Date(startTime), new Date(), (Exception)null));
               return FailureDecider.Decision.PROCEED;
            } else {
               this.setState(WorkflowProgress.State.REVERT_FAIL);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.REVERT, false, new Date(startTime), new Date(), (Exception)null));
               FailureDecider.Decision decision = this.decideFailure();
               OrchestrationLogger.logWorkunitFailNoException(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationRevert(), decision == null ? "null" : decision.toLocalizedString());
               return decision;
            }
         } catch (Exception var7) {
            this.setState(WorkflowProgress.State.REVERT_FAIL);
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.REVERT, false, new Date(startTime), new Date(), var7));
            FailureDecider.Decision decision = this.decideFailure();
            if (var7 instanceof CommandFailedNoTraceException) {
               OrchestrationLogger.logWorkunitFailNoTrace(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7.getMessage());
            } else {
               OrchestrationLogger.logWorkunitFail(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7);
            }

            return decision;
         }
      }
   }

   public FailureDecider.Decision retry() {
      OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
      this.initializeCommand();
      this.setState(WorkflowProgress.State.RETRY);
      long startTime = System.currentTimeMillis();

      try {
         CommandInterface command = this.commandProvider.getCommand();
         boolean result;
         if (command instanceof CommandRetryInterface) {
            result = ((CommandRetryInterface)command).retry();
         } else {
            result = command.execute();
         }

         if (result) {
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RETRY, true, new Date(startTime), new Date(), (Exception)null));
            this.setState(WorkflowProgress.State.SUCCESS);
            return FailureDecider.Decision.PROCEED;
         } else {
            this.setState(WorkflowProgress.State.FAIL);
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RETRY, false, new Date(startTime), new Date(), (Exception)null));
            FailureDecider.Decision decision = this.decideFailure();
            if (decision == FailureDecider.Decision.REVERT || decision == FailureDecider.Decision.FAIL) {
               this.setState(WorkflowProgress.State.CAUSE_FAILURE);
            }

            OrchestrationLogger.logWorkunitFailNoException(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationRetry(), decision == null ? "null" : decision.toLocalizedString());
            return decision;
         }
      } catch (Exception var7) {
         this.setState(WorkflowProgress.State.FAIL);
         this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.RETRY, false, new Date(startTime), new Date(), var7));
         FailureDecider.Decision decision = this.decideFailure();
         if (decision == FailureDecider.Decision.REVERT) {
            this.setState(WorkflowProgress.State.CAUSE_FAILURE);
         }

         if (var7 instanceof CommandFailedNoTraceException) {
            OrchestrationLogger.logWorkunitFailNoTrace(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7.getMessage());
         } else {
            OrchestrationLogger.logWorkunitFail(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), decision == null ? "null" : decision.toLocalizedString(), var7);
         }

         return decision;
      }
   }

   public String getId() {
      return this.id;
   }

   public String getWorkflowId() {
      return this.getRootWorkflow().getId();
   }

   public WorkflowContextImpl getContext() {
      return this.context;
   }

   UUID getValidationId() {
      return this.validationId;
   }

   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
      ois.defaultReadObject();
      this.workUnitsToStore = new IdentityHashMap();
      this.storeFile = null;
      this.parentWorkUnit = null;
      this.listeners = Collections.synchronizedList(new ArrayList());
      this.cancel = false;
      this.context = new WorkflowContextImpl(this);
   }

   public Map getShareState() {
      return this.sharedState;
   }

   public Map getWorkUnitsToStore() {
      return this.workUnitsToStore;
   }

   public File getStoreFile() {
      return this.storeFile;
   }

   protected File getRootDirectory() {
      File f = this.getStoreFile();
      return f == null ? null : f.getParentFile();
   }

   public Workflow getRootWorkflow() {
      WorkUnit wu;
      for(wu = this; wu.parentWorkUnit != null; wu = wu.parentWorkUnit) {
      }

      return (Workflow)wu;
   }

   public WorkUnit getParentWorkUnit() {
      return this.parentWorkUnit;
   }

   public WorkflowProgress.State getState() {
      return this.state;
   }

   public List getOperationResults() {
      return this.operationResults;
   }

   public ProgressInfo.OperationResult getLastOperationResult() {
      return this.operationResults.isEmpty() ? null : (ProgressInfo.OperationResult)this.operationResults.get(this.operationResults.size() - 1);
   }

   public List getSubProgresses() {
      return null;
   }

   public FailureDecider getFailureDecider() {
      return this.failureDecider == null ? FailureDecider.DEFAULT_DECIDER : this.failureDecider;
   }

   public boolean isComplete() {
      return this.state == WorkflowProgress.State.FAIL || this.state == WorkflowProgress.State.SUCCESS || this.state == WorkflowProgress.State.REVERT_FAIL;
   }

   protected Set listAllUnsatisfiedSharedStateIds() {
      final Set result = new TreeSet();
      SimpleInjectionSupport resultFinder = new SimpleInjectionSupport() {
         protected Object getValue(Object target, Class targetClazz, Annotation annotation, String fieldName) throws NoSuchElementException {
            SharedState sst = (SharedState)annotation;
            String name = StateInjectionSupport.getName(sst, fieldName);

            try {
               WorkUnit.this.getContext().getSharedState(name);
            } catch (NoSuchElementException var8) {
               result.add(WorkUnit.this.commandProvider.getCommandClass() + "." + name);
            }

            throw new NoSuchElementException();
         }
      };
      resultFinder.inject((Object)null, this.commandProvider.getCommandClass(), SharedState.class);
      return result;
   }

   protected void setState(WorkflowProgress.State state) {
      if (this.state != state) {
         WorkflowProgress.State origState = this.state;
         this.state = state;
         Iterator var3 = this.listeners.iterator();

         while(var3.hasNext()) {
            WorkflowStateChangeListener listener = (WorkflowStateChangeListener)var3.next();
            listener.workflowStateChanged(origState, state, this.getWorkflowId(), this.getId());
         }
      }

   }

   public void registerListener(WorkflowStateChangeListener listener, boolean recursive) {
      if (listener != null) {
         this.listeners.add(listener);
      }

   }

   protected FailureDecider.Decision decideFailure() {
      FailureDecider.Decision result = this.getFailureDecider().decideFailure(this, this.getLastOperationResult());
      if (result == null) {
         result = FailureDecider.Decision.FAIL;
      }

      return result;
   }

   public boolean isCancel() {
      boolean isCancel = this.cancel;
      if (this.commandProvider != null) {
         CommandInterface command = this.commandProvider.getCommand();
         if (command instanceof CommandCancelInterface) {
            isCancel = ((CommandCancelInterface)command).isCancel();
         }
      }

      return isCancel;
   }

   public void setCancel(boolean cancel) throws IllegalStateException {
      this.cancel = cancel;
      if (this.commandProvider != null) {
         CommandInterface command = this.commandProvider.getCommand();
         if (command instanceof CommandCancelInterface) {
            ((CommandCancelInterface)command).setCancel(cancel);
         }
      }

   }

   public String toString() {
      return this.getClass().getSimpleName() + "[" + this.id + "]";
   }

   protected String getIdentifierForLogMessages() {
      StringBuilder result = new StringBuilder();
      String name = this.getRootWorkflow().getName();
      if (name != null && name.length() > 0) {
         result.append('[').append(name).append("] ");
      }

      result.append(this.getId()).append(' ');
      result.append('(').append(this.getCommandSimpleName()).append(')');
      return result.toString();
   }

   protected String getCommandSimpleName() {
      return this.commandProvider.getCommandClass().getSimpleName();
   }

   CommandFailedException getFailureCause() {
      Iterator var1 = this.getOperationResults().iterator();

      ProgressInfo.OperationResult operationResult;
      do {
         do {
            if (!var1.hasNext()) {
               return null;
            }

            operationResult = (ProgressInfo.OperationResult)var1.next();
         } while(operationResult.isSuccess());
      } while(operationResult.getOperation() != ProgressInfo.Operation.EXECUTE && operationResult.getOperation() != ProgressInfo.Operation.RESUME);

      if (operationResult.getException() == null) {
         return new CommandFailedException(operationResult.getStartTime(), this.getIdentifierForLogMessages(), operationResult.getOperation().toLocalizedString());
      } else {
         Exception originalException = operationResult.getException();
         CommandFailedException commandFailedException = new CommandFailedException(operationResult.getStartTime(), this.getIdentifierForLogMessages(), operationResult.getOperation().toLocalizedString(), originalException);
         if (originalException != null) {
            commandFailedException.setStackTrace(originalException.getStackTrace());
         }

         return commandFailedException;
      }
   }

   protected int findMaxUsedFromSequence() {
      int result = 0;
      Iterator var2 = this.getOperationResults().iterator();

      while(var2.hasNext()) {
         ProgressInfo.OperationResult operationResult = (ProgressInfo.OperationResult)var2.next();
         if (operationResult.getSequenceNumber() > result) {
            result = operationResult.getSequenceNumber();
         }
      }

      return result;
   }

   protected int getNextFromSequence() {
      return this.getParentWorkUnit().getNextFromSequence();
   }

   protected static File getStoreFileForId(File root, String id) {
      return new File(root, id + ".wu");
   }

   public static WorkUnit load(File file) {
      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
         Throwable var2 = null;

         WorkUnit var3;
         try {
            var3 = (WorkUnit)ois.readObject();
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (ois != null) {
               if (var2 != null) {
                  try {
                     ois.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  ois.close();
               }
            }

         }

         return var3;
      } catch (ClassNotFoundException | IOException var15) {
         throw new CorruptedStoreException(var15, file.getPath());
      }
   }
}

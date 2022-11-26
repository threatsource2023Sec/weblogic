package weblogic.management.workflow.internal;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.WorkflowProgress;

public class SerialWorkflow extends Workflow implements Serializable {
   private static final long serialVersionUID = 1L;
   private int pointer = -1;
   private FailureDecider.Decision lastDecision;
   private long startTime;

   public SerialWorkflow(String id, String name, FailureDecider failureDecider, WorkUnit parentWorkUnit, File storeDirectory) {
      super(id, name, failureDecider, parentWorkUnit, storeDirectory);
      this.lastDecision = FailureDecider.Decision.PROCEED;
      this.startTime = -1L;
   }

   public FailureDecider.Decision execute() {
      if (this.getState() == WorkflowProgress.State.SUCCESS) {
         return FailureDecider.Decision.PROCEED;
      } else {
         List workUnits = this.getWorkUnits();
         boolean resumeLast = false;
         Workflow decision;
         if (this.pointer >= 0 && this.getState() != WorkflowProgress.State.CANCELED && this.getState() != WorkflowProgress.State.REVERT_CANCELED) {
            resumeLast = true;
         } else if (this.pointer >= 0 && workUnits.size() > this.pointer && workUnits.get(this.pointer) instanceof Workflow) {
            decision = (Workflow)workUnits.get(this.pointer);
            if (decision.getState() == WorkflowProgress.State.CANCELED || decision.getState() == WorkflowProgress.State.REVERT_CANCELED) {
               resumeLast = true;
            }
         }

         if (!resumeLast && this.pointer + 1 >= workUnits.size()) {
            return FailureDecider.Decision.PROCEED;
         } else {
            if (this.startTime < 0L) {
               this.startTime = System.currentTimeMillis();
            }

            if (this.getState() != WorkflowProgress.State.RETRY) {
               this.setState(WorkflowProgress.State.STARTED);
            }

            decision = null;
            this.getContext().storeAll();

            while(this.pointer < workUnits.size()) {
               if (this.isCancel()) {
                  this.setState(WorkflowProgress.State.CANCELED);
                  this.getContext().store();
                  return FailureDecider.Decision.PROCEED;
               }

               if (!resumeLast) {
                  if (this.pointer == workUnits.size() - 1) {
                     break;
                  }

                  ++this.pointer;
               }

               this.lastDecision = FailureDecider.Decision.PROCEED;
               WorkUnit workUnit = (WorkUnit)workUnits.get(this.pointer);

               try {
                  workUnit.getContext().store();
                  this.getContext().store();
                  if (resumeLast) {
                     resumeLast = false;
                     this.lastDecision = workUnit.resume();
                  } else {
                     this.lastDecision = workUnit.execute();
                  }
               } catch (CorruptedStoreException var15) {
                  OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
                  this.lastDecision = FailureDecider.Decision.FAIL;
                  this.setState(WorkflowProgress.State.FAIL);
                  OrchestrationLogger.logWorkUnitStoreFail(this.getWorkflowId(), this.getIdentifierForLogMessages(), messageFormatter.getOperationExecute(), var15);
               } finally {
                  workUnit.getContext().store();
                  this.getContext().store();
               }

               while(this.lastDecision == FailureDecider.Decision.RETRY) {
                  try {
                     if (this.isCancel()) {
                        this.setState(WorkflowProgress.State.CANCELED);
                        FailureDecider.Decision var5 = FailureDecider.Decision.PROCEED;
                        return var5;
                     }

                     this.lastDecision = workUnit.retry();
                  } finally {
                     workUnit.getContext().store();
                     this.getContext().store();
                  }
               }

               switch (this.lastDecision) {
                  case REVERT:
                     this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, false, new Date(this.startTime)));
                     return FailureDecider.Decision.REVERT;
                  case FAIL:
                     this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, false, new Date(this.startTime)));
                     this.setState(WorkflowProgress.State.FAIL);
                     return FailureDecider.Decision.FAIL;
               }
            }

            this.setState(WorkflowProgress.State.SUCCESS);
            this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.EXECUTE, true, new Date(this.startTime)));
            this.getContext().store();
            return FailureDecider.Decision.PROCEED;
         }
      }
   }

   public FailureDecider.Decision retry() {
      this.pointer = -1;
      this.setState(WorkflowProgress.State.RETRY);
      return this.execute();
   }

   public FailureDecider.Decision revert() {
      if (this.getState() == WorkflowProgress.State.REVERTED) {
         return FailureDecider.Decision.PROCEED;
      } else {
         this.setState(WorkflowProgress.State.REVERTING);

         while(this.pointer >= 0) {
            if (this.isCancel()) {
               this.setState(WorkflowProgress.State.REVERT_CANCELED);
               this.getContext().store();
               return FailureDecider.Decision.PROCEED;
            }

            WorkUnit workUnit = (WorkUnit)this.getWorkUnits().get(this.pointer);
            workUnit.getContext().store();
            this.lastDecision = workUnit.revert();
            if (this.lastDecision != FailureDecider.Decision.PROCEED && this.lastDecision != FailureDecider.Decision.REVERT) {
               this.setState(WorkflowProgress.State.REVERT_FAIL);
               this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.REVERT, false, new Date(this.startTime)));
               this.getContext().store();
               return FailureDecider.Decision.FAIL;
            }

            --this.pointer;
            this.getContext().store();
         }

         this.setState(WorkflowProgress.State.REVERTED);
         this.operationResults.add(new ProgressInfo.OperationResult(this.getNextFromSequence(), ProgressInfo.Operation.REVERT, true, new Date(this.startTime)));
         return FailureDecider.Decision.PROCEED;
      }
   }

   public FailureDecider.Decision resume() {
      if (this.pointer >= 0 && this.getWorkUnits().size() >= this.pointer) {
         switch (this.getState()) {
            case NONE:
            case INITIALIZED:
            case RETRY:
            case STARTED:
            case CANCELED:
               return this.execute();
            case REVERTING:
            case REVERT_CANCELED:
               return this.revert();
            case SUCCESS:
               return FailureDecider.Decision.PROCEED;
            case REVERT_FAIL:
               return FailureDecider.Decision.FAIL;
            default:
               return this.decideFailure();
         }
      } else {
         return this.execute();
      }
   }

   WorkUnit getNextStep() {
      if (this.getState() == WorkflowProgress.State.SUCCESS) {
         return null;
      } else {
         boolean resumeLast = false;
         List workUnits = this.getWorkUnits();
         if (this.pointer >= 0 && this.getState() != WorkflowProgress.State.CANCELED && this.getState() != WorkflowProgress.State.REVERT_CANCELED) {
            resumeLast = true;
         } else if (this.pointer >= 0 && workUnits.size() > this.pointer && workUnits.get(this.pointer) instanceof Workflow) {
            Workflow wf = (Workflow)workUnits.get(this.pointer);
            if (wf.getState() == WorkflowProgress.State.CANCELED || wf.getState() == WorkflowProgress.State.REVERT_CANCELED) {
               resumeLast = true;
            }
         }

         int myPointer = this.pointer + 1;
         if (resumeLast) {
            myPointer = this.pointer;
         }

         return workUnits.size() <= myPointer ? null : (WorkUnit)workUnits.get(myPointer);
      }
   }

   WorkUnit getNextRevertStep() {
      if (this.getState() != WorkflowProgress.State.REVERTED && this.pointer > 0) {
         List workUnits = this.getWorkUnits();
         return workUnits.size() <= this.pointer ? null : (WorkUnit)workUnits.get(this.pointer);
      } else {
         return null;
      }
   }
}

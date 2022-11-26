package weblogic.management.workflow.internal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import weblogic.management.workflow.WorkflowProgress;

public interface ProgressInfo extends Serializable {
   WorkflowProgress.State getState();

   List getOperationResults();

   OperationResult getLastOperationResult();

   List getSubProgresses();

   String getId();

   String getWorkflowId();

   public static class OperationResult implements Serializable {
      private static final long serialVersionUID = 1L;
      private final int sequenceNumber;
      private final Operation operation;
      private final boolean success;
      private final Date startTime;
      private final Date endTime;
      private final Exception exception;

      public OperationResult(int sequenceNumber, Operation operation, boolean success, Date startTime, Date endTime, Exception exception) {
         this.sequenceNumber = sequenceNumber;
         this.operation = operation;
         this.success = success;
         this.startTime = startTime;
         this.endTime = endTime;
         this.exception = exception;
      }

      public OperationResult(int sequenceNumber, Operation operation, boolean success, Date startTime, Exception exception) {
         this(sequenceNumber, operation, success, startTime, new Date(), exception);
      }

      public OperationResult(int sequenceNumber, Operation operation, boolean success, Date startTime) {
         this(sequenceNumber, operation, success, startTime, (Exception)null);
      }

      public Operation getOperation() {
         return this.operation;
      }

      public boolean isSuccess() {
         return this.success;
      }

      public Date getStartTime() {
         return this.startTime;
      }

      public Date getEndTime() {
         return this.endTime;
      }

      public Exception getException() {
         return this.exception;
      }

      public int getSequenceNumber() {
         return this.sequenceNumber;
      }

      public String toString() {
         return this.getClass().getSimpleName() + "[" + this.operation + "-" + (this.success ? "success]" : "fail]");
      }
   }

   public static enum Operation {
      EXECUTE,
      RESUME,
      RETRY,
      REVERT;

      public String toLocalizedString() {
         OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
         switch (this) {
            case EXECUTE:
               return messageFormatter.getOperationExecute();
            case RESUME:
               return messageFormatter.getOperationResume();
            case RETRY:
               return messageFormatter.getOperationRetry();
            case REVERT:
               return messageFormatter.getOperationRevert();
            default:
               return this.toString();
         }
      }
   }
}

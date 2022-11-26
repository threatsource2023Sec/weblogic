package weblogic.management.workflow.internal;

import java.io.Serializable;
import java.util.Date;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.utils.TimeUtils;

public abstract class StatusHistoryMessage implements Comparable, Serializable {
   private final int sortIndex;
   private final Date timestamp;

   public StatusHistoryMessage(Date timestamp, int sortIndex) {
      this.sortIndex = sortIndex;
      this.timestamp = timestamp == null ? new Date() : timestamp;
   }

   public abstract String getMessage();

   public Date getTimestamp() {
      return this.timestamp;
   }

   public int compareTo(StatusHistoryMessage o) {
      long result = this.timestamp.getTime() - o.timestamp.getTime();
      if (result == 0L) {
         return this.sortIndex - o.sortIndex;
      } else {
         return result > 0L ? 1 : -1;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof StatusHistoryMessage)) {
         return false;
      } else {
         StatusHistoryMessage that = (StatusHistoryMessage)o;
         if (this.sortIndex != that.sortIndex) {
            return false;
         } else {
            if (this.timestamp != null) {
               if (!this.timestamp.equals(that.timestamp)) {
                  return false;
               }
            } else if (that.timestamp != null) {
               return false;
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int result = this.sortIndex;
      result = 31 * result + (this.timestamp != null ? this.timestamp.hashCode() : 0);
      return result;
   }

   public static class ProgressHistoryMessage extends StatusHistoryMessage {
      private final WorkUnit wu;
      private final ProgressInfo.OperationResult or;

      public ProgressHistoryMessage(WorkUnit workUnit, ProgressInfo.OperationResult operationResult) {
         super(operationResult.getStartTime(), operationResult.getSequenceNumber());
         this.wu = workUnit;
         this.or = operationResult;
      }

      public String getMessage() {
         StringBuilder result = new StringBuilder();
         String duration = "?";
         if (this.or.getStartTime() != null && this.or.getEndTime() != null) {
            long dur = this.or.getEndTime().getTime() - this.or.getStartTime().getTime();
            if (dur >= 0L) {
               duration = TimeUtils.formatDuration(dur);
            }
         }

         String primitiveName = this.wu.commandProvider == null ? "" : this.wu.commandProvider.getCommandClass().getSimpleName();
         if (this.or.isSuccess()) {
            result.append(OrchestrationLogger.getHistoryMsgRecordOK(TimeUtils.yMdhmsS.format(this.or.getStartTime()), this.or.getOperation() == null ? "null" : this.or.getOperation().toLocalizedString(), this.wu.getId(), primitiveName, duration));
         } else if (this.or.getException() == null) {
            result.append(OrchestrationLogger.getHistoryMsgRecordUnknownError(TimeUtils.yMdhmsS.format(this.or.getStartTime()), this.or.getOperation() == null ? "null" : this.or.getOperation().toLocalizedString(), this.wu.getId(), primitiveName, duration));
         } else {
            result.append(OrchestrationLogger.getHistoryMsgRecordError(TimeUtils.yMdhmsS.format(this.or.getStartTime()), this.or.getOperation() == null ? "null" : this.or.getOperation().toLocalizedString(), this.wu.getId(), primitiveName, duration, this.or.getException().toString()));
         }

         return result.toString();
      }
   }

   public static class GlobalStatusHistoryMessage extends StatusHistoryMessage implements Serializable {
      private final ProgressInfo.Operation globalOperation;
      private final WorkflowProgress.State finishState;

      public GlobalStatusHistoryMessage(Date timestamp, int sortIndex, ProgressInfo.Operation globalOperation) {
         super(timestamp, sortIndex);
         this.globalOperation = globalOperation;
         this.finishState = null;
      }

      public GlobalStatusHistoryMessage(Date timestamp, int sortIndex, WorkflowProgress.State finishState) {
         super(timestamp, sortIndex);
         this.globalOperation = null;
         this.finishState = finishState;
      }

      public String getMessage() {
         return this.globalOperation == null ? TimeUtils.yMdhmsS.format(this.getTimestamp()) + " " + this.finishState : TimeUtils.yMdhmsS.format(this.getTimestamp()) + " " + this.globalOperation.toLocalizedString().toUpperCase();
      }
   }
}

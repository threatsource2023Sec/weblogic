package weblogic.management.workflow.internal;

import java.io.Serializable;
import weblogic.management.workflow.FailurePlan;

public class FailurePlanDecider implements FailureDecider, Serializable {
   private static final long serialVersionUID = 1L;
   private final FailurePlan plan;

   public FailurePlanDecider(FailurePlan plan) {
      if (plan == null) {
         this.plan = new FailurePlan();
      } else {
         this.plan = plan;
      }

   }

   public FailureDecider.Decision decideFailure(ProgressInfo info, ProgressInfo.OperationResult operationResult) {
      if (operationResult == null) {
         operationResult = info.getLastOperationResult();
         if (operationResult == null) {
            return FailureDecider.Decision.PROCEED;
         }
      }

      if (operationResult.getOperation() == ProgressInfo.Operation.REVERT) {
         return FailureDecider.Decision.FAIL;
      } else if (this.plan.shouldRetry() && (this.plan.getNumberOfRetriesAllowed() < 0 || this.plan.getNumberOfRetriesAllowed() >= info.getOperationResults().size())) {
         long spendDuration = System.currentTimeMillis() - operationResult.getEndTime().getTime();
         if (spendDuration < 0L) {
            spendDuration = 0L;
         }

         long delayMillis = this.plan.getRetryDelayInMillis() - spendDuration;
         if (delayMillis > 0L) {
            try {
               Thread.sleep(delayMillis);
            } catch (InterruptedException var8) {
            }
         }

         return FailureDecider.Decision.RETRY;
      } else if (this.plan.shouldIgnore()) {
         return FailureDecider.Decision.PROCEED;
      } else {
         return this.plan.shouldRevert() ? FailureDecider.Decision.REVERT : FailureDecider.Decision.FAIL;
      }
   }
}

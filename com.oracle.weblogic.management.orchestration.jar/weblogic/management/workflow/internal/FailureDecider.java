package weblogic.management.workflow.internal;

import java.io.Serializable;
import weblogic.management.workflow.FailurePlan;

public interface FailureDecider extends Serializable {
   FailureDecider DEFAULT_DECIDER = new FailurePlanDecider((FailurePlan)null);

   Decision decideFailure(ProgressInfo var1, ProgressInfo.OperationResult var2);

   public static enum Decision {
      PROCEED,
      FAIL,
      RETRY,
      REVERT;

      public ProgressInfo.Operation toOperation() {
         switch (this) {
            case RETRY:
               return ProgressInfo.Operation.RETRY;
            case REVERT:
               return ProgressInfo.Operation.REVERT;
            default:
               return null;
         }
      }

      public String toLocalizedString() {
         OrchestrationMessageTextFormatter messageFormatter = OrchestrationMessageTextFormatter.getInstance();
         switch (this) {
            case RETRY:
               return messageFormatter.getDecisionRetry();
            case REVERT:
               return messageFormatter.getDecisionRevert();
            case PROCEED:
               return messageFormatter.getDecisionProceed();
            case FAIL:
               return messageFormatter.getDecisionFail();
            default:
               return this.toString();
         }
      }
   }
}

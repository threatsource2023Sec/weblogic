package weblogic.work.concurrent;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkFilter;

public interface ConcurrentWork {
   WorkFilter CONCURRENT_WORK_FILTER = new WorkFilter() {
      public boolean matches(WorkAdapter work) {
         return work instanceof ConcurrentWork;
      }
   };

   ComponentInvocationContext getSubmittingCICInSharing();

   public static class SubmittingComponentWorkFilter implements WorkFilter {
      private final String applicationId;

      public SubmittingComponentWorkFilter(String applicationId) {
         this.applicationId = applicationId;
      }

      public boolean matches(WorkAdapter work) {
         if (!(work instanceof ConcurrentWork)) {
            return false;
         } else {
            ComponentInvocationContext submittingCIC = ((ConcurrentWork)work).getSubmittingCICInSharing();
            return submittingCIC == null ? false : this.applicationId.equals(submittingCIC.getApplicationId());
         }
      }
   }
}

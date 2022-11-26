package weblogic.diagnostics.image;

import weblogic.diagnostics.type.StackTraceUtility;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;

class ImageRequester {
   private static final String IMG_PKG = "weblogic.diagnostics.image";
   Exception requestException;
   String partitionName = null;
   String partitionId = null;

   ImageRequester(Exception request, String partitionName, String partitionId) {
      this.requestException = request;
      this.partitionName = partitionName;
      this.partitionId = partitionId;
   }

   String getRequester() {
      int framesToRemove = StackTraceUtility.getMatchingFrames(this.requestException, "weblogic.diagnostics.image");
      if (framesToRemove > 0) {
         --framesToRemove;
      }

      return StackTraceUtility.removeFrames(this.requestException, framesToRemove);
   }

   String getRequesterThreadName() {
      return Thread.currentThread().getName();
   }

   String getRequesterUserId() {
      return SubjectUtils.getUsername(Security.getCurrentSubject());
   }

   String getPartitionName() {
      return this.partitionName;
   }

   String getPartitionId() {
      return this.partitionId;
   }
}

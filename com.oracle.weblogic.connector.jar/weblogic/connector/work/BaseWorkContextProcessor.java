package weblogic.connector.work;

import weblogic.connector.security.layer.WorkContextWrapper;

public abstract class BaseWorkContextProcessor implements WorkContextProcessor {
   public WorkContextProcessor.WMPreference getpreferredWM(WorkContextWrapper context) {
      return WorkContextProcessor.WMPreference.defaultWM;
   }

   public String validate(WorkContextWrapper context, WorkRuntimeMetadata work) {
      return VALIDATION_OK;
   }

   public void cleanupContext(WorkContextWrapper context, boolean executionSuccess, WorkRuntimeMetadata work) throws Exception {
   }
}

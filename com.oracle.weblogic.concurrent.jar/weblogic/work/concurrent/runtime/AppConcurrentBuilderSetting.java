package weblogic.work.concurrent.runtime;

import weblogic.work.WorkManager;
import weblogic.work.WorkManagerCollection;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;

public class AppConcurrentBuilderSetting extends ConcurrentBuilderSetting {
   private final String appId;
   private final WorkManagerCollection workManagerCollection;

   public AppConcurrentBuilderSetting(String partitionName, String appId, WorkManagerCollection workManagerCollection) {
      super(partitionName);
      this.appId = appId;
      this.workManagerCollection = workManagerCollection;
   }

   protected void doConfig(ConcurrentManagedObjectBuilder builder, String moduleId, String dispatchPolicy) {
      builder.partitionName(this.getPartitionName());
      builder.appId(this.appId);
      builder.moduleId(moduleId);
      if (dispatchPolicy != null && dispatchPolicy.trim().length() != 0) {
         WorkManager workManager = this.workManagerCollection.get(moduleId, dispatchPolicy, false);
         if (workManager != null) {
            builder.workManager(workManager);
         } else {
            ConcurrencyLogger.logWorkManagerNotFound(this.appId, dispatchPolicy);
            builder.workManager(this.workManagerCollection.getDefault());
         }
      } else {
         builder.workManager(this.workManagerCollection.getDefault());
      }

   }
}

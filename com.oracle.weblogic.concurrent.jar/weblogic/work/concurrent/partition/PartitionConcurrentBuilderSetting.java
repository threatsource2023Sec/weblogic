package weblogic.work.concurrent.partition;

import weblogic.work.SelfTuningWorkManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.runtime.ConcurrentBuilderSetting;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;

public class PartitionConcurrentBuilderSetting extends ConcurrentBuilderSetting {
   public PartitionConcurrentBuilderSetting(String partitionName) {
      super(partitionName);
   }

   protected void doConfig(ConcurrentManagedObjectBuilder builder, String moduleId, String dispatchPolicy) {
      builder.partitionName(this.getPartitionName());
      WorkManager workmanager;
      if (dispatchPolicy != null && dispatchPolicy.trim().length() != 0) {
         workmanager = SelfTuningWorkManagerFactory.getInstance().find(dispatchPolicy);
         String applicationName = workmanager.getApplicationName();
         if (applicationName != null) {
            workmanager = SelfTuningWorkManagerFactory.getInstance().getDefault();
         }
      } else {
         workmanager = SelfTuningWorkManagerFactory.getInstance().getDefault();
      }

      builder.workManager(workmanager);
   }
}

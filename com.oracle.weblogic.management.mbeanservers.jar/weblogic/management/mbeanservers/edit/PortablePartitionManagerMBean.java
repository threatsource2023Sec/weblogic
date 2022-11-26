package weblogic.management.mbeanservers.edit;

import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.internal.ImportExportPartitionTaskMBean;
import weblogic.management.mbeanservers.edit.internal.ResourceGroupMigrationTaskMBean;

public interface PortablePartitionManagerMBean extends Service {
   int STATE_NOT_STARTED = -1;
   int STATE_STARTED = 1;
   int STATE_FINISHED = 2;
   int STATE_FAILED = 3;
   String OBJECT_NAME = "com.bea:Name=PortablePartitionManager,Type=" + PortablePartitionManagerMBean.class.getName();

   ResourceGroupMigrationTaskMBean migrateResourceGroup(TargetMBean var1, TargetMBean var2, TargetMBean var3, long var4);

   ImportExportPartitionTaskMBean importPartition(String var1, String var2, Boolean var3, String var4) throws Exception;

   ImportExportPartitionTaskMBean importPartition(String var1, Boolean var2) throws Exception;

   ImportExportPartitionTaskMBean importPartition(String var1, Boolean var2, String var3) throws Exception;

   ImportExportPartitionTaskMBean importPartition(String var1, String var2, Boolean var3) throws Exception;

   ImportExportPartitionTaskMBean exportPartition(String var1, String var2, Boolean var3, String var4) throws Exception;

   ImportExportPartitionTaskMBean exportPartition(String var1, String var2, Boolean var3) throws Exception;

   ImportExportPartitionTaskMBean exportPartition(String var1, String var2) throws Exception;

   ImportExportPartitionTaskMBean exportPartition(String var1, String var2, String var3) throws Exception;

   public static enum Operation {
      EXPORT_PARTITION(1, 2, 3),
      IMPORT_PARTITION(1, 2, 3);

      public final int nextState;
      public final int nextSuccessState;
      public final int nextFailureState;

      private Operation(int nextState, int nextSuccessState, int nextFailureState) {
         this.nextState = nextState;
         this.nextSuccessState = nextSuccessState;
         this.nextFailureState = nextFailureState;
      }
   }
}

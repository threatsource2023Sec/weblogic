package weblogic.management.workflow;

import java.io.File;
import weblogic.invocation.PartitionTable;
import weblogic.management.PartitionDir;

public class MTUtils {
   public static String getPartitionOrchestrationDir(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         PartitionTable partitionTable = PartitionTable.getInstance();
         if (partitionTable == null) {
            return null;
         } else {
            String pfsRoot = partitionTable.lookupByName(partitionName).getPartitionRoot();
            if (pfsRoot == null) {
               return null;
            } else {
               PartitionDir partitionDir = new PartitionDir(pfsRoot, partitionName);
               return partitionDir != null ? partitionDir.getRootDir() + WorkflowConstants.sep + "orchestration" + WorkflowConstants.sep + "workflow" : null;
            }
         }
      }
   }

   public static File getBaseDirForPartition(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         String partitionFS = getPartitionOrchestrationDir(partitionName);
         return partitionFS != null ? new File(getPartitionOrchestrationDir(partitionName)) : null;
      }
   }
}

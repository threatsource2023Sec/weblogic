package weblogic.management.patching.commands;

import java.util.List;
import weblogic.management.workflow.command.SharedState;

public abstract class PartitionOnClusterUpdateFailoverGroupBase extends ClusterUpdateFailoverGroupBase {
   private static final long serialVersionUID = 7872177891180805403L;
   @SharedState
   public transient String partitionName;

   protected void applyFailoverGroupsWithServerUtils(ServerUtils serverUtils, String serverName, List failoverGroups) throws CommandException {
      serverUtils.applyFailoverGroups(this.partitionName, serverName, failoverGroups);
   }
}

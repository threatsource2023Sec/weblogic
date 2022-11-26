package weblogic.management.patching.commands;

import java.rmi.RemoteException;
import java.util.List;
import javax.naming.NamingException;
import weblogic.management.workflow.command.SharedState;

public abstract class PartitionOnClusterSessionHandlingBase extends ClusterSessionHandlingBase {
   private static final long serialVersionUID = 942105753138842138L;
   @SharedState
   public transient String partitionName;

   protected void applySessionHandlingSettingToServerUtils(ServerUtils serverUtils, boolean enable, String serverName, List failoverGroups, boolean disableTimeout, int sessionHandlingTimeout) throws RemoteException, NamingException {
      serverUtils.applySessionHandlingSetting(enable, this.partitionName, serverName, failoverGroups, disableTimeout, sessionHandlingTimeout);
   }
}

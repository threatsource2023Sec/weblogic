package weblogic.management.runtime;

import java.rmi.Remote;

public interface PartitionLifeCycleTaskRuntimeMBean extends TaskRuntimeMBean, Remote {
   String getOperation();

   String getPartitionName();

   String getServerName();
}

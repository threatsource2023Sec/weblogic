package weblogic.management.runtime;

import java.rmi.Remote;

public interface ResourceGroupLifeCycleTaskRuntimeMBean extends TaskRuntimeMBean, Remote {
   String getOperation();

   String getResourceGroupName();

   String getServerName();
}

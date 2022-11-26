package weblogic.management.runtime;

import java.rmi.Remote;

public interface ServerLifeCycleTaskRuntimeMBean extends TaskRuntimeMBean, ServerLifeCycleTaskStatus, Remote {
   String getServerName();

   String getOperation();
}

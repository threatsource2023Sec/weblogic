package weblogic.management.runtime;

import java.util.Properties;
import weblogic.server.ServerLifecycleException;

public interface SystemComponentLifeCycleRuntimeMBean extends RuntimeMBean, ServerStates {
   SystemComponentLifeCycleTaskRuntimeMBean start(Properties var1) throws ServerLifecycleException;

   SystemComponentLifeCycleTaskRuntimeMBean shutdown(Properties var1) throws ServerLifecycleException;

   SystemComponentLifeCycleTaskRuntimeMBean[] getTasks();

   SystemComponentLifeCycleTaskRuntimeMBean lookupTask(String var1);

   String getState();

   int getNodeManagerRestartCount();

   void setState(String var1);

   int getStateVal();

   void clearOldServerLifeCycleTaskRuntimes();

   SystemComponentLifeCycleTaskRuntimeMBean softRestart(Properties var1) throws ServerLifecycleException;
}

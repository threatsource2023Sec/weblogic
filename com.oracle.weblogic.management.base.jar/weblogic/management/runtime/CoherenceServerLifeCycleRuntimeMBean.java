package weblogic.management.runtime;

import weblogic.server.ServerLifecycleException;

/** @deprecated */
@Deprecated
public interface CoherenceServerLifeCycleRuntimeMBean extends RuntimeMBean, ServerStates {
   CoherenceServerLifeCycleTaskRuntimeMBean start() throws ServerLifecycleException;

   CoherenceServerLifeCycleTaskRuntimeMBean forceShutdown() throws ServerLifecycleException;

   CoherenceServerLifeCycleTaskRuntimeMBean[] getTasks();

   String getState();

   int getNodeManagerRestartCount();

   void setState(String var1);

   int getStateVal();

   void clearOldServerLifeCycleTaskRuntimes();
}

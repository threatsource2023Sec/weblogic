package weblogic.management.runtime;

import weblogic.management.PartitionLifeCycleException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;

public interface PartitionLifeCycleRuntimeMBean extends RuntimeMBean {
   PartitionLifeCycleTaskRuntimeMBean start() throws PartitionLifeCycleException;

   String getState();

   String getSubState();

   PartitionRuntimeMBean.State getInternalState();

   PartitionLifeCycleTaskRuntimeMBean[] getTasks();

   PartitionLifeCycleTaskRuntimeMBean lookupTask(String var1);

   ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes();

   ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String var1);

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean start(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean start(String[] var1) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean startInAdmin(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean startInAdmin(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean startInAdmin() throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean start(String var1, int var2) throws PartitionLifeCycleException, InterruptedException;

   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3, TargetMBean[] var4) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3, String[] var4) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, TargetMBean[] var3) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, String[] var3) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean shutdown(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean shutdown(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean shutdown() throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean forceShutdown(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceShutdown(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceShutdown() throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceShutdown(int var1) throws PartitionLifeCycleException, InterruptedException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2, TargetMBean[] var3) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2, String[] var3) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean suspend(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean suspend(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean suspend() throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean forceSuspend(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceSuspend(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceSuspend() throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean resume(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean resume(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean resume() throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean boot(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean boot(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean boot() throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   PartitionLifeCycleTaskRuntimeMBean halt(TargetMBean[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean halt(String[] var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean halt() throws PartitionLifeCycleException;

   void purgeTasks();

   /** @deprecated */
   @Deprecated
   String getState(ServerMBean var1) throws PartitionLifeCycleException;

   /** @deprecated */
   @Deprecated
   String getSubState(ServerMBean var1) throws PartitionLifeCycleException;

   String getState(String var1) throws PartitionLifeCycleException;

   String getSubState(String var1) throws PartitionLifeCycleException;

   PartitionLifeCycleTaskRuntimeMBean forceRestart() throws PartitionLifeCycleException;
}

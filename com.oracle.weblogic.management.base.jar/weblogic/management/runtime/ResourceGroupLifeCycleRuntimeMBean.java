package weblogic.management.runtime;

import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;

public interface ResourceGroupLifeCycleRuntimeMBean extends RuntimeMBean {
   ResourceGroupLifeCycleTaskRuntimeMBean start() throws ResourceGroupLifecycleException;

   String getState();

   ResourceGroupLifecycleOperations.RGState getInternalState();

   ResourceGroupLifeCycleTaskRuntimeMBean[] getTasks();

   ResourceGroupLifeCycleTaskRuntimeMBean lookupTask(String var1);

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean start(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean start(String[] var1) throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin() throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3) throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3, TargetMBean[] var4) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, boolean var3, String[] var4) throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, TargetMBean[] var3) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2, String[] var3) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int var1, boolean var2) throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean shutdown() throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown() throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2, TargetMBean[] var3) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2, String[] var3) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean suspend(int var1, boolean var2) throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean suspend(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean suspend(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean suspend() throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend() throws ResourceGroupLifecycleException;

   /** @deprecated */
   @Deprecated
   ResourceGroupLifeCycleTaskRuntimeMBean resume(TargetMBean[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean resume(String[] var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifeCycleTaskRuntimeMBean resume() throws ResourceGroupLifecycleException;

   void purgeTasks();

   /** @deprecated */
   @Deprecated
   String getState(ServerMBean var1) throws ResourceGroupLifecycleException;

   String getState(String var1) throws ResourceGroupLifecycleException;
}

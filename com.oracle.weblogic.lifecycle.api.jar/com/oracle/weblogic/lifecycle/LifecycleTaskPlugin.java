package com.oracle.weblogic.lifecycle;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleTaskPlugin {
   String getTaskStatus(LifecycleRuntime var1, Map var2) throws LifecycleException;

   String getTaskProgress(LifecycleRuntime var1, Map var2) throws LifecycleException;

   LifecycleTask getLifecycleTask(LifecycleRuntime var1, Map var2) throws LifecycleException;

   void cancelTask(LifecycleRuntime var1, Map var2) throws LifecycleException;

   boolean taskCompleted(LifecycleRuntime var1, LifecycleTask var2, LifecycleContext var3) throws LifecycleException;
}

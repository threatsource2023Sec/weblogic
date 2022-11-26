package com.oracle.weblogic.lifecycle;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleTaskManager {
   String getTaskStatus(String var1, String var2, Map var3) throws LifecycleException;

   String getTaskProgress(String var1, String var2, Map var3) throws LifecycleException;

   LifecycleTask getLifecycleTask(String var1, String var2, Map var3) throws LifecycleException;

   void cancelTask(String var1, String var2, Map var3) throws LifecycleException;
}

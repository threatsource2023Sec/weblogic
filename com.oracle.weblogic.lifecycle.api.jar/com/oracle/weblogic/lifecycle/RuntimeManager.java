package com.oracle.weblogic.lifecycle;

import java.util.List;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RuntimeManager {
   LifecycleRuntime createRuntime(String var1, String var2, Map var3) throws LifecycleException;

   void deleteRuntime(String var1, String var2) throws LifecycleException;

   LifecycleRuntime updateRuntime(String var1, String var2, Map var3) throws LifecycleException;

   LifecycleTask quiesceRuntime(String var1, String var2, String var3, String var4, Map var5) throws LifecycleException;

   LifecycleTask startRuntime(String var1, String var2, String var3, String var4, Map var5) throws LifecycleException;

   void registerRuntime(String var1, String var2, Map var3) throws LifecycleException;

   void unregisterRuntime(String var1, String var2) throws LifecycleException;

   LifecycleTask scaleUp(String var1, String var2, int var3, Map var4) throws LifecycleException;

   LifecycleTask scaleDown(String var1, String var2, int var3, Map var4) throws LifecycleException;

   LifecycleRuntime selectRuntime(String var1);

   LifecycleRuntime getRuntime(String var1);

   List getRuntimes(String var1);

   List getRuntimes();
}

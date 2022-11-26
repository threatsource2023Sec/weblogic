package com.oracle.weblogic.lifecycle;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class RuntimePlugin {
   public abstract void create(String var1, LifecycleContext var2) throws LifecycleException;

   public abstract void delete(String var1, LifecycleContext var2) throws LifecycleException;

   public abstract void update(String var1, LifecycleContext var2) throws LifecycleException;

   public abstract LifecycleTask scaleUp(String var1, int var2, LifecycleContext var3) throws LifecycleException;

   public abstract LifecycleTask scaleDown(String var1, int var2, LifecycleContext var3) throws LifecycleException;

   public abstract LifecycleTask quiesce(String var1, String var2, LifecycleContext var3) throws LifecycleException;

   public abstract LifecycleTask start(String var1, String var2, LifecycleContext var3) throws LifecycleException;

   public List getPartitions(String runtimeName) throws LifecycleException {
      return null;
   }
}

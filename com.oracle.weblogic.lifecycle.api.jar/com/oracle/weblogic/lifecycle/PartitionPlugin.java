package com.oracle.weblogic.lifecycle;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class PartitionPlugin {
   public abstract LifecyclePartition create(String var1, LifecycleContext var2, LifecycleRuntime var3) throws LifecycleException;

   public abstract LifecyclePartition migrate(String var1, String var2, LifecycleContext var3, LifecycleRuntime var4) throws LifecycleException;

   public abstract void delete(String var1, LifecycleContext var2, LifecycleRuntime var3) throws LifecycleException;

   public abstract LifecyclePartition update(String var1, LifecycleContext var2, LifecycleRuntime var3) throws LifecycleException;

   public abstract List quiesce(String var1, String var2, LifecycleContext var3, LifecycleRuntime var4) throws LifecycleException;

   public abstract List start(String var1, String var2, LifecycleContext var3, LifecycleRuntime var4) throws LifecycleException;

   public abstract void associate(LifecycleContext var1, LifecyclePartition var2, LifecyclePartition var3, LifecycleRuntime var4) throws LifecycleException;

   public abstract void dissociate(LifecycleContext var1, LifecyclePartition var2, LifecyclePartition var3, LifecycleRuntime var4) throws LifecycleException;

   public List restart(String partitionName, String phase, LifecycleContext ctx, LifecycleRuntime runtime) throws LifecycleException {
      return null;
   }
}

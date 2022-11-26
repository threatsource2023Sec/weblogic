package com.oracle.weblogic.lifecycle;

import java.util.List;
import java.util.Map;

public interface Environment {
   String getName();

   void addPartition(LifecyclePartition var1);

   void removePartition(String var1, String var2);

   List getPartitions();

   List getPartitions(String var1);

   void migratePartition(LifecyclePartition var1, LifecycleRuntime var2, String var3, Map var4) throws LifecycleException;

   void associate(LifecyclePartition var1, LifecyclePartition var2, Map var3) throws LifecycleException;

   void dissociate(LifecyclePartition var1, LifecyclePartition var2, Map var3) throws LifecycleException;

   List getAssociations() throws LifecycleException;

   Map quiesce(LifecyclePartition var1, String var2, Map var3) throws LifecycleException;

   Map start(LifecyclePartition var1, String var2, Map var3) throws LifecycleException;

   Map restart(LifecyclePartition var1, String var2, Map var3) throws LifecycleException;
}

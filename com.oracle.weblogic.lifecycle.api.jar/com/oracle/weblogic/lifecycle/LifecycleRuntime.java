package com.oracle.weblogic.lifecycle;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface LifecycleRuntime {
   String getRuntimeName();

   String getRuntimeType();

   void applyPartitionTemplate(File var1);

   Properties getRuntimeProperties();

   LifecyclePartition createPartition(String var1, Map var2) throws LifecycleException;

   void deletePartition(String var1, Map var2) throws LifecycleException;

   LifecyclePartition updatePartition(String var1, Map var2) throws LifecycleException;

   List getPartitions();

   LifecyclePartition getPartition(String var1);

   LifecyclePartition registerPartition(String var1, String var2) throws LifecycleException;

   void unregisterPartition(String var1) throws LifecycleException;

   void syncPartitions() throws LifecycleException;
}

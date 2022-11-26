package com.oracle.weblogic.lifecycle;

import java.util.Map;

public interface LifecyclePartition {
   String getPartitionName();

   String getPartitionType();

   String getId();

   String getRuntimeName();

   Map getPartitionProperties();
}

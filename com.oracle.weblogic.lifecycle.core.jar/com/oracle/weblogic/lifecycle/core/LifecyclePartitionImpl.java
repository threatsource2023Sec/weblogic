package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecyclePartition;
import java.util.HashMap;
import java.util.Map;

public class LifecyclePartitionImpl implements LifecyclePartition {
   private String partitionName;
   private String partitionType;
   private String id;
   private String runtimeName;
   private Map properties;

   public LifecyclePartitionImpl(String id, String partitionName, String partitionType, String runtimeName) {
      this(id, partitionName, partitionType, runtimeName, new HashMap());
   }

   public LifecyclePartitionImpl(String id, String partitionName, String partitionType, String runtimeName, Map properties) {
      this.id = id;
      this.partitionName = partitionName;
      this.partitionType = partitionType;
      this.runtimeName = runtimeName;
      this.properties = properties;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getPartitionType() {
      return this.partitionType;
   }

   public String getRuntimeName() {
      return this.runtimeName;
   }

   public Map getPartitionProperties() {
      return this.properties;
   }
}

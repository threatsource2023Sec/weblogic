package org.opensaml.core.config.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.opensaml.core.config.Configuration;

public class MapBasedConfiguration implements Configuration {
   private Map storage = new ConcurrentHashMap();

   public Object get(Class configClass, String partitionName) {
      Map partition = this.getPartition(partitionName);
      return partition.get(configClass.getName());
   }

   public void register(Class configClass, Object configuration, String partitionName) {
      Map partition = this.getPartition(partitionName);
      partition.put(configClass.getName(), configuration);
   }

   public Object deregister(Class configClass, String partitionName) {
      Map partition = this.getPartition(partitionName);
      synchronized(partition) {
         Object old = partition.get(configClass.getName());
         partition.remove(configClass.getName());
         return old;
      }
   }

   private synchronized Map getPartition(String partitionName) {
      Map partition = (Map)this.storage.get(partitionName);
      if (partition == null) {
         partition = new ConcurrentHashMap();
         this.storage.put(partitionName, partition);
      }

      return (Map)partition;
   }
}

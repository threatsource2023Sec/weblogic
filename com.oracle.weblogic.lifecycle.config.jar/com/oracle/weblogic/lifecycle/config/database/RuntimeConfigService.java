package com.oracle.weblogic.lifecycle.config.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.glassfish.hk2.configuration.api.ChildInject;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/runtimes/runtime")
public class RuntimeConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/runtimes/runtime";
   private static final String PATH_NAME_PREFIX = "lifecycle-config.runtimes";
   @Configured
   private String name;
   @Configured
   private String hostname;
   @Configured
   private String port;
   @Configured
   private String type;
   @Configured
   private Properties properties;
   @ChildInject
   private ChildIterable partitionServices;

   public String getHostname() {
      return this.hostname;
   }

   public String getName() {
      return this.name;
   }

   public String getPort() {
      return this.port;
   }

   public String getType() {
      return this.type;
   }

   public Properties getProperties() {
      return this.properties;
   }

   public Object getProperty(String key) {
      return this.properties.get(key);
   }

   public List getPartitions() {
      return ConfigUtil.toList(this.partitionServices);
   }

   public PartitionConfigService getPartitionById(String id) {
      return (PartitionConfigService)this.partitionServices.byKey(id);
   }

   public PartitionConfigService getPartitionByName(String name) {
      Iterator var2 = this.partitionServices.iterator();

      PartitionConfigService ps;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ps = (PartitionConfigService)var2.next();
      } while(!ps.getName().equals(name));

      return ps;
   }

   public PartitionConfigService createPartition(String id, String name) {
      return this.createPartition(id, name, new Properties());
   }

   public PartitionConfigService createPartition(String id, String name, Properties props) {
      Map map = new HashMap();
      map.put("id", id);
      map.put("name", name);
      map.put("properties", props);
      props.remove("name");
      props.remove("id");
      this.createPartition(map);
      return this.getPartitionById(id);
   }

   public void createPartition(Map map) {
      this.add("/lifecycle-config/runtimes/runtime/partition", PartitionConfigService.getInstanceId(this, map), map);
   }

   public void deletePartition(PartitionConfigService ps) {
      this.delete("/lifecycle-config/runtimes/runtime/partition", ps.getInstanceId());
   }

   static String getInstanceId(Map map) {
      return ConfigUtil.addWithSeparator("lifecycle-config.runtimes", (String)map.get("name"));
   }
}

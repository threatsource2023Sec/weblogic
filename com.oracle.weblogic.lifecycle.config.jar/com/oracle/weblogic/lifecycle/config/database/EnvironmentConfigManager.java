package com.oracle.weblogic.lifecycle.config.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;

@Service
public class EnvironmentConfigManager {
   @Inject
   private IterableProvider allEnvironmentServices;
   @Inject
   private LifecycleConfigManager lcm;

   public EnvironmentConfigService createEnvironment(String name) {
      Map map = new HashMap();
      map.put("name", name);
      this.createEnvironment((Map)map);
      return this.getEnvironmentByName(name);
   }

   public void createEnvironment(Map map) {
      this.lcm.add("/lifecycle-config/environments/environment", EnvironmentConfigService.getInstanceId(map), map);
   }

   public EnvironmentConfigService getOrCreateEnvironment(String name) {
      EnvironmentConfigService es = this.getEnvironmentByName(name);
      if (es == null) {
         this.createEnvironment(name);
         es = this.getEnvironmentByName(name);
      }

      return es;
   }

   public void deleteEnvironment(EnvironmentConfigService environmentService) {
      this.lcm.delete("/lifecycle-config/environments/environment", environmentService.getInstanceId());
   }

   public List getEnvironments() {
      return ConfigUtil.toList(this.allEnvironmentServices);
   }

   public EnvironmentConfigService getEnvironmentByName(String name) {
      Iterator var2 = this.allEnvironmentServices.iterator();

      EnvironmentConfigService environmentService;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         environmentService = (EnvironmentConfigService)var2.next();
      } while(!environmentService.getName().equals(name));

      return environmentService;
   }

   public PartitionRefConfigService getPartitionRef(PartitionConfigService partition) {
      Iterator var2 = this.getEnvironments().iterator();

      PartitionRefConfigService partitionRef;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         EnvironmentConfigService environment = (EnvironmentConfigService)var2.next();
         partitionRef = environment.getPartitionRefById(partition.getId());
      } while(partitionRef == null);

      return partitionRef;
   }

   public EnvironmentConfigService getReferencedEnvironment(PartitionConfigService partition) {
      Iterator var2 = this.getEnvironments().iterator();

      EnvironmentConfigService environment;
      PartitionRefConfigService partitionRef;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         environment = (EnvironmentConfigService)var2.next();
         partitionRef = environment.getPartitionRefById(partition.getId());
      } while(partitionRef == null);

      return environment;
   }
}

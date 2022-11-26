package com.oracle.weblogic.lifecycle.config.database;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/environments/environment/association")
public class AssociationConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/environments/environment/association";
   @Inject
   private IterableProvider allPartitionServices;
   @Configured
   private String partition1;
   @Configured
   private String partition2;

   public PartitionConfigService getPartition1() {
      return this.getPartition(this.partition1);
   }

   public PartitionConfigService getPartition2() {
      return this.getPartition(this.partition2);
   }

   public static String getInstanceId(EnvironmentConfigService environmentConfigService, Map map) {
      return ConfigUtil.addWithSeparator(environmentConfigService.getInstanceId(), (String)map.get("partition1"));
   }

   public EnvironmentConfigService getEnvironment() {
      return (EnvironmentConfigService)this.getServiceLocator().getService(EnvironmentConfigService.class, this.getParentInstanceId(), new Annotation[0]);
   }

   private PartitionConfigService getPartition(String id) {
      Iterator var2 = this.allPartitionServices.iterator();

      PartitionConfigService ps;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ps = (PartitionConfigService)var2.next();
      } while(!ps.getId().equals(id));

      return ps;
   }
}

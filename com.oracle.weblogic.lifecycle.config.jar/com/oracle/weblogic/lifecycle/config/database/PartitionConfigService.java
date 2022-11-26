package com.oracle.weblogic.lifecycle.config.database;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Properties;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.glassfish.hk2.configuration.api.Dynamicity;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/runtimes/runtime/partition")
public class PartitionConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/runtimes/runtime/partition";
   static final String ID = "id";
   static final String NAME = "name";
   @Configured
   private String id;
   @Configured
   private String name;
   @Configured
   private Properties properties;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(@Configured(value = "name",dynamicity = Dynamicity.FULLY_DYNAMIC) String name) {
      this.name = name;
   }

   public static String getInstanceId(RuntimeConfigService rs, Map map) {
      return ConfigUtil.addWithSeparator(rs.getInstanceId(), (String)map.get("id"));
   }

   public RuntimeConfigService getRuntime() {
      return (RuntimeConfigService)this.getServiceLocator().getService(RuntimeConfigService.class, this.getParentInstanceId(), new Annotation[0]);
   }

   public Properties getProperties() {
      return this.properties;
   }

   public Object getProperty(String key) {
      return this.properties.getProperty(key);
   }
}

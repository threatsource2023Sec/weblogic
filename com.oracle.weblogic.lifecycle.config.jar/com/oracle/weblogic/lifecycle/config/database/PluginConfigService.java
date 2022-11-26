package com.oracle.weblogic.lifecycle.config.database;

import java.util.Map;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/plugins")
public class PluginConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/plugins";
   private static final String PATH_NAME_PREFIX = "lifecycle-config.plugins";
   @Configured
   private String name;
   @Configured
   private String type;
   @Configured
   private String path;

   public void setPath(String path) {
      this.path = path;
   }

   public void setType(String type) {
      this.type = type;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPath() {
      return this.path;
   }

   public String getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   static String getInstanceId(Map map) {
      return ConfigUtil.addWithSeparator("lifecycle-config.plugins", (String)map.get("name"));
   }
}

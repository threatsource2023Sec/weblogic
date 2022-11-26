package com.oracle.weblogic.lifecycle.config.database;

import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/tenants/resources/resource")
public class TenantResourceConfigService extends ResourceConfigService {
   static final String PATH_KEY = "/lifecycle-config/tenants/resources/resource";
   private static final String PATH_NAME_PREFIX = "lifecycle-config.tenants.resources";

   public static String getInstanceId(String name) {
      return ConfigUtil.addWithSeparator("lifecycle-config.tenants.resources", name);
   }
}

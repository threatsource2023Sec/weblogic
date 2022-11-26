package com.oracle.weblogic.lifecycle.config.database;

import java.util.Map;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/tenants/tenant/service/resources/resource")
public class ServiceResourceConfigService extends ResourceConfigService {
   static final String PATH_KEY = "/lifecycle-config/tenants/tenant/service/resources/resource";

   public static String getInstanceId(ServiceConfigService ss, Map map) {
      return ConfigUtil.addWithSeparator(ss.getInstanceId(), (String)map.get("name"));
   }
}

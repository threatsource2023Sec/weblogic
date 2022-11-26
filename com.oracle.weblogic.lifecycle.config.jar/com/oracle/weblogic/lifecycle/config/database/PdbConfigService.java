package com.oracle.weblogic.lifecycle.config.database;

import java.lang.annotation.Annotation;
import java.util.Map;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/tenants/tenant/service/pdb")
public class PdbConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/tenants/tenant/service/pdb";
   @Configured
   private String id;
   @Configured
   private String name;
   @Configured
   private String pdbStatus;

   public String getName() {
      return this.name;
   }

   public String getId() {
      return this.id;
   }

   public String getPdbStatus() {
      return this.pdbStatus;
   }

   public static String getInstanceId(ServiceConfigService ss, Map map) {
      return ConfigUtil.addWithSeparator(ss.getInstanceId(), (String)map.get("id"));
   }

   public ServiceConfigService getServiceConfigService() {
      return (ServiceConfigService)this.getServiceLocator().getService(ServiceConfigService.class, this.getParentInstanceId(), new Annotation[0]);
   }
}

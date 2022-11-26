package com.oracle.weblogic.lifecycle.config.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.glassfish.hk2.configuration.api.ChildInject;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/tenants/tenant")
public class TenantConfigService extends LifecycleConfigService {
   @Configured
   private String id;
   @Configured
   private String topLevelDir;
   @Configured
   private String name;
   @ChildInject
   private ChildIterable services;
   public static final String PATH_KEY = "/lifecycle-config/tenants/tenant";
   private static final String PATH_NAME_PREFIX = "lifecycle-config.tenants";

   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setTopLevelDir(String top_level_dir) {
      this.topLevelDir = top_level_dir;
   }

   public void addTopLevelDir(String top_level_dir) {
      this.setTopLevelDir(top_level_dir);
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getTopLevelDir() {
      return this.topLevelDir;
   }

   public void createService(Map map) {
      this.add("/lifecycle-config/tenants/tenant/service", ServiceConfigService.getInstanceId(this, map), map);
   }

   public void createService(String id, String name, String serviceType, String environment_ref) {
      this.createService(this.getServiceMapData(id, name, serviceType, (String)null, environment_ref));
   }

   public void createService(String name, String serviceType, String environment_ref) {
      this.createService(this.getServiceMapData(this.getName() + this.getId(), name, serviceType, (String)null, environment_ref));
   }

   private void createService(String id, String name, String serviceType, String identityDomain, String environment_ref) {
      this.createService(this.getServiceMapData(id, name, serviceType, identityDomain, environment_ref));
   }

   private Map getServiceMapData(String id, String name, String serviceType, String identityDomain, String environment_ref) {
      Map map = new HashMap();
      map.put("id", id);
      map.put("name", name);
      map.put("serviceType", serviceType);
      map.put("environmentRef", environment_ref);
      if (identityDomain != null) {
         map.put("identityDomain", identityDomain);
      }

      return map;
   }

   public List getServices() {
      return ConfigUtil.toList(this.services);
   }

   public ServiceConfigService getServiceById(String id) {
      return (ServiceConfigService)this.services.byKey(id);
   }

   public ServiceConfigService getServiceByName(String name) {
      Iterator var2 = this.services.iterator();

      ServiceConfigService service;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         service = (ServiceConfigService)var2.next();
      } while(!service.getName().equals(name));

      return service;
   }

   public void deleteService(ServiceConfigService serviceService) {
      this.delete("/lifecycle-config/tenants/tenant/service", serviceService.getInstanceId());
   }

   public ServiceConfigService deleteService(String serviceName) {
      ServiceConfigService ss = this.getServiceByName(serviceName);
      this.deleteService(ss);
      return ss;
   }

   public static String getInstanceId(Map map) {
      return ConfigUtil.addWithSeparator("lifecycle-config.tenants", (String)map.get("id"));
   }
}

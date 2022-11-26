package com.oracle.weblogic.lifecycle.config.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;

@Service
public class TenantConfigManager {
   @Inject
   private IterableProvider allTenantServices;
   @Inject
   private IterableProvider allServiceConfigs;
   @Inject
   private IterableProvider allTenantResourceServices;
   @Inject
   private LifecycleConfigManager lcm;

   public void createTenant(Map map) {
      this.lcm.add("/lifecycle-config/tenants/tenant", TenantConfigService.getInstanceId(map), map);
   }

   public TenantConfigService createTenant(String name, String id) {
      Map map = new HashMap();
      map.put("id", id);
      map.put("name", name);
      this.createTenant(map);
      return this.getTenantById(id);
   }

   public void deleteTenant(TenantConfigService tenantService) {
      this.lcm.delete("/lifecycle-config/tenants/tenant", tenantService.getInstanceId());
   }

   public List getTenants() {
      return ConfigUtil.toList(this.allTenantServices);
   }

   public TenantConfigService getTenant(String name, String id) {
      Iterator var3 = this.allTenantServices.iterator();

      TenantConfigService tenantService;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         tenantService = (TenantConfigService)var3.next();
      } while(!tenantService.getName().equals(name) || !tenantService.getId().equals(id));

      return tenantService;
   }

   public TenantConfigService getTenantByName(String name) {
      Iterator var2 = this.allTenantServices.iterator();

      TenantConfigService tenantService;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         tenantService = (TenantConfigService)var2.next();
      } while(!tenantService.getName().equals(name));

      return tenantService;
   }

   public TenantConfigService getTenantById(String id) {
      Iterator var2 = this.allTenantServices.iterator();

      TenantConfigService tenantService;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         tenantService = (TenantConfigService)var2.next();
      } while(!tenantService.getId().equals(id));

      return tenantService;
   }

   public void createResource(String name, String type, Properties properties) {
   }

   public ResourceConfigService deleteResource(String name) {
      ResourceConfigService rs = this.getResource(name);
      return rs;
   }

   public ResourceConfigService updateResource(String name, Properties props) {
      return this.getResource(name);
   }

   public List getResources() {
      return ConfigUtil.toList(this.allTenantResourceServices);
   }

   public TenantResourceConfigService getResource(String name) {
      Iterator var2 = this.allTenantResourceServices.iterator();

      TenantResourceConfigService rs;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         rs = (TenantResourceConfigService)var2.next();
      } while(!rs.getName().equals(name));

      return rs;
   }

   public ServiceConfigService getServiceById(String id) {
      Iterator var2 = this.allServiceConfigs.iterator();

      ServiceConfigService ss;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ss = (ServiceConfigService)var2.next();
      } while(!ss.getId().equals(id));

      return ss;
   }
}

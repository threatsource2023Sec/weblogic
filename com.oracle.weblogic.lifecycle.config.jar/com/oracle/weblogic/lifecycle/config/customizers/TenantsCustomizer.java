package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Service;
import com.oracle.weblogic.lifecycle.config.Tenant;
import com.oracle.weblogic.lifecycle.config.Tenants;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class TenantsCustomizer {
   @Inject
   private XmlService xmlService;

   public Tenant createTenant(Tenants tenants, String name, String id, String topLevelDir) {
      Tenant existing = tenants.lookupTenant(name);
      if (existing != null) {
         throw new IllegalStateException("Keys cannot be duplicate. Old value of this key property, " + name + ", will be retained");
      } else {
         Tenant tenant = (Tenant)this.xmlService.createBean(Tenant.class);
         tenant.setName(name);
         tenant.setId(id);
         if (topLevelDir != null) {
            tenant.setTopLevelDir(topLevelDir);
         }

         return tenants.addTenant(tenant);
      }
   }

   public Tenant createTenant(Tenants tenants, String name, String id) {
      return this.createTenant(tenants, name, id, (String)null);
   }

   public Tenant deleteTenant(Tenants tenants, Tenant tenant) {
      return tenants.removeTenant(tenant);
   }

   public Tenant getTenant(Tenants tenants, String name, String id) {
      Tenant retVal = tenants.lookupTenant(name);
      if (retVal == null) {
         return null;
      } else {
         return id != null && !id.equals(retVal.getId()) ? null : retVal;
      }
   }

   public Tenant getTenant(Tenants tenants, String id) {
      if (id == null) {
         return null;
      } else {
         Iterator var3 = tenants.getTenants().iterator();

         Tenant candidate;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            candidate = (Tenant)var3.next();
         } while(!id.equals(candidate.getId()));

         return candidate;
      }
   }

   public Tenant getTenantById(Tenants tenants, String id) {
      return this.getTenant(tenants, id);
   }

   public Tenant getTenantByName(Tenants tenants, String name) {
      return tenants.lookupTenant(name);
   }

   public Tenant getTenantForPartition(Tenants tenants, String partitionName, String partitionId) {
      return null;
   }

   public Tenant getTenantForPDB(Tenants tenants, String pdbName, String pdbId) {
      Iterator var4 = tenants.getTenants().iterator();

      Tenant tenant;
      Service service;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         tenant = (Tenant)var4.next();
         service = tenant.getServiceByPDBId(pdbId);
      } while(service == null);

      return tenant;
   }

   public Service getServiceById(Tenants tenants, String serviceId) {
      Iterator var3 = tenants.getTenants().iterator();

      Service service;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         Tenant tenant = (Tenant)var3.next();
         service = tenant.getServiceById(serviceId);
      } while(service == null);

      return service;
   }

   public void addGlobalTopLevelDir(Tenants tenants, String topLevelDir) {
      tenants.setGlobalTopLevelDirectory(topLevelDir);
   }
}

package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Service;
import com.oracle.weblogic.lifecycle.config.Tenant;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class TenantCustomizer {
   @Inject
   private XmlService xmlService;

   public void addTopLevelDir(Tenant tenant, String topLevelDir) {
      tenant.setTopLevelDir(topLevelDir);
   }

   /** @deprecated */
   @Deprecated
   public Service createService(Tenant tenant, String name, String type, String envName) {
      String id = tenant.getName() + tenant.getId();
      return this.createService(tenant, id, name, type, envName);
   }

   public Service createService(Tenant tenant, String id, String name, String type, String envName) {
      return this.createService(tenant, id, name, type, (String)null, envName);
   }

   public Service createService(Tenant tenant, String id, String name, String type, String identityDomain, String envName) {
      XmlHk2ConfigurationBean bean = (XmlHk2ConfigurationBean)tenant;
      XmlRootHandle handle = bean._getRoot();
      XmlHandleTransaction transactionHandle = handle.lockForTransaction();
      boolean success = false;

      Service var13;
      try {
         Service service = (Service)this.xmlService.createBean(Service.class);
         service.setId(id);
         service.setName(name);
         service.setEnvironmentRef(envName);
         service.setServiceType(type);
         if (identityDomain != null) {
            service.setIdentityDomain(identityDomain);
         }

         Service retVal = tenant.addService(service);
         success = true;
         var13 = retVal;
      } finally {
         if (success) {
            transactionHandle.commit();
         } else {
            transactionHandle.abandon();
         }

      }

      return var13;
   }

   public Service getServiceByName(Tenant tenant, String serviceName) {
      if (serviceName == null) {
         return null;
      } else {
         Iterator var3 = tenant.getServices().iterator();

         Service service;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            service = (Service)var3.next();
         } while(!serviceName.equals(service.getName()));

         return service;
      }
   }

   public Service getServiceById(Tenant tenant, String id) {
      return tenant.lookupService(id);
   }

   public Service getServiceByPDBId(Tenant tenant, String pdbId) {
      List services = tenant.getServices();
      Iterator var4 = services.iterator();

      Service service;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         service = (Service)var4.next();
      } while(!pdbId.equals(service.getPdb().getId()));

      return service;
   }

   public Service deleteService(Tenant tenant, Service service) {
      return service == null ? null : tenant.removeService(service);
   }

   public Service deleteService(Tenant tenant, String serviceName) {
      Service killMe = this.getServiceByName(tenant, serviceName);
      return killMe == null ? null : tenant.removeService(killMe);
   }
}

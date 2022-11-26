package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.configuration.api.ChildInject;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/tenants/tenant/service")
public class ServiceConfigService extends LifecycleConfigService {
   @Configured
   private String id;
   @Configured
   private String serviceType;
   @Configured
   private String name;
   @Configured
   private String environmentRef;
   @Inject
   private EnvironmentConfigManager em;
   @ChildInject
   private ChildIterable pdbs;
   @ChildInject
   private ChildIterable resources;
   static final String PATH_KEY = "/lifecycle-config/tenants/tenant/service";
   static final String ID = "id";
   static final String NAME = "name";
   static final String TYPE = "serviceType";
   static final String IDENTITY_DOMAIN = "identityDomain";
   static final String ENVIRONMENT_REF = "environmentRef";

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setEnvironmentRef(String environmentRef) {
      this.environmentRef = environmentRef;
   }

   public String getEnvironmentRef() {
      return this.environmentRef;
   }

   public String getName() {
      return this.name;
   }

   public PdbConfigService getPdb() {
      List list = ConfigUtil.toList(this.pdbs);
      return list.size() == 0 ? null : (PdbConfigService)list.get(0);
   }

   public void setServiceType(String serviceType) {
      this.serviceType = serviceType;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public void createPdb(Map map) throws LifecycleException {
      if (ConfigUtil.toList(this.pdbs).size() > 0) {
         throw new LifecycleException("PDB already exists for service " + this.getName());
      } else {
         this.add("/lifecycle-config/tenants/tenant/service/pdb", PdbConfigService.getInstanceId(this, map), map);
      }
   }

   public PdbConfigService createPdb(String name, String id, String pdb_status) throws LifecycleException {
      Map map = new HashMap();
      map.put("id", id);
      map.put("name", name);
      map.put("pdb_status", pdb_status);
      this.createPdb(map);
      return this.getPdb(id);
   }

   public PdbConfigService deletePdb(PdbConfigService pdbService) {
      this.delete("/lifecycle-config/tenants/tenant/service/pdb", pdbService.getInstanceId());
      return pdbService;
   }

   public PdbConfigService getPdb(String id) {
      return (PdbConfigService)this.pdbs.byKey(id);
   }

   public static String getInstanceId(TenantConfigService ts, Map map) {
      return ConfigUtil.addWithSeparator(ts.getInstanceId(), (String)map.get("id"));
   }

   public TenantConfigService getTenant() {
      return (TenantConfigService)this.getServiceLocator().getService(TenantConfigService.class, this.getParentInstanceId(), new Annotation[0]);
   }

   public EnvironmentConfigService getEnvironment() {
      return this.em.getEnvironmentByName(this.environmentRef);
   }

   public void createResource(String name, String type, Properties properties) {
   }

   public List getResources() {
      return ConfigUtil.toList(this.resources);
   }

   public ResourceConfigService deleteResource(String name) {
      ResourceConfigService rs = this.getResource(name);
      return rs;
   }

   public ResourceConfigService updateResource(String name, Properties props) {
      return this.getResource(name);
   }

   public ServiceResourceConfigService getResource(String name) {
      Iterator var2 = this.resources.iterator();

      ServiceResourceConfigService rs;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         rs = (ServiceResourceConfigService)var2.next();
      } while(!rs.getName().equals(name));

      return rs;
   }
}

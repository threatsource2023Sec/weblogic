package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.TenantsCustomizer;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({TenantsCustomizer.class})
public interface Tenants {
   @XmlElement(
      name = "tenant"
   )
   @Valid List getTenants();

   void setTenants(List var1);

   Tenant lookupTenant(String var1);

   Tenant addTenant(Tenant var1);

   Tenant removeTenant(Tenant var1);

   @Customize
   Tenant createTenant(String var1, String var2, String var3);

   @Customize
   Tenant createTenant(String var1, String var2);

   @Customize
   Tenant deleteTenant(Tenant var1);

   @Customize
   Tenant getTenant(String var1, String var2);

   @Customize
   Tenant getTenant(String var1);

   @Customize
   Tenant getTenantById(String var1);

   @Customize
   Tenant getTenantByName(String var1);

   @Customize
   Tenant getTenantForPartition(String var1, String var2);

   @Customize
   Tenant getTenantForPDB(String var1, String var2);

   @Customize
   Service getServiceById(String var1);

   @XmlElement(
      name = "resources"
   )
   Resources getResources();

   void setResources(Resources var1);

   @XmlAttribute(
      name = "global-top-level-directory"
   )
   String getGlobalTopLevelDirectory();

   void setGlobalTopLevelDirectory(String var1);

   @Customize
   void addGlobalTopLevelDir(String var1);
}

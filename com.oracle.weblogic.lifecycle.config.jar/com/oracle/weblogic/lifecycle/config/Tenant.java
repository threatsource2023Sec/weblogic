package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.TenantCustomizer;
import java.util.List;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({TenantCustomizer.class, AuditableCustomizer.class})
public interface Tenant extends Named, Payload, Auditable {
   @XmlAttribute(
      name = "id"
   )
   String getId();

   void setId(String var1);

   @XmlAttribute(
      name = "top-level-dir"
   )
   String getTopLevelDir();

   void setTopLevelDir(String var1);

   @XmlElement(
      name = "service"
   )
   @Valid List getServices();

   void setServices(List var1);

   Service lookupService(String var1);

   Service addService(Service var1);

   Service removeService(Service var1);

   @Customize
   void addTopLevelDir(String var1);

   /** @deprecated */
   @Customize
   @Deprecated
   Service createService(String var1, String var2, String var3);

   @Customize
   Service createService(String var1, String var2, String var3, String var4);

   @Customize
   Service createService(String var1, String var2, String var3, String var4, String var5);

   @Customize
   Service getServiceByName(String var1);

   @Customize
   Service getServiceById(String var1);

   @Customize
   Service getServiceByPDBId(String var1);

   @Customize
   Service deleteService(Service var1);

   @Customize
   Service deleteService(String var1);
}

package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.ResourcesCustomizer;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({ResourcesCustomizer.class, AuditableCustomizer.class})
public interface Resources extends Auditable {
   @XmlElement(
      name = "resource"
   )
   @Valid List getResources();

   void setResources(List var1);

   Resource lookupResource(String var1);

   Resource addResource(Resource var1);

   Resource removeResource(Resource var1);

   Resource removeResource(String var1);

   @Customize
   Resource createResource(String var1, String var2, Map var3, ServiceLocator var4);

   @Customize
   Resource updateResource(String var1, Map var2, ServiceLocator var3);

   @Customize
   Resource deleteResource(String var1, ServiceLocator var2);

   @Customize
   Resource getResource(String var1, ServiceLocator var2);

   @Customize
   ResourceHandler getResourceHandler(Resource var1, ServiceLocator var2);
}

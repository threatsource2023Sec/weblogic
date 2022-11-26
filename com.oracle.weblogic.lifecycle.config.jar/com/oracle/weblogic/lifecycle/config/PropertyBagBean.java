package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PropertyBagBeanCustomizer.class})
public interface PropertyBagBean {
   @XmlElement(
      name = "property"
   )
   @Valid List getProperty();

   PropertyBean addProperty(PropertyBean var1);

   PropertyBean lookupProperty(String var1);

   PropertyBean removeProperty(String var1);

   PropertyBean removeProperty(PropertyBean var1);

   @Customize
   PropertyBean getProperty(String var1);

   @Customize
   String getPropertyValue(String var1);

   @Customize
   String getPropertyValue(String var1, String var2);
}

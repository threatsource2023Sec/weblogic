package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PartitionCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PartitionCustomizer.class, PropertyBagBeanCustomizer.class, AuditableCustomizer.class})
public interface Partition extends PropertyBagBean, Auditable {
   @XmlAttribute(
      required = true
   )
   @XmlID
   @NotNull String getId();

   void setId(String var1);

   @XmlAttribute(
      required = true
   )
   @NotNull String getName();

   void setName(String var1);

   @Customize
   Runtime getRuntime();

   @Customize
   Partition update(Map var1);
}

package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PartitionRefCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import com.oracle.weblogic.lifecycle.config.validators.ReferenceConstraint;
import java.beans.PropertyVetoException;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PartitionRefCustomizer.class, PropertyBagBeanCustomizer.class, AuditableCustomizer.class})
public interface PartitionRef extends PropertyBagBean, Payload, Auditable {
   @XmlAttribute(
      required = true,
      name = "id"
   )
   @XmlID
   @ReferenceConstraint(
      message = "{resourceref.invalid.configref}",
      type = Partition.class
   )
   @NotNull String getId();

   void setId(String var1) throws PropertyVetoException;

   @XmlAttribute(
      required = true,
      name = "runtime-ref"
   )
   @ReferenceConstraint(
      message = "{resourceref.invalid.configref}",
      type = Runtime.class
   )
   @NotNull String getRuntimeRef();

   void setRuntimeRef(String var1) throws PropertyVetoException;

   @Customize
   Runtime getRuntime();

   @Customize
   Environment getEnvironment();
}

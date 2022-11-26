package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@XmlRootElement
@Hk2XmlPreGenerate
@Customizer({PropertyBagBeanCustomizer.class})
public interface LifecycleConfig extends PropertyBagBean {
   @XmlElement
   @NotNull @Valid Runtimes getRuntimes();

   void setRuntimes(Runtimes var1);

   @XmlElement
   @NotNull @Valid Environments getEnvironments();

   void setEnvironments(Environments var1);

   @XmlElement
   @NotNull @Valid Tenants getTenants();

   void setTenants(Tenants var1);

   @XmlElement
   @NotNull Plugins getPlugins();

   void setPlugins(Plugins var1);

   @XmlAttribute(
      name = "persistence-type"
   )
   String getPersistenceType();

   void setPersistenceType(String var1);

   @XmlElement(
      name = "edit-sessions"
   )
   @NotNull @Valid EditSessions getEditSessions();

   void setEditSessions(EditSessions var1);
}

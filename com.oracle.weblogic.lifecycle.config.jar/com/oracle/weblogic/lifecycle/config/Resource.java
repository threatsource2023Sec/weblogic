package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import java.beans.PropertyVetoException;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PropertyBagBeanCustomizer.class, AuditableCustomizer.class})
public interface Resource extends PropertyBagBean, Auditable {
   String PROXY_DATASOURCE = "proxy-datasource";
   String DATASOURCE = "datasource";
   String JDBC_PROPERTIES = "jdbc-properties";

   @XmlAttribute(
      required = true
   )
   @NotNull String getType();

   void setType(String var1);

   @XmlAttribute(
      required = true
   )
   @XmlID
   @NotNull String getName();

   void setName(String var1) throws PropertyVetoException;
}

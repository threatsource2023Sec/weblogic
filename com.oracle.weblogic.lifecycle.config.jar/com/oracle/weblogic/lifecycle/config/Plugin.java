package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PluginCustomizer;
import javax.xml.bind.annotation.XmlAttribute;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PluginCustomizer.class, AuditableCustomizer.class})
public interface Plugin extends Named, Auditable {
   @XmlAttribute
   String getType();

   void setType(String var1);

   @XmlAttribute
   String getPath();

   void setPath(String var1);
}

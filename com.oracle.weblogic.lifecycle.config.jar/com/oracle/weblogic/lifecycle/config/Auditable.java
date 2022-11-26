package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({AuditableCustomizer.class})
public interface Auditable {
   @XmlAttribute(
      name = "created-on"
   )
   String getCreatedOn();

   void setCreatedOn(String var1);

   @XmlAttribute(
      name = "updated-on"
   )
   String getUpdatedOn();

   void setUpdatedOn(String var1);

   @Customize
   Date getCreatedOnDate();

   @Customize
   Date getUpdatedOnDate();
}

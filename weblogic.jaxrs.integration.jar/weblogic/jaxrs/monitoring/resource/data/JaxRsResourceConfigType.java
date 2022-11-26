package weblogic.jaxrs.monitoring.resource.data;

import java.util.Properties;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import weblogic.management.runtime.JaxRsResourceConfigTypeRuntimeMBean;

@XmlRootElement
public class JaxRsResourceConfigType {
   JaxRsResourceConfigTypeRuntimeMBean config = null;

   public JaxRsResourceConfigType(JaxRsResourceConfigTypeRuntimeMBean config) {
      this.config = config;
   }

   public JaxRsResourceConfigType() {
   }

   @XmlElement
   public String getClassName() {
      return this.config.getClassName();
   }

   @XmlElement
   public Properties getProperties() {
      return this.config.getProperties();
   }
}

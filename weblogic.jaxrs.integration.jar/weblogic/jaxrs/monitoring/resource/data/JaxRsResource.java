package weblogic.jaxrs.monitoring.resource.data;

import javax.xml.bind.annotation.XmlElement;
import weblogic.management.runtime.JaxRsResourceRuntimeMBean;

public class JaxRsResource extends JaxRsMonitoringInfo {
   JaxRsResourceRuntimeMBean bean;

   public JaxRsResource() {
   }

   public JaxRsResource(JaxRsResourceRuntimeMBean mBean) {
      super(mBean);
      this.bean = mBean;
   }

   @XmlElement
   public String getClassName() {
      return this.bean.getClassName();
   }

   @XmlElement
   public String getResourceType() {
      return this.bean.getResourceType();
   }

   @XmlElement
   public String getPath() {
      return this.bean.getPath();
   }

   @XmlElement
   public JaxRsSubResourceLocator[] getSubResourceLocators() {
      return null;
   }

   @XmlElement
   public JaxRsResourceMethod[] getResourceMethods() {
      return null;
   }
}

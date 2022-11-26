package weblogic.jaxrs.monitoring.resource.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceRuntimeMBean;

@XmlRootElement
public class JaxRsApplication extends JaxRsMonitoringInfo {
   JaxRsApplicationRuntimeMBean appMbean = null;

   public JaxRsApplication() {
   }

   public JaxRsApplication(JaxRsApplicationRuntimeMBean mbean) {
      super(mbean);
      this.appMbean = mbean;
   }

   @XmlElement
   public String getName() {
      return this.appMbean.getName();
   }

   @XmlElement
   public JaxRsResourceConfigType getConfigType() {
      return new JaxRsResourceConfigType(this.appMbean.getResourceConfig());
   }

   @XmlElement
   public long getErrorCount() {
      return this.appMbean.getErrorCount();
   }

   @XmlElement
   public String[] getLastErrorDetails() {
      return this.appMbean.getLastErrorDetails();
   }

   @XmlElement
   public String getLastErrorMapper() {
      return this.appMbean.getLastErrorMapper();
   }

   @XmlElement
   public Date getLastErrorTime() {
      return this.appMbean.getLastErrorTime() < 0L ? null : new Date(this.appMbean.getLastErrorTime());
   }

   @XmlElement
   public String getLastHttpMethod() {
      return this.appMbean.getLastHttpMethod();
   }

   @XmlElement
   public int getLastResponseCode() {
      return this.appMbean.getLastResponseCode();
   }

   @XmlElement
   public JaxRsResource[] getRootResources() {
      JaxRsResourceRuntimeMBean[] beans = this.appMbean.getRootResources();
      if (beans == null) {
         return null;
      } else {
         List resources = new ArrayList();
         JaxRsResourceRuntimeMBean[] var3 = beans;
         int var4 = beans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            JaxRsResourceRuntimeMBean b = var3[var5];
            resources.add(new JaxRsResource(b));
         }

         return (JaxRsResource[])resources.toArray(new JaxRsResource[0]);
      }
   }
}

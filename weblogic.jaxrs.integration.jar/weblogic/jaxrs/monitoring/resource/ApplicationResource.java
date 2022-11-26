package weblogic.jaxrs.monitoring.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import weblogic.jaxrs.monitoring.resource.data.JaxRsApplication;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;

@Path("sys/monitoring")
public class ApplicationResource {
   private JaxRsApplication app = null;

   @GET
   public JaxRsApplication getApplication() {
      Object appMBean = null;
      if (this.app == null && appMBean != null && appMBean instanceof JaxRsApplicationRuntimeMBean) {
         this.app = new JaxRsApplication((JaxRsApplicationRuntimeMBean)appMBean);
      }

      return this.app;
   }
}

package weblogic.jaxrs.monitoring.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceMethod.JaxrsType;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsResourceMethodRuntimeMBean;

public final class JaxRsResourceMethodMBeanImpl extends JaxRsResourceMethodBaseMBeanImpl implements JaxRsResourceMethodRuntimeMBean {
   private final String[] consumingMediaTypes;
   private final String[] producingMediaTypes;
   private final boolean subResourceMethod;
   private final String httpMethod;

   public JaxRsResourceMethodMBeanImpl(String name, JaxRsMonitoringInfoMBeanImpl parent, ResourceMethod method, ResourceMethodStatistics stats, boolean fullPath, boolean extended) throws ManagementException {
      super(name, parent, method, stats, fullPath, extended);
      this.httpMethod = method.getHttpMethod();
      this.subResourceMethod = method.getType() == JaxrsType.SUB_RESOURCE_METHOD;
      this.consumingMediaTypes = this.mediaTypesToStrings(method.getConsumedTypes());
      this.producingMediaTypes = this.mediaTypesToStrings(method.getProducedTypes());
   }

   private String[] mediaTypesToStrings(List mediaTypes) {
      List strings = new ArrayList(mediaTypes.size());
      Iterator var3 = mediaTypes.iterator();

      while(var3.hasNext()) {
         MediaType mt = (MediaType)var3.next();
         strings.add(mt.toString());
      }

      return (String[])strings.toArray(new String[strings.size()]);
   }

   public String getHttpMethod() {
      return this.httpMethod;
   }

   public String[] getConsumingMediaTypes() {
      return this.consumingMediaTypes;
   }

   public String[] getProducingMediaTypes() {
      return this.producingMediaTypes;
   }

   public boolean isSubResource() {
      return this.subResourceMethod;
   }

   public boolean isSubResourceMethod() {
      return this.subResourceMethod;
   }
}

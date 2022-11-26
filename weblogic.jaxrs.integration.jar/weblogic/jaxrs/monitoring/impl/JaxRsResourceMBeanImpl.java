package weblogic.jaxrs.monitoring.impl;

import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import weblogic.jaxrs.monitoring.util.MonitoringUtil;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceRuntimeMBean;

public final class JaxRsResourceMBeanImpl extends JaxRsUriMBeanImpl implements JaxRsResourceRuntimeMBean {
   private final String resourceType;
   private final String className;

   JaxRsResourceMBeanImpl(String name, JaxRsApplicationRuntimeMBean parent, String path, Class clazz, ResourceStatistics stats, boolean extended, boolean extendedEnabled) throws ManagementException {
      super(name, parent, path, stats, extended, extendedEnabled);
      this.resourceType = MonitoringUtil.isEJB(clazz) ? "EJB" : "POJO";
      this.className = clazz.getName();
   }

   public String getClassName() {
      return this.className;
   }

   public String getResourceType() {
      return this.resourceType;
   }
}

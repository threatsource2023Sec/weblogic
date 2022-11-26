package weblogic.jaxrs.monitoring.impl;

import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsSubResourceLocatorRuntimeMBean;

final class JaxRsSubResourceLocatorMBeanImpl extends JaxRsResourceMethodBaseMBeanImpl implements JaxRsSubResourceLocatorRuntimeMBean {
   public JaxRsSubResourceLocatorMBeanImpl(String name, JaxRsMonitoringInfoMBeanImpl parent, ResourceMethod method, ResourceMethodStatistics stats, boolean fullPath, boolean extended) throws ManagementException {
      super(name, parent, method, stats, fullPath, extended);
   }
}

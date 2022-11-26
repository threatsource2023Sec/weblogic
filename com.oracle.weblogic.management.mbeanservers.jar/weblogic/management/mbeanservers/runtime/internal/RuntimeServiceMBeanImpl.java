package weblogic.management.mbeanservers.runtime.internal;

import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.internal.RuntimeServiceImpl;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public class RuntimeServiceMBeanImpl extends RuntimeServiceImpl implements RuntimeServiceMBean {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXRuntime");
   private final RuntimeAccess runtime;
   private final String serverName;
   private final DomainMBean domain;
   private final ServerMBean server;
   private final ServerRuntimeMBean serverRuntime;

   RuntimeServiceMBeanImpl(RuntimeAccess _runtime) {
      super("RuntimeService", RuntimeServiceMBean.class.getName(), (Service)null);
      this.runtime = _runtime;
      this.domain = this.runtime.getDomain();
      this.server = this.runtime.getServer();
      this.serverName = this.runtime.getServerName();
      this.serverRuntime = this.runtime.getServerRuntime();
   }

   public DomainMBean getDomainConfiguration() {
      return this.domain;
   }

   public ServerMBean getServerConfiguration() {
      return this.server;
   }

   public String getServerName() {
      return this.serverName;
   }

   public ServerRuntimeMBean getServerRuntime() {
      return this.serverRuntime;
   }

   public RuntimeMBean findRuntime(DescriptorBean configurationMBean) {
      return this.runtime.lookupRuntime(configurationMBean);
   }

   public DescriptorBean findConfiguration(RuntimeMBean runtimeMBean) {
      if (debug.isDebugEnabled()) {
         debug.debug("Looking up configuration for a " + runtimeMBean);
      }

      DescriptorBean result = this.runtime.lookupConfigurationBean(runtimeMBean);
      if (debug.isDebugEnabled()) {
         debug.debug("Found " + result);
      }

      return result;
   }

   public Service findService(String serviceName, String type) {
      return (Service)this.runtime.findService(serviceName, type);
   }

   public Service[] getServices() {
      weblogic.management.provider.Service[] localServices = this.runtime.getRootServices();
      Service[] mbeanServices = new Service[localServices.length];
      System.arraycopy(localServices, 0, mbeanServices, 0, localServices.length);
      return mbeanServices;
   }
}

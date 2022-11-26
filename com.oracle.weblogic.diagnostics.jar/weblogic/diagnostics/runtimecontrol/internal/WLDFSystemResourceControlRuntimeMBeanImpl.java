package weblogic.diagnostics.runtimecontrol.internal;

import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.runtimecontrol.RuntimeProfileDriver;
import weblogic.management.ManagementException;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFControlRuntimeMBean;
import weblogic.management.runtime.WLDFHarvesterManagerRuntimeMBean;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;
import weblogic.management.runtime.WLDFWatchManagerRuntimeMBean;

public class WLDFSystemResourceControlRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFSystemResourceControlRuntimeMBean {
   private static RuntimeProfileDriver profileDriver = RuntimeProfileDriver.getInstance();
   private WLDFResourceBean resource;
   private WLDFHarvesterManagerRuntimeMBean harvesterRuntime;
   private WLDFWatchManagerRuntimeMBean watchRuntime;
   private boolean external;

   public boolean isEnabled() {
      return RuntimeProfileDriver.getInstance().isEnabled(this.resource);
   }

   public WLDFSystemResourceControlRuntimeMBeanImpl(WLDFResourceBean res, String name, WLDFControlRuntimeMBean parent, boolean isExternal) throws ManagementException {
      super(name, parent);
      this.resource = res;
      this.external = isExternal;
      this.harvesterRuntime = new HarvesterManagerRuntimeMBeanImpl(this.resource, this);
      this.watchRuntime = new WatchManagerRuntimeMBeanImpl(this.resource, this);
   }

   public void setEnabled(boolean enable) {
      try {
         RuntimeProfileDriver.getInstance().enable(this.resource, enable);
      } catch (ManagementException var3) {
         throw new ManagementRuntimeException(var3);
      }
   }

   public void unregister() throws ManagementException {
      profileDriver.undeploy(this.resource);
      super.unregister();
   }

   public void redeploy(WLDFResourceBean proposedBean) throws ManagementException {
      boolean enabled = profileDriver.isEnabled(proposedBean.getName());
      profileDriver.undeploy(proposedBean);
      if (enabled) {
         profileDriver.deploy(proposedBean);
         profileDriver.enable(proposedBean, enabled);
      }

   }

   public WLDFHarvesterManagerRuntimeMBean getHarvesterManagerRuntime() {
      return this.harvesterRuntime;
   }

   public WLDFWatchManagerRuntimeMBean getWatchManagerRuntime() {
      return this.watchRuntime;
   }

   public boolean isExternalResource() {
      return this.external;
   }
}

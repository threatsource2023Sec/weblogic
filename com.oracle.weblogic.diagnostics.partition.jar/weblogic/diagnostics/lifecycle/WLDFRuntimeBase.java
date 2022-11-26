package weblogic.diagnostics.lifecycle;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFControlRuntimeMBean;
import weblogic.management.runtime.WLDFImageRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;

public class WLDFRuntimeBase extends RuntimeMBeanDelegate {
   private WLDFAccessRuntimeMBean accessRuntime;
   private WLDFImageRuntimeMBean imageRuntime;
   private WLDFWatchNotificationRuntimeMBean watchRuntime;
   private WLDFControlRuntimeMBean control;

   public WLDFRuntimeBase(String name) throws ManagementException {
      super(name);
   }

   public WLDFRuntimeBase(RuntimeMBean parent) throws ManagementException {
      super(parent.getName(), parent);
   }

   public WLDFAccessRuntimeMBean getWLDFAccessRuntime() {
      return this.accessRuntime;
   }

   public void setWLDFAccessRuntime(WLDFAccessRuntimeMBean dar) {
      this.accessRuntime = dar;
   }

   public WLDFImageRuntimeMBean getWLDFImageRuntime() {
      return this.imageRuntime;
   }

   public void setWLDFImageRuntime(WLDFImageRuntimeMBean bean) {
      this.imageRuntime = bean;
   }

   public WLDFWatchNotificationRuntimeMBean getWLDFWatchNotificationRuntime() {
      return this.watchRuntime;
   }

   public void setWLDFWatchNotificationRuntime(WLDFWatchNotificationRuntimeMBean bean) {
      this.watchRuntime = bean;
   }

   public WLDFControlRuntimeMBean getWLDFControlRuntime() {
      return this.control;
   }

   public void setWLDFControlRuntime(WLDFControlRuntimeMBean controlRuntime) {
      this.control = controlRuntime;
   }
}

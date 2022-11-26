package weblogic.diagnostics.partition;

import java.security.AccessController;
import weblogic.diagnostics.lifecycle.WLDFRuntimeBase;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionHarvesterRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionImageRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLDFPartitionRuntimeMBeanImpl extends WLDFRuntimeBase implements WLDFPartitionRuntimeMBean {
   private WLDFPartitionAccessRuntimeMBean accessRuntime;
   private WLDFPartitionHarvesterRuntimeMBean harvesterRuntime;
   private WLDFPartitionImageRuntimeMBean imageRuntime;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public WLDFPartitionRuntimeMBeanImpl(PartitionRuntimeMBean parent) throws ManagementException {
      super((RuntimeMBean)parent);
   }

   public WLDFAccessRuntimeMBean getWLDFAccessRuntime() {
      return this.accessRuntime;
   }

   public WLDFPartitionAccessRuntimeMBean getWLDFPartitionAccessRuntime() {
      return this.accessRuntime;
   }

   public void setWLDFPartitionAccessRuntime(WLDFPartitionAccessRuntimeMBean bean) {
      this.accessRuntime = bean;
   }

   public WLDFPartitionHarvesterRuntimeMBean getWLDFPartitionHarvesterRuntime() {
      return this.harvesterRuntime;
   }

   public void setWLDFPartitionHarvesterRuntime(WLDFPartitionHarvesterRuntimeMBean bean) {
      this.harvesterRuntime = bean;
   }

   public WLDFPartitionImageRuntimeMBean getWLDFPartitionImageRuntime() {
      return this.imageRuntime;
   }

   public void setWLDFPartitionImageRuntime(WLDFPartitionImageRuntimeMBean bean) {
      this.imageRuntime = bean;
   }
}

package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFArchiveRuntimeMBean;
import weblogic.management.runtime.WLDFControlRuntimeMBean;
import weblogic.management.runtime.WLDFDebugPatchesRuntimeMBean;
import weblogic.management.runtime.WLDFHarvesterRuntimeMBean;
import weblogic.management.runtime.WLDFImageRuntimeMBean;
import weblogic.management.runtime.WLDFInstrumentationRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLDFRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFRuntimeMBean {
   private WLDFAccessRuntimeMBean accessRuntime;
   private WLDFHarvesterRuntimeMBean harvesterRuntime;
   private WLDFImageRuntimeMBean imageRuntime;
   private WLDFWatchNotificationRuntimeMBean watchRuntime;
   private WLDFControlRuntimeMBean control;
   private WLDFDebugPatchesRuntimeMBean debugPatchesRuntime;
   private List archiveRuntimesList = new ArrayList();
   private List instRuntimesList = new ArrayList();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public WLDFRuntimeMBeanImpl() throws ManagementException {
      super("WLDFRuntime");
      ServerRuntimeMBean serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      serverBean.setWLDFRuntime(this);
   }

   public WLDFAccessRuntimeMBean getWLDFAccessRuntime() {
      return this.accessRuntime;
   }

   public void setWLDFAccessRuntime(WLDFAccessRuntimeMBean dar) {
      this.accessRuntime = dar;
   }

   public WLDFArchiveRuntimeMBean[] getWLDFArchiveRuntimes() {
      synchronized(this.archiveRuntimesList) {
         WLDFArchiveRuntimeMBean[] arr = new WLDFArchiveRuntimeMBean[this.archiveRuntimesList.size()];
         arr = (WLDFArchiveRuntimeMBean[])((WLDFArchiveRuntimeMBean[])this.archiveRuntimesList.toArray(arr));
         return arr;
      }
   }

   public boolean addWLDFArchiveRuntime(WLDFArchiveRuntimeMBean bean) {
      synchronized(this.archiveRuntimesList) {
         return this.archiveRuntimesList.contains(bean) ? false : this.archiveRuntimesList.add(bean);
      }
   }

   public boolean removeWLDFArchiveRuntime(WLDFArchiveRuntimeMBean bean) {
      synchronized(this.archiveRuntimesList) {
         return this.archiveRuntimesList.remove(bean);
      }
   }

   public WLDFHarvesterRuntimeMBean getWLDFHarvesterRuntime() {
      return this.harvesterRuntime;
   }

   public void setWLDFHarvesterRuntime(WLDFHarvesterRuntimeMBean bean) {
      this.harvesterRuntime = bean;
   }

   public WLDFImageRuntimeMBean getWLDFImageRuntime() {
      return this.imageRuntime;
   }

   public void setWLDFImageRuntime(WLDFImageRuntimeMBean bean) {
      this.imageRuntime = bean;
   }

   public WLDFInstrumentationRuntimeMBean[] getWLDFInstrumentationRuntimes() {
      synchronized(this.instRuntimesList) {
         WLDFInstrumentationRuntimeMBean[] arr = new WLDFInstrumentationRuntimeMBean[this.instRuntimesList.size()];
         arr = (WLDFInstrumentationRuntimeMBean[])((WLDFInstrumentationRuntimeMBean[])this.instRuntimesList.toArray(arr));
         return arr;
      }
   }

   public boolean addWLDFInstrumentationRuntime(WLDFInstrumentationRuntimeMBean bean) {
      synchronized(this.instRuntimesList) {
         return this.instRuntimesList.contains(bean) ? false : this.instRuntimesList.add(bean);
      }
   }

   public boolean removeWLDFInstrumentationRuntime(WLDFInstrumentationRuntimeMBean bean) {
      synchronized(this.instRuntimesList) {
         return this.instRuntimesList.remove(bean);
      }
   }

   public WLDFWatchNotificationRuntimeMBean getWLDFWatchNotificationRuntime() {
      return this.watchRuntime;
   }

   public void setWLDFWatchNotificationRuntime(WLDFWatchNotificationRuntimeMBean bean) {
      this.watchRuntime = bean;
   }

   public WLDFInstrumentationRuntimeMBean lookupWLDFInstrumentationRuntime(String name) {
      Iterator i = this.instRuntimesList.iterator();

      WLDFInstrumentationRuntimeMBean instRuntime;
      do {
         if (!i.hasNext()) {
            return null;
         }

         instRuntime = (WLDFInstrumentationRuntimeMBean)i.next();
      } while(!instRuntime.getName().equals(name));

      return instRuntime;
   }

   public WLDFControlRuntimeMBean getWLDFControlRuntime() {
      return this.control;
   }

   public void setWLDFControlRuntime(WLDFControlRuntimeMBean controlRuntime) {
      this.control = controlRuntime;
   }

   public WLDFDebugPatchesRuntimeMBean getWLDFDebugPatchesRuntime() {
      return this.debugPatchesRuntime;
   }

   public void setWLDFDebugPatchesRuntime(WLDFDebugPatchesRuntimeMBean debugPatchesRuntime) {
      this.debugPatchesRuntime = debugPatchesRuntime;
   }
}

package weblogic.osgi.internal;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import weblogic.j2ee.descriptor.wl.OsgiFrameworkReferenceBean;
import weblogic.osgi.OSGiException;
import weblogic.osgi.OSGiLogger;
import weblogic.utils.jars.VirtualJarFile;

public class OSGiAmalgamated {
   private String frameworkName;
   private String bsnOfAttachedBundle;
   private String versionRangeOfAttachedBundle;
   private final List deploymentInfo = new LinkedList();
   private boolean first = true;

   public String getFrameworkName() {
      return this.frameworkName;
   }

   public String getBsnOfAttachedBundle() {
      return this.bsnOfAttachedBundle;
   }

   public String getVersionRangeOfAttachedBundle() {
      return this.versionRangeOfAttachedBundle;
   }

   public List getDeploymentInfo() {
      return this.deploymentInfo;
   }

   public void addDeploymentInfo(OsgiFrameworkReferenceBean bean, File root, VirtualJarFile altRoot) throws OSGiException {
      SpecificDeploymentInfo sdi;
      if (this.first) {
         this.first = false;
         this.frameworkName = bean.getName();
         this.bsnOfAttachedBundle = bean.getApplicationBundleSymbolicName();
         this.versionRangeOfAttachedBundle = bean.getApplicationBundleVersion();
         sdi = new SpecificDeploymentInfo();
         sdi.setEmbeddedApplication(altRoot);
         sdi.setRootOfApplication(root);
         sdi.setOsgiLibName(bean.getBundlesDirectory());
         this.deploymentInfo.add(sdi);
      } else if (!Utilities.safeEquals(this.frameworkName, bean.getName())) {
         throw new OSGiException(OSGiLogger.logConflictingFrameworkNamesLoggable(this.frameworkName, bean.getName()).getMessage());
      } else if (!Utilities.safeEquals(this.bsnOfAttachedBundle, bean.getApplicationBundleSymbolicName())) {
         throw new OSGiException(OSGiLogger.logConflictingBSNNamesLoggable(this.bsnOfAttachedBundle, bean.getApplicationBundleSymbolicName()).getMessage());
      } else {
         sdi = new SpecificDeploymentInfo();
         sdi.setEmbeddedApplication(altRoot);
         sdi.setRootOfApplication(root);
         sdi.setOsgiLibName(bean.getBundlesDirectory());
         this.deploymentInfo.add(sdi);
      }
   }

   public boolean hasWork() {
      return this.deploymentInfo.size() > 0;
   }
}

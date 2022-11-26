package weblogic.ejb.spi;

import java.io.File;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.ejb.container.metadata.EjbJarLoader;
import weblogic.ejb.container.metadata.WeblogicEjbJarLoader;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.classloaders.Source;

/** @deprecated */
@Deprecated
public final class EjbJarDescriptor {
   private EjbJarLoader ejbJarDescriptor;
   private WeblogicEjbJarLoader wlsEjbJarDescriptor;

   public EjbJarDescriptor(File altDD, String appName, String uri) {
      ApplicationContextInternal aci = ApplicationAccess.getApplicationAccess().getApplicationContext(appName);
      File configDir = null;
      DeploymentPlanBean plan = null;
      if (aci != null) {
         plan = aci.findDeploymentPlan();
         AppDeploymentMBean dmb = aci.getAppDeploymentMBean();
         if (dmb.getPlanDir() != null) {
            configDir = new File(dmb.getLocalPlanDir());
         }
      }

      if (altDD.getPath().endsWith("weblogic-ejb-jar.xml")) {
         this.wlsEjbJarDescriptor = new WeblogicEjbJarLoader(altDD, configDir, plan, uri, "META-INF/weblogic-ejb-jar.xml", (Source)null);
      } else {
         this.ejbJarDescriptor = new EjbJarLoader(altDD, configDir, plan, uri, "META-INF/ejb-jar.xml", (Source)null);
      }

   }

   public EjbJarDescriptor(File ejbDD, File wlsEjbDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.ejbJarDescriptor = new EjbJarLoader(ejbDD, configDir, plan, moduleName, "META-INF/ejb-jar.xml", (Source)null);
      this.wlsEjbJarDescriptor = new WeblogicEjbJarLoader(wlsEjbDD, configDir, plan, moduleName, "META-INF/weblogic-ejb-jar.xml", (Source)null);
   }

   public AbstractDescriptorLoader2 getEjbDescriptorLoader() {
      return this.ejbJarDescriptor;
   }

   public AbstractDescriptorLoader2 getWlsEjbDescriptorLoader() {
      return this.wlsEjbJarDescriptor;
   }
}

package weblogic.osgi.internal;

import java.io.File;
import weblogic.utils.jars.VirtualJarFile;

public class SpecificDeploymentInfo {
   private String osgiLibName;
   private File rootOfApplication;
   private VirtualJarFile embeddedApplication;

   public String getOsgiLibName() {
      return this.osgiLibName;
   }

   public void setOsgiLibName(String osgiLibName) {
      this.osgiLibName = osgiLibName;
   }

   public File getRootOfApplication() {
      return this.rootOfApplication;
   }

   public void setRootOfApplication(File rootOfApplication) {
      this.rootOfApplication = rootOfApplication;
   }

   public VirtualJarFile getEmbeddedApplication() {
      return this.embeddedApplication;
   }

   public void setEmbeddedApplication(VirtualJarFile embeddedApplication) {
      this.embeddedApplication = embeddedApplication;
   }

   public String toString() {
      return "SpecificDeploymentInfo(" + this.osgiLibName + "," + System.identityHashCode(this) + ")";
   }
}

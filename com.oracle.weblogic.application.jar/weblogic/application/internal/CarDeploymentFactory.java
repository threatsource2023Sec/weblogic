package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class CarDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return this.isCar(file);
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   private boolean isCar(ApplicationArchive f) {
      if (f.getName().endsWith(".jar")) {
         return f.getEntry("META-INF/application-client.xml") != null;
      } else {
         return false;
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive f) throws DeploymentException {
      return this.isCar(f) ? new CarDeployment(mbean, f) : null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }

   private boolean isCar(File f) {
      if (f.isDirectory()) {
         return (new File(f, "META-INF/application-client.xml")).exists() || (new File(f, "META-INF/weblogic-application-client.xml")).exists();
      } else if (f.getName().endsWith(".jar")) {
         JarFile jf = null;

         boolean var4;
         try {
            jf = new JarFile(f);
            boolean var3 = jf.getEntry("META-INF/application-client.xml") != null || jf.getEntry("META-INF/weblogic-application-client.xml") != null;
            return var3;
         } catch (IOException var14) {
            var4 = false;
         } finally {
            try {
               if (jf != null) {
                  jf.close();
               }
            } catch (IOException var13) {
            }

         }

         return var4;
      } else {
         return false;
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return new CarDeployment(mbean, f);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return null;
   }
}

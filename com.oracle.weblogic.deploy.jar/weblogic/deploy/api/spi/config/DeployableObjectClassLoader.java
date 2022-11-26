package weblogic.deploy.api.spi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.deploy.model.DeployableObject;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public final class DeployableObjectClassLoader extends GenericClassLoader {
   private static final boolean debug;
   private final DeployableObject dObject;
   private final DeploymentPlanBean plan;
   private final File configDir;
   private final String moduleName;
   private final InputStream myIS;
   private final String myUri;

   public DeployableObjectClassLoader(DeployableObject dObject, DeploymentPlanBean plan, File configDir, String moduleName, ClassFinder finder, ClassLoader parent) {
      super(finder, parent);
      this.dObject = dObject;
      this.plan = plan;
      this.configDir = configDir;
      this.moduleName = moduleName;
      this.myIS = null;
      this.myUri = null;
   }

   public DeployableObjectClassLoader(InputStream is, String uri, ClassFinder finder, ClassLoader parent) {
      super(finder, parent);
      this.myIS = is;
      this.myUri = uri;
      this.dObject = null;
      this.plan = null;
      this.configDir = null;
      this.moduleName = null;
   }

   public InputStream getResourceAsStream(String uri) {
      if (debug) {
         Debug.say("Getting stream for " + uri);
      }

      if (uri.equals(this.myUri)) {
         if (this.myIS == null) {
            return null;
         } else {
            try {
               this.myIS.reset();
            } catch (IOException var7) {
            }

            this.myIS.mark(1000000);
            return this.myIS;
         }
      } else {
         InputStream is = super.getResourceAsStream(uri);
         if (is == null && this.dObject != null) {
            is = this.dObject.getEntry(uri);
         }

         if (is == null && this.plan != null) {
            ModuleDescriptorBean md = this.plan.findModuleDescriptor(this.moduleName, uri);
            if (md != null) {
               File config;
               if (this.plan.rootModule(this.moduleName)) {
                  config = this.configDir;
               } else {
                  config = new File(this.configDir, this.moduleName);
               }

               File dd = new File(config, md.getUri());

               try {
                  is = new FileInputStream(dd);
               } catch (FileNotFoundException var8) {
               }
            }
         }

         return (InputStream)is;
      }
   }

   static {
      ClassLoader.registerAsParallelCapable();
      debug = Debug.isDebug("config");
   }
}

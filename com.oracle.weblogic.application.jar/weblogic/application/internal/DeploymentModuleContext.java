package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.Extensible;
import weblogic.application.Module;
import weblogic.application.ModuleExtensionFactory;
import weblogic.application.ModuleWrapper;
import weblogic.application.naming.ModuleRegistry;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.GenericClassLoader;

public class DeploymentModuleContext extends ModuleContextImpl {
   private final String id;
   private FlowContext appCtx;
   private final String URI;
   private File cacheDir = null;
   private File generatedOutputDir = null;
   private final Module originalModule;

   public DeploymentModuleContext(String URI, String id, String applicationId, String applicationName, String moduleType, ApplicationContextInternal appCtx, ModuleRegistry moduleRegistry, Module module) {
      super(applicationId, applicationName, moduleType, moduleRegistry, (File)null, (DeploymentPlanBean)null);
      this.URI = URI;
      this.id = id;
      this.appCtx = (FlowContext)appCtx;
      if (module instanceof ModuleWrapper) {
         this.originalModule = ((ModuleWrapper)module).unwrap();
      } else {
         this.originalModule = module;
      }

   }

   public String getURI() {
      return this.URI;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      if (this.name == null && !this.appCtx.isEar()) {
         this.name = this.appCtx.getApplicationName();
      }

      return super.getName();
   }

   public DeploymentPlanBean getPlan() {
      return this.appCtx.findDeploymentPlan();
   }

   public File getConfigDir() {
      DeploymentPlanBean plan = this.appCtx.findDeploymentPlan();
      if (plan != null) {
         String configRoot = plan.getConfigRoot();
         if (configRoot != null) {
            return new File(configRoot);
         }
      }

      return null;
   }

   public GenericClassLoader getClassLoader() {
      AppClassLoaderManager manager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
      return manager.findModuleLoader(this.applicationId, this.id);
   }

   public ModuleValidationInfo getValidationInfo() {
      return null;
   }

   public void setupWLDirectories() {
      this.cacheDir = new File(this.appCtx.getApplicationFileManager().getOutputPath(), "META-INF/.WL_internal/cache/" + this.URI);
      this.generatedOutputDir = new File(this.appCtx.getApplicationFileManager().getOutputPath(), "META-INF/.WL_internal/generated/" + this.URI);
   }

   public File getCacheDir() {
      return this.cacheDir;
   }

   public File getGeneratedOutputDir() {
      return this.generatedOutputDir;
   }

   public void setupVirtualJarFile() throws IOException {
      if (this.URI != null) {
         this.vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile(this.URI);
         if (this.appCtx.getApplicationArchive() != null) {
            this.archive = this.appCtx.getApplicationArchive().getApplicationArchive(this.URI);
         }
      }

   }

   public boolean isExtensible() {
      return this.originalModule instanceof Extensible;
   }

   public Set getExtensionAnnotationClasses() {
      if (!this.isExtensible()) {
         return Collections.EMPTY_SET;
      } else {
         Set extensionAnnotationClasses = new HashSet();
         Iterator iterator = ApplicationFactoryManager.getApplicationFactoryManager().getModuleExtensionFactories(this.getType());

         while(iterator.hasNext()) {
            ModuleExtensionFactory factory = (ModuleExtensionFactory)iterator.next();
            Class[] annotationClasses = factory.getSupportedClassLevelAnnotations();
            Class[] var5 = annotationClasses;
            int var6 = annotationClasses.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Class annotation = var5[var7];
               extensionAnnotationClasses.add(annotation);
            }
         }

         return extensionAnnotationClasses;
      }
   }
}

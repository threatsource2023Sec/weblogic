package weblogic.application.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.library.LibraryProvider;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

class ConfigDescriptorManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private String appModuleName;
   private GenericClassLoader bindingClassLoader;
   private boolean useBindingCache = false;

   public ConfigDescriptorManager(String appModuleName) {
      this.appModuleName = appModuleName;
   }

   public void initBindingInfo(GenericClassLoader classLoader, String bindingJarUri, boolean useBindingCache) throws ModuleException {
      this.bindingClassLoader = classLoader;
      this.useBindingCache = useBindingCache;
      if (bindingJarUri != null) {
         URL bindingJarURL = this.bindingClassLoader.getResource(bindingJarUri);
         if (bindingJarURL == null) {
            throw new ModuleException("Unable to load " + bindingJarUri + " for module");
         }

         this.bindingClassLoader.addClassFinder(new ClasspathClassFinder2(bindingJarURL.getPath()));
      }

   }

   public void destroy() {
      if (this.useBindingCache) {
         ConfigDescriptorManagerCache.SINGLETON.removeEntry(this.bindingClassLoader);
      }

   }

   public DescriptorBean parseMergedDescriptorBean(ApplicationFileManager applicationFileManager, String applicationFileName, File configDir, DeploymentPlanBean deploymentPlan, String descriptorUri, LibraryProvider libraryProvider, ModuleType parentModuleType, String parentModuleUri, boolean mergeLibraryDescriptors, boolean ignoreMissingDescriptors) throws XMLStreamException, IOException, ModuleException {
      VirtualJarFile moduleVirtualJarFile;
      String moduleName;
      if (parentModuleType == ModuleType.EAR) {
         moduleVirtualJarFile = applicationFileManager.getVirtualJarFile();
         moduleName = applicationFileName;
      } else {
         moduleVirtualJarFile = applicationFileManager.getVirtualJarFile(parentModuleUri);
         moduleName = parentModuleUri;
      }

      AbstractDescriptorLoader2 loader = new AbstractDescriptorLoader2(moduleVirtualJarFile, configDir, deploymentPlan, moduleName, descriptorUri);
      DescriptorManager dm;
      if (this.useBindingCache) {
         dm = ConfigDescriptorManagerCache.SINGLETON.getEntry(this.bindingClassLoader);
      } else {
         dm = new DescriptorManager(this.bindingClassLoader);
      }

      loader.setDescriptorManager(dm);
      if (mergeLibraryDescriptors) {
         VirtualJarFile[] vjfs = null;

         try {
            vjfs = LibraryUtils.getLibraryVjarsWithDescriptor(libraryProvider, descriptorUri);
            if (vjfs.length > 0) {
               DescriptorBean bean = loader.mergeDescriptors(vjfs);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("loaded descriptor and libs to bean: " + bean);
               }

               if (bean == null && !ignoreMissingDescriptors) {
                  throw new ModuleException("Descriptor not found: " + descriptorUri + " for app module " + this.appModuleName);
               }

               DescriptorBean var17 = bean;
               return var17;
            }
         } finally {
            IOUtils.forceClose(vjfs);
         }
      }

      DescriptorBean bean = loader.loadDescriptorBean();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("loaded descriptor to bean: " + bean);
      }

      if (bean == null && !ignoreMissingDescriptors) {
         throw new ModuleException("Descriptor not found: " + descriptorUri + " for app module " + this.appModuleName);
      } else {
         return bean;
      }
   }
}

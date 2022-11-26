package weblogic.application;

import java.io.File;
import java.util.Set;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.naming.ModuleRegistry;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public interface ModuleContext {
   String getURI();

   String getId();

   String getName();

   String getApplicationId();

   String getPartitionName();

   String getApplicationName();

   String getType();

   ModuleRegistry getRegistry();

   File getConfigDir();

   DeploymentPlanBean getPlan();

   ModuleValidationInfo getValidationInfo();

   boolean isArchive();

   File getAltDDFile();

   File getOutputDir();

   String getOutputFileName();

   VirtualJarFile getVirtualJarFile();

   /** @deprecated */
   @Deprecated
   ApplicationArchive getApplicationArchive();

   GenericClassLoader getClassLoader();

   String getClassLoaderClassPath();

   ClassFinder getClassFinder();

   File getGeneratedOutputDir();

   File getCacheDir();

   boolean isExtensible();

   Set getExtensionAnnotationClasses();

   GenericClassLoader getTemporaryClassLoader();

   void removeTemporaryClassLoader();

   void markShareableConfiguration(boolean var1);

   Boolean checkShareableConfiguration();

   void markShareability();

   boolean checkShareability();

   void createdSharedModuleClassLoader();

   boolean wasSharedModuleClassLoaderCreated();
}

package weblogic.application.internal;

import java.io.File;
import weblogic.application.ModuleContext;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public abstract class ModuleContextImpl implements ModuleContext {
   protected final String applicationId;
   private final String partitionName;
   protected String name;
   private String applicationName;
   private final String moduleType;
   private final ModuleRegistry moduleRegistry;
   private final File configDir;
   private final DeploymentPlanBean plan;
   protected boolean isArchive;
   protected File outputDir;
   protected String outputFileName;
   protected VirtualJarFile vjf;
   protected ApplicationArchive archive;
   protected File altDDFile;
   protected GenericClassLoader moduleClassLoader;
   protected ClassFinder moduleClassFinder;
   private GenericClassLoader temporaryClassLoader = null;
   private Boolean shareableConfiguration = null;
   private boolean shareabilityEnabled = false;
   private boolean wasSharedModuleClassLoaderCreated = false;

   public ModuleContextImpl(String applicationId, String applicationName, String moduleType, ModuleRegistry moduleRegistry, File configDir, DeploymentPlanBean plan) {
      this.applicationId = applicationId;
      this.partitionName = ApplicationVersionUtils.getPartitionName(applicationId);
      this.applicationName = applicationName;
      this.moduleType = moduleType;
      this.moduleRegistry = moduleRegistry;
      this.configDir = configDir;
      this.plan = plan;
   }

   public String getApplicationId() {
      return this.applicationId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setApplicationName(String appName) {
      this.applicationName = appName;
   }

   public File getConfigDir() {
      return this.configDir;
   }

   public ModuleRegistry getRegistry() {
      return this.moduleRegistry;
   }

   public String getType() {
      return this.moduleType;
   }

   public String getName() {
      if (this.name == null) {
         throw new IllegalStateException("Module name has not yet been determined.");
      } else {
         return this.name;
      }
   }

   public void setName(String aName) {
      this.name = aName;
   }

   public DeploymentPlanBean getPlan() {
      return this.plan;
   }

   public File getAltDDFile() {
      return this.altDDFile;
   }

   public ClassFinder getClassFinder() {
      return this.moduleClassFinder;
   }

   public GenericClassLoader getClassLoader() {
      return this.moduleClassLoader;
   }

   public String getClassLoaderClassPath() {
      return ClassLoaderUtils.getClassLoaderClassPath(this.getClassLoader());
   }

   public File getOutputDir() {
      return this.outputDir;
   }

   public String getOutputFileName() {
      return this.outputFileName;
   }

   public VirtualJarFile getVirtualJarFile() {
      return this.vjf;
   }

   public ApplicationArchive getApplicationArchive() {
      return this.archive;
   }

   public boolean isArchive() {
      return this.isArchive;
   }

   public void setAltDDFile(File file) {
      this.altDDFile = file;
   }

   public void cleanup() {
      if (this.vjf != null) {
         IOUtils.forceClose(this.vjf);
         this.vjf = null;
      }

   }

   public GenericClassLoader getTemporaryClassLoader() {
      if (this.temporaryClassLoader == null) {
         GenericClassLoader moduleClassLoader = this.getClassLoader();
         if (moduleClassLoader != null) {
            this.temporaryClassLoader = weblogic.utils.classloaders.ClassLoaderUtils.createTemporaryAppClassLoader(moduleClassLoader);
         }
      }

      return this.temporaryClassLoader;
   }

   public void removeTemporaryClassLoader() {
      this.temporaryClassLoader = null;
   }

   public void markShareableConfiguration(boolean value) {
      this.shareableConfiguration = value;
   }

   public Boolean checkShareableConfiguration() {
      return this.shareableConfiguration;
   }

   public void markShareability() {
      this.shareabilityEnabled = true;
   }

   public boolean checkShareability() {
      return this.shareabilityEnabled;
   }

   public void createdSharedModuleClassLoader() {
      this.wasSharedModuleClassLoaderCreated = true;
   }

   public boolean wasSharedModuleClassLoaderCreated() {
      return this.wasSharedModuleClassLoaderCreated;
   }
}

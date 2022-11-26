package weblogic.management.mbeans.custom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.StagingDirectory;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.deploy.ApplicationsDirPoller;
import weblogic.management.deploy.internal.DeploymentManagerImpl;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public class AppDeployment extends ConfigurationMBeanCustomizer {
   private static final boolean debug = false;
   private static final String APP_DIR = "app";
   private static final String DEPLOYMENTS_DIR = "config/deployments";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private transient String appName = null;
   private transient String versionId = null;
   private transient StagingDirectory sd = null;
   private transient DeploymentPlanBean plan = null;
   private transient String configuredAppId = null;
   private transient boolean isMultiVersionApp = false;
   private String installDir = null;
   private String planPath = null;
   private String planDir = null;
   private String altDescriptorPath = null;
   private String altDescriptorDir = null;
   private String sourcePath = null;
   private String configPath = null;
   private String stagingMode = null;
   private String planStagingMode = null;

   public AppDeployment(ConfigurationMBeanCustomized base) {
      super(base);
   }

   private String getDeploymentsDir() {
      if (this.configPath == null) {
         this.configPath = this.resolveToBase("config/deployments", (String)null);
      }

      return this.configPath;
   }

   private String resolveToBase(String dir, String base) {
      dir = FileUtils.normalize(dir);
      base = FileUtils.normalize(base);
      String newDir = dir;
      if (dir != null) {
         File dirf = new File(dir);
         if (!FileUtils.isAbsolute(dirf)) {
            if (base == null) {
               newDir = (new File(DomainDir.getRootDir(), dir)).getAbsolutePath();
            } else {
               newDir = (new File(base, dir)).getAbsolutePath();
            }
         }
      }

      return newDir;
   }

   public String getInstalldir() {
      return this.installDir;
   }

   public void setInstalldir(String installDir) {
      if (this.isTemplateParent()) {
         this.installDir = installDir;
      } else {
         String computedInstallDir = installDir;
         if (verifyIfPathIsInRootDir(installDir)) {
            computedInstallDir = makePathRelativeToRootDir(installDir);
         }

         this.installDir = computedInstallDir;
      }
   }

   public String getPlanPath() {
      return this.planPath;
   }

   public void setPlanPath(String planPath) {
      if (!this.isTemplateParent() && planPath != null) {
         String computedPlanPath = planPath;
         String thePlanDir = this.getAbsolutePlanDir();
         File planDirFile;
         if (thePlanDir != null) {
            planDirFile = new File(thePlanDir);
            if (verifyIfPathIsIn(planPath, planDirFile)) {
               computedPlanPath = makePathRelativeTo(planPath, planDirFile);
            }
         } else {
            planDirFile = new File(DomainDir.getRootDir());
            if (verifyIfPathIsIn(planPath, planDirFile)) {
               computedPlanPath = makePathRelativeTo(planPath, planDirFile);
            }
         }

         this.planPath = computedPlanPath;
      } else {
         this.planPath = planPath;
      }
   }

   public String getPlanDir() {
      return this.planDir;
   }

   public void setPlanDir(String planDir) {
      if (this.isTemplateParent()) {
         this.planDir = planDir;
      } else {
         String computedPlanDir = planDir;
         String beanPlanPath;
         if (planDir != null) {
            beanPlanPath = this.getAbsoluteInstallDir();
            File domainRoot;
            if (beanPlanPath != null && (new File(beanPlanPath)).exists()) {
               domainRoot = new File(beanPlanPath);
               if (verifyIfPathIsIn(planDir, domainRoot)) {
                  computedPlanDir = makePathRelativeTo(planDir, domainRoot);
               }
            } else {
               domainRoot = new File(DomainDir.getRootDir());
               if (verifyIfPathIsIn(planDir, domainRoot)) {
                  computedPlanDir = makePathRelativeTo(planDir, domainRoot);
               }
            }
         }

         this.planDir = computedPlanDir;
         if (this.planDir != null) {
            beanPlanPath = ((AppDeploymentMBean)this.getMbean()).getPlanPath();
            if (beanPlanPath != null && FileUtils.isAbsolute(new File(beanPlanPath))) {
               this.setPlanPath(((AppDeploymentMBean)this.getMbean()).getPlanPath());
            }
         }

      }
   }

   public String createPlan() {
      return DeploymentManagerImpl.createPlan(this.getAbsoluteSourcePath());
   }

   public String createPlan(String planPath) {
      return DeploymentManagerImpl.createPlan(this.getAbsoluteSourcePath(), planPath);
   }

   public String getAltDescriptorPath() {
      return this.altDescriptorPath;
   }

   public void setAltDescriptorPath(String altDescriptorPath) {
      if (!this.isTemplateParent() && altDescriptorPath != null) {
         String computedAltDescriptorPath = altDescriptorPath;
         String theAltDescriptorDir = this.getAbsoluteAltDescriptorDir();
         File altDescriptorDirFile;
         if (theAltDescriptorDir != null) {
            altDescriptorDirFile = new File(theAltDescriptorDir);
            if (verifyIfPathIsIn(altDescriptorPath, altDescriptorDirFile)) {
               computedAltDescriptorPath = makePathRelativeTo(altDescriptorPath, altDescriptorDirFile);
            }
         } else {
            altDescriptorDirFile = new File(DomainDir.getRootDir());
            if (verifyIfPathIsIn(altDescriptorPath, altDescriptorDirFile)) {
               computedAltDescriptorPath = makePathRelativeTo(altDescriptorPath, altDescriptorDirFile);
            }
         }

         this.altDescriptorPath = computedAltDescriptorPath;
      } else {
         this.altDescriptorPath = altDescriptorPath;
      }
   }

   public String getSourcePath() {
      return this.sourcePath;
   }

   public void setSourcePath(String sourcePath) {
      if (!this.isTemplateParent() && sourcePath != null) {
         String computedSourcePath = sourcePath;
         String absoluteAppDir = this.getAbsoluteAppDir();
         if (absoluteAppDir != null && (new File(absoluteAppDir)).exists()) {
            File theAppDirFile = new File(absoluteAppDir);
            if (verifyIfPathIsIn(sourcePath, theAppDirFile)) {
               computedSourcePath = makePathRelativeTo(sourcePath, theAppDirFile);
            }
         } else if (verifyIfPathIsInRootDir(sourcePath)) {
            computedSourcePath = makePathRelativeToRootDir(sourcePath);
         }

         this.sourcePath = computedSourcePath;
      } else {
         this.sourcePath = sourcePath;
      }
   }

   public String getAbsoluteInstallDir() {
      return this.isTemplateParent() ? this.installDir : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getInstallDir(), (String)null);
   }

   public String getAbsolutePlanPath() {
      return this.isTemplateParent() ? this.planPath : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getPlanPath(), this.getAbsolutePlanDir());
   }

   public String getAbsolutePlanDir() {
      return this.isTemplateParent() ? this.planDir : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getPlanDir(), this.getAbsoluteInstallDir());
   }

   public String getAbsoluteAltDescriptorPath() {
      return this.isTemplateParent() ? this.altDescriptorPath : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getAltDescriptorPath(), this.getAbsoluteAltDescriptorDir());
   }

   public String getAbsoluteAltDescriptorDir() {
      return this.isTemplateParent() ? this.altDescriptorDir : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getAltDescriptorDir(), this.getAbsoluteInstallDir());
   }

   private String getAbsoluteAppDir() {
      String inst = this.getAbsoluteInstallDir();
      return inst != null ? this.resolveToBase("app", inst) : null;
   }

   public String getAbsoluteSourcePath() {
      return this.isTemplateParent() ? this.sourcePath : this.resolveToBase(((AppDeploymentMBean)this.getMbean()).getSourcePath(), this.getAbsoluteAppDir());
   }

   public String getApplicationIdentifier() {
      return this.getMbean().getName();
   }

   public String getApplicationName() {
      if (this.appName == null) {
         this.appName = ApplicationVersionUtils.getApplicationName(this.getApplicationIdentifier());
      }

      return this.appName;
   }

   public String getVersionIdentifier() {
      if (this.versionId == null) {
         this.versionId = ApplicationVersionUtils.getVersionId(this.getApplicationIdentifier());
      }

      return this.versionId;
   }

   public void setStagingMode(String mode) throws ManagementException {
      this.stagingMode = mode;
   }

   public String getStagingMode() {
      return this.stagingMode;
   }

   public void setPlanStagingMode(String mode) throws ManagementException {
      this.planStagingMode = mode;
   }

   public String getPlanStagingMode() {
      return this.planStagingMode;
   }

   public boolean isAutoDeployedApp() {
      return ApplicationsDirPoller.isInAppsDir(new File(DomainDir.getAppPollerDir()), this.getAbsoluteSourcePath());
   }

   public static boolean deriveDefaultIsParallelDeployModules(WebLogicMBean parent) {
      if (parent instanceof DomainMBean) {
         return ((DomainMBean)parent).isParallelDeployApplicationModules();
      } else if (parent instanceof ResourceGroupTemplateMBean) {
         return deriveDefaultIsParallelDeployModules(parent.getParent());
      } else {
         return parent instanceof PartitionMBean ? ((PartitionMBean)parent).isParallelDeployApplicationModules() : false;
      }
   }

   public static String derivePartitionName(WebLogicMBean parent, String deploymentName) {
      if (parent instanceof ResourceGroupTemplateMBean) {
         return derivePartitionName(parent.getParent(), deploymentName);
      } else if (parent instanceof PartitionMBean) {
         return ((PartitionMBean)parent).getName();
      } else {
         String partitionName = ApplicationVersionUtils.getPartitionName(deploymentName);
         if ("DOMAIN".equals(partitionName)) {
            partitionName = null;
         }

         return partitionName;
      }
   }

   public ApplicationMBean getAppMBean() {
      DomainMBean domain = (DomainMBean)this.getMbean().getDescriptor().getRootBean();
      if (domain == null) {
         return null;
      } else {
         String name = this.getMbean().getName();
         ApplicationMBean app = domain.lookupApplication(name);
         return app;
      }
   }

   public static String getInitialSecurityDDModel(AppDeploymentMBean mbean) {
      String model = "DDOnly";
      DomainMBean domain = (DomainMBean)mbean.getDescriptor().getRootBean();
      if (domain != null) {
         RealmMBean realm = domain.getSecurityConfiguration().getDefaultRealm();
         if (realm != null) {
            model = realm.getSecurityDDModel();
         }
      }

      return model;
   }

   private StagingDirectory getStagingDirectory() throws IOException {
      if (this.sd == null) {
         this.sd = new StagingDirectory(this.getAbsolutePlanPath(), this.getAbsoluteAltDescriptorPath(), (new File(this.getAbsoluteSourcePath())).getName(), this.getRootStagingDir());
      }

      return this.sd;
   }

   public String getLocalInstallDir() {
      this.assertOnServer();
      String p = this.getAbsoluteInstallDir();
      if (this.isStagedOnThisServer()) {
         try {
            p = this.getStagingDirectory().getRoot();
         } catch (IOException var3) {
         }
      }

      return p;
   }

   public String getLocalPlanPath() {
      this.assertOnServer();
      String p = this.getAbsolutePlanPath();
      if (p != null && this.isPlanStaged()) {
         try {
            p = this.getStagingDirectory().getPlan();
         } catch (IOException var3) {
         }
      }

      return p;
   }

   public String getLocalAltDescriptorPath() {
      this.assertOnServer();
      String a = this.getAbsoluteAltDescriptorPath();
      if (a != null && this.isStaged()) {
         try {
            a = this.getStagingDirectory().getAltDescriptor();
         } catch (IOException var3) {
         }
      }

      return a;
   }

   public String getLocalPlanDir() throws IOException {
      this.assertOnServer();
      String p = this.getAbsolutePlanDir();
      if (p != null && this.isPlanStaged()) {
         p = this.getStagingDirectory().getPlanDir();
      }

      return p;
   }

   public String getLocalSourcePath() {
      this.assertOnServer();
      String p = this.getAbsoluteSourcePath();
      if (p != null && this.isStagedOnThisServer()) {
         try {
            p = this.getStagingDirectory().getSource();
         } catch (IOException var3) {
            throw new IllegalArgumentException(var3.getMessage(), var3);
         }
      }

      return p;
   }

   private void assertOnServer() {
      if (ManagementService.getPropertyService(kernelId) == null) {
         throw new IllegalStateException();
      }
   }

   private boolean isStaged() {
      String mode = this.getStagingMode(ManagementService.getPropertyService(kernelId).getServerName());
      return !"nostage".equals(mode);
   }

   private boolean isPlanStaged() {
      String planMode = ((AppDeploymentMBean)this.getMbean()).getPlanStagingMode();
      if (planMode == null) {
         return this.isStaged();
      } else {
         return !"nostage".equals(planMode);
      }
   }

   private static String makePathRelativeToRootDir(String sourcePath) {
      return makePathRelativeTo(sourcePath, new File(DomainDir.getRootDir()));
   }

   private static String makePathRelativeTo(String sourcePath, File givenRootDir) {
      if (!givenRootDir.exists()) {
         return sourcePath;
      } else if (sourcePath == null) {
         return sourcePath;
      } else {
         try {
            String canonicalRootDir = givenRootDir.getCanonicalPath();
            String canonicalSourcePath = (new File(sourcePath)).getCanonicalPath();
            if (!canonicalSourcePath.startsWith(canonicalRootDir)) {
               return sourcePath;
            } else if (canonicalRootDir.length() == canonicalSourcePath.length()) {
               return ".";
            } else {
               String relativePath = canonicalSourcePath.substring(canonicalRootDir.length() + 1);
               File returnFile = new File(givenRootDir, relativePath);
               return !returnFile.exists() ? sourcePath : relativePath;
            }
         } catch (IOException var6) {
            return sourcePath;
         }
      }
   }

   private static boolean verifyIfPathIsInRootDir(String sourcePath) {
      return verifyIfPathIsIn(sourcePath, new File(DomainDir.getRootDir()));
   }

   private static boolean verifyIfPathIsIn(String sourcePath, File givenRootDir) {
      if (!givenRootDir.exists()) {
         return false;
      } else if (sourcePath == null) {
         return false;
      } else {
         File sourceFile = new File(sourcePath);
         if (!FileUtils.isAbsolute(sourceFile)) {
            return false;
         } else {
            try {
               File rootDir = givenRootDir.getCanonicalFile();

               File parentFile;
               for(File currentFile = sourceFile; (parentFile = currentFile.getParentFile()) != null; currentFile = currentFile.getParentFile()) {
                  parentFile = parentFile.getCanonicalFile();
                  if (parentFile.equals(rootDir)) {
                     return true;
                  }
               }

               return false;
            } catch (IOException var6) {
               return false;
            }
         }
      }
   }

   public String getRootStagingDir() {
      this.assertOnServer();
      String partitionName = this.getPartitionName();
      String partitionStageName;
      if (partitionName != null) {
         partitionStageName = this.getRootStagingDir(partitionName);
         if (partitionStageName != null) {
            return partitionStageName;
         }
      }

      partitionStageName = ManagementService.getRuntimeAccess(kernelId).getServer().getStagingDirectoryName();
      File sd = new File(partitionStageName, this.getApplicationName());
      if (this.getVersionIdentifier() != null) {
         sd = new File(sd, this.getVersionIdentifier());
      }

      return sd.getPath();
   }

   public String getStagingMode(String server) {
      this.assertOnServer();
      String mode = ((AppDeploymentMBean)this.getMbean()).getStagingMode();
      if (mode == null || mode.length() == 0) {
         mode = DeployHelper.getServerStagingMode(server);
      }

      return mode;
   }

   public DeploymentPlanBean getDeploymentPlanDescriptor() {
      if (this.isTemplateParent()) {
         return null;
      } else if (this.plan != null) {
         return this.plan;
      } else {
         String p = this.getLocalPlanPath();
         if (p != null) {
            File pf = new File(p);

            try {
               DeploymentPlanDescriptorLoader dpd = new DeploymentPlanDescriptorLoader(pf);
               this.plan = dpd.getDeploymentPlanBean();
            } catch (XMLStreamException var5) {
               throw new IllegalArgumentException(var5.getMessage(), var5);
            } catch (IOException var6) {
               throw new IllegalArgumentException(var6.getMessage(), var6);
            } catch (ClassCastException var7) {
               Loggable l = SlaveDeployerLogger.logUnknownPlanLoggable(p, this.getName());
               throw new IllegalArgumentException(l.getMessage(), var7);
            }
         }

         return this.plan;
      }
   }

   public void setDeploymentPlanDescriptor(DeploymentPlanBean plan) {
      this.plan = plan;
   }

   public byte[] getDeploymentPlan() {
      if (this.isTemplateParent()) {
         return null;
      } else {
         String planPath = this.getLocalPlanPath();
         if (planPath != null) {
            File localPlanFile = new File(planPath);
            if (localPlanFile.exists()) {
               try {
                  FileInputStream fis = new FileInputStream(localPlanFile);
                  int len = fis.available();
                  byte[] b = new byte[len];
                  fis.read(b);
                  fis.close();
                  return b;
               } catch (IOException var6) {
                  throw new IllegalArgumentException(var6.getMessage(), var6);
               }
            }
         }

         return null;
      }
   }

   public byte[] getDeploymentPlanExternalDescriptors() {
      if (this.isTemplateParent()) {
         return null;
      } else {
         DeploymentPlanBean bean = this.getDeploymentPlanDescriptor();
         if (bean == null) {
            return null;
         } else {
            try {
               boolean hasExternal = false;
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               ZipOutputStream zos = new ZipOutputStream(bos);
               String cfgRoot = bean.getConfigRoot();
               ModuleOverrideBean[] mods = bean.getModuleOverrides();
               ModuleOverrideBean[] var7 = mods;
               int var8 = mods.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  ModuleOverrideBean mod = var7[var9];
                  ModuleDescriptorBean[] descs = mod.getModuleDescriptors();
                  ModuleDescriptorBean[] var12 = descs;
                  int var13 = descs.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     ModuleDescriptorBean desc = var12[var14];
                     if (desc.isExternal()) {
                        File config;
                        String entryName;
                        if (bean.rootModule(mod.getModuleName())) {
                           config = new File(cfgRoot);
                           entryName = "";
                        } else {
                           config = new File(cfgRoot, mod.getModuleName());
                           entryName = mod.getModuleName() + "/";
                        }

                        File configFile = new File(config, desc.getUri());
                        if (configFile.exists()) {
                           hasExternal = true;
                           entryName = entryName + desc.getUri();
                           zos.putNextEntry(new ZipEntry(entryName));
                           FileInputStream fis = new FileInputStream(new File(config, desc.getUri()));
                           int len = fis.available();
                           byte[] b = new byte[len];
                           fis.read(b);
                           fis.close();
                           zos.write(b);
                           zos.closeEntry();
                        }
                     }
                  }
               }

               if (!hasExternal) {
                  return null;
               } else {
                  zos.close();
                  return bos.toByteArray();
               }
            } catch (IOException var22) {
               throw new IllegalArgumentException(var22.getMessage(), var22);
            }
         }
      }
   }

   private boolean isStagedOnThisServer() {
      if (!this.isStaged()) {
         return false;
      } else {
         AppDeploymentMBean appMBean = (AppDeploymentMBean)this.getMbean();
         return TargetHelper.isTargetedLocaly(appMBean) || TargetHelper.isPinnedToServerInCluster(appMBean);
      }
   }

   public String getConfiguredApplicationIdentifier() {
      return this.configuredAppId;
   }

   private boolean isTemplateParent() {
      AppDeploymentMBean appMBean = (AppDeploymentMBean)this.getMbean();
      return appMBean.getParent() instanceof ResourceGroupTemplateMBean && !(appMBean.getParent() instanceof ResourceGroupMBean);
   }

   private String getRootStagingDir(String partitionName) {
      this.assertOnServer();
      String retVal = null;
      if (partitionName != null && partitionName.length() > 0) {
         PartitionMBean partitionMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
         if (partitionMBean != null) {
            PartitionFileSystemMBean pfs = partitionMBean.getSystemFileSystem();
            String serverName = ManagementService.getPropertyService(kernelId).getServerName();
            StringBuilder var10000 = (new StringBuilder()).append(pfs.getRoot()).append(File.separator).append("servers").append(File.separator).append(serverName).append(File.separator);
            ManagementService.getRuntimeAccess(kernelId).getServer();
            String partitionStagingDir = var10000.append("stage").append(File.separator).append(this.getApplicationName()).toString();
            if (this.getVersionIdentifier() != null) {
               partitionStagingDir = partitionStagingDir + File.separator + this.getVersionIdentifier();
            }

            retVal = partitionStagingDir;
         }
      }

      return retVal;
   }

   public void setConfiguredApplicationIdentifier(String configuredAppId) {
      this.configuredAppId = configuredAppId;
   }

   private String getPartitionName() {
      ConfigurationMBean mbean = this.getMbean();
      return derivePartitionName(mbean.getParent(), mbean.getName());
   }

   public boolean isMultiVersionApp() {
      return this.isMultiVersionApp;
   }

   public void setMultiVersionApp(boolean isMultiVersionApp) {
      this.isMultiVersionApp = isMultiVersionApp;
   }
}

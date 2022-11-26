package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import weblogic.deploy.compatibility.NotificationBroadcaster;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public class AppData extends Data {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String appName;
   private String appVersionIdentifier;
   private String partition;
   private String appId;
   private String appRootLocation;
   private String stageLocation;
   private String originalPlanPath;
   private String relativePlanPath;
   private String originalAltDescriptorPath;
   private String relativeAltDescriptorPath;
   private BasicDeployment deployment;
   private final boolean isAppDeployment;
   private File srcFile;
   private final String srcFileLocation;
   private BasicDeploymentMBean deplMBean;

   public AppData(ServerMBean server, BasicDeploymentMBean mbean, BasicDeployment deployment, String stagingMode, String location, String lockPath) {
      this(server, mbean, deployment, stagingMode, (String)null, location, lockPath);
   }

   public AppData(ServerMBean server, BasicDeploymentMBean mbean, BasicDeployment deployment, String stagingMode, String planStagingMode, String location, String lockPath) {
      this(server, mbean, (String)null, deployment, stagingMode, planStagingMode, location, lockPath);
   }

   public AppData(ServerMBean server, BasicDeploymentMBean mbean, String name, BasicDeployment deployment, String stagingMode, String planStagingMode, String location, String lockPath) {
      super(location, lockPath, stagingMode, planStagingMode);
      this.appName = null;
      this.appVersionIdentifier = null;
      this.partition = null;
      this.appId = null;
      this.appRootLocation = null;
      this.stageLocation = null;
      this.originalPlanPath = null;
      this.relativePlanPath = null;
      this.originalAltDescriptorPath = null;
      this.relativeAltDescriptorPath = null;
      this.deployment = null;
      this.srcFile = null;
      this.deployment = deployment;
      this.deplMBean = mbean;
      this.appId = name != null ? name : mbean.getName();
      this.isAppDeployment = mbean instanceof AppDeploymentMBean;
      this.setAppRootLocation(server, mbean);
      if (this.isAppDeployment) {
         AppDeploymentMBean appMBean = (AppDeploymentMBean)mbean;
         this.appName = appMBean.getApplicationName();
         this.appVersionIdentifier = appMBean.getVersionIdentifier();
         this.partition = appMBean.getPartitionName();
         this.originalPlanPath = appMBean.getLocalPlanPath();
         this.originalAltDescriptorPath = appMBean.getLocalAltDescriptorPath();
         if (!this.isStagingEnabled() && !"external_stage".equals(stagingMode)) {
            this.srcFileLocation = appMBean.getAbsoluteSourcePath();
         } else {
            this.srcFileLocation = this.getLocation();
         }
      } else {
         this.appName = name != null ? name : mbean.getName();
         this.srcFileLocation = this.getLocation();
      }

   }

   public String getAppName() {
      return this.appName;
   }

   public String getAppVersionIdentifier() {
      return this.appVersionIdentifier;
   }

   public String getPartition() {
      return this.partition;
   }

   protected DataUpdate createDataUpdate(DataUpdateRequestInfo reqInfo) {
      if (reqInfo.isDelete()) {
         return this.isStagingEnabled() ? new AppDataDeleteUpdate(this, reqInfo) : null;
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         if (!runtimeAccess.isAdminServer() && !runtimeAccess.isAdminServerAvailable()) {
            if (isDebugEnabled()) {
               debug(" createDataUpdate - AdminServer unavailable");
            }

            return null;
         } else {
            boolean needsRestage = reqInfo.isStaging();
            if (!needsRestage && this.isStagingEnabled()) {
               if (reqInfo instanceof ModuleRedeployDataUpdateRequestInfo) {
                  return new ModuleRedeployDataUpdate(this, reqInfo);
               }

               if (reqInfo.isPlanUpdate()) {
                  return new PlanDataUpdate(this, reqInfo);
               }

               if (isDebugEnabled()) {
                  debug(" createDataUpdate - staging location: " + this.getLocation());
               }

               File loc = new File(this.getLocation());
               if (!loc.exists()) {
                  needsRestage = true;
               } else if (!this.isAppDeployment) {
                  needsRestage = false;
               } else if (reqInfo.isStatic()) {
                  int stagingState = this.deployment.getStagingState();
                  if (!this.isRestageOnlyOnRedeploy()) {
                     if (stagingState < 1) {
                        needsRestage = true;
                        if (isDebugEnabled()) {
                           debug(" createDataUpdate - needs restage stagingState: " + stagingState);
                        }
                     } else {
                        long archiveTime = this.deployment.getArchiveTimeStamp();
                        long planTime = this.deployment.getPlanTimeStamp();
                        if (isDebugEnabled()) {
                           debug(" createDataUpdate - check if needs restage archive ts: " + archiveTime + " loc.lastModified: " + loc.lastModified());
                        }

                        if (archiveTime > 0L && loc.lastModified() < archiveTime) {
                           needsRestage = true;
                        } else if (this.originalPlanPath != null) {
                           File plan = new File(this.originalPlanPath);
                           if (isDebugEnabled()) {
                              debug(" createDataUpdate - check if needs restage plan ts: " + planTime + " plan.lastModified: " + plan.lastModified());
                           }

                           if (!plan.exists() || planTime > 0L && plan.lastModified() < planTime) {
                              needsRestage = true;
                           }
                        }
                     }
                  }
               } else {
                  needsRestage = true;
               }
            }

            if (isDebugEnabled()) {
               debug(" +++ [" + this + "] needsRestage : " + needsRestage);
            }

            if (needsRestage) {
               AppDataUpdate theUpdate = new AppDataUpdate(this, reqInfo);
               return theUpdate;
            } else {
               return null;
            }
         }
      }
   }

   public final String getRootLocation() {
      return this.appRootLocation;
   }

   public final String getPlanPath() {
      return this.originalPlanPath;
   }

   public final String getRelativePlanPath() throws IOException {
      if (this.relativePlanPath == null || this.relativePlanPath.length() == 0) {
         if (!this.isPlanStagingEnabled()) {
            if (isDebugEnabled()) {
               debug(" getRelativePlanPath():nostage - returning original plan path :" + this.originalPlanPath);
            }

            this.relativePlanPath = this.originalPlanPath;
         } else {
            if (this.originalPlanPath == null) {
               return null;
            }

            File planFile = new File(this.originalPlanPath);
            if (isDebugEnabled()) {
               debug(" getRelativePlanPath(): plan file name : " + planFile.getAbsolutePath());
            }

            File appRoot = new File(this.getRootLocation());
            if (isDebugEnabled()) {
               debug(" getRelativePlanPath(): application root dir : " + appRoot);
            }

            String createdRelativePath = this.createRelativePath(appRoot, planFile);
            if (isDebugEnabled()) {
               debug(" getRelativePlanPath(): Created relative path for plan file : " + createdRelativePath);
            }

            this.relativePlanPath = createdRelativePath;
         }
      }

      return this.relativePlanPath;
   }

   public final String getRelativeAltDescriptorPath() throws IOException {
      if (this.relativeAltDescriptorPath == null || this.relativeAltDescriptorPath.length() == 0) {
         if (!this.isStagingEnabled()) {
            if (isDebugEnabled()) {
               debug(" getRelativeAltDescriptorPath():nostage - returning original AltDescriptor path :" + this.originalAltDescriptorPath);
            }

            this.relativeAltDescriptorPath = this.originalAltDescriptorPath;
         } else {
            if (this.originalAltDescriptorPath == null) {
               return null;
            }

            File AltDescriptorFile = new File(this.originalAltDescriptorPath);
            if (isDebugEnabled()) {
               debug(" getRelativeAltDescriptorPath(): AltDescriptor file name : " + AltDescriptorFile.getAbsolutePath());
            }

            File appRoot = new File(this.getRootLocation());
            if (isDebugEnabled()) {
               debug(" getRelativeAltDescriptorPath(): application root dir : " + appRoot);
            }

            String createdRelativePath = this.createRelativePath(appRoot, AltDescriptorFile);
            if (isDebugEnabled()) {
               debug(" getRelativeAltDescriptorPath(): Created relative path for AltDescriptor file : " + createdRelativePath);
            }

            this.relativeAltDescriptorPath = createdRelativePath;
         }
      }

      return this.relativeAltDescriptorPath;
   }

   public final void updateDescriptorsPathInfo(BasicDeploymentMBean mbean) {
      if (this.isAppDeployment && mbean instanceof AppDeploymentMBean) {
         this.originalPlanPath = ((AppDeploymentMBean)mbean).getLocalPlanPath();
         this.relativePlanPath = null;
      }

   }

   public void releaseLock(long requestId) {
   }

   public boolean removeStagedFiles() {
      if (this.isAppDeployment) {
         File rootDir = null;
         rootDir = new File(this.getRootLocation());
         if (rootDir.exists()) {
            File stageDir = new File(this.stageLocation);
            File source = rootDir;
            File parent = null;
            boolean removedSuccessfully = true;

            do {
               parent = source.getParentFile();
               File[] children = source.listFiles();
               if (children != null) {
                  for(int n = 0; n < children.length; ++n) {
                     if (this.isAppStagingEnabled() && this.getSourceFile().getPath().equals(children[n].getPath()) || (this.isPlanStagingEnabled() || "nostage".equals(this.getPlanStagingMode())) && "plan".equals(children[n].getName())) {
                        removedSuccessfully &= FileUtils.remove(children[n]);
                     }
                  }
               }

               if (stageDir.equals(parent)) {
                  children = source.listFiles();
                  if (children == null || children.length == 0) {
                     removedSuccessfully &= FileUtils.remove(source);
                  }
               }

               source = parent;
            } while(removedSuccessfully && parent != null && !stageDir.equals(parent));

            String debugStmt = removedSuccessfully ? " Removed staged files for deployment  - " + this.appName + " successfully" : " Couldn't remove staged files for app deployment  - " + this.appName;
            if (isDebugEnabled()) {
               debug(debugStmt);
            }

            if (!removedSuccessfully) {
               this.dumpFiles(rootDir, "");
            }

            return removedSuccessfully;
         }

         if (isDebugEnabled()) {
            debug(" Staging directory '" + rootDir + "' does not exists. So, nothing to remove here.");
         }
      }

      return true;
   }

   public synchronized File getSourceFile() {
      if (this.srcFile == null) {
         this.srcFile = new File(this.srcFileLocation);
      }

      return this.srcFile;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toString()).append("(appId=");
      sb.append(this.appId).append(")");
      return sb.toString();
   }

   protected final void prePrepareDataUpdate() {
      NotificationBroadcaster.sendAppNotification("distributing", this.appId, (String)null);
   }

   protected final void preCommitDataUpdate() throws DeploymentException {
      DataUpdate update = this.getCurrentDataUpate();
      if (update instanceof AppDataUpdate) {
         if (update != null && ((AppDataUpdate)update).isFullUpdate()) {
            boolean removedSuccessfully = this.removeStagedFiles();
            File appLocation = new File(this.getLocation());
            if (!removedSuccessfully && appLocation.exists()) {
               this.deployment.relayStagingState(0);
               Loggable l = SlaveDeployerLogger.logRemoveStagedFilesFailedLoggable(this.appName, appLocation.toString());
               throw new DeploymentException(l.getMessage());
            }
         }

      }
   }

   protected void postCommitDataUpdate() {
      NotificationBroadcaster.sendAppNotification("distributed", this.appId, (String)null);
      this.deployment.relayStagingState(1);
      if (this.isAppDeployment) {
         MBeanConverter.addStagedTarget((AppDeploymentMBean)this.deployment.getDeploymentMBean(), ManagementService.getRuntimeAccess(kernelId).getServerName());
      }

   }

   protected void onFailure(Throwable failure) {
      super.onFailure(failure);
      this.deployment.relayStagingState(0);
   }

   public void deleteFile(String targetURI, long requestId) {
   }

   public File getFileFor(long requestId, String targetPath) {
      return null;
   }

   protected final boolean isSystemResource() {
      return !this.isAppDeployment;
   }

   private String createRelativePath(File baseFile, File toFile) throws IOException {
      boolean debug = isDebugEnabled();
      String baseLocation = baseFile.getCanonicalPath();
      if (debug) {
         debug(" createRelativePath(): given baseFile: " + baseLocation);
      }

      String givenURI = toFile.getCanonicalPath();
      if (debug) {
         debug(" createRelativePath(): given givenURI: " + givenURI);
      }

      String result = null;
      if (baseLocation.equals(this.appRootLocation)) {
         result = baseLocation;
      } else {
         int baseLocationIndex = givenURI.indexOf(baseLocation);
         if (debug) {
            debug(" createRelativePath(): indexOf givenURI in given baseFile: " + baseLocationIndex);
         }

         if (baseLocationIndex == -1) {
            throw new AssertionError("uri '" + givenURI + "' is not sub dir of'" + baseLocation + "'");
         }

         String rootPath = givenURI.substring(baseLocation.length() + 1, givenURI.indexOf(toFile.getName()));
         if (debug) {
            debug(" createRelativePath(): relative rootPath of givenURI : " + rootPath);
         }

         result = rootPath + toFile.getName();
      }

      if (debug) {
         debug(" createRelativePath(): returning result : " + result);
      }

      return result;
   }

   private void setAppRootLocation(ServerMBean server, BasicDeploymentMBean mbean) {
      boolean debug = isDebugEnabled();
      if (!this.isStagingEnabled()) {
         this.appRootLocation = null;
      }

      if (this.isAppDeployment) {
         this.appRootLocation = ((AppDeploymentMBean)mbean).getRootStagingDir();
         if (this.appRootLocation != null) {
            String appRootDir = this.appRootLocation.replaceAll("\\\\", "/");
            String stagingValue = "/" + "stage" + "/";
            this.stageLocation = appRootDir.substring(0, appRootDir.lastIndexOf(stagingValue) + (stagingValue.length() - 1));
         }

         if (mbean.getPartitionName() == null) {
            this.stageLocation = server.getStagingDirectoryName();
         }

         if (debug) {
            debug("appRootLocation set to: " + this.appRootLocation);
         }

         if (debug) {
            debug("stageLocation set to: " + this.stageLocation);
         }
      } else {
         this.appRootLocation = DomainDir.getConfigDir();
      }

   }

   private boolean isRestageOnlyOnRedeploy() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain().getDeploymentConfiguration().isRestageOnlyOnRedeploy();
   }
}

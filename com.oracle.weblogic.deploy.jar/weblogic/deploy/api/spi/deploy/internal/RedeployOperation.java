package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.utils.FileUtils;

public class RedeployOperation extends BasicOperation {
   protected String path;
   protected boolean inPlace;
   protected boolean streams = false;

   protected RedeployOperation(WebLogicDeploymentManager dm, InputStream moduleArchive, InputStream plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
   }

   public RedeployOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File moduleArchive, File plan, DeploymentOptions options) {
      super(dm, moduleArchive, plan, options);
      this.tmids = tmids;
      this.cmd = CommandType.REDEPLOY;
   }

   public ProgressObject run() throws IllegalStateException {
      return super.run();
   }

   protected void initializeTask() throws Throwable {
      if (debug) {
         Debug.say("Starting task with path: " + this.path);
      }

      if (this.path == null) {
         this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).redeploy(this.appName, this.info, this.dm.getTaskId(), false);
      } else {
         this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).redeploy(this.path, this.appName, this.info, this.dm.getTaskId(), false);
      }

   }

   protected void setupPaths() throws FailedOperationException {
      if (!this.streams) {
         try {
            this.paths = new InstallDir(ConfigHelper.getAppName(this.tmids, this.options), ConfigHelper.getAppRootFromPlan(this.planBean), false);
            if (this.moduleArchive != null) {
               this.paths.setArchive(this.moduleArchive.getCanonicalFile());
            }

            if (this.plan != null) {
               this.paths.setPlan(this.plan.getCanonicalFile());
            }

            ConfigHelper.initPlanDirFromPlan(this.planBean, this.paths);
         } catch (IOException var2) {
            throw new FailedOperationException(this.failOperation(var2));
         }
      }

      this.inPlace = this.moduleArchive == null;
      if (debug) {
         Debug.say("in place redeploy: " + this.inPlace + " from moduleArchive: " + this.moduleArchive);
      }

      if (this.inPlace) {
         this.path = null;
      } else {
         this.path = this.paths.getArchive().getPath();
         this.appName = ApplicationVersionUtils.getApplicationName(this.appName);
      }

      if (debug) {
         Debug.say("redeploy src path: " + this.path);
      }

   }

   protected boolean isInPlaceApp(String appId, String mVersion, String pVersion) {
      String vid1 = ApplicationVersionUtils.getVersionId(mVersion, pVersion);
      String vid2 = ApplicationVersionUtils.getVersionId(appId);
      return this.isInPlace(vid1, vid2);
   }

   protected boolean isInPlace(String vid1, String vid2) {
      if (vid1 != null) {
         return vid1.equals(vid2);
      } else {
         return vid2 != null ? vid2.equals(vid1) : true;
      }
   }

   protected void uploadFiles() throws IOException {
      if (this.paths != null) {
         String planPath;
         if (this.moduleArchive != null) {
            planPath = this.getAppIdForUpload();
            this.paths = this.dm.getServerConnection().upload(this.paths, planPath, (String[])null, this.options);
            this.path = this.paths.getArchive().getPath();
         } else if (this.plan != null) {
            planPath = this.uploadConfig();
            if (debug) {
               Debug.say("Updating " + this.appName + " from " + planPath);
            }
         }

      }
   }

   private String uploadConfig() throws ServerConnectionException {
      String inPath = this.plan.getAbsoluteFile().getPath();
      String outPath = inPath;
      if (!this.dm.isLocal()) {
         File planf = new File(inPath);
         if (!this.paths.isInConfigDir(planf)) {
            File newPlan = new File(this.paths.getConfigDir(), planf.getName());
            if (debug) {
               Debug.say("Copying " + planf.getPath() + " to config area, " + newPlan.getPath());
            }

            try {
               FileUtils.copyPreservePermissions(planf, newPlan);
            } catch (IOException var6) {
               throw new ServerConnectionException(var6.toString());
            }

            this.paths.setPlan(newPlan);
         }

         inPath = this.paths.getConfigDir().getPath();
         String appId = ApplicationVersionUtils.getApplicationId(this.appName, this.options.getVersionIdentifier());
         outPath = this.dm.getServerConnection().uploadPlan(inPath, appId);
         this.paths.setConfigDir(new File(outPath));
         this.paths.setPlan(new File(outPath, this.plan.getName()));
      }

      return outPath;
   }
}

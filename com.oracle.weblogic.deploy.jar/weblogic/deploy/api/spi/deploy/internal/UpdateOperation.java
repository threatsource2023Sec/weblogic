package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.logging.Loggable;
import weblogic.utils.FileUtils;

public class UpdateOperation extends RedeployOperation {
   protected String path;
   protected boolean inPlace;
   private String[] delta;

   public UpdateOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File plan, DeploymentOptions options) {
      super(dm, tmids, (File)null, plan, options);
      this.tmids = tmids;
      this.cmd = WebLogicCommandType.UPDATE;
   }

   public UpdateOperation(WebLogicDeploymentManager dm, TargetModuleID[] tmids, File plan, String[] delta, DeploymentOptions options) {
      super(dm, tmids, (File)null, plan, options);
      this.tmids = tmids;
      this.cmd = WebLogicCommandType.UPDATE;
      this.delta = delta;
   }

   protected void validateParams() throws FailedOperationException {
      super.validateParams();

      try {
         if (this.options.isRemovePlanOverride()) {
            if (this.plan != null) {
               Loggable l = SPIDeployerLogger.logParamCombinationLoggable("removePlanOverride", "plan");
               l.log();
               throw new IllegalArgumentException(l.getMessage());
            }
         } else {
            ConfigHelper.checkParam("plan", this.plan);
         }

      } catch (IllegalArgumentException var2) {
         throw new FailedOperationException(this.failOperation(var2));
      }
   }

   protected void uploadFiles() throws IOException {
      String planPath = this.uploadConfig();
      if (debug) {
         Debug.say("Updating " + this.appName + " from " + planPath);
      }

   }

   protected void buildDeploymentData() {
      super.buildDeploymentData();
      this.info.setFile(this.delta);
      this.addDDPaths();
      this.info.setPlanUpdate(true);
   }

   protected void initializeTask() throws Throwable {
      this.task = this.dm.getServerConnection().getHelper().getDeployer(this.options.getPartition()).update(this.appName, this.info, this.dm.getTaskId(), false);
   }

   protected void setupPaths() throws FailedOperationException {
      try {
         if (this.planBean != null) {
            this.paths = new InstallDir(ConfigHelper.getAppName(this.tmids, this.options), ConfigHelper.getAppRootFromPlan(this.planBean), false);
            this.paths.setPlan(this.plan.getCanonicalFile());
            ConfigHelper.initPlanDirFromPlan(this.planBean, this.paths);
         }

      } catch (IOException var2) {
         throw new FailedOperationException(this.failOperation(var2));
      }
   }

   private String uploadConfig() throws ServerConnectionException {
      String outPath = null;
      if (this.plan != null) {
         String inPath = this.plan.getAbsoluteFile().getPath();
         outPath = inPath;
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
            outPath = this.dm.getServerConnection().uploadPlan(inPath, appId, this.options);
            this.paths.setConfigDir(new File(outPath));
            this.paths.setPlan(new File(outPath, this.plan.getName()));
         }
      }

      return outPath;
   }

   private void addDDPaths() {
      if (this.planBean != null) {
         ModuleOverrideBean[] mobs = this.planBean.getModuleOverrides();
         if (mobs != null) {
            String root = null;

            for(int i = 0; i < mobs.length; ++i) {
               if (this.planBean.rootModule(mobs[i].getModuleName())) {
                  root = mobs[i].getModuleName();
                  break;
               }
            }

            List files = new ArrayList();
            if (this.info.hasFiles()) {
               this.addFilesToList(this.info.getFiles(), files);
            }

            for(int i = 0; i < mobs.length; ++i) {
               boolean isEar = ModuleType.EAR.toString().equals(mobs[i].getModuleType());
               boolean isRoot = mobs[i].getModuleName().equals(root);
               ModuleDescriptorBean[] mds = mobs[i].getModuleDescriptors();
               if (mds != null) {
                  for(int j = 0; j < mds.length; ++j) {
                     String uri = mds[j].getUri();
                     if (!isRoot) {
                        uri = mobs[i].getModuleName() + "/" + uri;
                     }

                     if (this.hasOverrides(mds[j], uri) && !files.contains(uri)) {
                        files.add(uri);
                     }
                  }
               }
            }

            this.info.setFile((String[])((String[])files.toArray(new String[files.size()])));
            if (debug) {
               Debug.say(this.info.toString());
            }

         }
      }
   }

   private boolean hasOverrides(ModuleDescriptorBean md, String uri) {
      if (md.getVariableAssignments() != null && md.getVariableAssignments().length > 0) {
         return true;
      } else if (md.isExternal()) {
         File f = new File(this.paths.getConfigDir(), uri);
         if (debug) {
            Debug.say(f.getPath() + " has external dd: " + f.exists());
         }

         return f.exists();
      } else {
         return false;
      }
   }

   private void addFilesToList(String[] delta, List dList) {
      if (delta != null) {
         for(int i = 0; i < delta.length; ++i) {
            dList.add(delta[i]);
         }

      }
   }
}

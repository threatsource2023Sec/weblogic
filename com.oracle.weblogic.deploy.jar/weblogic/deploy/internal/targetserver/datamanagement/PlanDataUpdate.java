package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import weblogic.utils.FileUtils;
import weblogic.utils.jars.JarFileUtils;

public class PlanDataUpdate extends AppDataUpdate {
   private static final String stagePlanDir = "plan";
   private boolean isPlanExistsPreviously = false;
   private boolean isRestored = false;
   private boolean isBackupFileDeleted = false;
   private File backupFile = null;

   public PlanDataUpdate(Data localData, DataUpdateRequestInfo reqInfo) {
      super(localData, reqInfo);
   }

   protected void backup() {
      if (isDebugEnabled()) {
         debug(" +++ PlanDataUpdate.backup() : taking existing plan dir backup");
      }

      this.takePlanDirBackup();
   }

   protected void restore() {
      if (isDebugEnabled()) {
         debug(" +++ PlanDataUpdate.restore() : restoring plan dir from backup");
      }

      if (!this.isRestored) {
         this.restorePlanDir();
      }

   }

   protected void deleteBackup() {
      if (isDebugEnabled()) {
         debug(" +++ PlanDataUpdate.deleteBackup() : deleting backup file");
      }

      if (!this.isBackupFileDeleted && this.backupFile != null) {
         this.isBackupFileDeleted = FileUtils.remove(this.backupFile);
      }

   }

   private void takePlanDirBackup() {
      try {
         File planDir = this.getPlanDir();
         if (planDir.exists()) {
            this.isPlanExistsPreviously = true;
            this.backupFile = File.createTempFile("plan", ".jar", this.getAppRoot());
            JarFileUtils.createJarFileFromDirectory(this.backupFile, planDir, false);
         } else {
            this.isPlanExistsPreviously = false;
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   private void restorePlanDir() {
      this.removeCurrentPlanDir();
      if (this.isPlanExistsPreviously) {
         File planDir = this.getPlanDir();
         planDir.mkdirs();
         if (!planDir.exists()) {
            return;
         }

         if (this.backupFile.exists()) {
            try {
               JarFileUtils.extract(this.backupFile, planDir);
            } catch (IOException var6) {
               var6.printStackTrace();
            } finally {
               this.deleteBackup();
            }
         }
      }

      this.isRestored = true;
   }

   private boolean removeCurrentPlanDir() {
      File planDir = this.getPlanDir();
      return planDir.exists() ? FileUtils.remove(planDir) : false;
   }

   private File getPlanDir() {
      return new File(this.getAppRoot(), "plan");
   }

   private File getAppRoot() {
      String appRootName = this.getLocalAppData().getRootLocation();
      return new File(appRootName);
   }
}

package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import weblogic.deploy.common.Debug;
import weblogic.management.DomainDir;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.t3.srvr.FileOwnerFixer;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;

final class ConfigBackupRecoveryManager {
   private static final String CONFIG_DIRECTORY = "config";
   private static final String CONFIG_BAK_DIRECTORY;
   private static final String CONFIG_PREV_DIRECTORY;
   private static final String CONFIG_PREV_BAK_DIRECTORY;
   private static final String DELETED_FILE_INDEX;
   private static final String DOMAIN_ROOT;
   private static final String DOMAIN_BAK_ROOT;
   private boolean configBakSavedToConfigPrev;

   static ConfigBackupRecoveryManager getInstance() {
      return ConfigBackupRecoveryManager.Maker.singleton;
   }

   void handleBackup(File targetFile, String targetPath) throws IOException {
      if (this.isDebugEnabled()) {
         this.debug("handleBackup targetFile:  " + targetFile.getAbsolutePath() + "  targetPath:  " + targetPath);
         this.debug("targetFile.exists:  " + targetFile.exists());
      }

      this.saveConfigPrevToConfigPrevBak();
      if (targetFile.exists()) {
         this.saveFileToConfigBakDir(targetFile, targetPath);
      } else {
         this.appendToDeletedFilesIndex(targetPath);
      }

   }

   void restoreFromBackup() throws IOException {
      if (this.isDebugEnabled()) {
         this.debug("Restoring from backup: " + CONFIG_BAK_DIRECTORY);
      }

      this.deleteAddedFiles();
      File configBak = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY);
      if (configBak.exists()) {
         File config = new File(DOMAIN_ROOT, "config");
         String[] fileList = configBak.list();
         if (fileList != null && fileList.length > 0) {
            FileOwnerFixer.addPathJDK6(config);
            FileUtils.copyPreservePermissions(configBak, config);
         }

         this.deleteConfigBakDir();
      }

      this.restoreConfigPrevFromConfigPrevBakDir();
      this.deleteConfigPrevBakDir();
      this.configBakSavedToConfigPrev = false;
      if (this.isDebugEnabled()) {
         this.debug("Restored from backup");
      }

   }

   void deleteConfigBakFile(String targetPath) {
      File backupFile = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY + targetPath);
      if (backupFile.exists()) {
         this.deleteFileOrDir(backupFile);
      }
   }

   void saveConfigBakDirToConfigPrevDir() throws IOException {
      File configBak = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY);
      File configPrev = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_DIRECTORY);
      if (configBak.exists()) {
         boolean result = this.renameFileOrDir(configBak, configPrev);
         if (!result) {
            DeployerRuntimeLogger.logCannotRenameDirectory(configBak.getAbsolutePath(), configPrev.getAbsolutePath());
         }

         this.deleteConfigPrevBakDir();
         this.configBakSavedToConfigPrev = false;
      }
   }

   private final void debug(String message) {
      Debug.deploymentDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   private void saveFileToConfigBakDir(File targetFile, String path) throws IOException {
      try {
         this.copyFileToConfigBakDir(path, targetFile);
      } catch (Throwable var4) {
         this.deleteConfigPrevBakDir();
         throw new IOException(StackTraceUtils.throwable2StackTrace(var4));
      }
   }

   private void saveConfigPrevToConfigPrevBak() throws IOException {
      if (!this.configBakSavedToConfigPrev) {
         this.saveConfigPrevDirToConfigPrevBakDir();
         this.cleanConfigBakDir();
         this.configBakSavedToConfigPrev = true;
      }

   }

   private void copyFileToConfigBakDir(String path, File targetFile) throws IOException {
      String targetPath;
      if (path.startsWith("config")) {
         targetPath = path.substring("config".length() + 1);
      } else {
         targetPath = path;
      }

      String backupPath = CONFIG_BAK_DIRECTORY + targetPath;
      File backupFile = new File(DOMAIN_BAK_ROOT, backupPath);

      try {
         if (this.isDebugEnabled()) {
            this.debug("Saving file : '" + targetFile.getCanonicalPath() + "' to: '" + backupFile.getCanonicalPath() + "'");
         }

         FileOwnerFixer.addPathJDK6(backupFile);
         FileUtils.copyPreservePermissions(targetFile, backupFile);
      } catch (Throwable var7) {
         if (this.isDebugEnabled()) {
            this.debug("Back up failed due to " + var7.getMessage() + " " + StackTraceUtils.throwable2StackTrace(var7));
         }

         this.deleteConfigBakFile(targetPath);
         throw new IOException(StackTraceUtils.throwable2StackTrace(var7));
      }
   }

   private void saveConfigPrevDirToConfigPrevBakDir() throws IOException {
      this.deleteConfigPrevBakDir();
      File configPrevBak = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_BAK_DIRECTORY);
      File configPrev = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_DIRECTORY);
      this.renameFileOrDir(configPrev, configPrevBak);
   }

   private void appendToDeletedFilesIndex(String deletedFilePath) throws IOException {
      BufferedWriter bw = null;
      StringBuffer sb = null;
      if (this.isDebugEnabled()) {
         sb = new StringBuffer();
         this.debug(sb, "appendToDeletedFilesIndex:  " + deletedFilePath);
      }

      try {
         this.saveConfigPrevToConfigPrevBak();
         File configBak = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY);
         if (this.isDebugEnabled()) {
            this.debug(sb, "configBak:  " + configBak.getAbsolutePath());
         }

         if (!configBak.exists()) {
            FileOwnerFixer.addPathJDK6(configBak);
            configBak.mkdirs();
         }

         File deletedFilesIndexFile = new File(DOMAIN_BAK_ROOT, DELETED_FILE_INDEX);
         if (this.isDebugEnabled()) {
            this.debug(sb, "deletedFilesIndexFile:  " + deletedFilesIndexFile);
         }

         if (!deletedFilesIndexFile.exists()) {
            FileOwnerFixer.addPathJDK6(deletedFilesIndexFile);

            try {
               File deletedFilesIndexParent = deletedFilesIndexFile.getParentFile();
               if (!deletedFilesIndexParent.exists()) {
                  if (this.isDebugEnabled()) {
                     this.debug(sb, "deletedFilesIndexParent, " + deletedFilesIndexParent + ", does not exist. Creating it.");
                  }

                  deletedFilesIndexParent.mkdirs();
               }

               deletedFilesIndexFile.createNewFile();
            } catch (IOException var15) {
               if (this.isDebugEnabled()) {
                  this.debug(sb, "deletedFilesIndexFile, " + deletedFilesIndexFile + ". Exists:  " + deletedFilesIndexFile.exists());
                  if (deletedFilesIndexFile.exists()) {
                     this.debug(sb, "deletedFilesIndexFile permissions:");
                     this.debug(sb, "read:  " + deletedFilesIndexFile.canRead());
                     this.debug(sb, "write:  " + deletedFilesIndexFile.canWrite());
                     this.debug(sb, "execute:  " + deletedFilesIndexFile.canExecute());
                  }

                  this.debug(sb, "deletedFilesIndexFile parent exists" + deletedFilesIndexFile.getParentFile().exists());
                  if (deletedFilesIndexFile.getParentFile().exists()) {
                     this.debug(sb, "deletedFilesIndexFile permissions:");
                     this.debug(sb, "read:  " + deletedFilesIndexFile.getParentFile().canRead());
                     this.debug(sb, "write:  " + deletedFilesIndexFile.getParentFile().canWrite());
                     this.debug(sb, "execute:  " + deletedFilesIndexFile.getParentFile().canExecute());
                  }
               }

               if (sb != null) {
                  throw new IOException(sb.toString(), var15);
               }

               throw var15;
            }
         }

         bw = new BufferedWriter(new FileWriter(deletedFilesIndexFile, true));
         bw.write(deletedFilePath);
         bw.newLine();
         bw.flush();
      } catch (Throwable var16) {
         this.deleteConfigPrevBakDir();
         throw new IOException(StackTraceUtils.throwable2StackTrace(var16));
      } finally {
         if (bw != null) {
            try {
               bw.close();
            } catch (Exception var14) {
            }
         }

      }

   }

   private void debug(StringBuffer sb, String message) {
      sb.append(message).append(System.getProperty("line.separator"));
      this.debug(message);
   }

   private void deleteAddedFiles() throws IOException {
      File deletedFilesIndexFile = new File(DOMAIN_BAK_ROOT, DELETED_FILE_INDEX);
      if (deletedFilesIndexFile.exists()) {
         BufferedReader br = null;

         try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(deletedFilesIndexFile)));

            String path;
            while((path = br.readLine()) != null) {
               FileUtils.remove(new File(DOMAIN_ROOT, path));
            }
         } finally {
            if (br != null) {
               try {
                  br.close();
               } catch (Exception var9) {
               }
            }

         }

         FileUtils.remove(deletedFilesIndexFile);
      }
   }

   private void cleanConfigBakDir() {
      File configBak = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY);
      if (configBak.exists()) {
         FileUtils.remove(configBak, FileUtils.STAR);
      }

   }

   private void deleteConfigBakDir() {
      File configBak = new File(DOMAIN_BAK_ROOT, CONFIG_BAK_DIRECTORY);
      this.deleteFileOrDir(configBak);
   }

   private void deleteConfigPrevBakDir() {
      File configBakPrev = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_BAK_DIRECTORY);
      if (configBakPrev.exists()) {
         this.deleteFileOrDir(configBakPrev);
      }
   }

   private void deleteFileOrDir(File deleteTarget) {
      if (this.isDebugEnabled()) {
         this.debug("deleteFileOrDir:  " + deleteTarget.getAbsolutePath());
      }

      if (deleteTarget.exists()) {
         FileUtils.remove(deleteTarget);
      }

   }

   private boolean renameFileOrDir(File src, File target) throws IOException {
      if (!src.exists()) {
         if (this.isDebugEnabled()) {
            this.debug("Cannot rename '" + src.getCanonicalPath() + "' as it does not exist");
         }

         return false;
      } else {
         boolean renameResult = src.renameTo(target);
         if (renameResult) {
            if (this.isDebugEnabled()) {
               this.debug("Renamed '" + src.getCanonicalPath() + "' to: '" + target.getCanonicalPath());
            }
         } else if (this.isDebugEnabled()) {
            this.debug("Failed to rename '" + src.getCanonicalPath() + "' to: " + target.getCanonicalPath());
         }

         return renameResult;
      }
   }

   private void restoreConfigPrevFromConfigPrevBakDir() throws IOException {
      File configPrev = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_DIRECTORY);
      File configPrevBak = new File(DOMAIN_BAK_ROOT, CONFIG_PREV_BAK_DIRECTORY);
      if (configPrevBak.exists()) {
         this.renameFileOrDir(configPrevBak, configPrev);
      }
   }

   static {
      CONFIG_BAK_DIRECTORY = "config_bak" + File.separator;
      CONFIG_PREV_DIRECTORY = "config_prev" + File.separator;
      CONFIG_PREV_BAK_DIRECTORY = "config_prev_bak" + File.separator;
      DELETED_FILE_INDEX = CONFIG_BAK_DIRECTORY + File.separator + "__deleted_files_index__";
      DOMAIN_ROOT = DomainDir.getRootDir();
      DOMAIN_BAK_ROOT = DomainDir.getServersDir() + File.separator + "domain_bak";
   }

   private static class Maker {
      private static final ConfigBackupRecoveryManager singleton = new ConfigBackupRecoveryManager();
   }
}

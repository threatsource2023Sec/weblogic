package weblogic.application.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import weblogic.application.ApplicationAccess;
import weblogic.application.internal.FlowContext;
import weblogic.j2ee.J2EELogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.utils.ApplicationNameUtils;
import weblogic.utils.Debug;

public final class PathUtils {
   public static final boolean USE_NEW_DEPLOYED_DIR = Boolean.parseBoolean(System.getProperty("weblogic.application.info.UseNewDeployedDir", "true"));
   private static final String TEMP_DIR_NAME = "tmp";
   private static final String J2EE_TMP_DIR_NAME = "weblogic.j2ee.application.tmpDir";
   public static final String APPS_TMP_DIR = System.getProperty("weblogic.j2ee.application.tmpDir");

   public static String getUserAppRootDirForPartition(String partitionName) {
      File appRootDir = new File(getTempDirForPartition(partitionName), "_WL_user");
      return appRootDir.toURI().getPath();
   }

   public static File getAppTempDir(String server, String appName) {
      return getAppTempDir(server, appName, (String)null);
   }

   public static File getTempDirForAppArchive(String applicationId) {
      File dir = getAppTempDir(ManagementUtils.getServerName(), ApplicationVersionUtils.replaceDelimiter(applicationId, '_'));
      dir.mkdirs();
      return dir;
   }

   public static File getAppTempDir(String server, String appName, String moduleName) {
      return getAppTempDirForApplication(appName, generateTempPath(server, appName, moduleName));
   }

   public static File getAppTempDir(String path) {
      return getAppTempDirForApplication((String)null, path);
   }

   private static File getAppTempDirForApplication(String appId, String path) {
      FlowContext appCtx = (FlowContext)ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
      boolean isInternal = false;
      String appSourceDir = null;
      if (appCtx != null) {
         if (appId == null) {
            appId = appCtx.getApplicationId();
         }

         isInternal = appCtx.isInternalApp();
         if (appCtx.getAppDeploymentMBean().isCacheInAppDirectory()) {
            appSourceDir = (new File(appCtx.getAppDeploymentMBean().getSourcePath())).getParent();
         }
      }

      return getAppTempDir(appId, path, isInternal, appSourceDir);
   }

   public static File getAppTempDir(String appId, String path, boolean isInternal, String appSourceDir) {
      return new File(getRootTempDir(appId, isInternal, appSourceDir), path);
   }

   private static File getRootTempDir(String appId, boolean isInternal, String appSourceDir) {
      if (appSourceDir != null) {
         return PathUtils.TempPaths.getUserAppSourceDirTmpRoot(appSourceDir);
      } else {
         return isInternal ? PathUtils.TempPaths.getInternalAppTmpRoot(appId) : PathUtils.TempPaths.getUserAppTmpRoot(appId);
      }
   }

   public static String generateTempPath(String server, String appName, String moduleName) {
      StringBuffer sb = new StringBuffer();
      if (server != null) {
         sb.append(server);
      }

      if (appName != null) {
         if (USE_NEW_DEPLOYED_DIR) {
            appName = ApplicationVersionUtils.getNonPartitionName(appName);
         }

         sb.append("_").append(appName);
      }

      if (moduleName != null) {
         sb.append("_").append(moduleName);
      }

      return appName + File.separator + Long.toString((long)(sb.toString().hashCode() & Integer.MAX_VALUE), 36);
   }

   public static File generateDescriptorCacheDir(String serverName, String appId, boolean isInternal, String appSourceDir) {
      return new File(getRootTempDir(appId, isInternal, appSourceDir), generateTempPath(serverName, ApplicationVersionUtils.replaceDelimiter(appId, '_'), "__WL_DescriptorCache"));
   }

   public static File getTempDir() {
      String appId = ApplicationAccess.getApplicationAccess().getCurrentApplicationId();
      return getTempDirForApplication(appId);
   }

   private static File getTempDirForApplication(String appId) {
      String rootDir = ManagementUtils.getDomainRootDir();
      if (APPS_TMP_DIR != null) {
         rootDir = APPS_TMP_DIR;
      } else if (appId != null) {
         String partition = ApplicationVersionUtils.getPartitionName(appId);
         if ("DOMAIN".equals(partition)) {
            partition = null;
         }

         return getTempDirForPartition(partition);
      }

      return getTempDir(rootDir);
   }

   private static File getTempDirForPartition(String partition) {
      String rootDir = ManagementUtils.getDomainRootDir();
      if (partition != null && USE_NEW_DEPLOYED_DIR) {
         DomainMBean domain = ManagementUtils.getDomainMBean();
         PartitionMBean par = domain.lookupPartition(partition);
         if (par != null) {
            rootDir = par.getSystemFileSystem().getRoot();
         }
      }

      return getTempDir(rootDir);
   }

   private static File getTempDir(String rootDir) {
      String serverName = ManagementUtils.isRuntimeAccessAvailable() ? ManagementUtils.getServerName() : null;
      File dir = new File(rootDir + File.separator + "servers" + File.separator + serverName, "tmp");
      File tempDir = new File(dir.getAbsolutePath());
      String tmp = null;
      if (!tempDir.exists()) {
         if (!tempDir.mkdirs()) {
            tmp = "Create Failed for " + tempDir.toString();
         }

         writeReadmeFile(tempDir);
      } else if (!tempDir.isDirectory()) {
         tmp = "Cannot create " + tempDir.toString() + ". Non directory file already exists with same name. Please remove it";
      }

      Debug.assertion(tmp == null, tmp + ". Server cannot deploy any webapps");
      return tempDir;
   }

   private static void writeReadmeFile(File tempDir) {
      try {
         File readme = new File(tempDir, "README.TXT");
         Writer w = new FileWriter(readme);
         w.write(J2EELogger.logReadmeContentLoggable().getMessageText());
         w.close();
      } catch (IOException var3) {
      }

   }

   public static String getArchiveNameWithoutSuffix(File file) {
      return ApplicationNameUtils.getArchiveNameWithoutSuffix(file);
   }

   private static final class TempPaths {
      private static File getUserAppTmpRoot(String appId) {
         return new File(PathUtils.getTempDirForApplication(appId), "_WL_user");
      }

      private static File getInternalAppTmpRoot(String appId) {
         return new File(PathUtils.getTempDirForApplication(appId), "_WL_internal");
      }

      private static File getUserAppSourceDirTmpRoot(String userAppSourceDir) {
         File tempDir = PathUtils.getTempDir(userAppSourceDir + File.separator + "_WL_internal");
         return new File(tempDir, "_WL_user");
      }
   }
}

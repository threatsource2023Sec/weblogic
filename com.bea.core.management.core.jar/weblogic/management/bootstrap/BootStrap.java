package weblogic.management.bootstrap;

import java.io.File;
import java.util.ArrayList;
import javax.management.InvalidAttributeValueException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.utils.ArrayUtils;

public class BootStrap extends BootStrapMin {
   private static String sep;
   private static DebugLogger debugLogger;
   private static boolean backWardsCompatibilityMode;
   private static String weblogicHome;
   private static String serverName;
   private static String repositoryName;
   private static File configDirConfigFile;

   public static boolean getBackWardsCompatibiltyBootMode() {
      return backWardsCompatibilityMode;
   }

   public static void reinit() {
      init();
   }

   private static void init() {
      weblogicHome = WeblogicHome.getWebLogicHome();
      serverName = System.getProperty("weblogic.Name");
      if (serverName == null || serverName.length() < 1) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("serverName was not provided");
         }

         serverName = null;
      }

      repositoryName = System.getProperty("weblogic.Repository", "Default");
      if (KernelStatus.isServer() && isUsing6xConfigPath()) {
         ManagementLogger.logBooting6xConfig();
      }

      configDirConfigFile = new File(DomainDir.getConfigDir() + sep + getDefaultConfigFileName());
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("-----------------------------------------------------------------");
         debugLogger.debug("rootDir : " + (new File(getRootDirectory())).getAbsolutePath());
         debugLogger.debug("-----------------------------------------------------------------");
      }

   }

   public static String getWebLogicHome() {
      return weblogicHome;
   }

   public static String getPathRelativeWebLogicHome(String fileName) {
      return getWebLogicHome() + sep + fileName;
   }

   /** @deprecated */
   @Deprecated
   public static String getDomainDirectory() {
      return getDomainDirectory(false);
   }

   /** @deprecated */
   @Deprecated
   public static String getDomainDirectory(boolean ignoreConfigDir) {
      return getRootDirectory();
   }

   public static String getPathRelativeRootDirectory(String path) {
      return getRootDirectory() + sep + path;
   }

   public static String getServerName() {
      return serverName;
   }

   public static String getConfigLockFileName() {
      return DomainDir.getConfigDir() + sep + "config.lok";
   }

   public static File getConfigDirectoryConfigFile() {
      return configDirConfigFile;
   }

   public static String getRepositoryName() {
      return repositoryName;
   }

   public static String getDefaultConfigFileName() {
      return "config.xml";
   }

   public static String getDefaultDomainName() {
      return "mydomain";
   }

   public static String getDefaultServerName() {
      return "myserver";
   }

   public static String getPathRelativeDomainDir(String fileName) {
      return getDomainDirectory() + sep + fileName;
   }

   public static String getPathRelativeRunningServerDirectory() {
      return getRootDirectory() + sep + getServerName();
   }

   public static File apply(String fileName) throws InvalidAttributeValueException {
      return apply((String)null, (String)null, (String)null, fileName);
   }

   public static File apply(String domainName, String fileName) throws InvalidAttributeValueException {
      return apply(domainName, (String)null, (String)null, fileName);
   }

   public static File apply(String domainName, String serverName, String fileName) throws InvalidAttributeValueException {
      return apply(domainName, serverName, (String)null, fileName);
   }

   public static File apply(String domainName, String serverName, String addDir, String fileName) throws InvalidAttributeValueException {
      if (fileName == null) {
         fileName = "";
      }

      if (fileName.startsWith("./") || fileName.startsWith(".\\")) {
         fileName = fileName.substring(2);
      }

      File path = new File(fileName);
      if (!path.isAbsolute() && !fileName.startsWith("/") && !fileName.startsWith("\\")) {
         if (domainName != null && serverName == null) {
            path = new File(getDomainDirectory(), fileName);
         } else if (serverName != null && domainName != null && addDir == null) {
            path = new File(getDomainDirectory() + sep + serverName, fileName);
         } else if (serverName != null && domainName != null && addDir != null) {
            path = new File(getDomainDirectory() + sep + serverName + sep + addDir, fileName);
         } else if (isUsing6xConfigPath()) {
            path = new File(fileName);
         } else {
            path = new File(getRootDirectory(), fileName);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RootDirectory Path: " + path.getAbsolutePath());
         }
      }

      if (path.getParentFile() != null && !path.getParentFile().isDirectory() && !path.getParentFile().mkdirs()) {
         throw new InvalidAttributeValueException("Unable to create path to " + path.getPath());
      } else {
         return path;
      }
   }

   public static File[] getConfigFiles() {
      File config = new File(DomainDir.getConfigDir());
      return find(config, true);
   }

   private static File[] find(File directory, boolean stepIntoSubDirectories) {
      if (!directory.exists()) {
         return new File[0];
      } else if (!directory.isDirectory()) {
         throw new IllegalArgumentException(directory + " is not a directory.");
      } else {
         ArrayList result = new ArrayList();
         File[] files = directory.listFiles();
         if (files == null) {
            throw new IllegalStateException(directory + " is not readable.");
         } else {
            for(int i = 0; i < files.length; ++i) {
               File f = files[i];
               if (f.isDirectory()) {
                  if (stepIntoSubDirectories) {
                     ArrayUtils.addAll(result, find(f, true));
                  }
               } else {
                  result.add(f);
               }
            }

            return (File[])((File[])result.toArray(new File[result.size()]));
         }
      }
   }

   static {
      sep = File.separator;
      debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
      init();
   }
}

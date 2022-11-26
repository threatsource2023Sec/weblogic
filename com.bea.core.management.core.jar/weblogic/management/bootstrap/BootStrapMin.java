package weblogic.management.bootstrap;

import java.io.File;

class BootStrapMin {
   private static String configFileName;
   private static File configFile;
   private static String domainName;
   private static String rootDir;
   private static boolean using6xconfigPath = false;

   private static void initConfigFileName() {
      configFileName = System.getProperty("weblogic.ConfigFile", "config.xml");
      if (configFileName == null || configFileName.length() < 1) {
         configFileName = "config.xml";
      }

   }

   private static void initDomainName() {
      domainName = System.getProperty("weblogic.Domain");
      if (domainName == null || domainName.length() < 1) {
         domainName = null;
      }

   }

   private static void initRootDir() {
      String rootDirDefault = ".";
      String tmpRoot = System.getProperty("weblogic.RootDirectory", ".");
      initRootDir(tmpRoot);
   }

   private static void initRootDir(String tmpRoot) {
      File tmpRootFile = new File(tmpRoot);
      File config9x = get9xConfig(tmpRootFile);
      File config8x = get8xConfig(tmpRootFile);
      File config6x = get6xConfig(tmpRootFile);
      if (config9x.exists()) {
         rootDir = tmpRoot;
         configFile = config9x;
      } else if (config8x.exists()) {
         rootDir = tmpRoot;
         configFile = config8x;
      } else if (!config6x.exists() && !config6xMigrated(tmpRootFile)) {
         rootDir = tmpRoot;
         configFile = config9x;
      } else {
         rootDir = config6x.getParent();
         configFile = config6x;
         using6xconfigPath = true;
      }

   }

   public static void resetRootDirForExplicitUpgrade(String path) {
      initRootDir(path);
   }

   public static String getDomainName() {
      return domainName;
   }

   public static String getRootDirectory() {
      return rootDir;
   }

   public static String getConfigFileName() {
      return configFileName;
   }

   public static File getConfigFile() {
      return configFile;
   }

   private static File get6xConfig(File root) {
      root = new File(root, "config");
      if (domainName == null) {
         root = new File(root, "mydomain");
      } else {
         root = new File(root, domainName);
      }

      return new File(root, configFileName);
   }

   private static boolean config6xMigrated(File root) {
      String config = get6xConfig(root).getParent();
      File configFile = new File(config, "config");
      return (new File(configFile, configFileName)).exists();
   }

   private static File get8xConfig(File root) {
      return new File(root, configFileName);
   }

   private static File get9xConfig(File root) {
      root = new File(root, "config");
      return new File(root, configFileName);
   }

   public static boolean isUsing6xConfigPath() {
      return using6xconfigPath;
   }

   public static void main(String[] args) {
   }

   static {
      initConfigFileName();
      initDomainName();
      initRootDir();
   }
}

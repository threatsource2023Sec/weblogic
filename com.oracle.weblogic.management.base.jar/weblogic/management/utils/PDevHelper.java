package weblogic.management.utils;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import weblogic.Home;
import weblogic.common.internal.VersionInfo;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.utils.FileUtils;

public final class PDevHelper {
   private static final String COMMON_CONFIG_DIR;
   private static final String CONFIG_LAUNCH_JAR = "config-launch.jar";

   public static ClassLoader getPDevClassLoader(ClassLoader parent) {
      try {
         File configLaunchFile = new File(getConfigLaunchLocation());
         URL cLaunch = configLaunchFile.toURI().toURL();
         URL[] urls = new URL[]{cLaunch};
         return new URLClassLoader(urls, parent);
      } catch (MalformedURLException var4) {
         throw new AssertionError(var4);
      }
   }

   private static String getConfigLaunchLocation() {
      try {
         Home.getFile();
      } catch (Exception var5) {
         return "config-launch.jar";
      }

      File home = Home.getFile();
      String mwHome;
      String jarLocation;
      String configDir;
      if (home != null) {
         mwHome = (new File(home.getParentFile().getParentFile().getAbsolutePath())).getAbsolutePath();
         jarLocation = mwHome + File.separator + COMMON_CONFIG_DIR;
         configDir = findConfigLaunchJarLocation(jarLocation);
         if (configDir != null) {
            return configDir;
         }
      }

      mwHome = Home.getMiddlewareHomePath();
      if (mwHome != null) {
         jarLocation = (new File(mwHome)).getAbsolutePath();
         configDir = jarLocation + File.separator + COMMON_CONFIG_DIR;
         String jarLocation = findConfigLaunchJarLocation(configDir);
         if (jarLocation != null) {
            return jarLocation;
         }
      }

      jarLocation = System.getenv("FMWLAUNCH_CLASSPATH");
      return jarLocation != null ? jarLocation : "config-launch.jar";
   }

   private static String findConfigLaunchJarLocation(String configDirectory) {
      File configDirFile = new File(configDirectory);
      if (configDirFile.exists() && !configDirFile.isFile()) {
         File explicitPath = new File(configDirectory + File.separator + VersionInfoFactory.getVersionInfo().getMajor() + "." + VersionInfo.theOne().getMinor() + File.separator + "config-launch.jar");
         if (explicitPath.exists()) {
            return explicitPath.getAbsolutePath();
         } else {
            File[] jars = FileUtils.find(configDirFile, new FileFilter() {
               public boolean accept(File f) {
                  return f.isFile() && f.getName().equals("config-launch.jar");
               }
            });
            return jars != null && jars.length > 0 ? jars[0].getAbsolutePath() : null;
         }
      } else {
         return null;
      }
   }

   static {
      COMMON_CONFIG_DIR = "oracle_common" + File.separator + "common" + File.separator + "lib";
   }
}

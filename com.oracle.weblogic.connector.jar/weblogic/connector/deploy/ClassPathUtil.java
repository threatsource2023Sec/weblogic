package weblogic.connector.deploy;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import weblogic.connector.common.Debug;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClassFinderUtils;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public class ClassPathUtil {
   public static String computeClasspath(String base, List entries) {
      return computeClasspath(base, (String[])entries.toArray(new String[entries.size()]));
   }

   public static String computeClasspath(String base, String[] entries) {
      StringBuilder buffer = new StringBuilder();
      buffer.append(base).append(PlatformConstants.PATH_SEP);
      String[] var3 = entries;
      int var4 = entries.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String string = var3[var5];
         if (string.endsWith(".jar")) {
            buffer.append(base).append(PlatformConstants.FILE_SEP).append(string).append(PlatformConstants.PATH_SEP);
         }
      }

      return buffer.toString();
   }

   public static ClassFinder createManifestFinder(VirtualJarFile connectorVJar) {
      MultiClassFinder multiClassFinder = new MultiClassFinder();
      HashSet excludeSet = new HashSet();

      try {
         ClassFinder classFinder;
         if (connectorVJar.isDirectory()) {
            File file = new File(connectorVJar.getDirectory().toString());
            classFinder = ClassFinderUtils.getManifestFinder(file, excludeSet);
         } else {
            classFinder = ClassFinderUtils.getManifestFinder(connectorVJar.getJarFile(), excludeSet);
         }

         if (classFinder != null) {
            multiClassFinder.addFinder(classFinder);
         }
      } catch (IOException var5) {
         debug("Caught IOException while trying to get the manifest finders :" + var5.getMessage());
      }

      return multiClassFinder;
   }

   private static void debug(String msg) {
      if (Debug.isClassLoadingEnabled()) {
         Debug.classloading(msg);
      }

   }
}

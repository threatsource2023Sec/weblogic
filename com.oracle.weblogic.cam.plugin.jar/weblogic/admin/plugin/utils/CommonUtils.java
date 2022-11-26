package weblogic.admin.plugin.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.admin.plugin.PluginManager;

public class CommonUtils {
   private static Logger _commonUtilsLogger = Logger.getLogger(CommonUtils.class.getName());

   public static String[] splitCompletely(String s, String delim) {
      StringTokenizer stringTokenizer = new StringTokenizer(s, delim);
      int i = stringTokenizer.countTokens();
      String[] st = new String[i];

      for(int j = 0; j < i; ++j) {
         st[j] = stringTokenizer.nextToken();
      }

      return st;
   }

   public static File[] find(File directory, FileFilter match) {
      if (!directory.isDirectory()) {
         throw new IllegalArgumentException(directory + " is not a directory.");
      } else {
         ArrayList result = new ArrayList();
         File[] files = directory.listFiles();
         if (files != null) {
            for(int i = 0; i < files.length; ++i) {
               File f = files[i];
               if (f.isDirectory()) {
                  addAll(result, find(f, match));
               }

               if (match.accept(f)) {
                  result.add(f);
               }
            }
         }

         return (File[])result.toArray(new File[result.size()]);
      }
   }

   private static boolean addAll(ArrayList c, File[] a) {
      boolean b = false;

      for(int i = 0; i < a.length; ++i) {
         b |= c.add(a[i]);
      }

      return b;
   }

   public static String getOracleCommonDir() {
      String commonHome = System.getProperty("common.components.home");
      if (commonHome == null) {
         String classLocation = PluginManager.class.getName().replace('.', '/') + ".class";
         String path = PluginManager.class.getClassLoader().getResource(classLocation).getPath();
         if (path != null) {
            int moduleIndex = path.lastIndexOf("/modules/");
            int fileIndex = path.indexOf("file:");
            if (moduleIndex > -1 && fileIndex > -1) {
               commonHome = path.substring((new String("file:")).length(), moduleIndex);
            }
         }
      }

      if (_commonUtilsLogger.isLoggable(Level.FINE)) {
         _commonUtilsLogger.log(Level.FINE, "Got Oracle Common dir:  " + commonHome);
      }

      return commonHome;
   }

   public static String getMWHomeDir() {
      String mwHome = null;
      String wlsInstallDirProperty = System.getProperty("wls.installdir");
      if (wlsInstallDirProperty != null) {
         try {
            mwHome = (new File(wlsInstallDirProperty)).getParentFile().getCanonicalPath();
         } catch (IOException var6) {
            throw new IllegalArgumentException(var6.getMessage());
         }
      } else {
         String classLocation = PluginManager.class.getName().replace('.', '/') + ".class";
         String path = PluginManager.class.getClassLoader().getResource(classLocation).getPath();
         if (_commonUtilsLogger.isLoggable(Level.FINE)) {
            _commonUtilsLogger.log(Level.FINE, "Load PluginManager class from this location:  " + path);
         }

         if (path != null) {
            int moduleIndex = path.lastIndexOf("/wlserver/modules/");
            int fileIndex = path.indexOf("file:");
            if (fileIndex > -1) {
               if (moduleIndex > -1) {
                  mwHome = path.substring((new String("file:")).length(), moduleIndex);
               } else if (moduleIndex == -1) {
                  moduleIndex = path.lastIndexOf("/modules/");
                  if (moduleIndex > -1) {
                     mwHome = path.substring((new String("file:")).length(), moduleIndex);
                  } else {
                     moduleIndex = path.lastIndexOf("/wlserver/server/");
                     if (moduleIndex > -1) {
                        mwHome = path.substring((new String("file:")).length(), moduleIndex);
                     }
                  }
               }
            }
         }
      }

      if (_commonUtilsLogger.isLoggable(Level.FINE)) {
         _commonUtilsLogger.log(Level.FINE, "Got MWHome dir:  " + mwHome);
      }

      return mwHome;
   }

   public static String[] concat(List firstDirs, List secondDirs) {
      List bothList = new ArrayList(firstDirs);
      bothList.addAll(secondDirs);
      Set h = new HashSet(bothList);
      bothList.clear();
      bothList.addAll(h);
      Iterator var4 = bothList.iterator();

      while(var4.hasNext()) {
         String dir = (String)var4.next();
         if (_commonUtilsLogger.isLoggable(Level.FINE)) {
            _commonUtilsLogger.log(Level.FINE, "Got plugins/cam dir: " + dir);
         }
      }

      return (String[])bothList.toArray(new String[bothList.size()]);
   }

   public static String[] getPluginsCamDirs(String pluginsCamDir) {
      List mwCamDirs = getPluginsCamDirs(getMWHomeDir(), pluginsCamDir);
      List commonCamDirs = getPluginsCamDirs(getOracleCommonDir(), pluginsCamDir);
      return concat(mwCamDirs, commonCamDirs);
   }

   public static List getPluginsCamDirs(String baseDir, String pluginsCamDir) {
      List camList = new ArrayList();
      FileFilter fileFilter = new FileFilter() {
         public boolean accept(File file) {
            return file.isDirectory();
         }
      };
      if (baseDir == null) {
         return camList;
      } else {
         File theBaseDir = new File(baseDir);
         File[] subDirs = theBaseDir.listFiles(fileFilter);
         if (subDirs != null) {
            File[] var6 = subDirs;
            int var7 = subDirs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               File subDir = var6[var8];
               searchCamDirs(pluginsCamDir, subDir, fileFilter, camList);
               File[] pluginsDirs = subDir.listFiles(fileFilter);
               if (pluginsDirs == null) {
                  if (_commonUtilsLogger.isLoggable(Level.FINE)) {
                     _commonUtilsLogger.log(Level.FINE, "The path " + subDir.getAbsolutePath() + " does not denote a directory, or an IO error occurs when read.");
                  }
               } else {
                  File[] var11 = pluginsDirs;
                  int var12 = pluginsDirs.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     File pluginsDir = var11[var13];
                     searchCamDirs(pluginsCamDir, pluginsDir, fileFilter, camList);
                  }
               }
            }
         }

         return camList;
      }
   }

   private static void searchCamDirs(String pluginsCamDir, File pluginsDir, FileFilter fileFilter, List ohList) {
      int index = pluginsCamDir.indexOf(File.separator);
      String plugins = pluginsCamDir.substring(0, index);
      String cam = pluginsCamDir.substring(index + 1);
      if (pluginsDir.getName().equals(plugins)) {
         File[] camDirs = pluginsDir.listFiles(fileFilter);
         if (camDirs != null) {
            File[] var8 = camDirs;
            int var9 = camDirs.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               File camDir = var8[var10];
               if (camDir.getName().equals(cam)) {
                  try {
                     ohList.add(camDir.getCanonicalPath());
                  } catch (IOException var13) {
                     throw new IllegalArgumentException(var13.getMessage());
                  }
               }
            }
         }
      }

   }
}

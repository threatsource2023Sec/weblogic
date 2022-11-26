package weblogic.osgi.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import weblogic.application.ApplicationContextInternal;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.osgi.OSGiBundle;
import weblogic.osgi.OSGiException;
import weblogic.osgi.OSGiLogger;
import weblogic.osgi.OSGiServer;
import weblogic.utils.osgi.OSGiVersion;
import weblogic.utils.osgi.OSGiVersionRange;
import weblogic.utils.reflect.ReflectUtils;

public class Utilities {
   public static final int BAD_SERVICE_LEVEL = -1;
   public static final String JAR = ".jar";
   private static final int DEFAULT_START_LEVEL = 1;
   public static final String OSGI_LIB = "osgi-lib";
   public static final String SERVER_LIB = "lib";
   public static final String APACHE_FELIX_JAR = "org.apache.felix.org.apache.felix.main.jar";
   private static final String COMMA = ",";
   private static final String WILDCARD = "*";
   private static final String EMPTY = "";
   private static final String APP = "app";
   private static final String ALL_RANGE_STRING = "[0,*)";
   private static final OSGiVersionRange ALL_RANGE = new OSGiVersionRange("[0,*)");
   private static final OSGiVersion DEFAULT_BUNDLE_VERSION = new OSGiVersion(0, 0, 0, (String)null);

   public static String[] getAllInterfaces(Object obj) {
      if (obj == null) {
         return new String[0];
      } else {
         List ai = ReflectUtils.everyInterface(obj.getClass());
         String[] retVal = new String[ai.size()];
         int lcv = 0;

         Class iFace;
         for(Iterator var4 = ai.iterator(); var4.hasNext(); retVal[lcv++] = iFace.getName()) {
            iFace = (Class)var4.next();
         }

         return retVal;
      }
   }

   public static boolean isFragment(Bundle bundle) {
      Dictionary headers = bundle.getHeaders();
      return headers.get("Fragment-Host") != null;
   }

   public static boolean safeEquals(Object a, Object b) {
      if (a == null) {
         return b == null;
      } else {
         return b == null ? false : a.equals(b);
      }
   }

   private static boolean isJarFile(File file) {
      if (file == null) {
         return false;
      } else if (!file.exists()) {
         return false;
      } else if (!file.canRead()) {
         return false;
      } else {
         String name = file.getName();
         return name.endsWith(".jar");
      }
   }

   private static void installFile(OSGiServer framework, List associatedBundles, File toStart, int startLevel) throws IOException, OSGiException {
      if (Logger.isDebugEnabled()) {
         Logger.getLogger().debug("Installing bundle from file " + toStart.getAbsolutePath());
      }

      FileInputStream fis = new FileInputStream(toStart);

      OSGiBundle bundle;
      try {
         bundle = framework.installBundle(fis, startLevel);
      } catch (OSGiException var7) {
         OSGiLogger.logCouldNotInstallFile(toStart.getAbsolutePath(), var7.getMessage(), framework.getName());
         throw var7;
      }

      if (associatedBundles != null) {
         associatedBundles.add(bundle);
      }

   }

   public static int toNaturalNumber(String dirName) {
      int num;
      try {
         num = Integer.parseInt(dirName);
      } catch (NumberFormatException var3) {
         return -1;
      }

      return num <= 0 ? -1 : num;
   }

   private static int getDirectoryNumber(File directory) {
      if (directory == null) {
         return -1;
      } else if (!directory.exists()) {
         return -1;
      } else if (!directory.isDirectory()) {
         return -1;
      } else {
         String dirName = directory.getName();
         return dirName == null ? -1 : toNaturalNumber(dirName);
      }
   }

   public static void deployFileBundlesIntoFramework(OSGiServer framework, List associatedBundles, File root, String osgiLib) throws IOException, OSGiException {
      File osgiRoot = new File(root, osgiLib);
      if (osgiRoot.exists() && osgiRoot.isDirectory()) {
         File[] osgiFiles = osgiRoot.listFiles();
         if (osgiFiles != null) {
            try {
               File[] var6 = osgiFiles;
               int var7 = osgiFiles.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  File osgiFile = var6[var8];
                  if (osgiFile.isDirectory()) {
                     int startLevel = getDirectoryNumber(osgiFile);
                     if (startLevel < 0) {
                        if (Logger.isDebugEnabled()) {
                           Logger.getLogger().debug("A non-numbered directory " + osgiFile.getAbsolutePath() + " was found under an OSGi deployment directory");
                        }
                        continue;
                     }

                     File[] startLevelFiles = osgiFile.listFiles();
                     if (startLevelFiles == null) {
                        continue;
                     }

                     File[] var12 = startLevelFiles;
                     int var13 = startLevelFiles.length;

                     for(int var14 = 0; var14 < var13; ++var14) {
                        File startLevelFile = var12[var14];
                        if (isJarFile(startLevelFile)) {
                           installFile(framework, associatedBundles, startLevelFile, startLevel);
                        } else if (Logger.isDebugEnabled()) {
                           Logger.getLogger().debug("A non JAR file was found under " + osgiFile.getAbsolutePath() + " of name " + startLevelFile.getName());
                        }
                     }
                  }

                  if (isJarFile(osgiFile)) {
                     installFile(framework, associatedBundles, osgiFile, 1);
                  } else if (Logger.isDebugEnabled()) {
                     Logger.getLogger().debug("A non JAR file was found named " + osgiFile.getAbsolutePath());
                  }
               }

            } catch (IOException var16) {
               uninstallAll(associatedBundles);
               throw var16;
            } catch (OSGiException var17) {
               uninstallAll(associatedBundles);
               throw var17;
            }
         }
      }
   }

   public static void uninstallAll(List associatedBundles) {
      if (associatedBundles != null) {
         Iterator var1 = associatedBundles.iterator();

         while(var1.hasNext()) {
            OSGiBundle bundle = (OSGiBundle)var1.next();
            bundle.uninstall();
         }

         associatedBundles.clear();
      }
   }

   public static String getCommaDelimitedList(String original, String override, String[] addons, boolean allowWildcard) {
      String initialString = override != null ? override : original;
      if (initialString == null && addons == null) {
         return null;
      } else {
         if (initialString == null) {
            initialString = "";
         } else {
            initialString = initialString.trim();
         }

         if (allowWildcard && "*".equals(initialString)) {
            return "*";
         } else {
            StringBuffer retVal = new StringBuffer();
            StringTokenizer st = new StringTokenizer(initialString, ",");
            boolean first = true;

            while(st.hasMoreElements()) {
               String token = st.nextToken();
               if (first) {
                  first = false;
                  retVal.append(token);
               } else {
                  retVal.append(",");
                  retVal.append(token);
               }
            }

            if (addons != null) {
               String[] var12 = addons;
               int var9 = addons.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  String addon = var12[var10];
                  if (first) {
                     first = false;
                     retVal.append(addon);
                  } else {
                     retVal.append(",");
                     retVal.append(addon);
                  }
               }
            }

            return retVal.toString();
         }
      }
   }

   public static String osgiStateToString(int state) {
      switch (state) {
         case 2:
            return "INSTALLED";
         case 4:
            return "RESOLVED";
         case 8:
            return "STARTING";
         case 16:
            return "STOPPING";
         case 32:
            return "ACTIVE";
         default:
            return "UNKNOWN(" + state + ")";
      }
   }

   public static Context getAppCtx(ApplicationContextInternal aci) throws NamingException {
      Context root = aci.getRootContext();
      return (Context)root.lookup("app");
   }

   public static Bundle getBestBundle(Bundle[] bundles, String bsn, String versionRange) {
      OSGiVersionRange range;
      if (versionRange != null && !"".equals(versionRange.trim())) {
         range = new OSGiVersionRange(versionRange);
      } else {
         range = ALL_RANGE;
      }

      Bundle bestSoFar = null;
      OSGiVersion highestVersionSoFar = null;
      Bundle[] var6 = bundles;
      int var7 = bundles.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Bundle bundle = var6[var8];
         String bundleBSN = bundle.getSymbolicName();
         if (bundleBSN != null && bundleBSN.equals(bsn)) {
            Version osgiVersion = bundle.getVersion();
            OSGiVersion utilVersion;
            if (osgiVersion != null) {
               utilVersion = new OSGiVersion(osgiVersion.getMajor(), osgiVersion.getMinor(), osgiVersion.getMicro(), osgiVersion.getQualifier());
            } else {
               utilVersion = DEFAULT_BUNDLE_VERSION;
            }

            if (range.isVersionInRange(utilVersion)) {
               if (bestSoFar == null) {
                  bestSoFar = bundle;
                  highestVersionSoFar = utilVersion;
               } else if (utilVersion.compareTo(highestVersionSoFar) > 0) {
                  bestSoFar = bundle;
                  highestVersionSoFar = utilVersion;
               }
            }
         }
      }

      return bestSoFar;
   }

   public static ComponentInvocationContext getCurrentCIC() {
      ComponentInvocationContextManager ctxMgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext ctxCurrent = ctxMgr.getCurrentComponentInvocationContext();
      return ctxCurrent;
   }
}

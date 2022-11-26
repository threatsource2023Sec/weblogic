package weblogic;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.utils.StringUtils;

public class WLST {
   private static final String ALT_TYPES_DIR_PROP = "weblogic.alternateTypesDirectory";
   private static final String ALT_TYPES_DIR_SEP = ",";
   private static final String ORCL_TYPE_BASE_DIR;
   private static final String OAM_TYPE_DIR;
   private static final String OPSS_TYPE_DIR;

   public static void main(String[] args) {
      removeOracleSecurityProviderTypes();
      ClassLoader descriptorClassLoader = DescriptorClassLoader.getClassLoader();
      Thread.currentThread().setContextClassLoader(descriptorClassLoader);
      String debugFlag = System.getProperty("wlst.debug.init", "false");
      boolean debug = false;
      if (debugFlag.equals("true")) {
         debug = true;
      }

      try {
         Class wlstMainClass = descriptorClassLoader.loadClass("weblogic.management.scripting.WLST");
         Method runMethod = wlstMainClass.getMethod("main", String[].class);
         runMethod.invoke(wlstMainClass, args);
      } catch (InvocationTargetException var6) {
         Throwable th = var6.getTargetException();
         if (th != null) {
            System.err.println("Problem invoking WLST - " + th);
            if (debug) {
               th.printStackTrace();
            }
         } else {
            System.err.println("Problem invoking WLST - " + var6);
            if (debug) {
               var6.printStackTrace();
            }
         }

         System.exit(1);
      } catch (Throwable var7) {
         System.err.println("Problem invoking WLST - " + var7);
         if (debug) {
            var7.printStackTrace();
         }

         System.exit(1);
      }

   }

   private static final void removeOracleSecurityProviderTypes() {
      String altTypesDir = System.getProperty("weblogic.alternateTypesDirectory");
      if (altTypesDir != null && altTypesDir.length() > 0) {
         String[] dirs = StringUtils.splitCompletely(altTypesDir, ",");
         ArrayList newDirs = new ArrayList();
         String[] var3 = dirs;
         int var4 = dirs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String dir = var3[var5];
            if (!dir.endsWith(OPSS_TYPE_DIR) && !dir.endsWith(OAM_TYPE_DIR)) {
               newDirs.add(dir);
            }
         }

         if (newDirs.size() > 0) {
            altTypesDir = "";

            String dir;
            for(Iterator var7 = newDirs.iterator(); var7.hasNext(); altTypesDir = altTypesDir + dir) {
               dir = (String)var7.next();
               if (altTypesDir.length() > 0) {
                  altTypesDir = altTypesDir + ",";
               }
            }

            System.setProperty("weblogic.alternateTypesDirectory", altTypesDir);
         } else {
            System.clearProperty("weblogic.alternateTypesDirectory");
         }
      }

   }

   static {
      ORCL_TYPE_BASE_DIR = "oracle_common" + File.separator + "modules";
      OAM_TYPE_DIR = ORCL_TYPE_BASE_DIR + File.separator + "oracle.oamprovider";
      OPSS_TYPE_DIR = ORCL_TYPE_BASE_DIR + File.separator + "oracle.jps";
   }
}

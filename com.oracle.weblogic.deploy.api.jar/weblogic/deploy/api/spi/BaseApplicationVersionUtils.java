package weblogic.deploy.api.spi;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import weblogic.utils.jars.VirtualJarFile;

public class BaseApplicationVersionUtils {
   protected static final String VER_DELIMITER = "#";
   protected static final String PTN_DELIMITER = "$";
   private static final String LIB_NAME_MANIFEST_ATTR_NAME = "Extension-Name";

   public static String getArchiveVersion(String versionId) {
      return getFirstToken(versionId, "#");
   }

   public static String getPlanVersion(String versionId) {
      return getSecondToken(versionId, "#");
   }

   public static String getVersionId(String archiveVersion, String planVersion) {
      if (archiveVersion != null && archiveVersion.length() != 0) {
         return planVersion != null && planVersion.length() != 0 ? archiveVersion + "#" + planVersion : archiveVersion;
      } else {
         return null;
      }
   }

   public static String getLibName(VirtualJarFile libJar) {
      if (libJar == null) {
         return null;
      } else {
         Manifest manifest = null;

         try {
            manifest = libJar.getManifest();
         } catch (IOException var5) {
            return null;
         }

         if (manifest != null) {
            Attributes mainAttrs = manifest.getMainAttributes();
            if (mainAttrs != null) {
               try {
                  String s = mainAttrs.getValue("Extension-Name");
                  if (s != null) {
                     s = s.trim();
                  }

                  return s;
               } catch (IllegalArgumentException var4) {
                  return null;
               }
            }
         }

         return null;
      }
   }

   protected static String getFirstToken(String str, String delimiter) {
      if (str != null && str.length() != 0) {
         int index = str.indexOf(delimiter);
         if (index == -1) {
            return str;
         } else {
            String rtnStr = str.substring(0, index);
            return rtnStr.length() == 0 ? null : rtnStr;
         }
      } else {
         return null;
      }
   }

   protected static String getSecondToken(String str, String delimiter) {
      if (str != null && str.length() != 0) {
         int index = str.indexOf(delimiter);
         if (index == -1) {
            return null;
         } else {
            String rtnStr = str.substring(index + 1);
            return rtnStr.length() == 0 ? null : rtnStr;
         }
      } else {
         return null;
      }
   }

   protected static String trimLastToken(String str, String delimiter) {
      if (str != null && str.length() != 0) {
         int index = str.lastIndexOf(delimiter);
         if (index == -1) {
            return str;
         } else {
            String rtnStr = str.substring(0, index);
            return rtnStr.length() == 0 ? null : rtnStr;
         }
      } else {
         return null;
      }
   }
}

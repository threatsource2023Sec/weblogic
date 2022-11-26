package weblogic.management.utils;

import java.io.File;

public class ApplicationNameUtils {
   public static String computeApplicationName(File descriptorFile) {
      String archiveName = getArchiveNameWithoutSuffix(descriptorFile);
      return archiveName != null ? archiveName : descriptorFile.getName();
   }

   public static String getArchiveNameWithoutSuffix(File file) {
      if (file == null) {
         return null;
      } else {
         String baseName = file.getName();
         String[] parts = baseName.split("\\.");
         return parts.length < 2 || parts[parts.length - 1].length() != 3 || !baseName.endsWith("ar") && !"zip".equalsIgnoreCase(parts[parts.length - 1]) ? null : baseName.substring(0, baseName.length() - 4);
      }
   }
}

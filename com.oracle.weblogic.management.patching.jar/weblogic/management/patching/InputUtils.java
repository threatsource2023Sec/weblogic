package weblogic.management.patching;

import java.util.regex.Pattern;
import weblogic.management.patching.commands.PatchingLogger;

public class InputUtils {
   public static final String BAD_WINDOWS_PATTERN = "^[a-zA-Z]/";

   public static String sanitizePath(String inputPath) {
      String cleanPath = inputPath;
      if (inputPath != null && inputPath.length() > 0) {
         cleanPath = inputPath.trim();
         if (Pattern.matches("^[a-zA-Z]/", cleanPath)) {
            inputPath = inputPath.replaceAll("/", "\\");
         }
      }

      if (inputPath != null && !inputPath.equals(cleanPath)) {
         PatchingLogger.logPathInputTranslated(inputPath, cleanPath);
      }

      return cleanPath;
   }
}

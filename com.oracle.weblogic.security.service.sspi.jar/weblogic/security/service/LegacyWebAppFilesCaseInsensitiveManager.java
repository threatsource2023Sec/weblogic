package weblogic.security.service;

public class LegacyWebAppFilesCaseInsensitiveManager {
   private static boolean areWebAppFilesCaseInsensitive = false;

   private LegacyWebAppFilesCaseInsensitiveManager() {
   }

   public static void setWebAppFilesCaseInsensitive(boolean areWebAppFilesCaseInsensitive) {
      LegacyWebAppFilesCaseInsensitiveManager.areWebAppFilesCaseInsensitive = areWebAppFilesCaseInsensitive;
   }

   public static boolean getWebAppFilesCaseInsensitive() {
      return areWebAppFilesCaseInsensitive;
   }
}

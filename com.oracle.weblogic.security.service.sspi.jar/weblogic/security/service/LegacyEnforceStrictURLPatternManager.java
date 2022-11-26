package weblogic.security.service;

public class LegacyEnforceStrictURLPatternManager {
   private static boolean enforceStrictURLPattern = true;

   private LegacyEnforceStrictURLPatternManager() {
   }

   public static void setEnforceStrictURLPattern(boolean enforceStrictURLPattern) {
      LegacyEnforceStrictURLPatternManager.enforceStrictURLPattern = enforceStrictURLPattern;
   }

   public static boolean getEnforceStrictURLPattern() {
      return enforceStrictURLPattern;
   }
}

package weblogic.diagnostics.context;

import java.util.logging.Level;

public final class DiagnosticContextHelper implements DiagnosticContextConstants {
   public static String getContextId() {
      return CorrelationHelper.getContextId();
   }

   /** @deprecated */
   @Deprecated
   public static void setDye(byte dye, boolean enable) throws InvalidDyeException {
      CorrelationHelper.setApplicationDye(dye, enable);
   }

   public static void setApplicationDye(byte dye, boolean enable) throws InvalidDyeException {
      CorrelationHelper.setApplicationDye(dye, enable);
   }

   public static boolean isDyedWith(byte dye) throws InvalidDyeException {
      return CorrelationHelper.isDyedWith(dye);
   }

   /** @deprecated */
   @Deprecated
   public static String getPayload() {
      return CorrelationHelper.getPayload();
   }

   /** @deprecated */
   @Deprecated
   public static void setPayload(String payload) {
      CorrelationHelper.setPayload(payload);
   }

   public static long parseDyeMask(String mask) {
      return CorrelationHelper.parseDyeMask(mask);
   }

   public static long parseDyeMask(String[] maskNames) {
      return CorrelationHelper.parseDyeMask(maskNames);
   }

   public static String[] getDyeFlagNames() {
      return CorrelationHelper.getDyeFlagNames();
   }

   /** @deprecated */
   @Deprecated
   public static void validateDyeFlagNames(String[] maskNames) {
      CorrelationHelper.validateDyeFlagNames(maskNames);
   }

   /** @deprecated */
   @Deprecated
   public static void registerDye(String dyeName, int index) throws InvalidDyeException {
      CorrelationHelper.registerDye(dyeName, index);
   }

   public static String getRID() {
      return CorrelationHelper.getRID();
   }

   public static int getLogLevel() {
      Level level = CorrelationHelper.getLogLevel();
      return level == null ? -1 : level.intValue();
   }

   public static void setLogLevel(int level) {
      if (level != -1) {
         try {
            CorrelationHelper.setLogLevel(Level.parse(Integer.toString(level)));
            return;
         } catch (IllegalArgumentException var2) {
         }
      }

      CorrelationHelper.setLogLevel((Level)null);
   }

   public static long getDyeVector() {
      return CorrelationHelper.getDyeVector();
   }

   public static void handleLocalContextAsNonInheritable() {
      CorrelationHelper.handleLocalContextAsNonInheritable();
   }
}

package weblogic.servlet.internal;

class HSTSHeader {
   public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
   private static final int DEFAULT_MAX_AGE = 31536000;
   private static boolean isHSTSEnabled = false;
   private static String hstsValue;

   private static String buildHSTSValue() {
      Integer configedMaxAge = Integer.getInteger("weblogic.http.headers.hsts.maxage", 31536000);
      boolean includeSubDomains = "true".equalsIgnoreCase(System.getProperty("weblogic.http.headers.hsts.includesubdomains", "true"));
      boolean preload = "true".equalsIgnoreCase(System.getProperty("weblogic.http.headers.hsts.preload", "true"));
      StringBuilder valueBuilder = new StringBuilder();
      valueBuilder.append("max-age=").append(configedMaxAge);
      if (includeSubDomains) {
         valueBuilder.append("; ").append("includeSubDomains");
      }

      if (preload) {
         valueBuilder.append("; ").append("preload");
      }

      return valueBuilder.toString();
   }

   static boolean isHSTSEnabled() {
      return isHSTSEnabled;
   }

   static String getHSTSValue() {
      return hstsValue;
   }

   static {
      isHSTSEnabled = Boolean.getBoolean("weblogic.http.headers.enableHSTS");
      if (isHSTSEnabled) {
         hstsValue = buildHSTSValue();
      }

   }
}

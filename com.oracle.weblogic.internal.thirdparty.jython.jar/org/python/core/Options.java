package org.python.core;

public class Options {
   public static boolean showJavaExceptions = false;
   public static boolean includeJavaStackInExceptions = true;
   public static boolean showPythonProxyExceptions = false;
   public static boolean respectJavaAccessibility = true;
   public static boolean importSite = true;
   public static int verbose = 1;
   public static String proxyDebugDirectory;
   public static boolean caseok = false;
   public static boolean Qnew = false;
   public static boolean unbuffered = false;
   public static boolean py3k_warning = false;
   public static boolean dont_write_bytecode = false;
   public static boolean ignore_environment = false;
   public static boolean no_user_site = false;
   public static boolean no_site = false;
   public static int bytes_warning = 0;
   public static int optimize = 0;
   public static int division_warning = 0;
   public static final String sreCacheSpecDefault = "weakKeys,concurrencyLevel=4,maximumWeight=2621440,expireAfterAccess=30s";
   public static String sreCacheSpec = "weakKeys,concurrencyLevel=4,maximumWeight=2621440,expireAfterAccess=30s";

   private Options() {
   }

   private static boolean getBooleanOption(String name, boolean defaultValue) {
      String prop = PySystemState.registry.getProperty("python." + name);
      if (prop == null) {
         return defaultValue;
      } else {
         return prop.equalsIgnoreCase("true") || prop.equalsIgnoreCase("yes");
      }
   }

   private static String getStringOption(String name, String defaultValue) {
      String prop = PySystemState.registry.getProperty("python." + name);
      return prop == null ? defaultValue : prop;
   }

   public static void setFromRegistry() {
      showJavaExceptions = getBooleanOption("options.showJavaExceptions", showJavaExceptions);
      includeJavaStackInExceptions = getBooleanOption("options.includeJavaStackInExceptions", includeJavaStackInExceptions);
      showPythonProxyExceptions = getBooleanOption("options.showPythonProxyExceptions", showPythonProxyExceptions);
      respectJavaAccessibility = getBooleanOption("security.respectJavaAccessibility", respectJavaAccessibility);
      proxyDebugDirectory = getStringOption("options.proxyDebugDirectory", proxyDebugDirectory);
      String prop = PySystemState.registry.getProperty("python.verbose");
      if (prop != null) {
         if (prop.equalsIgnoreCase("error")) {
            verbose = -1;
         } else if (prop.equalsIgnoreCase("warning")) {
            verbose = 0;
         } else if (prop.equalsIgnoreCase("message")) {
            verbose = 1;
         } else if (prop.equalsIgnoreCase("comment")) {
            verbose = 2;
         } else {
            if (!prop.equalsIgnoreCase("debug")) {
               throw Py.ValueError("Illegal verbose option setting: '" + prop + "'");
            }

            verbose = 3;
         }
      }

      caseok = getBooleanOption("options.caseok", caseok);
      Qnew = getBooleanOption("options.Qnew", Qnew);
      prop = PySystemState.registry.getProperty("python.division_warning");
      if (prop != null) {
         if (prop.equalsIgnoreCase("old")) {
            division_warning = 0;
         } else if (prop.equalsIgnoreCase("warn")) {
            division_warning = 1;
         } else {
            if (!prop.equalsIgnoreCase("warnall")) {
               throw Py.ValueError("Illegal division_warning option setting: '" + prop + "'");
            }

            division_warning = 2;
         }
      }

      sreCacheSpec = getStringOption("sre.cachespec", sreCacheSpec);
      importSite = getBooleanOption("import.site", importSite);
   }
}

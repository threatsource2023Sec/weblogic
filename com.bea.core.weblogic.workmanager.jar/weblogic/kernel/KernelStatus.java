package weblogic.kernel;

import java.net.InetSocketAddress;

public class KernelStatus {
   public static final String DIRECT_DISPATCH = "direct";
   public static final String DEFAULT_DISPATCH_ALIAS = "default";
   public static final String DEFAULT_DISPATCH = "weblogic.kernel.Default";
   public static final String NON_BLOCKING_DISPATCH = "weblogic.kernel.Non-Blocking";
   public static final String SYSTEM_DISPATCH = "weblogic.kernel.System";
   private static boolean intentionalShutdown = false;
   private static boolean isServer = false;
   private static boolean isJ2eeClient = false;
   private static Boolean isEmbedded = false;
   private static boolean isDeployer = false;
   private static boolean isthinIIOPClient = false;
   private static boolean isApplet = false;
   private static boolean isInitialized = false;
   private static boolean isIsServerSet = false;
   private static boolean isConfigured = false;
   private static final String DEBUG_PROP = "weblogic.kernel.debug";
   public static final boolean DEBUG = initDebug();
   private static String tunnelURLPrefix = initTunnelURLPrefix();
   private static String buzzAddress;
   private static InetSocketAddress buzzSocketAddress;

   private static final boolean initDebug() {
      boolean debug = false;

      try {
         debug = System.getProperty("weblogic.kernel.debug") != null;
         if (!"true".equals(System.getProperty("java.class.version.applet")) && !"true".equals(System.getProperty("java.vendor.applet")) && !"true".equals(System.getProperty("java.version.applet"))) {
            isApplet = Boolean.getBoolean("weblogic.j2ee.client.isWebStart");
            return debug;
         } else {
            isApplet = true;
            return false;
         }
      } catch (SecurityException var2) {
         isApplet = true;
         return false;
      }
   }

   private static String initTunnelURLPrefix() {
      String prefix = null;
      if (!isApplet()) {
         prefix = System.getProperty("weblogic.tunnel.prefix");
         if (prefix != null && "".equals(prefix)) {
            prefix = null;
         }

         if (prefix != null && prefix.charAt(0) != '/') {
            prefix = '/' + prefix;
         }
      }

      return prefix;
   }

   public static boolean isApplet() {
      return isApplet;
   }

   public static void setIsServer(boolean value) {
      if (isIsServerSet) {
         try {
            Class.forName("com.bea.core.bootbundle.BootBundleLogger");
         } catch (Exception var2) {
            throw new IllegalStateException("Cannot change <isServer>");
         }
      }

      isIsServerSet = true;
      isServer = value;
   }

   public static boolean isServer() {
      return isServer;
   }

   public static void setIsEmbedded(boolean value) {
      isEmbedded = value;
   }

   public static boolean isEmbedded() {
      return isEmbedded;
   }

   public static void setIsJ2eeClient(boolean value) {
      isJ2eeClient = value;
   }

   public static boolean isJ2eeClient() {
      return isJ2eeClient;
   }

   public static void setIsDeployer(boolean value) {
      isDeployer = value;
   }

   public static boolean isDeployer() {
      return isDeployer;
   }

   public static void setIsThinIIOPClient(boolean value) {
      if (isIsServerSet) {
         try {
            Class.forName("com.bea.core.bootbundle.BootBundleLogger");
         } catch (Exception var2) {
            throw new IllegalStateException("Cannot change <isServer>");
         }
      }

      isthinIIOPClient = value;
   }

   public static boolean isThinIIOPClient() {
      return isthinIIOPClient;
   }

   public static void setIsConfigured() {
      isConfigured = true;
   }

   public static boolean isConfigured() {
      return !isServer || isConfigured;
   }

   public static void initialized() {
      isInitialized = true;
   }

   public static boolean isInitialized() {
      return isInitialized;
   }

   public static String getTunellingURLPrefix() {
      return tunnelURLPrefix;
   }

   public static String getTunellingURL(String url) {
      return tunnelURLPrefix == null ? url : tunnelURLPrefix + url;
   }

   public static void shutdown() {
      Class var0 = KernelStatus.class;
      synchronized(KernelStatus.class) {
         intentionalShutdown = true;
      }
   }

   public static boolean isIntentionalShutdown() {
      return intentionalShutdown;
   }

   public static void setBuzzAddress(String address) {
      buzzAddress = address;
   }

   public static String getBuzzAddress() {
      return buzzAddress;
   }

   public static void setBuzzSocketAddress(InetSocketAddress isa) {
      buzzSocketAddress = isa;
   }

   public static InetSocketAddress getBuzzSocketAddress() {
      return buzzSocketAddress;
   }
}

package weblogic.security.internal;

import java.util.ArrayList;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.spi.deploy.internal.InternalApp;
import weblogic.deploy.api.spi.deploy.internal.InternalAppFactory;
import weblogic.security.shared.LoggerWrapper;

@Service
public class SecurityInternalAppFactory implements InternalAppFactory, SAMLServerConfigConstants {
   public static final String V2_SUFFIX = "_v2";
   public static final String WAR_FILE_EXT = ".war";
   public static final boolean DISABLE_WAR_COPY = Boolean.getBoolean("weblogic.security.disableSAMLWarCopy");
   private static final String[] SAML_APP_NAMES = new String[]{"samlits_ba", "samlits_cc", "samlacs", "samlars"};
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecuritySAMLService");

   public List createInternalApps() {
      List internalApps = new ArrayList();
      String[] var2 = SAML_APP_NAMES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String app = var2[var4];
         if (isApplicationConfigured(app)) {
            InternalApp iapp = new InternalApp(app, ".war", false, false);
            String source = getApplicationSourceFileName(app);
            if (source != null) {
               iapp.setSourceFileName(source);
            }

            internalApps.add(iapp);
         }
      }

      return internalApps;
   }

   public static String getApplicationSourceFileName(String appName) {
      String fileName = null;
      if (DISABLE_WAR_COPY) {
         logDebug("getApplicationSourceFileName", "copy of SAML war files disabled");
      } else {
         fileName = appName + "_v2" + ".war";
         logDebug("getApplicationSourceFileName", "Use SAML war file '" + fileName + "'");
      }

      return fileName;
   }

   public static boolean isApplicationConfigured(String appName) {
      SAMLServerConfig config = SAMLServerConfig.getConfig();
      boolean shouldDeploy = false;
      if (!appName.equals("samlits_ba") && !appName.equals("samlits_cc")) {
         if (appName.equals("samlacs")) {
            if (isAppContextConfigured(appName, config.getAssertionConsumerURIs())) {
               shouldDeploy = true;
            }
         } else {
            if (!appName.equals("samlars")) {
               logDebug("isApplicationConfigured", "Unknown application '" + appName + "' will not be deployed");
               return false;
            }

            if (isAppContextConfigured(appName, config.getAssertionRetrievalURIs())) {
               shouldDeploy = true;
            }
         }
      } else if (isAppContextConfigured(appName, config.getIntersiteTransferURIs())) {
         shouldDeploy = true;
      }

      if (shouldDeploy) {
         logDebug("isApplicationConfigured", "Application '" + appName + "' will be deployed");
      } else {
         logDebug("isApplicationConfigured", "Application '" + appName + "' will not be deployed");
      }

      return shouldDeploy;
   }

   private static boolean isAppContextConfigured(String appName, String[] configuredURIs) {
      String appContext = "/" + appName + "/";

      for(int i = 0; configuredURIs != null && i < configuredURIs.length; ++i) {
         if (configuredURIs[i] != null && configuredURIs[i].startsWith(appContext)) {
            return true;
         }
      }

      return false;
   }

   private static void logDebug(String method, String msg) {
      logDebug("SAMLServerConfig", method, msg);
   }

   protected static void logDebug(String className, String method, String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(className + "." + method + "(): " + msg);
      }

   }
}

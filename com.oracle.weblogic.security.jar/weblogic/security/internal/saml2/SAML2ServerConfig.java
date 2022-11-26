package weblogic.security.internal.saml2;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.management.configuration.SingleSignOnServicesMBean;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.shared.LoggerWrapper;
import weblogic.utils.LocatorUtilities;

public class SAML2ServerConfig {
   public static final String SAML2_APP = "saml2";
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecuritySAML2Service");

   protected static void logDebug(String className, String method, String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(className + "." + method + "(): " + msg);
      }

   }

   private static void logDebug(String method, String msg) {
      logDebug("SAML2ServerConfig", method, msg);
   }

   public static boolean isApplicationConfigured(String appName) {
      if (!appName.equals("saml2")) {
         logDebug("isApplicationConfigured", "Unknown app '" + appName + "', return false");
         return false;
      } else {
         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });
         SingleSignOnServicesMBean ssoMBean = runtimeAccess.getServer().getSingleSignOnServices();
         if (ssoMBean == null) {
            logDebug("isApplicationConfigured", "SingleSignOnServicesMBean not found, return false");
            return false;
         } else if (!ssoMBean.isServiceProviderEnabled() && !ssoMBean.isIdentityProviderEnabled()) {
            logDebug("isApplicationConfigured", "SingleSignOnMBean neither IdP nor SP is enabled, return false");
            return false;
         } else {
            logDebug("isApplicationConfigured", "SingleSignOnMBean IdP or SP is enabled, return true");
            return true;
         }
      }
   }

   public static String getApplicationHostName(String appName) {
      String result = null;
      if (appName != null && appName.equals("saml2")) {
         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });

         try {
            SingleSignOnServicesMBean ssoMBean = runtimeAccess.getServer().getSingleSignOnServices();
            result = (new URL(ssoMBean.getPublishedSiteURL())).getHost();
            logDebug("getApplicationHostName", "Hostname of Published Site URL: " + result);
         } catch (Exception var4) {
            logDebug("getApplicationHostName", "Exception getting hostname: " + var4.toString());
         }
      }

      return result;
   }
}
